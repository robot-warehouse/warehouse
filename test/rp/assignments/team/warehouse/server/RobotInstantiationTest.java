package rp.assignments.team.warehouse.server;

import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Test;

import rp.assignments.team.warehouse.shared.Facing;

public class RobotInstantiationTest {
    
    private static RobotInfo robotInfo = RobotInfo.NAMELESS;
    private static Location location = mock(Location.class);
    private static Facing facing = Facing.NORTH;

    @Test
    public void canCreateRobotWithValidProperties() {
        Robot robot = new Robot(robotInfo, location, facing);
        Assert.assertNotNull(robot);
    }
    
    @Test(expected=AssertionError.class)
    public void cannotCreateRobotWithNullRobotInfo() {
        Robot robot = new Robot(null, location, facing);
        Assert.assertNull(robot);
    }
    
    @Test(expected=AssertionError.class)
    public void cannotCreateRobotWithNullLocation() {
        Robot robot = new Robot(robotInfo, null, facing);
        Assert.assertNull(robot);
    }
    
    @Test(expected=AssertionError.class)
    public void cannotCreateRobotWithNullFacing() {
        Robot robot = new Robot(robotInfo, location, null);
        Assert.assertNull(robot);
    }

}
