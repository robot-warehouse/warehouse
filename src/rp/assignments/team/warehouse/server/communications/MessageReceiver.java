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
	private List<rp.assignments.team.warehouse.server.route.planning.State> locations;

	public MessageReceiver(InputStream inpStream) {
		this.fromRobot = new DataInputStream(inpStream);
		this.locations = new ArrayList<>();

	}

	@Override
	public void run() {
		while(true) {
			try {
				Command command = Command.strToCommand((fromRobot.readUTF()));
				System.out.println(command);
				switch (command) {
				case SEND_POSITION:
					int x = Integer.valueOf(fromRobot.readUTF()); //get x
					int y = Integer.valueOf(fromRobot.readUTF()); //get y
					rp.assignments.team.warehouse.server.route.planning.State 
					currState = new rp.assignments.team.warehouse.server.route.planning.State(x, y);
					System.out.println("Got " + currState);
					locations.add(currState);
					break;
				//add more commands to switch on if neccessary
				default:
					System.out.println("Robot set unrecognised command");
					break;
				}
								
			} catch (IOException e) {
				System.err.println("Something went wrong with the robot's communications ");
				e.printStackTrace();
				break;
			}
			catch (IllegalArgumentException e) {
				System.err.println("Robot sent invalid command.");
			}
		}
	}

	/**
	 * Last position of robot as a State : see {@link State}
	 * 
	 * @return The last state of the robot.
	 */
	public rp.assignments.team.warehouse.server.route.planning.State getLatestPosition() {
		if (locations.size() == 0) {
			return null;
		} else {
			return locations.get(locations.size() - 1);
		}
	}

}
