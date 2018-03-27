package rp.assignments.team.warehouse.server.job.input;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import rp.assignments.team.warehouse.server.Location;
import rp.assignments.team.warehouse.server.job.Item;
import rp.assignments.team.warehouse.server.job.Job;
import rp.assignments.team.warehouse.server.job.JobItem;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ImportValidDatasetTest {

    public static Importer importer;

    // Drops
    public static Location drop0 = new Location(0, 0);
    public static Location drop1 = new Location(7, 7);

    public static Item aa = new Item("aa", 13.8f, 6.1f, new Location(2, 4));
    public static Item ab = new Item("ab", 20.2f, 10.0f, new Location(1, 3));
    public static Item ac = new Item("ac", 12.7f, 1.0f, new Location(0, 7));

    public static Job j1000 = new Job(1000, Arrays.asList(new JobItem(aa, 4), new JobItem(ac, 2)));
    public static Job j1001 = new Job(1001, Arrays.asList(new JobItem(ac, 1)));

    public static Job j101 = new Job(101, Arrays.asList(new JobItem(aa, 1), new JobItem(ab, 3), new JobItem(ac, 2)));
    public static Job j102 = new Job(102, Arrays.asList(new JobItem(aa, 4)));
    public static Job j103 = new Job(103, Arrays.asList(new JobItem(aa, 3), new JobItem(ab, 3)));
    public static Job j104 = new Job(104, Arrays.asList(new JobItem(aa, 2), new JobItem(ac, 2)));
    public static Job j105 = new Job(105, Arrays.asList(new JobItem(ab, 1), new JobItem(aa, 8)));
    public static Job j106 = new Job(106, Arrays.asList(new JobItem(ac, 3), new JobItem(aa, 10)));
    public static Job j201 = new Job(201, Arrays.asList(new JobItem(ac, 3)));
    public static Job j202 = new Job(202, Arrays.asList(new JobItem(ab, 2), new JobItem(ac, 8)));
    public static Job j203 = new Job(203, Arrays.asList(new JobItem(ac, 6), new JobItem(ab, 4)));
    public static Job j204 = new Job(204, Arrays.asList(new JobItem(ab, 1)));
    public static Job j205 = new Job(205, Arrays.asList(new JobItem(ac, 2)));
    public static Job j206 = new Job(206, Arrays.asList(new JobItem(ab, 10), new JobItem(ac, 1)));

    static {
        j1001.setCancelled();
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
    public void canImportValidDataset() {
        Assert.assertTrue(importer.parse());
    }

    @Test
    public void isDoneParsingShouldBeTrue() {
        Assert.assertTrue(importer.isDoneParsing());
    }

    @Test
    public void importsCorrectDropLocations() {
        Set<Location> drops = importer.getDrops();

        Assert.assertEquals(drops.size(), 2);
        Assert.assertThat(drops, hasItems(sameBeanAs(drop0), sameBeanAs(drop1)));
    }

    @Test
    public void importsCorrectItems() {
        Set<Item> items = importer.getItems();

        Assert.assertEquals(items.size(), 3);
        Assert.assertThat(items, hasItems(sameBeanAs(aa), sameBeanAs(ab), sameBeanAs(ac)));
    }

    @Test
    public void importsCorrectJobs() {
        Set<Job> jobs = importer.getJobs();

        Assert.assertEquals(jobs.size(), 2);
        Assert.assertThat(jobs,
            hasItems(
                allOf(
                    hasProperty("id", equalTo(j1000.getId())),
                    hasProperty("jobItems", sameBeanAs(j1000.getJobItems())),
                    hasProperty("cancelled", equalTo(j1000.isCancelled()))
                ),
                allOf(
                    hasProperty("id", equalTo(j1001.getId())),
                    hasProperty("jobItems", sameBeanAs(j1001.getJobItems())),
                    hasProperty("cancelled", equalTo(j1001.isCancelled()))
                )
            )
        );
    }

    @Test
    public void importsCorrectJobsMap() {
        Map<Integer, Job> jobsMap = importer.getJobsMap();
        
        Assert.assertEquals(jobsMap.size(), 2);
        Assert.assertThat(jobsMap.values(),
                hasItems(
                    allOf(
                        hasProperty("id", equalTo(j1000.getId())),
                        hasProperty("jobItems", sameBeanAs(j1000.getJobItems())),
                        hasProperty("cancelled", equalTo(j1000.isCancelled()))
                    ),
                    allOf(
                        hasProperty("id", equalTo(j1001.getId())),
                        hasProperty("jobItems", sameBeanAs(j1001.getJobItems())),
                        hasProperty("cancelled", equalTo(j1001.isCancelled()))
                    )
                )
            );
    }
    
    @Test
    public void importsCorrectTrainingJobs() {
        Set<Job> trainingJobs = importer.getTrainingJobs();

        Assert.assertEquals(trainingJobs.size(), 12);
        Assert.assertThat(trainingJobs,
            hasItems(
                allOf(
                    hasProperty("id", equalTo(j101.getId())),
                    hasProperty("jobItems", sameBeanAs(j101.getJobItems())),
                    hasProperty("previouslyCancelled", equalTo(j101.isPreviouslyCancelled()))
                ),
                allOf(
                    hasProperty("id", equalTo(j102.getId())),
                    hasProperty("jobItems", sameBeanAs(j102.getJobItems())),
                    hasProperty("previouslyCancelled", equalTo(j102.isPreviouslyCancelled()))
                ),
                allOf(
                    hasProperty("id", equalTo(j103.getId())),
                    hasProperty("jobItems", sameBeanAs(j103.getJobItems())),
                    hasProperty("previouslyCancelled", equalTo(j103.isPreviouslyCancelled()))
                ),
                allOf(
                    hasProperty("id", equalTo(j104.getId())),
                    hasProperty("jobItems", sameBeanAs(j104.getJobItems())),
                    hasProperty("previouslyCancelled", equalTo(j104.isPreviouslyCancelled()))
                ),
                allOf(
                    hasProperty("id", equalTo(j105.getId())),
                    hasProperty("jobItems", sameBeanAs(j105.getJobItems())),
                    hasProperty("previouslyCancelled", equalTo(j105.isPreviouslyCancelled()))
                ),
                allOf(
                    hasProperty("id", equalTo(j106.getId())),
                    hasProperty("jobItems", sameBeanAs(j106.getJobItems())),
                    hasProperty("previouslyCancelled", equalTo(j106.isPreviouslyCancelled()))
                ),
                allOf(
                    hasProperty("id", equalTo(j201.getId())),
                    hasProperty("jobItems", sameBeanAs(j201.getJobItems())),
                    hasProperty("previouslyCancelled", equalTo(j201.isPreviouslyCancelled()))
                ),
                allOf(
                    hasProperty("id", equalTo(j202.getId())),
                    hasProperty("jobItems", sameBeanAs(j202.getJobItems())),
                    hasProperty("previouslyCancelled", equalTo(j202.isPreviouslyCancelled()))
                ),
                allOf(
                    hasProperty("id", equalTo(j203.getId())),
                    hasProperty("jobItems", sameBeanAs(j203.getJobItems())),
                    hasProperty("previouslyCancelled", equalTo(j203.isPreviouslyCancelled()))
                ),
                allOf(
                    hasProperty("id", equalTo(j204.getId())),
                    hasProperty("jobItems", sameBeanAs(j204.getJobItems())),
                    hasProperty("previouslyCancelled", equalTo(j204.isPreviouslyCancelled()))
                ),
                allOf(
                    hasProperty("id", equalTo(j205.getId())),
                    hasProperty("jobItems", sameBeanAs(j205.getJobItems())),
                    hasProperty("previouslyCancelled", equalTo(j205.isPreviouslyCancelled()))
                ),
                allOf(
                    hasProperty("id", equalTo(j206.getId())),
                    hasProperty("jobItems", sameBeanAs(j206.getJobItems())),
                    hasProperty("previouslyCancelled", equalTo(j206.isPreviouslyCancelled()))
                )
            )
        );
    }

}
