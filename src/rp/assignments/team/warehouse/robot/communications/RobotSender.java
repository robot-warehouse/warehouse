package rp.assignments.team.warehouse.robot.communications;

import java.io.DataOutputStream;
import java.io.IOException;

import rp.assignments.team.warehouse.shared.communications.Command;

public class RobotSender {
	private DataOutputStream toServer;
	public RobotSender(DataOutputStream toServer) {
		this.toServer = toServer;
	}
	
	/**
	 * Send position of robot to the server
	 */
	public void sendPosition(int x, int y) {
		try {
			toServer.writeUTF(Command.SEND_POSITION.toString());	
			toServer.writeInt(x);
			toServer.writeInt(y);
		}
		catch(IOException e) {
			System.err.println("Something went wrong with the server");
			e.printStackTrace();
		}
		
	}
}
