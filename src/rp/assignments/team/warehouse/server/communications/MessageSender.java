package rp.assignments.team.warehouse.server.communications;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rp.assignments.team.warehouse.shared.Facing;
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
                Command command = this.commands.take();
                logger.info("Received command " + command.toString());
                switch (command) {
                    case SEND_ORDERS:
                        this.sendOrders();
                        break;
                    case CANCEL:
                        this.sendCancellation();
                        break;
                    case DISCONNECT:
                        this.disconnect();
                        break;
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

    /**
     * Sends SEND_ORDERS command to robot
     *
     * @throws IOException
     */
    public void sendOrders() throws IOException {
        // send a command to give new information
        toRobot.writeUTF(Command.SEND_ORDERS.toString());
        // send each command one by one
        if (orders.isEmpty()) {
            logger.warn("No orders to send");
        }
        for (Instruction order : orders) {
            toRobot.writeUTF(order.toString());
        }
        toRobot.writeUTF("-1");
        toRobot.flush();

    }

    /**
     * Sends CANCEL command to robot cancelling the current job
     *
     * @throws IOException In case of error with connection
     */
    public void sendCancellation() throws IOException {
        logger.info("Sending cancellation request to robot");
        toRobot.writeUTF(Command.CANCEL.toString());
        toRobot.flush();
    }

    /**
     * Sends SEND_PICKS command to robot along with the number of picks
     *
     * @param picks The number of items to pickup
     * @throws IOException In case of error with connection
     */
    public void sendNumberOfPicks(int picks) throws IOException {
        toRobot.writeUTF(Command.SEND_PICKS.toString());
        toRobot.writeUTF(Integer.toString(picks));
        toRobot.flush();
    }
    
    public void sendLocation(int x, int y) throws IOException {
    	toRobot.writeUTF(Command.SEND_POSITION.toString());
    	toRobot.writeUTF(Integer.toString(x));
    	toRobot.writeUTF(Integer.toString(y));
    	toRobot.flush();
    }
    
    public void sendFacing(Facing facing) throws IOException{
    	toRobot.writeUTF(Command.SEND_FACING.toString());
    	toRobot.writeUTF(facing.toString());
    	System.out.println("Sent " + facing.toString());
    	toRobot.flush();
    }

    /**
     * Sends DISCONNECT command to robot
     *
     * @throws IOException In case of error with connection
     */
    public void disconnect() throws IOException {
        toRobot.writeUTF(Command.DISCONNECT.toString());
        toRobot.flush();
    }

}
