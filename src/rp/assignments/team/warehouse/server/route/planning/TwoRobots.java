package rp.assignments.team.warehouse.server.route.planning;

import java.util.ArrayList;
import rp.assignments.team.warehouse.server.Location;

public class TwoRobots {

	// ---------At the beginning---------
	// priority is given to r1 and route is changed for r2

	static ArrayList<State> findPath(ArrayList<State> l1, State start, State goal) {
		long begin = System.currentTimeMillis();

		ArrayList<State> l2 = new ArrayList<State>();
	

		ArrayList<State> obs2 = new ArrayList<State>();

		l2 = AStar.findPath(start, goal, Data.getObstacles());
		
		obs2 = Data.getObstacles();
		
		System.out.println("before");
		
		int i = 0;
		while (i < l2.size() - 1) {
			for (int j = 0; j < l1.size() - 1; j++) {

				State loc1a = l2.get(i);
				State loc2a = l1.get(j);
				State loc1b = l2.get(i + 1);
				State loc2b = l1.get(j + 1);

				if (swapped(loc1a, loc1b, loc2a, loc2b)) {
					System.out.println("check3");
					if (!Data.singleRow.contains(loc1a)) {
						System.out.println(loc1a.toString());
						System.out.println("check4");
						ArrayList<State> neighbours = loc1a.getNeighbours(start, goal);
						for (State each : neighbours) {
							if (!obs2.contains(each)) {
								l2.add(i + 1, each);
								l2.add(i + 2, loc1a);
								break;
							}
						}
					} else {
						if (!loc1a.equals(start) && !loc1a.equals(goal))
							obs2.add(loc1a);
						else if (!loc1b.equals(start) && !loc1b.equals(goal))
							obs2.add(loc1b);
						l2 = AStar.findPath(start, goal, obs2);
						obs2.remove(obs2.size() - 1);
						i = 0;
						j = 0;
					}

				}
			}
			i++;
		}

		i = 0;
		while (i < Math.min(l2.size(), l1.size())) {
			State loc2 = l2.get(i);
			State loc1 = l1.get(i);
			if (loc1.equals(loc2)) {
				State temp = l2.get(i - 1);
				l2.add(i - 1, temp);
			}

			i++;
		}

		System.out.println("Done");
		long end = System.currentTimeMillis();

		System.out.println("after");
		System.out.println(l2);
		System.out.println(l1);
		System.out.println(end - begin);
		
		return l2;
	}

	// if there are no conflicts start moving
	// if any time step has the same coordinates in both the maps (without swapping)
	// add a pause for r2 in the map for that particular time step
	// and re-check.....again if there is a possibility of a collision without
	// swapping positions repeat. ( would have to put this in a loop )
	// if at any step the robots are swapping positions reroute for the second robot
	// with that coordinate as obstacle and re-check

	public static boolean swapped(Location loc1a, Location loc1b, Location loc2a, Location loc2b) {
		return loc1a.equals(loc2b) && loc1b.equals(loc2a);
	}

	public static void main(String args[]) {

		State start1 = new State(0, 6);
		State start2 = new State(0, 4);
		State goal1 = new State(0, 1);
		State goal2 = new State(5, 6);
		//System.out.println(AStar.findPath(start2, goal2, Data.obstacles));
		// TwoRobots.findPath(start1, goal1, start2, goal2, false , false);
	}
	// ---------Later-----------------
	// if r1 is moving and r2 is stationary i.e. waiting for a new route.

	// get the Route for r2

	// get the route for r1 and the current coordinate of r1

	// create a map for both the robots with the next to next position of r1 (say
	// p3) as the value for the first time step.

	// loop through both the maps simultaneously
	// if there are no conflicts, start moving as soon as r1 reaches p3
	// if any time step has the same coordinates in both the maps but they are not
	// swapping positions, add a pause for r2 in the map for that particular time
	// step
	// and re-check.....again if there is a possibility of a collision without
	// swapping positions repeat. ( would have to put this in a loop )
	// if at any step the robots are swapping positions reroute for the second robot
	// with that coordinate as obstacle

	// ------Similar approach if r2 is moving and r1 is stationary.

}
