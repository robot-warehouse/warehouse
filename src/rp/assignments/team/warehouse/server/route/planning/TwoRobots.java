package rp.assignments.team.warehouse.server.route.planning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rp.assignments.team.warehouse.server.Location;

public class TwoRobots {

	// ---------At the beginning---------
	// priority is given to r1 and route is changed for r2

//	private static void findPath(State start1, State goal1, State start2, State goal2, Boolean r1, Boolean r2) {
//		long begin = System.currentTimeMillis();
//		// boolean r1 is true when r1 is moving.
//
//		// boolean r2 is true when r2 is moving.
//
//		if (r1) {
//			// get the current position of r1.
//
//			//get the arraylist of the current route of the robot from current position + 2.
//
//			//stop the robot when it reaches current position + 3 and make it wait for the new route
//
//		} else if (r2) {
//			// get the current position of r2.
//
//			//get the arraylist of the current route of the robot from current position + 2.
//
//			//stop the robot when it reaches current position + 3 and make it wait for the new route
//
//		}
//
//		List<State> l1 = new ArrayList<State>();
//		List<State> l2 = new ArrayList<State>();
//
//		ArrayList<State> obs1 = new ArrayList<State>();
//		ArrayList<State> obs2 = new ArrayList<State>();
//
//		// get Route for r1 in a list called l1
//		l1 = AStar.findPath(start1, goal1, Data.getObstacles());
//		// get Route for r2 in a list called l2
//		l2 = AStar.findPath(start2, goal2, Data.getObstacles());
//
//		// the lists of obstacles for both the robots
//		obs1 = Data.getObstacles();
//		obs2 = Data.getObstacles();
//		System.out.println("before");
//		System.out.println(l1);
//		System.out.println(l2);
//
//		// create a map for both the robots with time steps as keys and coordinates as
//		// values.
//		// Map<Integer, Location> map1= new LinkedHashMap<>();
//		// Map<Integer, Location> map2= new LinkedHashMap<>();
//		//
//		// for(int i =0 ; i < l1.size();i++) {
//		// map1.put(i, l1.get(i));
//		// }
//		// for(int i =0 ; i < l1.size();i++) {
//		// map1.put(i, l1.get(i));
//		// }
//		// loop through both the maps simultaneously
//		// for (int i = 0; i < Math.min(l1.size(), l2.size()) - 1; i++) {
//		int i = 0;
//		while (i < Math.min(l1.size(), l2.size()) - 1) {
//			for (int j = 0; j < Math.min(l1.size(), l2.size()) - 1; j++) {
//
//				State loc1a = l1.get(i);
//				State loc2a = l2.get(j);
//				State loc1b = l1.get(i + 1);
//				State loc2b = l2.get(j + 1);
//
//				if (swapped(loc1a, loc1b, loc2a, loc2b)) {
//					System.out.println("check3");
//					if (!Data.singleRow.contains(loc1a)) {
//						System.out.println(loc1a.toString());
//						System.out.println("check4");
//						List<State> neighbours = loc1a.getNeighbours(start1, goal1);
//						for (State each : neighbours) {
//							if (!obs1.contains(each)) {
//								l1.add(i + 1, each);
//								l1.add(i + 2, loc1a);
//								break;
//							}
//						}
//					} else {
//						obs1.add(loc1a);
//						l1 = AStar.findPath(start1, goal1, obs1);
//						obs1.remove(obs1.size() - 1);
//						i = 0;
//					}
//
//				}
//			}
//			i++;
//		}
//
//		i = 0;
//		while (i < Math.min(l1.size(), l2.size())) {
//			State loc1a = l1.get(i);
//			State loc2a = l2.get(i);
//			if (loc1a.equals(loc2a)) {
//				// if (!loc1b.equals(l2.get(i - 1))) {
//				State temp = l1.get(i - 1);
//				// add a pause for 2nd robot for previous position
//				l1.add(i - 1, temp);
//				// } else if (!loc2b.equals(l1.get(i - 1))) {
//				// State temp = l1.get(i - 1);
//				// // add a pause for 1st robot for previous position
//				// l1.add(i - 1, temp);
//				// } else {
//				// obs2.add(loc1a);
//				// System.out.println("rerouting...");
//				// l2 = AStar.findPath(start2, goal2, obs2);
//				// i = 0;
//				// }
//			}
//
//			i++;
//		}
//
//		System.out.println("Done");
//		long end = System.currentTimeMillis();
//
//		System.out.println("after");
//		System.out.println(l1);
//		System.out.println(l2);
//		System.out.println(end - begin);
//	}
//
//	// if there are no conflicts start moving
//	// if any time step has the same coordinates in both the maps (without swapping)
//	// add a pause for r2 in the map for that particular time step
//	// and re-check.....again if there is a possibility of a collision without
//	// swapping positions repeat. ( would have to put this in a loop )
//	// if at any step the robots are swapping positions reroute for the second robot
//	// with that coordinate as obstacle and re-check
//
//	public static boolean swapped(Location loc1a, Location loc1b, Location loc2a, Location loc2b) {
//		return loc1a.equals(loc2b) && loc1b.equals(loc2a);
//	}
//
//	public static void main(String args[]) {
//
//		State start1 = new State(0, 0);
//		State start2 = new State(6, 0);
//		State goal1 = new State(3, 0);
//		State goal2 = new State(2, 0);
//
//		TwoRobots.findPath(start1, goal1, start2, goal2, false , false);
//	}
//	// ---------Later-----------------
//	// if r1 is moving and r2 is stationary i.e. waiting for a new route.
//
//	// get the Route for r2
//
//	// get the route for r1 and the current coordinate of r1
//
//	// create a map for both the robots with the next to next position of r1 (say
//	// p3) as the value for the first time step.
//
//	// loop through both the maps simultaneously
//	// if there are no conflicts, start moving as soon as r1 reaches p3
//	// if any time step has the same coordinates in both the maps but they are not
//	// swapping positions, add a pause for r2 in the map for that particular time
//	// step
//	// and re-check.....again if there is a possibility of a collision without
//	// swapping positions repeat. ( would have to put this in a loop )
//	// if at any step the robots are swapping positions reroute for the second robot
//	// with that coordinate as obstacle
//
//	// ------Similar approach if r2 is moving and r1 is stationary.

}
