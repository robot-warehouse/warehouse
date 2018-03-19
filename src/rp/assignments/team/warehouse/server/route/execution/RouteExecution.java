package rp.assignments.team.warehouse.server.route.execution;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rp.assignments.team.warehouse.server.Location;
import rp.assignments.team.warehouse.shared.Facing;
import rp.assignments.team.warehouse.shared.Instruction;

public class RouteExecution {

    private final static Logger logger = LogManager.getLogger(RouteExecution.class);

    public static ArrayList<Instruction> convertCoordinatesToInstructions(Facing facingDirection, List<Location> path) {

        logger.info("Start Convert coordinates into numerated actions");

        ArrayList<Instruction> movementCommands = new ArrayList<>();

        // loop through the array list containing the route.
        for (int i = 0; i < path.size() - 1; i++) {

            // since the robot's motion is relative to the previous coordinate at any time two coordinates need to
            // be considered.
            Location now = path.get(i);
            Location next = path.get(i + 1);

            // left and right is relative to the direction in which the robot is facing so every time we need to
            // check in which direction the robot is facing.
            switch (facingDirection) {
                case EAST:
                    // Stays at the same place
                    if (now.getX() == next.getX() && now.getY() == next.getY()) {
                        movementCommands.add(Instruction.STOP);
                    }
                    //if the x coordinate is same, the robot needs to move along the Y axis
                    else if (now.getX() == next.getX()) {
                        //this condition implies that the robot needs to move along the positive Y axis
                        if (now.getY() == next.getY() - 1) {
                            //considering the current direction of the robot this means it needs to turn left.
                            movementCommands.add(Instruction.LEFT);

                            //update the direction towards which the robot is facing.
                            facingDirection = Facing.NORTH;
                        } else if (now.getY() == next.getY() + 1) {
                            movementCommands.add(Instruction.RIGHT);
                            facingDirection = Facing.SOUTH;
                        }
                    // if the y coordinate is the same, the robot needs to move along the X axis
                    } else if (now.getY() == next.getY()) {
                        if (now.getX() == next.getX() - 1) {
                            movementCommands.add(Instruction.FORWARDS);
                        } else if (now.getX() == next.getX() + 1) {
                            movementCommands.add(Instruction.BACKWARDS);
                            facingDirection = Facing.WEST;
                        }
                    }
                    break;
                case SOUTH:
                    if (now.getX() == next.getX() && now.getY() == next.getY()) { // Stays at the same place
                        movementCommands.add(Instruction.STOP);
                    } else if (now.getX() == next.getX()) {
                        if (now.getY() == next.getY() - 1) {
                            movementCommands.add(Instruction.BACKWARDS);
                            facingDirection = Facing.NORTH;
                        } else if (now.getY() == next.getY() + 1) {
                            movementCommands.add(Instruction.FORWARDS);
                            facingDirection = Facing.SOUTH;
                        }
                    } else if (now.getY() == next.getY()) {
                        if (now.getX() == next.getX() - 1) {
                            movementCommands.add(Instruction.LEFT);
                            facingDirection = Facing.EAST;
                        } else if (now.getX() == next.getX() + 1) {
                            movementCommands.add(Instruction.RIGHT);
                            facingDirection = Facing.WEST;
                        }
                    }
                    break;
                case WEST:
                    if (now.getX() == next.getX() && now.getY() == next.getY()) { // Stays at the same place
                        movementCommands.add(Instruction.STOP);
                    } else if (now.getX() == next.getX()) {
                        if (now.getY() == next.getY() - 1) {
                            movementCommands.add(Instruction.RIGHT);
                            facingDirection = Facing.NORTH;
                        } else if (now.getY() == next.getY() + 1) {
                            movementCommands.add(Instruction.LEFT);
                            facingDirection = Facing.SOUTH;
                        }
                    } else if (now.getY() == next.getY()) {
                        if (now.getX() == next.getX() - 1) {
                            movementCommands.add(Instruction.BACKWARDS);
                            facingDirection = Facing.EAST;
                        } else if (now.getX() == next.getX() + 1) {
                            movementCommands.add(Instruction.FORWARDS);
                        }
                    }
                    break;
                case NORTH:
                    if (now.getX() == next.getX() && now.getY() == next.getY()) { // Stays at the same place
                        movementCommands.add(Instruction.STOP);
                    } else if (now.getX() == next.getX()) {
                        if (now.getY() == next.getY() - 1) {
                            movementCommands.add(Instruction.FORWARDS);
                        } else if (now.getY() == next.getY() + 1) {
                            movementCommands.add(Instruction.BACKWARDS);
                            facingDirection = Facing.SOUTH;
                        }
                    } else if (now.getY() == next.getY()) {
                        if (now.getX() == next.getX() - 1) {
                            movementCommands.add(Instruction.RIGHT);
                            facingDirection = Facing.EAST;
                        } else if (now.getX() == next.getX() + 1) {
                            movementCommands.add(Instruction.LEFT);
                            facingDirection = Facing.WEST;
                        }
                    }
                    break;
            }
        }

        logger.info("Finished converting");

        return movementCommands;
    }
}
