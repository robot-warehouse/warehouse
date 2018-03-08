package rp.assignments.team.warehouse.server.route.execution;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rp.assignments.team.warehouse.server.Location;

import java.util.ArrayList;

public class RouteExecution {

    // TODO Change directions into Enum
    // 0 for left, 1 for right, 2 for forward, 3 for back Works only if we assume
    // that robot starts from certain position. Will explain then meet

    private final static Logger logger = LogManager.getLogger(RouteExecution.class);

    public static ArrayList<Location> pathForReading;
    public static ArrayList<Integer> xyCoordinates = new ArrayList<Integer>();

    //Robot motion planner needs to read this array list and instruct the robot accordingly.
    // 0 for left, 1 for right, 2 for forward, 3 for back
    public ArrayList<Integer> movementCommands = new ArrayList<Integer>();

    //at the beginning the robot faces a particular direction(west to east) so we initialize this variable here
    private Facing facingDirection = Facing.East;

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

            // loop through the array list containing the route.
            for (int i = 0; i < pathForReading.size() - 1; i++) {

                // since the robot's motion is relative to the previous coordinate at any time two coordinates need to
                // be considered.
                Location now = pathForReading.get(i);
                Location next = pathForReading.get(i + 1);

                // left and right is relative to the direction in which the robot is facing so every time we need to
                // check in which direction the robot is facing.
                if (facingDirection == Facing.East) {

                    //if the x coordinate is same, the robot needs to move along the Y axis
                    if (now.getX() == next.getX()) {

                        //this condition implies that the robot needs to move along the positive Y axis
                        if (now.getY() == next.getY() - 1) {

                            //considering the current direction of the robot this means it needs to turn left.
                            movementCommands.add(Instruction.LEFT);
                            //update the direction towards which the robot is facing.
                            facingDirection = Facing.North;
                        } else if (now.getY() == next.getY() + 1) {
                            movementCommands.add(Instruction.RIGHT);
                            facingDirection = Facing.South;
                        }
                    } else if (now.getY() == next.getY()) {
                        if (now.getX() == next.getX() - 1) {
                            movementCommands.add(Instruction.FORWARDS);
                        } else if (now.getX() == next.getX() + 1) {
                            movementCommands.add(Instruction.BACKWARDS);
                            facingDirection = Facing.West;
                        }
                    }
                } else if (facingDirection == Facing.South) {
                    if (now.getX() == next.getX()) {
                        if (now.getY() == next.getY() - 1) {
                            movementCommands.add(Instruction.BACKWARDS);
                            facingDirection = Facing.North;
                        } else if (now.getY() == next.getY() + 1) {
                            movementCommands.add(Instruction.FORWARDS);
                            facingDirection = Facing.South;
                        }
                    } else if (now.getY() == next.getY()) {
                        if (now.getX() == next.getX() - 1) {
                            movementCommands.add(Instruction.LEFT);
                            facingDirection = Facing.East;
                        } else if (now.getX() == next.getX() + 1) {
                            movementCommands.add(Instruction.RIGHT);
                            facingDirection = Facing.West;
                        }
                    }
                } else if (facingDirection == Facing.West) {
                    if (now.getX() == next.getX()) {
                        if (now.getY() == next.getY() - 1) {
                            movementCommands.add(Instruction.RIGHT);
                            facingDirection = Facing.North;
                        } else if (now.getY() == next.getY() + 1) {
                            movementCommands.add(Instruction.LEFT);
                            facingDirection = Facing.South;
                        }
                    } else if (now.getY() == next.getY()) {
                        if (now.getX() == next.getX() - 1) {
                            movementCommands.add(Instruction.BACKWARDS);
                            facingDirection = Facing.East;
                        } else if (now.getX() == next.getX() + 1) {
                            movementCommands.add(Instruction.FORWARDS);
                        }
                    }
                } else if (facingDirection == Facing.North) {
                    if (now.getX() == next.getX()) {
                        if (now.getY() == next.getY() - 1) {
                            movementCommands.add(Instruction.FORWARDS);
                        } else if (now.getY() == next.getY() + 1) {
                            movementCommands.add(Instruction.BACKWARDS);
                            facingDirection = Facing.South;
                        }
                    } else if (now.getY() == next.getY()) {
                        if (now.getX() == next.getX() - 1) {
                            movementCommands.add(Instruction.RIGHT);
                            facingDirection = Facing.East;
                        } else if (now.getX() == next.getX() + 1) {
                            movementCommands.add(Instruction.LEFT);
                            facingDirection = Facing.West;
                        }
                    }
                }
            }

            movementCommands.add(Instruction.STOP);

            logger.info("Finished converting");
        } else {
            logger.info("no path is available");
        }

    }
}
