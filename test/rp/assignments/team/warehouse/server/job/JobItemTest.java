package rp.assignments.team.warehouse.server.job;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class JobItemTest {
    
    private static Item mockedItema = mock(Item.class);
    
    static {
        when(mockedItema.getReward()).thenReturn(10.0f);
    }

    public static class JobItemInstantiationTests {

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
    
    public static class JobItemPropertyTests {
        
        private JobItem jobItem;
        
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
            Assert.assertEquals(3*10.f, jobItem.getReward(), 0.0f);
        }
        
    }

}
