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

import rp.assignments.team.warehouse.server.RobotInfo;
import rp.assignments.team.warehouse.server.route.execution.Instruction;
import rp.assignments.team.warehouse.server.route.planning.State;
import rp.assignments.team.warehouse.shared.communications.Command;

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
     *
     * @param robotInfo The robot's information.
     */
    public CommunicationsManager(RobotInfo robotInfo) {
        assert robotInfo != null;

        String name = robotInfo.getName();
        String address = robotInfo.getAddress();

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
    
    public boolean getFinished() {
    	return receiver.getFinished();
    }

    /**
     * Start the server threads
     */
    public void startServer() {
        receiver.start();
        sender.start();
    }
    
    /**
     * 
     */
    public void stopServer() {
        // TODO send shutdown command to robot
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
        // TODO close sender and receiver threads
    }
    
    public void reconnect() {
    	try {
			if(communicator.open(nxtInf)) {
				receiver = new MessageReceiver(communicator.getInputStream());
				sender = new MessageSender(communicator.getOutputStream(), commands);
				logger.info("Reconnected with " + nxtInf.name);
			}
		} catch (NXTCommException e) {
			logger.error("Unable to reconnect with the server");
		}
    }

    /**
     * Send orders of where the robot should go
     *
     * @param orders What operations the robot should perform to reach goal. See route execution
     */
    public void sendOrders(List<Instruction> orders) {
        sender.setOrders(orders);
        receiver.setFinished(false);
        commands.offer(Command.SEND_ORDERS);

    }
    
    public void sendNumOfPicks(int picks) {
    	try {
			sender.sendNumberOfPicks(picks);
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
