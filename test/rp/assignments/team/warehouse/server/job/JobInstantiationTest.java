package rp.assignments.team.warehouse.server.job;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class JobInstantiationTest {

    private static List<JobItem> jobItems;

    @BeforeClass
    public static void setUpBeforeClass() {
        jobItems = new ArrayList<JobItem>();
    }

    @Test
    public void canCreateJobWithValidProperties() {
        Job job = new Job(1, jobItems);
        Assert.assertNotNull(job);
    }

    @Test(expected=AssertionError.class)
    public void cannotCreateJobWithNegativeId() {
        Job job = new Job(-1, jobItems);
        Assert.assertNull(job);
    }

    @Test(expected=AssertionError.class)
    public void cannotCreateJobWithNullJobItems() {
        Job job = new Job(1, null);
        Assert.assertNull(job);
    }

}
