package rp.assignments.team.warehouse.server;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

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
    
    @Test
    public void getNextDropLocationShouldAlternateBetweenGivenDropLocations() {
        Location l1 = mock(Location.class);
        Location l2 = mock(Location.class);
        List<Location> dropLocations = new ArrayList<Location>() {{
            add(l1);
            add(l2);
        }};
        when(warehouse.getDropLocations()).thenReturn(dropLocations);

        for (int i = 0; i < 3; i++) {
            Assert.assertEquals(l1, serverThread.getNextDropLocation());
            Assert.assertEquals(l2, serverThread.getNextDropLocation());
        }
    }

}
