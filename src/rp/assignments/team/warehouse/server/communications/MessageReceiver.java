package rp.assignments.team.warehouse.server.communications;

import java.io.DataInputStream;
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
    
    private Robot robot;
    
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
            try {
                Command command = Command.strToCommand((fromRobot.readUTF()));
                switch (command) {
                    case SEND_POSITION:
                        int x = Integer.valueOf(fromRobot.readUTF()); // get x
                        int y = Integer.valueOf(fromRobot.readUTF()); // get y
                        
                        Location currentLocation = new Location(x, y);
                        this.robot.setCurrentLocation(currentLocation);
                        
                        logger.info("Received " + currentLocation + " from robot.");
                        break;
                    case FINISHED_JOB:
                    	System.out.println("finished");
                    	this.robot.setHasFinishedJob(true);
                    case DISCONNECT:
                    	logger.info("Receiver thread ending");
                    	break;
                    case SEND_FACING:
                    	try {
                    		Facing facing = Facing.valueOf(fromRobot.readUTF());
                    		robot.setCurrentFacingDirection(facing);
                    	}
                    	catch(IllegalArgumentException e) {
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
                logger.error("Invalid/unrecognised command sent from robot, waiting for next command.");
            } 
        }
    }
}
