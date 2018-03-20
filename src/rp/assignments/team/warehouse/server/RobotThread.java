package rp.assignments.team.warehouse.server;

import java.util.List;

import rp.assignments.team.warehouse.server.communications.CommunicationsManager;
import rp.assignments.team.warehouse.server.route.execution.RouteExecution;
import rp.assignments.team.warehouse.server.route.planning.AStar;
import rp.assignments.team.warehouse.shared.Instruction;

public class RobotThread extends Thread {

    /** The robot the thread is for. */
    private Robot robot;

    /** The communication interface with the robot. */
    private CommunicationsManager communicationsManager;

    /**
     * @param robot The robot the thread is for.
     * @param communicationsManager The communication interface with the robot.
     */
    public RobotThread(Robot robot, CommunicationsManager communicationsManager) {
        this.robot = robot;
        this.communicationsManager = communicationsManager;
    }

    @Override
    public void run() {
        while (this.communicationsManager.isConnected()) {
            if (this.robot.isJobCancelled()) {
                this.communicationsManager.sendCancellation();
                this.robot.clearCurrentPicks();
            }

            if (this.robot.hasFinishedJob()) {
                this.robot.setHasFinishedPickup(false);
                this.robot.setHasFinishedDropOff(false);
                this.robot.clearCurrentPicks();
            }

            if (!this.robot.getCurrentPicks().isEmpty() && this.robot.getCurrentRoute().isEmpty()) {
                List<Location> path;

                if (!this.robot.hasFinishedPickup()) {
                    path = AStar.findPath(this.robot.getCurrentLocation(), this.robot.getCurrentPickLocation());
                } else if (!this.robot.hasFinishedDropOff()) {
                    path = AStar.findPath(this.robot.getCurrentLocation(), this.robot.getCurrentDropLocation());
                } else {
                    continue; // should never be reached here but wanted to be more explicit with if logic
                }

                // path cannot be found to goal node currently so wait around
                if (path == null) {
                    continue;
                }

                List<Instruction> instructions = RouteExecution
                        .convertCoordinatesToInstructions(this.robot.getCurrentFacingDirection(), path);

                Location lastLocationInPath = path.get(path.size() - 1);
                if (lastLocationInPath.equals(this.robot.getCurrentPickLocation())) {
                    instructions.add(Instruction.PICKUP);
                } else if (lastLocationInPath.equals(this.robot.getCurrentDropLocation())) {
                    instructions.add(Instruction.DROPOFF);
                }

                this.communicationsManager.sendOrders(instructions);
                this.robot.setCurrentRoute(path);
            }
        }
    }
}
