package rp.assignments.team.warehouse.server.communications;

import java.util.ArrayList;
import java.util.Scanner;

import rp.assignments.team.warehouse.server.route.execution.RouteExecution;
import rp.assignments.team.warehouse.server.route.planning.AStar;
import rp.assignments.team.warehouse.server.route.planning.Data;
import rp.assignments.team.warehouse.server.route.planning.State;

public class BluetoothTest {

    public static void main(String[] args) {
        Data.addObstacle();
        State start = new State(2, 2);
        State goal = new State(3, 2);
        AStar demo = new AStar(start, goal);
        demo.findPath();
        RouteExecution routeExecution = new RouteExecution();
        Scanner scan = new Scanner(System.in);
        CommunicationsManager manager = new CommunicationsManager(CommunicationsManager.ROBOT_1_NAME,
                CommunicationsManager.ROBOT_1_ADDRESS);
        manager.startServer();
        ArrayList<Integer> testOrders = RouteExecution.movementCommands;
        // t.start();
        if (manager.isConnected()) {
            System.out.println("Enter orders");
            manager.sendOrders(testOrders);
            System.out.println("Orders sent");
        }
        ;
//		while(true) {
//			testOrders.clear();
//			System.out.println("Send orders");
//			String str = scan.nextLine();
//			while(!str.equals("end")) {
//				testOrders.add(Integer.parseInt(str));
//				str = scan.nextLine();
//			}
//			manager.sendOrders(testOrders);
//
//
//		}

    }

}
