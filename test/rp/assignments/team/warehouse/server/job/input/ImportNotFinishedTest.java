package rp.assignments.team.warehouse.server.job.input;

import java.io.File;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import rp.assignments.team.warehouse.server.Location;
import rp.assignments.team.warehouse.server.job.Item;
import rp.assignments.team.warehouse.server.job.Job;
import rp.assignments.team.warehouse.server.job.JobItem;

public class ImportNotFinishedTest {

    public static Importer importer;

    // Drops
    public static Location drop0 = new Location(0, 0);
    public static Location drop1 = new Location(7, 7);

    public static Item aa = new Item("aa", 13.8f, 6.1f, new Location(2, 4));
    public static Item ab = new Item("ab", 20.2f, 10.0f, new Location(1, 3));
    public static Item ac = new Item("ac", 12.7f, 1.0f, new Location(0, 7));

    public static Job j1000 = new Job(1000, Arrays.asList(new JobItem(aa, 4), new JobItem(ac, 2)));
    public static Job j1001 = new Job(1001, Arrays.asList(new JobItem(ac, 1)));

    static {
        j1001.setPreviouslyCancelled();
    }

    @BeforeClass
    public static void setUpBeforeClass() {
        String datasetPath = ImporterInputPath.INPUT_PATH + "/valid/";
        File jobsFile = new File(datasetPath + "/jobs.csv");
        File cancellationsFile = new File(datasetPath + "/cancellations.csv");
        File trainingFile = new File(datasetPath + "/training.csv");
        File locationsFile = new File(datasetPath + "/locations.csv");
        File itemsFile = new File(datasetPath + "/items.csv");
        File dropsFile = new File(datasetPath + "/drops.csv");

        importer = new Importer(jobsFile, cancellationsFile, trainingFile, locationsFile, itemsFile, dropsFile);
    }
    
    @Test
    public void isDoneParsingShouldBeFalse() {
        Assert.assertFalse(importer.isDoneParsing());
    }

    @Test(expected=ImportNotFinishedException.class)
    public void cannotGetJobsBeforeImport() {
        importer.getJobs();
    }

    @Test(expected=ImportNotFinishedException.class)
    public void cannotGetJobsMapBeforeImport() {
        importer.getJobsMap();
    }

    @Test(expected=ImportNotFinishedException.class)
    public void cannotGetTrainingJobsBeforeImport() {
        importer.getTrainingJobs();
    }

    @Test(expected=ImportNotFinishedException.class)
    public void cannotGetItemsBeforeImport() {
        importer.getItems();
    }

    @Test(expected=ImportNotFinishedException.class)
    public void cannotGetDropsBeforeImport() {
        importer.getDrops();
    }
    
}
