package rp.assignments.team.warehouse.server.communications;

import java.util.ArrayList;
import java.util.Scanner;

public class BluetoothTest {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		CommunicationsManager manager = new CommunicationsManager("TriHard",
				"0016530A631F");
		manager.startServer();
		ArrayList<Integer> testOrders = new ArrayList<Integer>();
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					System.out.println(manager.getRobotState());	
					try {
						Thread.sleep(1000);	
					}
					catch(InterruptedException ex) {
						break;
					}
					
				}
				
			}
		});
		//t.start();
		if(manager.isConnected()) {
			System.out.println("Enter orders");
			
			testOrders.add(4);
			testOrders.add(5);
			testOrders.add(8);
			System.out.println("Orders sent");
			manager.sendOrders(testOrders);	
		}
		while(true) {
			testOrders.clear();
			System.out.println("Send orders");
			String str = scan.nextLine();
			while(!str.equals("end")) {
				testOrders.add(Integer.parseInt(str));
				str = scan.nextLine();
			}
			manager.sendOrders(testOrders);
			
			
		}
		
		
	}
}
