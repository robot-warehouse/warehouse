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
		
		//checks if the goal position of the first robot is an obstacle.
		if(l2.size()>l1.size()) {
			if(l2.subList(l1.size(),l2.size()-1).contains(l1.get(l1.size()-1))) {
				System.out.println("goal is an obstacle");
				obs2.add(l1.get(l1.size()-1));
				l2 = AStar.findPath(start, goal,obs2);
			}
		}
		
		
		int i = 0;
		while (i < l2.size() - 1) {
			for (int j = 0; j < l1.size() - 1; j++) {

				State loc1a = l2.get(i);
				State loc2a = l1.get(j);
				State loc1b = l2.get(i + 1);
				State loc2b = l1.get(j + 1);
				//checks if the robots are swapping positions anywhere.
				if (swapped(loc1a, loc1b, loc2a, loc2b)) {
				//checks if it is possible to step aside to prevent swapping.
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
					}//if it is not possible to step aside then reroute  
					else {
						if (!loc1a.equals(start) && !loc1a.equals(goal))
							obs2.add(loc1a);
						else if (!loc1b.equals(start) && !loc1b.equals(goal))
							obs2.add(loc1b);
						l2 = AStar.findPath(start, goal, obs2);
						//obs2.remove(obs2.size() - 1);
						i = 0;
						j = 0;
					}

				}
			}
			i++;
		}
		//checks for collisions.
		i = 0;
		while (i < Math.min(l2.size(), l1.size())) {
			State loc2 = l2.get(i);
			State loc1 = l1.get(i);
			//adds a wait to prevent collisions 
			if (loc1.equals(loc2)) {
				State temp = l2.get(i - 1);
				l2.add(i - 1, temp);
			}

			i++;
		}
		long end = System.currentTimeMillis();

		System.out.println("after");
		System.out.println(l2);
		System.out.println(l1);
		System.out.println(end - begin);
		
		return l2;
	}
	
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

}
