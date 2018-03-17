package rp.assignments.team.warehouse.server.route.planning;

import static org.junit.Assume.assumeFalse;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import rp.assignments.team.warehouse.server.Location;

public class RoutePlanningTest {

    @Test
    public void simpleHorizontalTest() {
        // rudimentary horizontal tests.
        ArrayList<Location> result1;
        Location start1 = new Location(0, 0);
        Location goal1 = new Location(5, 0);
        result1 = AStar.findPath(start1, goal1);
        ArrayList<Location> expected1 = new ArrayList<Location>() {
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
        ArrayList<Location> result2;
        Location start2 = new Location(0, 0);
        Location goal2 = new Location(0, 5);
        result2 = AStar.findPath(start2, goal2);
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
        ArrayList<Location> result5;
        Location start5 = new Location(1, 2);
        Location goal5 = new Location(0, 5);
        result5 = AStar.findPath(start5, goal5);
        Assert.assertNull(result5);
    }

    @Test
    public void goalPosIsObstacleTest() {
        // check if an obstacle is rejected as goal position
        ArrayList<Location> result6;
        Location start6 = new Location(0, 0);
        Location goal6 = new Location(1, 2);
        result6 = AStar.findPath(start6, goal6);
        Assert.assertNull(result6);
    }
}
