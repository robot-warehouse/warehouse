package rp.assignments.team.warehouse.server.communications;

import java.util.ArrayList;

import rp.assignments.team.warehouse.server.Facing;
import rp.assignments.team.warehouse.server.Location;
import rp.assignments.team.warehouse.server.RobotInfo;
import rp.assignments.team.warehouse.server.route.execution.Instruction;
import rp.assignments.team.warehouse.server.route.execution.RouteExecution;
import rp.assignments.team.warehouse.server.route.planning.AStar;

public class BluetoothTest {

    public static void main(String[] args) {
        CommunicationsManager manager = new CommunicationsManager(RobotInfo.JOHNCENA);
        manager.startServer();
        ArrayList<Instruction> testOrders = new ArrayList<>();
    	Location start1 = new Location(0, 0);
		Location goal1 = new Location(2, 0);
		testOrders = RouteExecution.convertCoordinatesToInstructions(Facing.East, AStar.findPath(start1, goal1));;

        if (manager.isConnected()) {
            System.out.println("Enter orders");
            manager.sendOrders(testOrders);
            manager.sendNumOfPicks(2);
            System.out.println("Orders sent");
        }


    }

}
