package rp.assignments.team.warehouse.server.route.execution;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rp.assignments.team.warehouse.server.Location;

import java.util.ArrayList;

public class RouteExecution {

    // TODO Change directions into Enum
    // 0 for left, 1 for right, 2 for forward, 3 for back Works only if we assume
    // that robot starts from certain position. Will explain then meet

    // EasttoWest 4
    // WesttoEast 5
    // NorthtoSouth 6
    // SouthtoNorth 7

    private final static Logger logger = LogManager.getLogger(RouteExecution.class);

    public static ArrayList<Location> pathForReading;
    public static ArrayList<Integer> xyCoordinates = new ArrayList<Integer>();

    //Robot motion planner needs to read this arraylist and instruct the robot accordingly.
    // 0 for left, 1 for right, 2 for forward, 3 for back
    public ArrayList<Integer> movementCommands = new ArrayList<Integer>();
    //at the beginning the robot faces a particular direction(west to east) so we initialize this variable here
    public int direction = 5;

    public RouteExecution() {
        reading();
    }

    // James should read this because this ArrayList will return coordinates
    public ArrayList<Integer> getMovements() {
        return movementCommands;
    }

    public void reading() {

        logger.info(" Start Convert coordinates into numerated actions");

        if (pathForReading != null) {
            //loop through the arraylist containing the route.
            for (int i = 0; i < pathForReading.size() - 1; i++) {
                //since the robot's motion is relative to the previous coordinate at any time two coordinates need to
				// be considered.
                Location now = pathForReading.get(i);
                Location next = pathForReading.get(i + 1);
                //left and right is relative to the direction in which the robot is facing so every time we need to
				// check in which direction the robot is facing.
                if (direction == 5) {
                    //if the x coordinate is same, the robot needs to move along the Y axis
                    if (now.getX() == next.getX()) {
                        //this condition implies that the robot needs to move along the positive Y axis
                        if (now.getY() == next.getY() - 1) {
                            //considering the current direction of the robot this means it needs to turn left.
                            // left
                            movementCommands.add(0);//0 = left
                            //update the direction towards which the robot is facing.
                            direction = 7;
                        } else if (now.getY() == next.getY() + 1) {
                            // right
                            movementCommands.add(1);
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

                } else if (direction == 6) { // NorthToSouth
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

                            direction = 5;

                        } else if (now.getX() == next.getX() + 1) {

                            // right
                            movementCommands.add(1);
                            direction = 4;

                        }
                    }

                } else if (direction == 4) { // EasttoWest
                    if (now.getX() == next.getX()) {
                        if (now.getY() == next.getY() - 1) {
                            // right
                            movementCommands.add(1);

                            direction = 7;
                        } else if (now.getY() == next.getY() + 1) {
                            // left
                            movementCommands.add(0);

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

                } else if (direction == 7) { // SouthToNorth
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

                            direction = 5;

                        } else if (now.getX() == next.getX() + 1) {
                            // left
                            movementCommands.add(0);

                            direction = 4;

                        }
                    }
                }
            }
            logger.info("Finished converting");
        } else {
            logger.info("no path is available");
        }

    }
}
