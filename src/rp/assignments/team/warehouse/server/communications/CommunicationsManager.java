package rp.assignments.team.warehouse.server.communications;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rp.assignments.team.warehouse.server.route.planning.State;
import rp.assignments.team.warehouse.shared.communications.Command;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Manages communications between a robot and the pc
 */
public class CommunicationsManager { 

    private final static Logger logger = LogManager.getLogger(CommunicationsManager.class);

    private NXTInfo nxtInf;
    private NXTComm communicator;
    private BlockingQueue<Command> commands;
    private MessageSender sender;
    private MessageReceiver receiver;
    private boolean connected;

    /**
     * Constructs a new instance of CommunicationsManager with the given robot name/address.
     */
    public CommunicationsManager(String name, String address) {
        logger.info("Initialising communications with " + name + " address " + address + ".");
        this.nxtInf = new NXTInfo(NXTCommFactory.BLUETOOTH, name, address);
        try {
            connected = false;
            this.communicator = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
            if (communicator.open(nxtInf)) {
                logger.info("Connected to robot " + nxtInf.name);
                commands = new LinkedBlockingQueue<>();
                sender = new MessageSender(communicator.getOutputStream(), commands);
                receiver = new MessageReceiver(communicator.getInputStream());
                connected = true;
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
        return connected;
    }

    /**
     * Gets the state of the robot. Returns null if the robot has not given it's position yet
     */
    public State getRobotState() {
        return receiver.getLatestPosition();
    }

    /*
     * Start the server threads
     */
    public void startServer() {
        receiver.start();
        sender.start();
    }

    /**
     * Send orders of where the robot should go
     *
     * @param orders What operations the robot should perform to reach goal. See route execution
     */
    public void sendOrders(List<Integer> orders) {
        // integers for now, not sure how they'll be implement by job execution
        sender.setOrders(orders);
        commands.offer(Command.SEND_ORDERS);

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

    }

}
