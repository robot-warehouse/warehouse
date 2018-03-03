package rp.assignments.team.warehouse.robot.communications;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import rp.assignments.team.warehouse.shared.communications.Command;

/*
 * Receives messages from the server
 */
public class RobotReceiver extends Thread {

	//temporary until type of orders known
	private List<Integer> orders;
	private boolean jobCancelled;
	private DataInputStream fromServer;
	public RobotReceiver(DataInputStream fromServer) {
		orders = new ArrayList<Integer>();
		fromServer = new DataInputStream(fromServer);
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Command command = Command.valueOf(fromServer.readUTF());
				switch(command) {
				case CANCEL:
					jobCancelled = true;
				case SEND_ORDERS:
					//remove previous
					orders.clear();
					//add orders
					String val = fromServer.readUTF();
					while(!Command.END.toString().equals(val)) {
						orders.add(Integer.parseInt(val));
						val = fromServer.readUTF();
					}
				}
			}
			catch(IOException e) {
				System.err.println("Something went wrong with the server");
				e.printStackTrace();
			}
			catch(IllegalArgumentException e) {
				System.err.println("Unrecognised command sent to server");
			}
		}
	}
	
	public List<Integer> getOrders(){
		return orders;
	}

}
