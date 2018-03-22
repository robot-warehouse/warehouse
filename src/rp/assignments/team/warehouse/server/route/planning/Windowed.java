package rp.assignments.team.warehouse.server.route.planning;

import java.util.ArrayList;
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

		int i;
		list2 = swapCheckForTwoRobots(list1, start, goal, obstacles, list2);
		if (list2 == null)
			return list2;

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
						list2 = swapCheckForTwoRobots(list1, start, goal, obstacles, list2);
						if (list2 == null)
							return list2;
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
						list2 = swapCheckForTwoRobots(list1, start, goal, obstacles, list2);
						if (list2 == null)
							return list2;
						i = 0;
						j = 0;
						break;
					}
				}
			}
		}

		return list2;
	}

	private static List<Location> swapCheckForTwoRobots(List<Location> list1, Location start, Location goal,
			List<Location> obstacles, List<Location> list2) {

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
		return list2;
	}

	public static List<Location> findPath(List<Location> list1, List<Location> list2, Location start, Location goal,
			List<Location> obstacles) {
		assert (list1.size() < WINDOW);
		assert (list2.size() < WINDOW);

		List<Location> tempPath = AStar.findPath(start, goal, obstacles);
		List<Location> list3 = new ArrayList<Location>();
		
		if (tempPath != null) {
			// if(list3.size()>WINDOW) {
			// goal = list3.get(list3.size()/2);
			// }
			list3 = tempPath;
			list3 = resize(list3);
		} else {
			list3 = attemptToFindAPartialPath(tempPath, start, goal, obstacles);
			if (list3 == null)
				return null;
		}
			

		// checks if the first and third robot are swapping locations anywhere
		int i;
		tempPath = swapCheckForThreeRobots(list1, list2, start, goal, obstacles, list3);

		if (tempPath == null) {
			list3 = attemptToFindAPartialPath(list3, start, goal, obstacles);
			if (list3 == null)
				return null;
		} else
			list3 = tempPath;

		for (i = 0; i < Math.max(list1.size(), list2.size()) - 1; i++) {
			for (int j = 0; j < list3.size(); j++) {

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
				} else if (i < list2.size()) {
					secondRobotsFirstLocation = (State) list2.get(i);
					secondRobotsSecondLocation = null;
				} else {
					secondRobotsFirstLocation = null;
					secondRobotsSecondLocation = null;
				}
				// reroutes or if not possible returns null

				if (firstRobotsFirstLocation.equals(thirdRobotsFirstLocation)) {
					obstacles.add(firstRobotsFirstLocation);
					tempPath = AStar.findPath(start, goal, obstacles);
					if (tempPath == null) {
						list3 = attemptToFindAPartialPath(list3, start, goal, obstacles);
						if (list3 == null)
							return null;
					} else {
						list3 = tempPath;
						list3 = resize(list3);
						tempPath = swapCheckForThreeRobots(list1, list2, start, goal, obstacles, list3);
						if (tempPath == null) {
							list3 = attemptToFindAPartialPath(list3, start, goal, obstacles);
							if (list3 == null)
								return null;
						} 
						else {
							list3 = tempPath;
							i = 0;
							j = 0;
							break;

						}

					}
				}

				if (firstRobotsSecondLocation.equals(thirdRobotsFirstLocation)) {
					obstacles.add(firstRobotsSecondLocation);
					tempPath = AStar.findPath(start, goal, obstacles);
					if (tempPath == null) {
						list3 = attemptToFindAPartialPath(list3, start, goal, obstacles);
						if (list3 == null)
							return null;
					}  else {
						list3 = tempPath;
						list3 = resize(list3);
						tempPath = swapCheckForThreeRobots(list1, list2, start, goal, obstacles, list3);
						if (tempPath == null) {
							list3 = attemptToFindAPartialPath(list3, start, goal, obstacles);
							if (list3 == null)
								return null;
						} 
						else {
							list3 = tempPath;
							i = 0;
							j = 0;
							break;

						}
					}
				}

				if (secondRobotsFirstLocation.equals(thirdRobotsFirstLocation)) {
					obstacles.add(secondRobotsFirstLocation);
					tempPath = AStar.findPath(start, goal, obstacles);
					if (tempPath == null) {
						list3 = attemptToFindAPartialPath(list3, start, goal, obstacles);
						if (list3 == null)
							return null;
					}  else {
						list3 = tempPath;
						list3 = resize(list3);
						tempPath = swapCheckForThreeRobots(list1, list2, start, goal, obstacles, list3);
						if (tempPath == null) {
							list3 = attemptToFindAPartialPath(list3, start, goal, obstacles);
							if (list3 == null)
								return null;
						} 
						else {
							list3 = tempPath;
							i = 0;
							j = 0;
							break;
						}

					}
				}

				if (secondRobotsSecondLocation.equals(thirdRobotsFirstLocation)) {
					obstacles.add(secondRobotsSecondLocation);
					tempPath = AStar.findPath(start, goal, obstacles);
					if (tempPath == null) {
						list3 = attemptToFindAPartialPath(list3, start, goal, obstacles);
						if (list3 == null)
							return null;
					}  else {
						list3 = tempPath;
						list3 = resize(list3);
						tempPath = swapCheckForThreeRobots(list1, list2, start, goal, obstacles, list3);
						if (tempPath == null) {
							list3 = attemptToFindAPartialPath(list3, start, goal, obstacles);
							if (list3 == null)
								return null;
						} 
						else {
							list3 = tempPath;
							i = 0;
							j = 0;
							break;
						}
					}
				}

			}
		}
		return list3;
	}

	private static List<Location> swapCheckForThreeRobots(List<Location> list1, List<Location> list2, Location start,
			Location goal, List<Location> obstacles, List<Location> list3) {
		int i;
		
		i = 0;
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

		i = 0;
		while (i < list2.size() - 1) {
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
		
		return list3;
	}

	private static List<Location> attemptToFindAPartialPath(List<Location> list, Location start, Location goal,
			List<Location> obstacles) {
		
		System.out.println(list);
		List<Location> tempPath = AStar.findPath(start, goal, obstacles);
		System.out.println(goal);
		
		for (int k = 0; tempPath == null && k < list.size() - 1; k++) {
		
			
			tempPath = AStar.findPath(start, list.get(list.size()-1 - k), obstacles);
			System.out.println(tempPath + "herreeee");
		}
		return tempPath;
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
