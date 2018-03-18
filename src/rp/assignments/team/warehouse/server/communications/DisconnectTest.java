package rp.assignments.team.warehouse.server.communications;

import java.util.Scanner;

import rp.assignments.team.warehouse.server.RobotInfo;

public class DisconnectTest {
	public static void main(String[] args) {
		System.out.println("Starting connection with " + RobotInfo.JOHNCENA.getName() + ".");
		CommunicationsManager manager = new CommunicationsManager(RobotInfo.JOHNCENA);
		Scanner scan = new Scanner(System.in);
		if(manager.isConnected()) {
			System.out.println("Connected");
			while(true) {
				System.out.println("Type disconnect to disconnect.");
				String input = scan.nextLine();
				if(input.equals("disconnect")) {
					manager.stopServer();
				}
				//attempt to reconnect
				System.out.println("Attempting to reconnect");
				manager.reconnect();
				if(manager.isConnected()) {
					System.out.println("Reconnected with " + RobotInfo.JOHNCENA.getName() + ".\n");
				}
			}
		}
		scan.close();
	}
}
