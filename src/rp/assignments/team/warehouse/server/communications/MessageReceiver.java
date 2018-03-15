package rp.assignments.team.warehouse.server.communications;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rp.assignments.team.warehouse.server.Location;
import rp.assignments.team.warehouse.shared.communications.Command;

public class MessageReceiver extends Thread {

    private final static Logger logger = LogManager.getLogger(MessageReceiver.class);

    private DataInputStream fromRobot;
    private List<Location> locations;
    private boolean robotFinished;

    /**
     * @param inpStream A stream of communications from the robot.
     */
    public MessageReceiver(InputStream inpStream) {
        this.fromRobot = new DataInputStream(inpStream);
        this.locations = new ArrayList<>();
        this.robotFinished = false;
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
                        rp.assignments.team.warehouse.server.route.planning.State currState = new rp.assignments.team
                                .warehouse.server.route.planning.State(x, y);
                        logger.info("Received " + currState + " from robot.");
                        locations.add(currState);
                        break;
                    case FINISHED_JOB:
                    	System.out.println("finished");
                    	robotFinished = true;
                    case DISCONNECT:
                    	logger.info("Receiver thread ending");
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

    /**
     * Last position of robot as a State : see the State class.
     *
     * @return The last state of the robot.
     */
    public Location getLatestPosition() {
        if (locations.size() == 0) {
            return null;
        } else {
            return locations.get(locations.size() - 1);
        }
    }
    
    public boolean getFinished() {
    	return robotFinished;
    }
    
    public void setFinished(boolean finished) {
    	this.robotFinished = finished;
    }
    
   
    

}
