package rp.assignments.team.warehouse.server.route.planning;

import java.util.ArrayList;

import rp.assignments.team.warehouse.server.Location;

public class ThreeRobots {

	static ArrayList<State> findPath(ArrayList<State> l1, ArrayList<State> l2, State start, State goal) {

		ArrayList<State> l3 = new ArrayList<State>();
		l3 = AStar.findPath(start, goal, Data.getObstacles());
		System.out.println(l3 + "b4");

		ArrayList<State> obs3 = new ArrayList<State>();
		obs3 = Data.obstacles;
		
		boolean check = true;
		int n = 1;
		while (check) {
			switch (n) {

			case 1:
				int i = 0;
				while (i < l3.size() - 1) {
					for (int j = 0; j < l1.size() - 1; j++) {

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
								l3 = AStar.findPath(start, goal, obs3);
								System.out.println(l3);
								// obs3.remove(obs3.size() - 1);
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
				while (i < l3.size() - 1) {
					for (int j = 0; j < l2.size() - 1; j++) {

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
								l3 = AStar.findPath(start, goal, obs3);
								check = true;
								// obs3.remove(obs3.size() - 1);
								i = 0;
								j = 0;
							}

						}
					}
					i++;
				}
			}
		}

		int i = 0;
		while (i < Math.max(l3.size(), l1.size())) {
			State loc1;
			State loc2;
			State loc3;
			if(i<l1.size())
			 loc1 = l1.get(i);
			else
				loc1 = null;
			if(i<l2.size())
			 loc2 = l2.get(i);
			else
				loc2 = null;
			if(i<l2.size())
			 loc3 = l3.get(i);
			else
				loc3 = null;
			State temp;
			if (i > 0) {
				temp = l3.get(i - 1);
			} else
				temp = loc3;
			if (loc1.equals(loc3) && !loc2.equals(temp)) {
				l3.add(i - 1, temp);
			} else if (loc2.equals(loc3) && !loc1.equals(temp)) {
				l3.add(i - 1, temp);
			}
			i++;
		}

		
		return l3;
	}

	public static boolean swapped(Location loc1a, Location loc1b, Location loc2a, Location loc2b) {
		return loc1a.equals(loc2b) && loc1b.equals(loc2a);
	}
}
