package rp.assignments.team.warehouse.server.route.planning;

import java.util.List;

import rp.assignments.team.warehouse.server.Location;

public class TwoRobots {

    // ---------At the beginning---------
    // priority is given to r1 and route is changed for r2

    static List<Location> findPath(List<Location> l1, Location start, Location goal, List<Location> obstacles) {
        long begin = System.currentTimeMillis();

        List<Location> l2 = AStar.findPath(start, goal, obstacles);

        //checks if the goal position of the first robot is an obstacle.
        if (l2.size() > l1.size()) {
            if (l2.subList(l1.size(), l2.size() - 1).contains(l1.get(l1.size() - 1))) {
                System.out.println("goal is an obstacle");
                obstacles.add(l1.get(l1.size() - 1));
                l2 = AStar.findPath(start, goal, obstacles);
            }
        }


        int i = 0;
        while (i < l2.size() - 1) {
            for (int j = 0; j < l1.size() - 1; j++) {

                State loc1a = (State) l2.get(i);
                State loc2a = (State) l1.get(j);
                State loc1b = (State) l2.get(i + 1);
                State loc2b = (State) l1.get(j + 1);
                //checks if the robots are swapping positions anywhere.
                if (swapped(loc1a, loc1b, loc2a, loc2b)) {
                    //checks if it is possible to step aside to prevent swapping.
                    if (!Data.getRow().contains(loc1a)) {
                        System.out.println(loc1a.toString());
                        System.out.println("check4");
                        List<State> neighbours = loc1a.getNeighbours(start, goal);
                        for (State each : neighbours) {
                            if (!obstacles.contains(each)) {
                                l2.add(i + 1, each);
                                l2.add(i + 2, loc1a);
                                break;
                            }
                        }
                    }//if it is not possible to step aside then reroute
                    else {
                        if (!loc1a.equals(start) && !loc1a.equals(goal)) {
                            obstacles.add(loc1a);
                        } else if (!loc1b.equals(start) && !loc1b.equals(goal)) { obstacles.add(loc1b); }
                        l2 = AStar.findPath(start, goal, obstacles);
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
            State loc2 = (State) l2.get(i);
            State loc1 = (State) l1.get(i);
            //adds a wait to prevent collisions
            if (loc1.equals(loc2)) {
                State temp = (State) l2.get(i - 1);
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
}
