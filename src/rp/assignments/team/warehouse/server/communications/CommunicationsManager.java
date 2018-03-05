package rp.assignments.team.warehouse.server.communications;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;
import rp.assignments.team.warehouse.server.route.planning.State;
import rp.util.test.Assert;

/**
 * Manages communications between a robot and the pc
 *
 */
public class CommunicationsManager {
	// default robot name/address
	public static final String NXT_NAME = "John Cena";
	public static final String NXT_ADDRESS = "00165308E5A7";
	private NXTInfo nxtInf;
	private NXTComm communicator;
	private MessageSender sender;
	private MessageReceiver receiver;
	private boolean connected;

	/**
	 * Constructs a new instance of CommunicationsManager with the given robot name/address.
	 */
	public CommunicationsManager(String name, String address) {
		this.nxtInf = new NXTInfo(NXTCommFactory.BLUETOOTH, name, address);
		try {
			connected = false;
			this.communicator = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
			if (communicator.open(nxtInf)) {
				sender = new MessageSender(communicator.getOutputStream());
				receiver = new MessageReceiver(communicator.getInputStream());
				connected = true;
			}

		} catch (NXTCommException e) {
			System.err.println("Could not connect to robot"); // use log4j later
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
	 * Start the receiving thread.
	 */
	public void startServer() {
		receiver.start();
		//should sender be a thread as well?
	}
	
	/**
	 * Send orders of where the robot should go
	 * @param orders What operations the robot should perform to reach goal.
	 */
	public void sendOrders(List<Integer> orders) {
		//integers for now, not sure how they'll be implement by job execution
		try {
			sender.sendOrders(orders);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}

	public void close() {
		connected = false;
		try {
			communicator.close();
			receiver.interrupt();
		} catch (IOException e) {
			System.err.println("Something went wrong with server");
		}

	}

}
