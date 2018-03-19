package rp.assignments.team.warehouse.server.route.planning;

import java.util.ArrayList;

import rp.assignments.team.warehouse.server.Location;

public class ThreeRobots {

	static ArrayList<State> findPath(ArrayList<State> l1, ArrayList<State> l2, State start, State goal) {

		ArrayList<State> l3 = new ArrayList<State>();
		ArrayList<State> temporary = new ArrayList<State>();
		l3 = AStar.findPath(start, goal, Data.getObstacles());
		System.out.println(l3 + "b4");

		ArrayList<State> obs3 = new ArrayList<State>();
		obs3 = Data.obstacles;
				
		l3 = swapAndGoalCheck(l1, l2, start, goal, l3, obs3);

		l3 = collisionCheck(l1, l2, start, goal, l3, obs3);

		return l3;
	}

	static ArrayList<State> collisionCheck(ArrayList<State> l1, ArrayList<State> l2, State start, State goal, ArrayList<State> l3,
			ArrayList<State> obs3) {
		int i = 0;
		while (i < Math.max(l3.size(), l1.size())) {
			boolean reroute = true;
			State loc1;
			State loc2;
			State loc3;
			if (i < l1.size())
				loc1 = l1.get(i);
			else
				loc1 = null;
			if (i < l2.size())
				loc2 = l2.get(i);
			else
				loc2 = null;
			if (i < l3.size())
				loc3 = l3.get(i);
			else
				loc3 = null;
			State prev;
			if (i > 0) {
				prev = l3.get(i - 1);
			} else
				prev = loc3;
		
			
			if (loc1.equals(loc3) && !loc2.equals(prev)) {
				l3.add(i - 1, prev);
				reroute = false;
			} else if (loc2.equals(loc3) && !loc1.equals(prev)) {
				l3.add(i - 1, prev);
				reroute = false;
			} else if (loc1.equals(loc3)) {
				ArrayList<State> neighbours = new ArrayList<>();
				neighbours = loc3.getNeighbours(start, goal);
				for (State each : neighbours) {
					if (!loc2.equals(each) && !obs3.contains(each)) {
						l3.add(i - 1, each);
						l3.add(i, loc3);
						reroute = false;
						break;
					}
				}
			} else if(loc2.equals(loc3)) {
				ArrayList<State> neighbours = new ArrayList<>();
				neighbours = loc3.getNeighbours(start, goal);
				for (State each : neighbours) {
					if (!loc1.equals(each) && !obs3.contains(each)) {
						l3.add(i - 1, each);
						l3.add(i, loc3);
						reroute = false;
						break;
					}
				}
			} else if(reroute){
				obs3.add(loc3);
				if(AStar.findPath(start, goal,obs3) != null ) {
				l3 = AStar.findPath(start, goal,obs3);
				l3 = swapAndGoalCheck(l1, l2, start, goal, l3, obs3);
				i = 0;
				}
				else {
					// indefinite wait
					for (int k = 0; AStar.findPath(start, goal, obs3) != null; k++) {
						l3 = AStar.findPath(start, l3.get(l3.indexOf(l1.get(l1.size() - 1)) - k), obs3);
					}
					
				}
					
			}

			i++;
		}
		
		return l3;
	}

