package rp.assignments.team.warehouse.server.route.planning;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import rp.assignments.team.warehouse.server.Location;

public class RoutePlanningTest {

	@Test
	public void test() {
		// rudimentary horizontal tests.
		ArrayList<Location> result1 = new ArrayList<>();
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

		// rudimentary vertical tests.
		ArrayList<Location> result2 = new ArrayList<>();
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

		// check validation of starting position
		ArrayList<Location> result3 = new ArrayList<>();
		Location start3 = new Location(-1, 0);
		Location goal3 = new Location(0, 5);
		result3 = AStar.findPath(start3, goal3);
		ArrayList<Location> expected3 = null;
		assertEquals(expected3, result3);

		// check validation of goal position
		ArrayList<Location> result4 = new ArrayList<>();
		Location start4 = new Location(-1, 0);
		Location goal4 = new Location(0, 5);
		result4 = AStar.findPath(start4, goal4);
		ArrayList<Location> expected4 = null;
		assertEquals(expected4, result4);
		
		//check if an obstacle is rejected as starting position
		ArrayList<Location> result5 = new ArrayList<>();
		Location start5 = new Location(1, 2);
		Location goal5 = new Location(0, 5);
		result5 = AStar.findPath(start5, goal5);
		ArrayList<Location> expected5 = null;
		assertEquals(expected5, result5);
		
		//check if an obstacle is rejected as goal position
		ArrayList<Location> result6 = new ArrayList<>();
		Location start6 = new Location(0, 0);
		Location goal6 = new Location(1, 2);
		result6 = AStar.findPath(start6, goal6);
		ArrayList<Location> expected6 = null;
		assertEquals(expected6, result6);

	}

}
