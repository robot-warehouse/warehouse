package rp.assignments.team.warehouse.server.communications;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rp.assignments.team.warehouse.server.Robot;
import rp.assignments.team.warehouse.shared.Facing;
import rp.assignments.team.warehouse.shared.Instruction;
import rp.assignments.team.warehouse.shared.communications.Command;

/**
 * Manages communications between a robot and the pc
 */
public class CommunicationsManager {

    private final static Logger logger = LogManager.getLogger(CommunicationsManager.class);

    /** Reference to the message sender to the robot */
    private MessageSender sender;

    /** Reference to the receiver thread from the robot */
    private MessageReceiver receiver;

    /** Reference to the connection to the robot */
    private NXTComm communicator;

    /** Commands being sent to the robot */
    private BlockingQueue<Command> commands;

    /** Is the robot connected? */
    private boolean connected;

    /**
     * Constructs a new instance of CommunicationsManager with the given robot name/address.
     *
     * @param robot The robot the communications manager is connecting to
     */
    public CommunicationsManager(Robot robot) {
        String name = robot.getRobotInfo().getName();
        String address = robot.getRobotInfo().getAddress();

        logger.info("Initialising communications with " + name + " address " + address + ".");
        NXTInfo nxtInf = new NXTInfo(NXTCommFactory.BLUETOOTH, name, address);

        try {
            this.connected = false;
            this.communicator = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);

            if (this.communicator.open(nxtInf)) {
                this.commands = new LinkedBlockingQueue<>();
                this.sender = new MessageSender(this.communicator.getOutputStream(), this.commands);
                this.receiver = new MessageReceiver(this.communicator.getInputStream(), robot);
                this.connected = true;

                this.receiver.start();
                this.sender.start();

                logger.info("Connected to robot " + nxtInf.name);
            }
        } catch (NXTCommException e) {
            logger.fatal("Could not connect to robot");
            e.printStackTrace();
        }

    }

    /**
     * Return whether the server is currently connected with the robot.
     */
    public boolean isConnected() {
        // should be called to check whether server is working before this class is used
        return this.connected;
    }

    /**
     * Disconnect from the robot.
     */
    public void stopServer() {
        commands.offer(Command.DISCONNECT);

        try {
            this.receiver.join();
            this.sender.join();
            this.communicator.close();
        } catch (InterruptedException e) {
            logger.error("Unable to stop server threads");
        } catch (IOException e) {
            logger.error("Error disconnecting the server");
        }

        this.connected = false;
    }

    /**
     * Send orders of where the robot should go
     *
     * @param orders What operations the robot should perform to reach goal. See route execution
     */
    public void sendOrders(List<Instruction> orders) {
        this.sender.setOrders(orders);
        this.commands.offer(Command.SEND_ORDERS);
    }

    /**
     * Sends the robot location to the robot
     *
     * @param x The x coordinate of the robot
     * @param y The y coordinate of the robot
     */
    public void sendPosition(int x, int y) {
        try {
            this.sender.sendLocation(x, y);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends the facing of the robot to the robot
     *
     * @param facing The facing of the robot
     */
    public void sendFacing(Facing facing) {
        try {
            this.sender.sendFacing(facing);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends the number of items to pickup to the robot
     *
     * @param picks The number of items to pickup
     */
    public void sendNumOfPicks(int picks) {
        try {
            this.sender.sendNumberOfPicks(picks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tell the robot to cancel the current set of orders
     */
    public void sendCancellation() {
        this.commands.offer(Command.CANCEL);
    }
}
