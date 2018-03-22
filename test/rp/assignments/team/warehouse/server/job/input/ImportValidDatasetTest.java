package rp.assignments.team.warehouse.server.job.input;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;

import java.io.File;
import java.util.Arrays;
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
                    hasProperty("previouslyCancelled", equalTo(j1000.isPreviouslyCancelled()))
                ),
                allOf(
                    hasProperty("id", equalTo(j1001.getId())),
                    hasProperty("jobItems", sameBeanAs(j1001.getJobItems())),
                    hasProperty("previouslyCancelled", equalTo(j1001.isPreviouslyCancelled()))
                )
            )
        );
    }

}
