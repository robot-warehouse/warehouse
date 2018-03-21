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

    private MessageSender sender;
    private MessageReceiver receiver;
    private Robot robot;

    private NXTInfo nxtInf;
    private NXTComm communicator;

    private BlockingQueue<Command> commands;

    private boolean connected;

    /**
     * Constructs a new instance of CommunicationsManager with the given robot
     * name/address.
     *
     * @param robot The robot the communications manager is connecting to
     */
    public CommunicationsManager(Robot robot) {
        String name = robot.getRobotInfo().getName();
        String address = robot.getRobotInfo().getAddress();

        logger.info("Initialising communications with " + name + " address " + address + ".");
        this.nxtInf = new NXTInfo(NXTCommFactory.BLUETOOTH, name, address);
        this.robot = robot;

        try {
            this.connected = false;
            this.communicator = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);

            if (communicator.open(nxtInf)) {
                this.commands = new LinkedBlockingQueue<>();
                this.sender = new MessageSender(this.communicator.getOutputStream(), this.commands);
                this.receiver = new MessageReceiver(this.communicator.getInputStream(), robot);
                this.connected = true;

                logger.info("Connected to robot " + this.nxtInf.name);
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
     * Start the server threads
     */
    public void startServer() {
        receiver.start();
        sender.start();
    }

    /**
     * Disconnect from the robot.
     */
    public void stopServer() {
        commands.offer(Command.DISCONNECT);
        try {
            receiver.join();
            sender.join();
            communicator.close();
        } catch (InterruptedException e) {
            logger.error("Unable to stop server threads");
        } catch (IOException e) {
            logger.error("Error disconnecting the server");
        }
        connected = false;
    }

    /**
     * Resume the connection to the robot.
     */
    public void reconnect() {
        try {
            if (communicator.open(nxtInf)) {
                receiver = new MessageReceiver(communicator.getInputStream(), this.robot);
                sender = new MessageSender(communicator.getOutputStream(), this.commands);
                logger.info("Reconnected with " + nxtInf.name);
            }
        } catch (NXTCommException e) {
            logger.error("Unable to reconnect with the server");
        }
    }

    /**
     * Send orders of where the robot should go
     *
     * @param orders What operations the robot should perform to reach goal. See
     *            route execution
     */
    public void sendOrders(List<Instruction> orders) {
        sender.setOrders(orders);
        commands.offer(Command.SEND_ORDERS);

    }
    
    public void sendPosition(int x, int y) {
    	try {
    		sender.sendLocation(x, y);
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    	
    }
    
    public void sendFacing(Facing facing) {
    	try {
    		sender.sendFacing(facing);
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    }

    public void sendNumOfPicks(int picks) {
        try {
            sender.sendNumberOfPicks(picks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tell the robot to cancel the current set of orders
     */
    public void sendCancellation() {
        commands.offer(Command.CANCEL);
    }

    /**
     * Stop all communicator threads and close the communicator
     */
    public void close() {
        connected = false;
        try {
            communicator.close();
            receiver.interrupt();
            sender.interrupt();
        } catch (IOException e) {
            logger.error("Something went wrong with server");
        }
        connected = false;

    }

}
