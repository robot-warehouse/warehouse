package rp.assignments.team.warehouse.server;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import rp.assignments.team.warehouse.server.job.Pick;
import rp.assignments.team.warehouse.shared.Facing;

public class RobotTest {
    
    private static RobotInfo robotInfo = RobotInfo.NAMELESS;
    private static Location location = mock(Location.class);
    private static Facing facing = Facing.NORTH;
        
    private Robot robot;
        
    @Before
    public void setupBeforeEach() {
        robot = new Robot(robotInfo, location, facing);
    }
    
    @Test
    public void canGetNameOfRobot() {
        Assert.assertEquals(robotInfo.getName(), robot.getName());
    }
    
    @Test
    public void canGetAddressOfRobot() {
        Assert.assertEquals(robotInfo.getAddress(), robot.getAddress());
    }
    
    @Test
    public void canGetCurrentLocation() {
        Assert.assertEquals(location, robot.getCurrentLocation());
    }
    
    @Test
    public void canGetRobotInfo() {
        Assert.assertEquals(robotInfo, robot.getRobotInfo());
    }
    
    @Test
    public void canSetCurrentLocation() {
        Location newLocation = mock(Location.class);
        robot.setCurrentLocation(newLocation);
        Assert.assertEquals(newLocation, robot.getCurrentLocation());
    }
    
    @Test(expected=AssertionError.class)
    public void cannotSetCurrentLocationToNull() {
        robot.setCurrentLocation(null);
    }
    
    @Test
    public void canGetCurrentFacingDirection() {
        Assert.assertEquals(facing, robot.getCurrentFacingDirection());
    }
    
    @Test
    public void canSetCurrentFacingDirection() {
        Facing newFacing = Facing.EAST;
        robot.setCurrentFacingDirection(newFacing);
        Assert.assertEquals(newFacing, robot.getCurrentFacingDirection());
    }
    
    @Test(expected=AssertionError.class)
    public void cannotSetCurrentFacingDirectionToNull() {
        robot.setCurrentFacingDirection(null);
    }
    
    @Test
    public void getCurrentWeightShouldBeInitiallyZero() {
        Assert.assertEquals(0.0f, robot.getCurrentWeight(), 0.0f);
    }
    
    @Test
    public void isAvailableShouldBeInitiallyTrueRegardlessOfPick() {
        Pick pick = mock(Pick.class);
        Assert.assertTrue(robot.isAvailable(pick));
    }
    
    @Test
    public void getNumPicksAtLocationShouldBeInitiallyZero() {
        Assert.assertEquals(0, robot.getNumPicksAtLocation(location));
    }
    
    // TODO pick assignment, bids, cancellation

}
