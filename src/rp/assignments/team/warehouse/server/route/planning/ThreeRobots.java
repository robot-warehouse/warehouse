package rp.assignments.team.warehouse.server.route.planning;

import java.util.ArrayList;
import java.util.List;

import rp.assignments.team.warehouse.server.Location;

public class ThreeRobots {

    static List<Location> findPath(List<Location> l1, List<Location> l2, Location start, Location goal,
                                   List<Location> obstacles) {
        // Stores the route for third robot.
        // used to store the original route when the robot moves to the nearest possible
        // location.
        List<Location> l3 = AStar.findPath(start, goal, obstacles);

        System.out.println(l3 + "b4");

        l3 = swapAndGoalCheck(l1, l2, start, goal, l3, obstacles);

        l3 = collisionCheck(l1, l2, start, goal, l3, obstacles);

        return l3;
    }

    private static List<Location> collisionCheck(List<Location> l1, List<Location> l2, Location start, Location goal,
                                                 List<Location> l3, List<Location> obstacles) {
        int i = 0;
        // loops through all three lists checking for collisions.
        while (i < l3.size()) {
            // to keep track of if we ever need to reroute.
            boolean reroute = true;
            // the location of first robot
            State loc1;
            // the location of second robot
            State loc2;
            // the location of third robot
            State loc3;

            if (i < l1.size()) { loc1 = (State) l1.get(i); } else { loc1 = null; }
            if (i < l2.size()) { loc2 = (State) l2.get(i); } else { loc2 = null; }
            if (i < l3.size()) { loc3 = (State) l3.get(i); } else { loc3 = null; }

            State prev;
            if (i > 0 && i < l3.size()) {
                prev = (State) l3.get(i - 1);
            } else {
                prev = loc3;
            }

            // the third robot waits if first and third robot have the same location and if
            // it's waiting would not affect the second robot.
            if (loc1.equals(loc3) && !loc2.equals(prev)) {
                l3.add(i - 1, prev);
                reroute = false;
            } // the third robot waits if second and third robot have the same location and if
            // it's waiting would not affect the first robot.
            else if (loc2.equals(loc3) && !loc1.equals(prev)) {
                l3.add(i - 1, prev);
                reroute = false;
            } // if waiting at the current position is blocking other robot's route move to a
            // neighbouring conflict-free position.
            else if (loc1.equals(loc3)) {
                List<State> neighbours = new ArrayList<>();
                neighbours = loc3.getNeighbours(start, goal);
                for (State each : neighbours) {
                    if (!loc2.equals(each) && !obstacles.contains(each)) {
                        l3.add(i - 1, each);
                        l3.add(i, loc3);
                        reroute = false;
                        break;
                    }
                }
            } // if waiting at the current position is blocking other robot's route move to a
            // neighbouring conflict-free position.
            else if (loc2.equals(loc3)) {
                List<State> neighbours = new ArrayList<>();
                neighbours = loc3.getNeighbours(start, goal);
                for (State each : neighbours) {
                    if (!loc1.equals(each) && !obstacles.contains(each)) {
                        l3.add(i - 1, each);
                        l3.add(i, loc3);
                        reroute = false;
                        break;
                    }
                }
            } // if no neighbouring conflict-free location is found, reroute
            else if (reroute) {
                obstacles.add(loc3);
                if (AStar.findPath(start, goal, obstacles) != null) {
                    l3 = AStar.findPath(start, goal, obstacles);
                    l3 = swapAndGoalCheck(l1, l2, start, goal, l3, obstacles);
                    // go through the collision check from the beginning.
                    i = 0;
                } else {
                    // indefinite wait
                    for (int k = 0; AStar.findPath(start, goal, obstacles) != null; k++) {
                        l3 = AStar.findPath(start, l3.get(l3.indexOf(l1.get(l1.size() - 1)) - k), obstacles);
                    }

                }

            }

            i++;
        }

        return l3;
    }

