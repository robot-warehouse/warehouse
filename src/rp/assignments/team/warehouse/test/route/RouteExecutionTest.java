package rp.assignments.team.warehouse.test.route;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import rp.assignments.team.warehouse.server.Location;
import rp.assignments.team.warehouse.server.route.planning.AStar;
import rp.assignments.team.warehouse.server.route.execution.RouteExecution;;

public class RouteExecutionTest {
	
	// 4 tests

	@Test
	public void simpleTest1() {
		// simple horizontal test.
		ArrayList<Integer> result1;
		Location start1 = new Location(0, 0);
		Location goal1 = new Location(2, 0);
		RouteExecution.pathForReading = AStar.findPath(start1, goal1);
		RouteExecution rExecution = new RouteExecution();
		result1 = rExecution.movementCommands;
		ArrayList<Integer> expected1 = new ArrayList<Integer>() {
			{
				add(2);
				add(2);
			}
		};
		assertEquals(expected1, result1);
	}

	@Test
	public void simpleTest2() {
		// simple vertical test.
		ArrayList<Integer> result2;
		Location start2 = new Location(0, 0);
		Location goal2 = new Location(0, 5);
		RouteExecution.pathForReading = AStar.findPath(start2, goal2);
		RouteExecution rExecution = new RouteExecution();
		result2 = rExecution.getMovements();
		ArrayList<Integer> expected2 = new ArrayList<Integer>() {
			{
				add(0);
				add(2);
				add(2);
				add(2);
				add(2);
				add(2);
			}
		};
		assertEquals(expected2, result2);
	}

	@Test
	public void hardTest() {
		// Longer path
		ArrayList<Integer> result3;
		Location start3 = new Location(1, 0);
		Location goal3 = new Location(11, 4);
		RouteExecution.pathForReading = AStar.findPath(start3, goal3);
		RouteExecution rExecution = new RouteExecution();
		result3 = rExecution.getMovements();
		ArrayList<Integer> expected3 = new ArrayList<Integer>() {
			{
				add(2);
				add(2);
				add(2);
				add(2);
				add(2);
				add(2);
				add(2);
				add(2);
				add(2);
				add(2);
				add(0);
				add(2);
				add(2);
				add(2);
				add(2);
			}
		};
		assertEquals(expected3, result3);
	}

	@Test
	public void invalidLocationTest() {
		// check if an obstacle is rejected in conversion
		ArrayList<Integer> result4;
		Location start4 = new Location(1, 3);
		Location goal4 = new Location(11, 4);
		RouteExecution.pathForReading = AStar.findPath(start4, goal4);
		RouteExecution rExecution = new RouteExecution();
		result4 = rExecution.movementCommands;
		ArrayList<Integer> expected4 = new ArrayList<Integer>() {
			{

			}
		};
		assertEquals(expected4, result4);
	}

}
