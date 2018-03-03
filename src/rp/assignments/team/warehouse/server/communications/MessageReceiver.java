package rp.assignments.team.warehouse.server.communications;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import rp.assignments.team.warehouse.server.route.planning.State;
import rp.assignments.team.warehouse.shared.communications.Command;


public class MessageReceiver extends Thread {
	private DataInputStream fromRobot;
	private List<State> locations;
	public MessageReceiver(InputStream inpStream) {
		this.fromRobot = new DataInputStream(inpStream);
		this.locations = new ArrayList<State>();

	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Command command = Command.valueOf( fromRobot.readUTF());
				switch (command) {
				case SEND_POSITION:
					int x = fromRobot.readInt(); //get x
					int y = fromRobot.readInt(); //get y
					State currState = new State(x, y);
					locations.add(currState);
					break;
				//add more commands to switch on if neccessary
				default:
					break;
				}
								
			} catch (IOException e) {
				System.err.println("Something went wrong with the robot's communications ");
				e.printStackTrace();
			}
			catch (IllegalArgumentException e) {
				System.err.println("Robot sent invalid command.");
			}
		}
	}
	
	/**
	 * Last position of robot as a State : see {@link State}
	 * @return The last state of the robot. 
	 */
	public State getLatestPosition() {
		if(locations.size() == 0) {
			return null;
		}
		else {
			return locations.get(locations.size() - 1);
		}
	}
	
}
