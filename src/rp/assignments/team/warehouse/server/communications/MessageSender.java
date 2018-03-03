package rp.assignments.team.warehouse.server.communications;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import rp.assignments.team.warehouse.shared.communications.Command;

/**
 * Sends Messages to the robot. Non threaded for now
 */
public class MessageSender {
	public DataOutputStream toRobot;
	
	public MessageSender(OutputStream toRobot) {
		this.toRobot = new DataOutputStream(toRobot);
	}
	
	//perhaps use a type instead of int here?
	//should this throw exception or should it be handled in this method?
	public void sendOrders(List<Integer> orders) throws IOException {
		//send a command to give new information
		toRobot.writeUTF(Command.SEND_ORDERS.toString());
		//send each command one by one
		for(Integer order : orders) {
			toRobot.writeInt(order);
		}
		
	}
	
	public void sendCancellation() throws IOException {
		toRobot.writeUTF(Command.CANCEL.toString());
	}
	
	
}
