package rp.assignments.team.warehouse.server.communications;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rp.assignments.team.warehouse.shared.communications.Command;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MessageReceiver extends Thread {

    private final static Logger logger = LogManager.getLogger(MessageReceiver.class);

    private DataInputStream fromRobot;
    private List<rp.assignments.team.warehouse.server.route.planning.State> locations;

    public MessageReceiver(InputStream inpStream) {
        this.fromRobot = new DataInputStream(inpStream);
        this.locations = new ArrayList<>();
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
                    // add more commands to switch on if neccessary
                    default:
                        logger.warn("Robot set unrecognised command");
                        break;
                }

            } catch (IOException e) {
                logger.fatal("Failed to communicate with robot, stopping thread...");
                e.printStackTrace();
                break;
            } catch (IllegalArgumentException e) {
                logger.error("Invalid/unrecgonised command sent from robot, waiting for next command.");
            }
        }
    }

    /**
     * Last position of robot as a State : see the State class.
     *
     * @return The last state of the robot.
     */
    public rp.assignments.team.warehouse.server.route.planning.State getLatestPosition() {
        if (locations.size() == 0) {
            return null;
        } else {
            return locations.get(locations.size() - 1);
        }
    }

}
