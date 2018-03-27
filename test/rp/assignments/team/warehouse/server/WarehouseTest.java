package rp.assignments.team.warehouse.server;

import static org.mockito.Mockito.*;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import rp.assignments.team.warehouse.server.job.Job;

public class WarehouseTest {

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    Job job0;
    Robot robot0;
    Location location0, location1;
    Controller controller;

    Warehouse warehouse;

    @Before
    public void setupBeforeEach() {
        job0 = mock(Job.class);
        robot0 = mock(Robot.class);
        when(robot0.getName()).thenReturn("Robot0");
        location0 = mock(Location.class);
        location1 = mock(Location.class);
        controller = mock(Controller.class);

        warehouse = new Warehouse();
        warehouse.setController(controller);
    }

    @Test
    public void isRunningShouldBeInitiallyTrue() {
        Assert.assertTrue(warehouse.isRunning());
    }

    @Test
    public void shutdownShouldExitApplication() {
        exit.expectSystemExit();
        Assert.assertTrue(warehouse.isRunning());
        warehouse.shutdown();
        Assert.assertFalse(warehouse.isRunning());
    }

    @Test
    public void workingOnJobsShouldBeInitiallyEmpty() {
        Assert.assertEquals(0, warehouse.getWorkingOnJobs().size());
    }

    @Test
    public void canAddJob() {
        warehouse.addWorkingOnJob(job0);
        Assert.assertTrue(warehouse.getWorkingOnJobs().contains(job0));
    }
    
    @Test
    public void canCancelJob() {
        warehouse.addRobot(robot0);
        warehouse.addWorkingOnJob(job0);
        Assert.assertTrue(warehouse.getRobots().contains(robot0));
        Assert.assertTrue(warehouse.getWorkingOnJobs().contains(job0));
        
        warehouse.cancelJob(job0);
        
        verify(job0, times(1)).setCancelled();
        Assert.assertFalse(warehouse.getWorkingOnJobs().contains(job0));
    }

    @Test
    public void robotsShouldBeInitiallyEmpty() {
        Assert.assertEquals(0, warehouse.getRobots().size());
    }

    @Test
    public void canAddRobot() {
        warehouse.addRobot(robot0);
        Assert.assertTrue(warehouse.getRobots().contains(robot0));
    }

    @Test
    public void getRobotLocationsShouldBeEmptyWhenNoRobotsAdded() {
        Assert.assertEquals(0, warehouse.getRobotLocations().size());
    }

    @Test
    public void getRobotLocationsGivesCorrectLocations() {
        warehouse.addRobot(robot0);
        when(robot0.getCurrentLocation()).thenReturn(location0);
        Assert.assertEquals(location0, warehouse.getRobotLocations().get("Robot0"));

        when(robot0.getCurrentLocation()).thenReturn(location1);
        Assert.assertEquals(location1, warehouse.getRobotLocations().get("Robot0"));
    }
    
    @Test
    public void canGetKnownRobots() {
        Assert.assertThat(warehouse.getKnownRobots(), sameBeanAs(RobotInfo.values()));
    }

}
