package rp.assignments.team.warehouse.server.communications;

import rp.assignments.team.warehouse.server.Location;
import rp.assignments.team.warehouse.server.route.execution.RouteExecution;
import rp.assignments.team.warehouse.server.route.planning.AStar;

import java.util.ArrayList;

public class BluetoothTest {

    public static void main(String[] args) {
        CommunicationsManager manager = new CommunicationsManager(CommunicationsManager.ROBOT_1_NAME,
                CommunicationsManager.ROBOT_1_ADDRESS);
        manager.startServer();
        ArrayList<Integer> testOrders = new ArrayList<>();
    	Location start1 = new Location(0, 0);
		Location goal1 = new Location(0, 2);
		RouteExecution.pathForReading = AStar.findPath(start1, goal1);
		RouteExecution rExecution = new RouteExecution();
		testOrders = rExecution.getMovements();

        if (manager.isConnected()) {
            System.out.println("Enter orders");
            manager.sendOrders(testOrders);
            System.out.println("Orders sent");
        }


    }

}