    static List<Location> swapAndGoalCheck(List<Location> l1, List<Location> l2, Location start, Location goal,
                                           List<Location> l3, List<Location> obstacles) {
        List<Location> temporary;
        boolean check = true;
        int n = 1;
        int counter = 0;
        int j = 0;
        while (check) {
            switch (n) {

                case 1:
                    // checks if the goal position of the first robot could be an obstacle
                    if (l3.size() > l1.size()) {
                        if (l3.subList(l1.size(), l3.size() - 1).contains(l1.get(l1.size() - 1))) {
                            System.out.println("first goal is an obstacle");
                            obstacles.add(l1.get(l1.size() - 1));

                            List<Location> tempPath = AStar.findPath(start, goal, obstacles);
                            if (tempPath != null) { l3 = tempPath; } else {
                                // indefinite wait
                                for (int k = 0; AStar.findPath(start, goal, obstacles) != null; k++) {
                                    l3 = AStar.findPath(start, l3.get(l3.indexOf(l1.get(l1.size() - 1)) - k),
                                                        obstacles);
                                }
                            }
                        }
                    }

                case 2:
                    // checks if the goal position of the second robot could be an obstacle
                    if (l3.size() > l2.size()) {
                        if (l3.subList(l2.size(), l3.size() - 1).contains(l2.get(l2.size() - 1))) {
                            System.out.println("second goal is an obstacle");
                            obstacles.add(l2.get(l2.size() - 1));
                            if (AStar.findPath(start, goal, obstacles) != null) {
                                l3 = AStar.findPath(start, goal, obstacles);
                            } else {
                                // indefinite wait
                                for (int k = 0; AStar.findPath(start, goal, obstacles) != null; k++) {
                                    l3 = AStar.findPath(start, l3.get(l3.indexOf(l2.get(l2.size() - 1)) - k),
                                                        obstacles);
                                }
                            }
                        }
                    }

                case 3:
                    // checks if the first and third robot are swapping locations anywhere
                    int i = 0;
                    while (i < l3.size() - 1) {
                        for (j = 0; j < l1.size() - 1; j++) {

                            State loc1a = (State) l3.get(i);
                            State loc2a = (State) l1.get(j);
                            State loc1b = (State) l3.get(i + 1);
                            State loc2b = (State) l1.get(j + 1);
                            // if they swap check if there is space to step aside.
                            if (swapped(loc1a, loc1b, loc2a, loc2b)) {
                                System.out.println("swapped 1");
                                if (!Data.getRow().contains(loc1a)) {
                                    System.out.println(loc1a.toString());
                                    System.out.println("check4");
                                    List<State> neighbours = loc1a.getNeighbours(start, goal);
                                    for (State each : neighbours) {
                                        if (!obstacles.contains(each)) {
                                            l3.add(i + 1, each);
                                            l3.add(i + 2, loc1a);
                                            break;
                                        }
                                    }
                                } // if there is no place to step aside, reroute with that particular location as
                                // an obstacle.
                                else {
                                    if (!loc1a.equals(start) && !loc1a.equals(goal)) {
                                        obstacles.add(loc1a);
                                    } else if (!loc1b.equals(start) && !loc1b.equals(goal)) { obstacles.add(loc1b); }
                                    System.out.println("Rerouting..");
                                    if (AStar.findPath(start, goal, obstacles) != null) {
                                        l3 = AStar.findPath(start, goal, obstacles);
                                    } else {
                                        for (int k = 0; AStar.findPath(start, goal, obstacles) != null; k++) {
                                            l3 = AStar.findPath(start, l3.get(l3.indexOf(goal) - k), obstacles);
                                        }
                                    }
                                    System.out.println(l3);
                                    // dont delete this obstacle because we want this to be considered if there is a
                                    // need to reroute due to swapping with second robot.
                                    // obs3.remove(obs3.size() - 1);
                                    // restart the loop and recheck for swapping because we have got a new route
                                    // now.
                                    i = 0;
                                    j = 0;
                                }

                            }
                        }
                        i++;
                    }

                case 4:
                    // similarly, check if the second and third robot swap positions anywhere
                    check = false;
                    i = 0;
                    while (i < l3.size() - 1) {
                        for (j = 0; j < l2.size() - 1; j++) {

                            State loc1a = (State) l3.get(i);
                            State loc2a = (State) l2.get(j);
                            State loc1b = (State) l3.get(i + 1);
                            State loc2b = (State) l2.get(j + 1);

                            if (swapped(loc1a, loc1b, loc2a, loc2b)) {
                                if (!Data.getRow().contains(loc1a)) {
                                    List<State> neighbours = loc1a.getNeighbours(start, goal);
                                    for (State each : neighbours) {
                                        if (!obstacles.contains(each)) {
                                            l3.add(i + 1, each);
                                            l3.add(i + 2, loc1a);
                                            break;
                                        }
                                    }
                                } else {
                                    if (!loc1a.equals(start) && !loc1a.equals(goal)) {
                                        obstacles.add(loc1a);
                                    } else if (!loc1b.equals(start) && !loc1b.equals(goal)) { obstacles.add(loc1b); }
                                    if (AStar.findPath(start, goal, obstacles) != null) {
                                        l3 = AStar.findPath(start, goal, obstacles);
                                    } else {
                                        // indefinite wait
                                        for (int k = 0; AStar.findPath(start, goal, obstacles) != null; k++) {
                                            l3 = AStar.findPath(start, l3.get(l3.indexOf(goal) - k), obstacles);
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
                    // resolves an infinite loop of rerouting by rerouting till the nearest possible
                    // collision free position.
                    if (counter >= 3) {
                        // stores the original route.
                        temporary = l3;
                        l3.clear();
                        for (Location each : temporary) {
                            if (!Data.getRow().contains(each) && !obstacles.contains(each)) {
                                l3.add(each);
                            } else { break; }
                        }
                        // if this loop does not create a new route, delete the last location (helps to
                        // get rid of the infinite loop)
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
