package rp.assignments.team.warehouse.server;

import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import rp.assignments.team.warehouse.server.job.selection.PriorityJobSelector;

public class ServerThreadTest {

    private Warehouse warehouse;
    private PriorityJobSelector jobSelector;
    private ServerThread serverThread;
    
    @Before
    public void setupBeforeEach() {
        warehouse = mock(Warehouse.class);
        jobSelector = mock(PriorityJobSelector.class);
        serverThread = new ServerThread(warehouse, jobSelector);
    }
    
    @Test
    public void workingOnJobsShouldEqualWarehouseWorkingOnJobs() {
        Assert.assertEquals(warehouse.getWorkingOnJobs(),  serverThread.getWorkingOnJobs());
    }
    
    @Test
    public void jobsHaveAvailablePicksShouldBeInitiallyFalse() {
        Assert.assertFalse(serverThread.jobsHaveAvailablePicks());
    }

}
