package rp.assignments.team.warehouse.server.route.planning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rp.assignments.team.warehouse.server.Location;

public class TwoRobots {

//---------At the beginning---------
	//priority is given to r1 and route is changed for r2
	
	private static void findPath(Location start1, Location goal1, Location start2, Location goal2) { 
		List<Location> l1 = new ArrayList<Location>();
		List<Location> l2 = new ArrayList<Location>();
	
		//get Route for r1 in a list called l1
		l1 = AStar.findPath(start1,goal1);
		//get Route for r2 in a list called l2
		l2 = AStar.findPath(start2, goal2);
	
	
	//get the current time----> not sure how this would be used
	
	//create a map for both the robots with time steps as keys and coordinates as values.
		Map<Integer, Location> map1= new LinkedHashMap<>();
		Map<Integer, Location> map2= new LinkedHashMap<>();
		
		for(int i =0 ; i < l1.size();i++) {
			map1.put(i, l1.get(i));
		}
		for(int i =0 ; i < l1.size();i++) {
			map1.put(i, l1.get(i));
		}
	//loop through both the maps simultaneously
		for (int i=0; i< Math.min(l1.size(), l2.size())-1;i++) {
			  Location loc1a = map1.get(i);
			  Location loc2a = map2.get(i);
			  Location loc1b = map1.get(i+1);
			  Location loc2b = map2.get(i+1);
			  
			  if(swapped(loc1a,loc1b,loc2a,loc2b)) {
				  
			  }
		
		}
		
		//if there are no conflicts start moving
				//if any time step has the same coordinates in both the maps (without swapping) add a pause for r2 in the map for that particular time step
				//and re-check.....again if there is a possibility of a collision without swapping positions repeat. ( would have to put this in a loop )
				//if at any step the robots are swapping positions reroute for the second robot with that coordinate as obstacle and re-check
		
	}
	
	public static boolean swapped(Location loc1a, Location loc1b, Location loc2a, Location loc2b) {
		return loc1a.equals(loc2a) && loc1b.equals(loc2b);
		
	}	
		
		
		
		
		
//---------Later-----------------
	//if r1 is moving and r2 is stationary i.e. waiting for a new route.
	
	//get the Route for r2
	
	//get the route for r1 and the current coordinate of r1
	
	//create a map for both the robots with the next to next position of r1 (say p3) as the value for the first time step.
	
	//loop through both the maps simultaneously 
		//if there are no conflicts, start moving as soon as r1 reaches p3
		//if any time step has the same coordinates in both the maps but they are not swapping positions, add a pause for r2 in the map for that particular time step
		//and re-check.....again if there is a possibility of a collision without swapping positions repeat. ( would have to put this in a loop )
		//if at any step the robots are swapping positions reroute for the second robot with that coordinate as obstacle
	
//------Similar approach if r2 is moving and r1 is stationary.	
	
}
