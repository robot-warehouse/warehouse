package rp.assignments.team.warehouse.server;

import java.util.ArrayList;

import rp.assignments.team.warehouse.server.communications.CommunicationsManager;
import rp.assignments.team.warehouse.server.route.execution.RouteExecution;
import rp.assignments.team.warehouse.server.route.planning.AStar;

public class RobotThread extends Thread {

	private Robot robot;
	private CommunicationsManager commsManager;
	
	public RobotThread(Robot robot, CommunicationsManager commsManager) {
		this.robot = robot;
		this.commsManager = commsManager;
	}
	
	@Override
    public void run() {		
    	while (commsManager.isConnected()) {
    		if (robot.getCurrentPick() != null && !robot.hasComputedInstructionsForPick()) {
    			ArrayList<Location> path = AStar.findPath(robot.getCurrentLocation(), robot.getCurrentPick().getPickLocation());
    			ArrayList<Integer> instructions = RouteExecution.convertCoordinatesToInstructions(robot.getCurrentFacingDirection(), path);
    			
    			commsManager.sendOrders(instructions);
    			
    			robot.setHasComputedInstructionsForPick(true);
    		}
    	}
    }
}
