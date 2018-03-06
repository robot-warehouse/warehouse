package rp.assignments.team.warehouse.server.route.execution;

import rp.assignments.team.warehouse.server.Location;
import rp.assignments.team.warehouse.server.route.planning.AStar;

import java.util.ArrayList;

public class RouteExecution {

    // TODO Change directions into Enum
    // 0 for left, 1 for right, 2 for forward, 3 for back Works only if we assume
    // that robot starts from certain position. Will explain then meet

    // EasttoWest 4
    // WesttoEast 5
    // NorthtoSouth 6
    // SouthtoNorth 7

    public static int comparisonY = 1;
    public static int comparisonX = 1;
    public static ArrayList<Location> pathForReading;
    public static ArrayList<Integer> xyCoordinates = new ArrayList<Integer>();
    public static ArrayList<Integer> movementCommands = new ArrayList<Integer>(); // James should read this

    // public static ArrayList<Integer> yCoordinates = new ArrayList<Integer>();
    public int direction = 7;

    public RouteExecution() {
        reading();
    }

    public static void main(String[] args) {

        Location start = new Location(2, 2);
        Location goal = new Location(3, 2);
        pathForReading = AStar.findPath(start, goal);

        for (Location x : pathForReading) { System.out.println(x.toString()); }
        for (int i : movementCommands) {
            System.out.println(i);
        }
    }

    public void reading() {

        for (int i = 0; i < pathForReading.size() - 1; i++) {
            Location now = pathForReading.get(i);
            Location next = pathForReading.get(i + 1);
            if (direction == 5) {
                if (now.getX() == next.getX()) {
                    if (now.getY() == next.getY() - 1) {
                        // left
                        movementCommands.add(0);
                        movementCommands.add(2);
                        direction = 7;
                    } else if (now.getY() == next.getY() + 1) {
                        // right
                        movementCommands.add(1);
                        movementCommands.add(2);
                        direction = 6;
                    }
                } else if (now.getY() == next.getY()) {
                    if (now.getX() == next.getX() - 1) {
                        // forward
                        movementCommands.add(2);

                    } else if (now.getX() == next.getX() + 1) {
                        // back
                        movementCommands.add(3);
                        direction = 4;

                    }
                }

            } else if (direction == 6) { //NorthToSouth
                if (now.getX() == next.getX()) {
                    if (now.getY() == next.getY() - 1) {
                        // backward
                        movementCommands.add(3);

                        direction = 7;
                    } else if (now.getY() == next.getY() + 1) {
                        // forward
                        movementCommands.add(2);
                        direction = 6;
                    }
                } else if (now.getY() == next.getY()) {
                    if (now.getX() == next.getX() - 1) {
                        // left
                        movementCommands.add(0);
                        movementCommands.add(2);
                        direction = 5;

                    } else if (now.getX() == next.getX() + 1) {

                        // right
                        movementCommands.add(1);
                        movementCommands.add(2);
                        direction = 4;

                    }
                }

            } else if (direction == 4) { //EasttoWest
                if (now.getX() == next.getX()) {
                    if (now.getY() == next.getY() - 1) {
                        // right
                        movementCommands.add(1);
                        movementCommands.add(2);
                        direction = 7;
                    } else if (now.getY() == next.getY() + 1) {
                        // left
                        movementCommands.add(0);
                        movementCommands.add(2);
                        direction = 6;
                    }
                } else if (now.getY() == next.getY()) {
                    if (now.getX() == next.getX() - 1) {
                        // back
                        movementCommands.add(3);
                        direction = 5;

                    } else if (now.getX() == next.getX() + 1) {
                        // forward
                        movementCommands.add(2);

                    }
                }

            } else if (direction == 7) { //SouthToNorth
                if (now.getX() == next.getX()) {
                    if (now.getY() == next.getY() - 1) {
                        // forward
                        movementCommands.add(2);
                    } else if (now.getY() == next.getY() + 1) {
                        // back
                        movementCommands.add(3);

                        direction = 6;
                    }
                } else if (now.getY() == next.getY()) {
                    if (now.getX() == next.getX() - 1) {
                        // right
                        movementCommands.add(1);
                        movementCommands.add(2);
                        direction = 5;

                    } else if (now.getX() == next.getX() + 1) {
                        // left
                        movementCommands.add(0);
                        movementCommands.add(2);
                        direction = 4;

                    }
                }
            }
        }
    }
}
