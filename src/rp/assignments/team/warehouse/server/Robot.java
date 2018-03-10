package rp.assignments.team.warehouse.server;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rp.assignments.team.warehouse.server.communications.CommunicationsManager;
import rp.assignments.team.warehouse.server.job.Pick;
import rp.assignments.team.warehouse.server.job.assignment.Bidder;
import rp.assignments.team.warehouse.server.job.assignment.Picker;
import rp.assignments.team.warehouse.server.route.planning.AStar;

public class Robot implements Picker, Bidder {

    private RobotInfo robotInfo;
    private Location currentLocation;
    private Facing currentFacingDirection;
    private Set<Pick> currentPicks;
    private boolean hasComputedInstructionsForPick;

    private static Logger logger = LogManager.getLogger(Robot.class);

    public static float MAX_WEIGHT = 50.0f;

    public Robot(RobotInfo robotInfo, Location currentLocation, Facing currentFacingDirection) {
    	this.robotInfo = robotInfo;
        this.currentLocation = currentLocation;
        this.currentFacingDirection = currentFacingDirection;
        this.currentPicks = new HashSet<Pick>();
    }

	/**
	 * Get the name of the robot.
	 *
	 * @return The name of the robot.
	 */
    public String getName() {
        return this.robotInfo.getName();
    }

    /**
     * Get the address of the robot for bluetooth communications.
     *
     * @return The bluetooth address of the robot.
     */
    public String getAddress() {
    	return this.robotInfo.getAddress();
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Facing getCurrentFacingDirection() {
    	return currentFacingDirection;
    }

    public void setCurrentFacingDirection(Facing currentFacingDirection) {
    	this.currentFacingDirection = currentFacingDirection;
    }

    public boolean hasComputedInstructionsForPick() {
    	return hasComputedInstructionsForPick;
    }

    public void setHasComputedInstructionsForPick(boolean hasIt) {
    	hasComputedInstructionsForPick = hasIt;
    }

    public Set<Pick> getCurrentPicks() {
    	return currentPicks;
    }

    public boolean connect() {
    	CommunicationsManager commsManager = new CommunicationsManager(this.robotInfo);
    	commsManager.startServer();

    	if (commsManager.isConnected()) {
    		(new RobotThread(this, commsManager)).start();
    		return true;
    	}

    	return false;
    }

    @Override
    public boolean isAvailable() {
        return this.currentPicks != null;
    }

    public float getCurrentWeight() {
        return (float) this.currentPicks.stream()
            .filter(p -> p.isPicked())
            .mapToDouble(p -> p.getWeight())
            .sum();
    }

    /**
     * Get the number of items to pick up at the location.
     *
     * @param location The location
     * @return The number of items to take at this location.
     */
    public int getNumPicksAtLocation(Location location) {
        return (int) this.currentPicks.stream()
            .filter(p -> location.equals(p.getPickLocation()))
            .count();
    }

    @Override
    public void assignPick(Pick pick) {
        assert this.currentPicks != null;

        this.currentPicks.add(pick);

        logger.trace("Adding pick to robot %s.", this.getName());

        // TODO this needs updating for having a set of picks
        setHasComputedInstructionsForPick(false); // this might need to be before the line above
    }

    @Override
    public int getBid(Pick pick) {
        if (this.getCurrentWeight() + pick.getWeight() <= MAX_WEIGHT) {
            return AStar.findDistance(this.getCurrentLocation(), pick.getPickLocation());
        } else {
            return Integer.MAX_VALUE;
        }
    }
}
