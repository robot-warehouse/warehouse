package rp.assignments.team.warehouse.test.route;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import rp.assignments.team.warehouse.server.Location;
import rp.assignments.team.warehouse.server.route.planning.AStar;
import rp.assignments.team.warehouse.server.route.planning.State;

public class RoutePlanningTest {
    
    private static boolean assertionStatus;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        assertionStatus = Location.class.desiredAssertionStatus();
        Location.class.getClassLoader().setClassAssertionStatus(Location.class.getName(), false);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        Location.class.getClassLoader().setClassAssertionStatus(Location.class.getName(), assertionStatus);
    }

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
        assertEquals(expected1, result1);
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
        assertEquals(expected2, result2);
    }

    @Test
    public void startPosValidationTest() {
        // check validation of starting position
        ArrayList<Location> result3;
        Location start3 = new Location(-1, 0);
        Location goal3 = new Location(0, 5);
        result3 = AStar.findPath(start3, goal3);
        assertNull(result3);
    }

    @Test
    public void goalPosValidationTest() {
        // check validation of goal position
        ArrayList<Location> result4;
        Location start4 = new Location(0, 5);
        Location goal4 = new Location(-1, 0);
        result4 = AStar.findPath(start4, goal4);
        assertNull(result4);
    }

    @Test
    public void startPosIsObstacleTest() {
        // check if an obstacle is rejected as starting position
        ArrayList<Location> result5;
        Location start5 = new Location(1, 2);
        Location goal5 = new Location(0, 5);
        result5 = AStar.findPath(start5, goal5);
        assertNull(result5);
    }

    @Test
    public void goalPosIsObstacleTest() {
        // check if an obstacle is rejected as goal position
        ArrayList<Location> result6;
        Location start6 = new Location(0, 0);
        Location goal6 = new Location(1, 2);
        result6 = AStar.findPath(start6, goal6);
        assertNull(result6);
    }
}
