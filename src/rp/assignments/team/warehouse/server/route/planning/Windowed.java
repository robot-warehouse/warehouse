package rp.assignments.team.warehouse.server.route.planning;

import java.util.List;

import rp.assignments.team.warehouse.server.Location;

public class Windowed {

	private final static int WINDOW = 4;

	public static List<Location> findPath(Location start, Location goal, List<Location> obstacles) {

		List<Location> list1 = AStar.findPath(start, goal, obstacles);

		list1 = resize(list1);

		return list1;

	}

	public static List<Location> findPath(List<Location> list1, Location start, Location goal,
			List<Location> obstacles) {
		assert (list1.size() < WINDOW);

		List<Location> list2 = AStar.findPath(start, goal, obstacles);
		if (list2 != null)
			list2 = resize(list2);

		int i = 0;
		while (i < list1.size() - 1) {
			for (int j = 0; j < list2.size() - 1; j++) {

				State firstRobotsFirstLocation = (State) list1.get(i);
				State secondRobotsFirstLocation = (State) list2.get(j);
				State firstRobotsSecondLocation = (State) list1.get(i + 1);
				State secondRobotsSecondLocation = (State) list2.get(j + 1);
				// checks if the robots are swapping positions anywhere.
				if (swapped(firstRobotsFirstLocation, firstRobotsSecondLocation, secondRobotsFirstLocation,
						secondRobotsSecondLocation)) {
					obstacles.add(firstRobotsFirstLocation);
					list2 = AStar.findPath(start, goal, obstacles);
					if (list2 == null) {
						return list2;
					} else {
						list2 = resize(list2);
					i = 0;
					j = 0;
					break;
					}
				}

			}
			i++;
		}

		for (i = 0; i < list1.size() - 1; i++) {
			for (int j = 0; j < list2.size(); j++) {

				State secondRobotsFirstLocation = (State) list2.get(j);
				State firstRobotsFirstLocation = (State) list1.get(i);
				State firstRobotsSecondLocation = (State) list1.get(i + 1);

				// reroutes or if not possible returns null
				if (firstRobotsFirstLocation.equals(secondRobotsFirstLocation)) {
					obstacles.add(firstRobotsFirstLocation);
					list2 = AStar.findPath(start, goal, obstacles);
					if (list2 == null) {
						return list2;
					} else {
						list2 = resize(list2);
						i = 0;
						j = 0;
						break;
					}
				}

				else if (firstRobotsSecondLocation.equals(secondRobotsFirstLocation)) {
					obstacles.add(firstRobotsSecondLocation);
					list2 = AStar.findPath(start, goal, obstacles);
					if (list2 == null) {
						return list2;
					} else {
						list2 = resize(list2);
						i = 0;
						j = 0;
						break;
					}
				}
			}
		}

		return list2;
	}

