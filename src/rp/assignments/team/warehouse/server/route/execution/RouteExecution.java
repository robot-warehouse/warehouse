package rp.assignments.team.warehouse.server.route.execution;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rp.assignments.team.warehouse.server.Facing;
import rp.assignments.team.warehouse.server.Location;

public class RouteExecution {

    private final static Logger logger = LogManager.getLogger(RouteExecution.class);

    public static ArrayList<Instruction> convertCoordinatesToInstructions(Facing facingDirection, ArrayList<Location> path) {

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
        
		return movementCommands;

    }
}