	static ArrayList<State> swapAndGoalCheck(ArrayList<State> l1, ArrayList<State> l2, State start, State goal,
			ArrayList<State> l3, ArrayList<State> obs3) {
		ArrayList<State> temporary;
		boolean check = true;
		int n = 1;
		int counter = 0;
		int j = 0;
		while (check) {
			switch (n) {

			case 1:
				if (l3.size() > l1.size()) {
					if (l3.subList(l1.size(), l3.size() - 1).contains(l1.get(l1.size() - 1))) {
						System.out.println("first goal is an obstacle");
						obs3.add(l1.get(l1.size() - 1));
						if (AStar.findPath(start, goal, obs3) != null)
							l3 = AStar.findPath(start, goal, obs3);
						else {
							// indefinite wait
							for (int k = 0; AStar.findPath(start, goal, obs3) != null; k++) {
								l3 = AStar.findPath(start, l3.get(l3.indexOf(l1.get(l1.size() - 1)) - k), obs3);
							}
						}
					}
				}

			case 2:
				if (l3.size() > l2.size()) {
					if (l3.subList(l2.size(), l3.size() - 1).contains(l2.get(l2.size() - 1))) {
						System.out.println("first goal is an obstacle");
						obs3.add(l2.get(l2.size() - 1));
						if (AStar.findPath(start, goal, obs3) != null)
							l3 = AStar.findPath(start, goal, obs3);
						else {
							// indefinite wait
							for (int k = 0; AStar.findPath(start, goal, obs3) != null; k++) {
								l3 = AStar.findPath(start, l3.get(l3.indexOf(l2.get(l2.size() - 1)) - k), obs3);
							}
						}
					}
				}

			case 3:
				int i = 0;
				while (i < l3.size() - 1) {
					for (j = 0; j < l1.size() - 1; j++) {

						State loc1a = l3.get(i);
						State loc2a = l1.get(j);
						State loc1b = l3.get(i + 1);
						State loc2b = l1.get(j + 1);

						if (swapped(loc1a, loc1b, loc2a, loc2b)) {
							System.out.println("swapped 1");
							if (!Data.singleRow.contains(loc1a)) {
								System.out.println(loc1a.toString());
								System.out.println("check4");
								ArrayList<State> neighbours = loc1a.getNeighbours(start, goal);
								for (State each : neighbours) {
									if (!obs3.contains(each)) {
										l3.add(i + 1, each);
										l3.add(i + 2, loc1a);
										break;
									}
								}
							} else {
								if (!loc1a.equals(start) && !loc1a.equals(goal))
									obs3.add(loc1a);
								else if (!loc1b.equals(start) && !loc1b.equals(goal))
									obs3.add(loc1b);
								System.out.println("Rerouting..");
								if (AStar.findPath(start, goal, obs3) != null)
									l3 = AStar.findPath(start, goal, obs3);
								else {
									for (int k = 0; AStar.findPath(start, goal, obs3) != null; k++) {
										l3 = AStar.findPath(start, l3.get(l3.indexOf(goal) - k), obs3);
									}
								}
								System.out.println(l3);
								// obs3.remove(obs3.size() - 1);
								i = 0;
								j = 0;
							}

						}
					}
					i++;
				}

			case 4:
				check = false;
				i = 0;
				while (i < l3.size() - 1) {
					for (j = 0; j < l2.size() - 1; j++) {

						State loc1a = l3.get(i);
						State loc2a = l2.get(j);
						State loc1b = l3.get(i + 1);
						State loc2b = l2.get(j + 1);

						if (swapped(loc1a, loc1b, loc2a, loc2b)) {
							if (!Data.singleRow.contains(loc1a)) {
								ArrayList<State> neighbours = loc1a.getNeighbours(start, goal);
								for (State each : neighbours) {
									if (!obs3.contains(each)) {
										l3.add(i + 1, each);
										l3.add(i + 2, loc1a);
										break;
									}
								}
							} else {
								if (!loc1a.equals(start) && !loc1a.equals(goal))
									obs3.add(loc1a);
								else if (!loc1b.equals(start) && !loc1b.equals(goal))
									obs3.add(loc1b);
								if (AStar.findPath(start, goal, obs3) != null)
									l3 = AStar.findPath(start, goal, obs3);
								else{
									for (int k = 0; AStar.findPath(start, goal, obs3) != null; k++) {
										l3 = AStar.findPath(start, l3.get(l3.indexOf(goal) - k), obs3);
									}
								}	
								check = true;
								counter = 0;
								// obs3.remove(obs3.size() - 1);
								i = 0;
								j = 0;
							}

						}
					}
					i++;
				}
			case 5:
				if (counter >= 3) {

					System.out.println("inside counter if");
					temporary = l3;
					l3.clear();
					for (State each : temporary) {
						if (!Data.singleRow.contains(each) && !obs3.contains(each)) {
							l3.add(each);
						} else
							break;
					}
					if (l3.size() == temporary.size()) {
						l3.remove(l3.size() - 1);
					}

					counter = 0;
					i = 0;
					j = 0;
				}
			}
			counter++;
		}
		return l3;
	}

	public static boolean swapped(Location loc1a, Location loc1b, Location loc2a, Location loc2b) {
		return loc1a.equals(loc2b) && loc1b.equals(loc2a);
	}

}
