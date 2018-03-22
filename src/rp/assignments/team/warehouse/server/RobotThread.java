package rp.assignments.team.warehouse.server;

import java.util.List;

import rp.assignments.team.warehouse.server.communications.CommunicationsManager;
import rp.assignments.team.warehouse.server.job.Pick;
import rp.assignments.team.warehouse.server.route.execution.RouteExecution;
import rp.assignments.team.warehouse.server.route.planning.RoutePlanning;
import rp.assignments.team.warehouse.shared.Instruction;

public class RobotThread extends Thread {

	/** The robot the thread is for. */
	private Robot robot;

	/** The communication interface with the robot. */
	private CommunicationsManager communicationsManager;

	/** The instance of the route planning class */
	private RoutePlanning routePlanner;

	/** Wait for pickup before calculating anymore instructions */
	private boolean waitForPickup = false;

	/** Wait for drop off before calculating anymore instructions */
	private boolean waitForDropOff = false;

	/**
	 * @param robot
	 *            The robot the thread is for.
	 * @param communicationsManager
	 *            The communication interface with the robot.
	 */
	public RobotThread(Robot robot, CommunicationsManager communicationsManager, RoutePlanning routePlanner) {
		this.robot = robot;
		this.communicationsManager = communicationsManager;
		this.routePlanner = routePlanner;
	}

	@Override
	public void run() {
		while (this.communicationsManager.isConnected()) {
			if (this.robot.isJobCancelled()) {
				this.communicationsManager.sendCancellation();
				this.robot.clearCurrentPicks();
			}

			if (this.robot.hasFinishedJob()) {
				this.robot.getCurrentPicks().forEach(Pick::setCompleted);
				this.robot.setHasFinishedPickup(false);
				this.robot.setHasFinishedDropOff(false);
				this.robot.clearCurrentPicks();
				this.waitForPickup = false;
				this.waitForDropOff = false;
			}

			if (!this.robot.getCurrentPicks().isEmpty()) {
				if (this.robot.getCurrentRoute().isEmpty()) {
					if (waitForPickup && !robot.hasFinishedPickup()) {
						continue;
					}

					if (waitForDropOff && !robot.hasFinishedDropOff()) {
						continue;
					}

					List<Location> path;

					if (!this.robot.hasFinishedPickup()) {
						path = this.routePlanner.findPath(this.getName(), this.robot.getCurrentLocation(),
								this.robot.getCurrentPickLocation());
						System.out.println("Current route for " + robot.getName() + ": " + path);

					} else if (!this.robot.hasFinishedDropOff()) {
						path = this.routePlanner.findPath(this.getName(), this.robot.getCurrentLocation(),
								this.robot.getCurrentDropLocation());
						System.out.println("Current route for " + robot.getName() + ": " + path);
					} else {
						continue; // should never be reached here but wanted to be more explicit with if logic
					}

					// path cannot be found to goal node currently so wait around
					if (path == null) {
						continue;
					}

					List<Instruction> instructions = RouteExecution
							.convertCoordinatesToInstructions(this.robot.getCurrentFacingDirection(), path);
					System.out.println("Instructions for " + robot.getName() + ": " + instructions);
					Location lastLocationInPath = path.get(path.size() - 1);
					if (lastLocationInPath.equals(this.robot.getCurrentPickLocation())) {
						instructions.add(Instruction.PICKUP);
						waitForPickup = true;
					} else if (lastLocationInPath.equals(this.robot.getCurrentDropLocation())) {
						instructions.add(Instruction.DROPOFF);
						waitForDropOff = true;
					}

					this.communicationsManager.sendPosition(this.robot.getCurrentLocation().getX(),
							this.robot.getCurrentLocation().getY());
					// if() {
					this.communicationsManager.sendFacing(this.robot.getCurrentFacingDirection());
					// }

					this.communicationsManager.sendNumOfPicks(this.robot.getNumPicksAtLocation(lastLocationInPath));
					this.communicationsManager.sendOrders(instructions);
					this.robot.setCurrentRoute(path);
				}
			}
		}
	}
}
