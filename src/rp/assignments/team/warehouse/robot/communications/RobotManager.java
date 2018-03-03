package rp.assignments.team.warehouse.robot.communications;

import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

public class RobotManager {
	private RobotReceiver receiver;
	private RobotSender sender;
	public RobotManager() {
		System.out.println("Opening connection.");
		NXTConnection connection = Bluetooth.waitForConnection();
		System.out.println("Connected.");
		receiver = new RobotReceiver(connection.openDataInputStream());
		sender = new RobotSender(connection.openDataOutputStream());
	}
	
	public void startServer() {
		receiver.start();
	}
	
	public void sendPosition(int x, int y) {
		sender.sendPosition(x, y);
	}
	
 
}
