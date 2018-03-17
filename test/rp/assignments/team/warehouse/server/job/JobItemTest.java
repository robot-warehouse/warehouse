package rp.assignments.team.warehouse.server.job;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JobItemTest {

    private static Item mockedItema = mock(Item.class);

    private JobItem jobItem;

    @BeforeClass
    public static void setupBeforeClass() {
        when(mockedItema.getReward()).thenReturn(10.0f);
    }

    @Before
    public void setupBeforeEach() {
        jobItem = new JobItem(mockedItema, 3);
    }

    @Test
    public void canGetItem() {
        Assert.assertEquals(mockedItema, jobItem.getItem());
    }

    @Test
    public void canGetCount() {
        Assert.assertEquals(3, jobItem.getCount());
    }

    @Test
    public void canGetReward() {
        Assert.assertEquals(3 * 10.f, jobItem.getReward(), 0.0f);
    }

}
