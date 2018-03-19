package rp.assignments.team.warehouse.server;

import java.util.ArrayList;

import rp.assignments.team.warehouse.server.communications.CommunicationsManager;
import rp.assignments.team.warehouse.server.job.Pick;
import rp.assignments.team.warehouse.server.route.execution.Instruction;
import rp.assignments.team.warehouse.server.route.execution.RouteExecution;
import rp.assignments.team.warehouse.server.route.planning.AStar;
import rp.assignments.team.warehouse.server.route.planning.RoutePlanning;

public class RobotThread extends Thread {

    /** The robot the thread is for. */
	private Robot robot;
	
	/** The communication interface with the robot. */
	private CommunicationsManager commsManager;

	/** The instance of the route planning class */
	private RoutePlanning routePlanner;

	/**
	 * @param robot The robot the thread is for.
	 * @param commsManager The communication interface with the robot.
	 */
	public RobotThread(Robot robot, CommunicationsManager commsManager, RoutePlanning routePlanner) {
		this.robot = robot;
		this.commsManager = commsManager;
		this.routePlanner = routePlanner;
	}

	@Override
    public void run() {
    	while (commsManager.isConnected()) {
    		if (this.robot.getCurrentPicks() != null && this.robot.getRoute().isEmpty()) {
				if (!this.robot.hasPickedUpItems()) {
					Location itemLocation = this.robot.getCurrentPickLocation();
				
					ArrayList<Location> path = routePlanner.findPath(this.robot.getCurrentLocation(), itemLocation);
					ArrayList<Instruction> instructions = RouteExecution.convertCoordinatesToInstructions(robot.getCurrentFacingDirection(), path);
	
					if (path.get(path.size() - 1).equals(itemLocation)) {
						instructions.add(Instruction.PICKUP);
						hasPickedUpItems = true;
					}
	
					this.commsManager.sendOrders(instructions);
	
					this.robot.setRoute(path);

					while (!this.robot.isFinished()) {
						if (this.robot.isJobCancelled()) {
							continue;
						}
					}
				}

				boolean willReachDropoffLocation = false;
				Location dropoffLocation = this.robot.getCurrentDropoffLocation();

				while (!willReachDropoffLocation) {
					ArrayList<Location> path = routePlanner.findPath(this.robot.getCurrentLocation(), dropoffLocation);
					ArrayList<Instruction> instructions = RouteExecution.convertCoordinatesToInstructions(robot.getCurrentFacingDirection(), path);
	
					if (path.get(path.size() - 1).equals(dropoffLocation)) {
						instructions.add(Instruction.DROPOFF);
						willReachDropoffLocation = true;
					}
	
					this.commsManager.sendOrders(instructions);
	
					this.robot.setRoute(path);

					while (!this.robot.isFinished()) {
						if (this.robot.isJobCancelled()) {
							continue;
						}
					}
				}
    		}
    	}
    }
}
