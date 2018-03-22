package rp.assignments.team.warehouse.server.route.planning;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import rp.assignments.team.warehouse.server.Location;

public class RoutePlanningTest {

    @Test
    public void simpleHorizontalTest() {
        // rudimentary horizontal tests.
        Location start1 = new Location(0, 0);
        Location goal1 = new Location(5, 0);
        List<Location> result1 = AStar.findPath(start1, goal1);
        List<State> expected1 = new ArrayList<State>() {
            {
                add(new State(0, 0));
                add(new State(1, 0));
                add(new State(2, 0));
                add(new State(3, 0));
                add(new State(4, 0));
                add(new State(5, 0));
            }
        };
        Assert.assertEquals(expected1, result1);
    }

    @Test
    public void simpleVerticalTest() {
        // rudimentary vertical tests.
        Location start2 = new Location(0, 0);
        Location goal2 = new Location(0, 5);
        List<Location> result2 = AStar.findPath(start2, goal2);
        ArrayList<Location> expected2 = new ArrayList<Location>() {
            {
                add(new State(0, 0));
                add(new State(0, 1));
                add(new State(0, 2));
                add(new State(0, 3));
                add(new State(0, 4));
                add(new State(0, 5));
            }
        };
        Assert.assertEquals(expected2, result2);
    }

    @Test
    public void startPosIsObstacleTest() {
        // check if an obstacle is rejected as starting position
        Location start5 = new Location(1, 2);
        Location goal5 = new Location(0, 5);
        List<Location> result5 = AStar.findPath(start5, goal5, Data.getObstacles());
        Assert.assertNull(result5);
    }

    @Test
    public void goalPosIsObstacleTest() {
        // check if an obstacle is rejected as goal position
        Location start6 = new Location(0, 0);
        Location goal6 = new Location(1, 2);
        List<Location> result6 = AStar.findPath(start6, goal6,Data.getObstacles());
        Assert.assertNull(result6);
    }
}