	public static List<Location> findPath(List<Location> list1, List<Location> list2, Location start, Location goal,
			List<Location> obstacles) {
		assert (list1.size() < WINDOW);
		assert (list2.size() < WINDOW);

		List<Location> list3 = AStar.findPath(start, goal, obstacles);
		
		System.out.println("before" + list3);

		if (list3 != null) {
			list3 = resize(list3);
		}
		else
			return null;

		// checks if the first and third robot are swapping locations anywhere
		int i = 0;
		while (i < list1.size() - 1) {
			for (int j = 0; j < list3.size() - 1; j++) {

				State firstRobotsFirstLocation = (State) list1.get(i);
				State thirdRobotsFirstLocation = (State) list3.get(j);
				State firstRobotsSecondLocation = (State) list1.get(i + 1);
				State thirdRobotsSecondLocation = (State) list3.get(j + 1);
				// checks if the robots are swapping positions anywhere.
				if (swapped(firstRobotsFirstLocation, firstRobotsSecondLocation, thirdRobotsFirstLocation,
						thirdRobotsSecondLocation)) {
					obstacles.add(firstRobotsFirstLocation);
					list3 = AStar.findPath(start, goal, obstacles);
					if (list3 == null) {
						return list3;
					} else {
						list3 = resize(list3);
					i = 0;
					j = 0;
					break;
					}
				}

			}
			i++;
		}
		System.out.println("first swap check completed");
		i = 0;
		while (i < list1.size() - 1) {
			for (int j = 0; j < list3.size() - 1; j++) {

				State secondRobotsFirstLocation = (State) list2.get(i);
				State thirdRobotsFirstLocation = (State) list3.get(j);
				State secondRobotsSecondLocation = (State) list2.get(i + 1);
				State thirdRobotsSecondLocation = (State) list3.get(j + 1);
				// checks if the robots are swapping positions anywhere.
				if (swapped(secondRobotsFirstLocation, secondRobotsSecondLocation, thirdRobotsFirstLocation,
						thirdRobotsSecondLocation)) {
					obstacles.add(secondRobotsFirstLocation);
					list3 = AStar.findPath(start, goal, obstacles);
					if (list3 == null) {
						return list3;
					} else {
						list3 = resize(list3);
					i = 0;
					j = 0;
					break;
					}
				}

			}
			i++;
		}
		System.out.println("second swap check completed");
		int counter = 0;
		System.out.println(list1);
		System.out.println(list2);
		System.out.println(list3);
		for (i = 0; i < Math.max(list1.size(), list2.size()) - 1; i++) {
			for (int j = 0; j < list3.size(); j++) {
				System.out.println(counter++);
				State firstRobotsFirstLocation;
				State firstRobotsSecondLocation;
				State secondRobotsFirstLocation;
				State secondRobotsSecondLocation;

				State thirdRobotsFirstLocation = (State) list3.get(j);
			
				
				if (i + 1 < list1.size()) {
					firstRobotsFirstLocation = (State) list1.get(i);
					firstRobotsSecondLocation = (State) list1.get(i + 1);
				} else if (i < list1.size()) {
					firstRobotsFirstLocation = (State) list1.get(i);
					firstRobotsSecondLocation = null;
				} else {
					firstRobotsFirstLocation = null;
					firstRobotsSecondLocation = null;
				}
				
				
				if (i + 1 < list2.size()) {
					secondRobotsFirstLocation = (State) list2.get(i);
					secondRobotsSecondLocation = (State) list2.get(i + 1);
				}
				else if (i < list2.size()) {
					secondRobotsFirstLocation = (State) list2.get(i);
					secondRobotsSecondLocation = null;
				} else {
					secondRobotsFirstLocation = null;
					secondRobotsSecondLocation = null;
				}
				// reroutes or if not possible returns null
				
				
				if (firstRobotsFirstLocation.equals(thirdRobotsFirstLocation)) {
					obstacles.add(firstRobotsFirstLocation);
					list3 = AStar.findPath(start, goal, obstacles);
					if (list3 == null) {
						return list3;
					} else {
						list3 = resize(list3);
						i = 0;
						j = 0;
						break;
					}
				}

				 if (firstRobotsSecondLocation.equals(thirdRobotsFirstLocation)) {
					obstacles.add(firstRobotsSecondLocation);
					list3 = AStar.findPath(start, goal, obstacles);
					if (list3 == null) {
						return list3;
					} else {
						list3 = resize(list3);
						i = 0;
						j = 0;
						break;
					}
				}
				
				if (secondRobotsFirstLocation.equals(thirdRobotsFirstLocation)) {
					obstacles.add(secondRobotsFirstLocation);
					list3 = AStar.findPath(start, goal, obstacles);
					if (list3 == null) {
						return list3;
					} else {
						list3 = resize(list3);
						i = 0;
						j = 0;
						break;
					}
				}

				if (secondRobotsSecondLocation.equals(thirdRobotsFirstLocation)) {
					obstacles.add(secondRobotsSecondLocation);
					list3 = AStar.findPath(start, goal, obstacles);
					if (list3 == null) {
						return list3;
					} else {
						list3 = resize(list3);
						i = 0;
						j = 0;
						break;
					}
				}
				
			}
		}
		System.out.println("here here");
		return list3;
	}

	private static boolean swapped(Location loc1a, Location loc1b, Location loc2a, Location loc2b) {
		return loc1a.equals(loc2b) && loc1b.equals(loc2a);
	}

	private static List<Location> resize(List<Location> list) {
		if (list.size() > WINDOW) {
			list = list.subList(0, WINDOW);
		}
		return list;
	}

}
