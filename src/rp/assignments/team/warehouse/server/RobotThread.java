package rp.assignments.team.warehouse.server;

import java.util.ArrayList;

import rp.assignments.team.warehouse.server.communications.CommunicationsManager;
import rp.assignments.team.warehouse.server.job.Pick;
import rp.assignments.team.warehouse.server.route.execution.Instruction;
import rp.assignments.team.warehouse.server.route.execution.RouteExecution;
import rp.assignments.team.warehouse.server.route.planning.AStar;

public class RobotThread extends Thread {

    /** The robot the thread is for. */
	private Robot robot;
	
	/** The communication interface with the robot. */
	private CommunicationsManager commsManager;

	/**
	 * @param robot The robot the thread is for.
	 * @param commsManager The communication interface with the robot.
	 */
	public RobotThread(Robot robot, CommunicationsManager commsManager) {
		this.robot = robot;
		this.commsManager = commsManager;
	}

	@Override
    public void run() {
    	while (commsManager.isConnected()) {
    		if (robot.getCurrentPicks() != null && robot.getRoute().isEmpty()) {
    		    Location goalLocation = (Pick) robot.getCurrentPicks()
    		    
    			ArrayList<Location> path = RoutePlanning.findPath(robot.getCurrentLocation(), ((Pick)robot.getCurrentPicks().toArray()[0]).getPickLocation());
    			ArrayList<Instruction> instructions = RouteExecution.convertCoordinatesToInstructions(robot.getCurrentFacingDirection(), path);

    			if (path.get(path.size() - 1).equals(other))
    			
    			commsManager.sendOrders(instructions);
    			
			    robot.setRoute(path);
    		}
    	}
    }
}
