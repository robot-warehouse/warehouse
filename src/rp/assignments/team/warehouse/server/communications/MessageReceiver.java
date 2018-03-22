package rp.assignments.team.warehouse.server.communications;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rp.assignments.team.warehouse.server.Location;
import rp.assignments.team.warehouse.server.Robot;
import rp.assignments.team.warehouse.shared.Facing;
import rp.assignments.team.warehouse.shared.communications.Command;

public class MessageReceiver extends Thread {

    private final static Logger logger = LogManager.getLogger(MessageReceiver.class);

    /** Reference to the robot to update attributes */
    private Robot robot;

    /** Data stream from the robot */
    private DataInputStream fromRobot;

    /**
     * @param robot Reference to the robot instance to update information
     * @param inputStream A stream of communications from the robot.
     */
    public MessageReceiver(InputStream inputStream, Robot robot) {
        this.robot = robot;
        this.fromRobot = new DataInputStream(inputStream);

        logger.info("Constructing Receiver.");
    }

    @Override
    public void run() {
        while (true) {
        	String robotCommand = "";
            try {
            	
            	robotCommand = this.fromRobot.readUTF();
                Command command = Command.strToCommand(robotCommand);
                switch (command) {
                    case SEND_POSITION:
                        int x = Integer.valueOf(this.fromRobot.readUTF()); // get x
                        int y = Integer.valueOf(this.fromRobot.readUTF()); // get y

                        Location currentLocation = new Location(x, y);
                        this.robot.setCurrentLocation(currentLocation);

                        logger.info("Received " + currentLocation + " from robot " + this.robot.getName() + ".");
                        break;
                    case FINISHED_JOB:
                        this.robot.finishedJob();
                        logger.info("Robot has finished the current job");
                        break;
                    case DISCONNECT:
                        logger.info("Receiver thread ending");
                        break;
                    case SEND_FACING:
                        try {
                            Facing facing = Facing.valueOf(this.fromRobot.readUTF());
                            this.robot.setCurrentFacingDirection(facing);
                        } catch (IllegalArgumentException e) {
                            logger.error("Robot attempted to send it's direction, but it sent an invalid direction ");
                        }
                        break;
                    default:
                        logger.warn("Robot set unrecognised command");
                        break;
                }

            } catch (IOException e) {
                logger.fatal("Failed to communicate with robot, stopping thread...");
                e.printStackTrace();
                break;
            } catch (IllegalArgumentException e) {
                logger.error("Invalid/unrecognised command sent from robot: ." + robotCommand);
            }
        }
    }
}
