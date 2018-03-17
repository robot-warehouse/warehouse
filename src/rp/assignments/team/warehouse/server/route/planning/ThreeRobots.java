package rp.assignments.team.warehouse.server.route.planning;

import java.util.ArrayList;

import rp.assignments.team.warehouse.server.Location;

public class ThreeRobots {

	private static ArrayList<State> findPath(ArrayList<State> l1, ArrayList<State> l2, State start, State goal) {

		ArrayList<State> l3 = new ArrayList<State>();
		l3 = AStar.findPath(start, goal, Data.getObstacles());

		ArrayList<State> obs3 = new ArrayList<State>();
		obs3 = Data.obstacles;

		boolean check = true;
		int n = 1;
		while (check) {
			switch (n) {

			case 1:
				int i = 0;
				while (i < Math.min(l3.size(), l1.size()) - 1) {
					for (int j = 0; j < Math.min(l3.size(), l1.size()) - 1; j++) {

						State loc1a = l3.get(i);
						State loc2a = l1.get(j);
						State loc1b = l3.get(i + 1);
						State loc2b = l1.get(j + 1);

						if (swapped(loc1a, loc1b, loc2a, loc2b)) {
							System.out.println("check3");
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
								l3 = AStar.findPath(start, goal, obs3);
								//obs3.remove(obs3.size() - 1);
								i = 0;
								j = 0;
							}

						}
					}
					i++;
				}

			case 2:
				check = false;
				i = 0;
				while (i < Math.min(l3.size(), l2.size()) - 1) {
					for (int j = 0; j < Math.min(l3.size(), l2.size()) - 1; j++) {

						State loc1a = l3.get(i);
						State loc2a = l2.get(j);
						State loc1b = l3.get(i + 1);
						State loc2b = l2.get(j + 1);

						if (swapped(loc1a, loc1b, loc2a, loc2b)) {
							System.out.println("check3");
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
								l3 = AStar.findPath(start, goal, obs3);
								check = true;
								//obs3.remove(obs3.size() - 1);
								i = 0;
								j = 0;
							}

						}
					}
					i++;
				}
			}
		}
		
		

		return l3;
	}

	public static boolean swapped(Location loc1a, Location loc1b, Location loc2a, Location loc2b) {
		return loc1a.equals(loc2b) && loc1b.equals(loc2a);
	}
}
