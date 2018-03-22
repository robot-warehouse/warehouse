package rp.assignments.team.warehouse.server.job.selection;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rp.assignments.team.warehouse.server.job.Item;
import rp.assignments.team.warehouse.server.job.Job;
import rp.assignments.team.warehouse.server.job.JobItem;
import rp.assignments.team.warehouse.server.job.input.Importer;

public class CancellationClassifier {
    private AbstractClassifier classifier;

    private static final Logger logger = LogManager.getLogger(CancellationClassifier.class);
    
    private static final int SPLIT_NUMBER = 1;
    private static final float TRAINING_RATIO = 0.8f;
    private static final String TRAINING_FILE = "./training.arff";
    private static final String TESTING_FILE = "./testing.arff";
    private static final String CLASSIFYING_FILE = "./classifying.arff";

    private String itemIdsString;
    private String[] itemIdsArray;
    private Map<Integer, Job> jobs;
    private List<Item> items;
    private List<Job> allData;

    public CancellationClassifier(Importer importer) {
        assert importer.isDoneParsing();

        this.jobs = importer.getJobsMap();
        this.items = new ArrayList<Item>(importer.getItems());
        this.allData = new ArrayList<Job>(importer.getTrainingJobs());
        this.itemIdsArray = this.items.stream().map(i -> Item.numericIdToString(i.getId())).toArray(String[]::new);
        this.itemIdsString = "";
        for (String id : this.itemIdsArray) {
            this.itemIdsString += "," + id;
        }
        
        this.classifier = new NaiveBayes();
    }

    private static void writeToDataSource(List<Job> jobs, List<String> itemIds, String itemIdsString, File outFile) {
        logger.info("Writing jobs to data source file");
        try (BufferedWriter out = new BufferedWriter(new FileWriter(outFile))) {
            out.write("% 1. Title: Job Training Data\n\n");
            out.write("@RELATION jobs\n\n");
            out.write("@ATTRIBUTE id NUMERIC\n");
            out.write("@ATTRIBUTE reward NUMERIC\n");
            out.write("@ATTRIBUTE weight NUMERIC\n");
            for (String id : itemIds) {
                out.write("@ATTRIBUTE " + id + " NUMERIC\n");
            }
            out.write("@ATTRIBUTE class {0,1}\n");
            out.write("\n@DATA\n");
            // CSV
            // out.write("ID,Cancelled,Reward,Weight" + itemIdsString+"\n");

            for (Job j : jobs) {
                String jobString = "" + j.getId() + "," + j.getReward() + ","
                        + j.getWeight();
                List<JobItem> jobItems = j.getJobItems();
                Map<String, Integer> itemsMap = new HashMap<String, Integer>();
                jobItems.forEach(x -> itemsMap.put(x.getItem().getIdString(), x.getCount()));

                for (String id : itemIds) {
                    if (itemsMap.containsKey(id)) {
                        jobString += "," + itemsMap.get(id);
                    } else {
                        jobString += ",0";
                    }
                }

                jobString += "," + (j.isPreviouslyCancelled() ? "1" : "0");

                out.write(jobString+"\n");
            }
        } catch (IOException e) {
            logger.fatal(e.getMessage());
        }

    }

    private Instances getData(String filePath) throws Exception {
        logger.info("Importing data from source file.");
        DataSource source = new DataSource(filePath);
        Instances data = source.getDataSet();
        // setting class attribute if the data format does not provide this information
        // For example, the XRFF format saves the class attribute information as well
        if (data.classIndex() == -1)
            data.setClassIndex(data.numAttributes() - 1);

        return data;
    }

    public void train() throws Exception {
        logger.info("Training");
        
        /*String[] options = new String[1];
        options[0] = "-U"; // unpruned tree
        classifier = new J48(); // new instance of tree
        classifier.setOptions(options); // set the options
        classifier.buildClassifier(newData); // build classifier
*/

        for (int split = 0; split < SPLIT_NUMBER; split++) {
            Collections.shuffle(this.allData);
            int splitIndex = (int) Math.floor(TRAINING_RATIO * this.allData.size());
            List<Job> trainData = new ArrayList<Job>(this.allData.subList(0, splitIndex));
            List<Job> testData = new ArrayList<Job>(this.allData.subList(splitIndex, allData.size()));

            logger.trace("Perform split into training and test.");
            writeToDataSource(trainData, Arrays.asList(this.itemIdsArray), this.itemIdsString, new File(TRAINING_FILE));
            writeToDataSource(testData, Arrays.asList(this.itemIdsArray), this.itemIdsString, new File(TESTING_FILE));
    
            Instances training = getData(TRAINING_FILE);
            Instances testing = getData(TESTING_FILE);
            
            logger.info("Evaluate training model");
            classifier.buildClassifier(training);
            Evaluation eval = new Evaluation(training);
            eval.crossValidateModel(classifier, testing, 10, new Random(1));
            logger.trace("Cross validation result: {}", eval.toSummaryString());
        }
    }

    public void classify() throws Exception {
        logger.info("Classifying");
        writeToDataSource(new ArrayList<>(this.jobs.values()), Arrays.asList(this.itemIdsArray), this.itemIdsString, new File(CLASSIFYING_FILE));
        // load unlabeled data
        Instances unlabeled = getData(CLASSIFYING_FILE);

        // set class attribute
        unlabeled.setClassIndex(unlabeled.numAttributes() - 1);

        // label instances
        logger.info("Starting to classify");
        for (int i = 0; i < unlabeled.numInstances(); i++) {
            Instance inst = unlabeled.instance(i);
            double clsLabel = classifier.classifyInstance(inst);
            int jobId = (int) (inst.value(0));
            if (this.jobs.containsKey(jobId)) {
                if (clsLabel == 1d) {
                    logger.trace("job {} given label {}", jobId, clsLabel);
                    this.jobs.get(jobId).setPredictedCancelled();
                }
            } else {
                logger.error("Came across job ID {} which was not in the map", jobId);
            }
        }
        logger.info("Finished classification");

    }
}
