package rp.assignments.team.warehouse.server.route.planning;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import rp.assignments.team.warehouse.server.Location;

public class MultiRobotRoutePlanningTests {
	
	@Test
	public void identicalFirstTwoStartingLocationsTest() {
		Location start1 = new Location(0,0);
		Location goal1 = new Location(3,5);
		Location start2 = new Location(0,0);
		Location goal2 = new Location(0,2);
		Location start3 = new Location(7,0);
		Location goal3 = new Location(10,0);
		
		List<Location> list1 = Windowed.findPath(start1, goal1, Data.getObstacles()); 
		List<Location> list2 = Windowed.findPath(list1, start2, goal2, Data.getObstacles());
		
		Assert.assertNull(list2);
		Assert.assertNull(Windowed.findPath(list1, list2, start3, goal3, Data.getObstacles()));
	}
	
	@Test
	public void identicalFirstAndThirdStartingLocations() {
		Location start1 = new Location(0,0);
		Location goal1 = new Location(3,5);
		Location start2 = new Location(6,0);
		Location goal2 = new Location(0,2);
		Location start3 = new Location(0,0);
		Location goal3 = new Location(10,0);
		
		List<Location> list1 = Windowed.findPath(start1, goal1, Data.getObstacles()); 
		List<Location> list2 = Windowed.findPath(list1, start2, goal2, Data.getObstacles());
		
		Assert.assertNull(Windowed.findPath(list1, list2, start3, goal3, Data.getObstacles()));
		
	}
	
	@Test
	public void identicalSecondAndThirdStartingLocations() {
		Location start1 = new Location(6,0);
		Location goal1 = new Location(3,5);
		Location start2 = new Location(0,0);
		Location goal2 = new Location(0,2);
		Location start3 = new Location(0,0);
		Location goal3 = new Location(10,0);
		
		List<Location> list1 = Windowed.findPath(start1, goal1, Data.getObstacles()); 
		List<Location> list2 = Windowed.findPath(list1, start2, goal2, Data.getObstacles());
		
		Assert.assertNull(Windowed.findPath(list1, list2, start3, goal3, Data.getObstacles()));		
	}
	
	@Test
	public void reroutingTest1() {
		Location start1 = new Location(0,5);
		Location goal1 = new Location(4,6);
		Location start2 = new Location(0,3);
		Location goal2 = new Location(0,7);
		
		List<Location> list1 = Windowed.findPath(start1, goal1, Data.getObstacles());
		List<Location> list2 = Windowed.findPath(list1, start2, goal2, Data.getObstacles());
		
		List<Location> expected = new ArrayList<Location>() {{
			add(new Location(0,3));
			add(new Location(0,2));
			add(new Location(0,1));
			add(new Location(0,0));
		}};
		
		Assert.assertEquals(expected, list2);
	}
	

}