package rp.assignments.team.warehouse.server.communications;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rp.assignments.team.warehouse.shared.Instruction;
import rp.assignments.team.warehouse.shared.communications.Command;

/**
 * Sends Messages to the robot.
 */
public class MessageSender extends Thread {

    private final static Logger logger = LogManager.getLogger(MessageSender.class);

    private DataOutputStream toRobot;
    private BlockingQueue<Command> commands;
    private List<Instruction> orders;

    /**
     * @param toRobot The stream for communications to the robot.
     * @param commandQueue A queue of commands to send to the robot.
     */
    public MessageSender(OutputStream toRobot, BlockingQueue<Command> commandQueue) {
        logger.info("Constructing sender");
        this.toRobot = new DataOutputStream(toRobot);
        this.commands = commandQueue;
        this.orders = new ArrayList<>();
    }

    private String orderString() {
        String strOrders = "";
        for (Instruction in : this.orders) {
            strOrders += in.toString() + ", ";
        }
        return strOrders;
    }

    public void setOrders(List<Instruction> orders) {
        this.orders = new ArrayList<>(orders);
        logger.info("Received orders: " + orderString());
    }

    @Override
    public void run() {
        while (true) {
            try {
                Command command = commands.take();
                logger.info("Received command " + command.toString());
                switch (command) {
                    case SEND_ORDERS:
                        this.sendOrders();
                        break;
                    case CANCEL:
                        this.sendCancellation();
                        break;
                    case DISCONNECT:
                    default:
                        logger.warn("Unrecognised command send to MessageSender");
                        break;
                }
            } catch (IOException e) {
                logger.fatal("Robot connection unexpectedly terminated.");
                break;
            } catch (InterruptedException e) {
                logger.info("Communication interrupted by Communications manager");
                break;
            }
        }

    }

    public void sendOrders() throws IOException {
        // send a command to give new information
        toRobot.writeUTF(Command.SEND_ORDERS.toString());
        // send each command one by one
        if (orders.isEmpty()) {
            logger.warn("No orders to send");
        }
        for (Instruction order : orders) {
            toRobot.writeUTF(order.toString());
            toRobot.flush();
        }
        toRobot.writeUTF("-1");
        toRobot.flush();

    }

    public void sendCancellation() throws IOException {
        logger.info("Sending cancellation request to robot");
        toRobot.writeUTF(Command.CANCEL.toString());
        toRobot.flush();
    }
    
    public void sendNumberOfPicks(int picks) throws IOException {
    	toRobot.writeUTF(Command.SEND_PICKS.toString());
    	toRobot.writeUTF(Integer.toString(picks));
    	toRobot.flush();
    }
    
    public void disconnect() throws IOException {
    	toRobot.writeUTF(Command.DISCONNECT.toString());
    	toRobot.flush();
    }

}
