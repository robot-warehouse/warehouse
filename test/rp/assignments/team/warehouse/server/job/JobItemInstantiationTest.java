package rp.assignments.team.warehouse.server.job;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class JobItemInstantiationTest {
    
    private static Item mockedItema = mock(Item.class);
    
    @BeforeClass
    public static void setupBeforeClass() {
        when(mockedItema.getReward()).thenReturn(10.0f);
    }

    @Test
    public void canCreateJobItemWithValidProperties() {
        JobItem jobItem = new JobItem(mockedItema, 3);
        Assert.assertNotNull(jobItem);
    }

    @Test(expected=AssertionError.class)
    public void cannotCreateJobItemWithNullItem() {
        JobItem jobItem = new JobItem(null, 3);
        Assert.assertNull(jobItem);
    }

    @Test(expected=AssertionError.class)
    public void cannotCreateJobItemWithNegativeCount() {
        JobItem jobItem = new JobItem(mockedItema, -3);
        Assert.assertNull(jobItem);
    }

}
