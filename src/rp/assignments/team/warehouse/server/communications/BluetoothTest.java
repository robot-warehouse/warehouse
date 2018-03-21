package rp.assignments.team.warehouse.server.communications;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rp.assignments.team.warehouse.server.Location;
import rp.assignments.team.warehouse.server.Robot;
import rp.assignments.team.warehouse.server.RobotInfo;
import rp.assignments.team.warehouse.server.route.execution.RouteExecution;
import rp.assignments.team.warehouse.server.route.planning.AStar;
import rp.assignments.team.warehouse.server.route.planning.Data;
import rp.assignments.team.warehouse.shared.Facing;
import rp.assignments.team.warehouse.shared.Instruction;

public class BluetoothTest {


	public static void main(String[] args) {
		CommunicationsManager manager = new CommunicationsManager(
				new Robot(RobotInfo.NAMELESS, new Location(0, 0), Facing.WEST));
		
		manager.startServer();
		ArrayList<Instruction> testOrders = new ArrayList<>();
		Location start1 = new Location(0, 0);
		Location goal1 = new Location(5, 2);
		List<Location> locations = AStar.findPath(start1, goal1);
		System.out.println(locations);
		testOrders = RouteExecution.convertCoordinatesToInstructions(Facing.WEST,locations);
		testOrders.add(Instruction.PICKUP);
		System.out.println(testOrders);
		if (manager.isConnected()) {
			System.out.println("Enter orders");
			manager.sendPosition(0, 0);
			manager.sendNumOfPicks(2);
			manager.sendFacing(Facing.EAST);
			manager.sendOrders(testOrders);
			System.out.println("Orders sent");
		}

	}


}
