package rp.assignments.team.warehouse.server;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rp.assignments.team.warehouse.server.communications.CommunicationsManager;
import rp.assignments.team.warehouse.server.job.Job;
import rp.assignments.team.warehouse.server.job.Pick;
import rp.assignments.team.warehouse.server.job.assignment.Bidder;
import rp.assignments.team.warehouse.server.job.assignment.Picker;
import rp.assignments.team.warehouse.server.route.planning.AStar;

public class Robot implements Picker, Bidder {

    /** The robot's information */
    private RobotInfo robotInfo;

    /** The current location of the robot in the warehouse */
    private Location currentLocation;

    /** The direction the robot is facing in the warehouse */
    private Facing currentFacingDirection;

    /** The picks the robot is assigned */
    private Set<Pick> currentPicks;
    
    /** The location of the pick */
    private Location currentPickLocation;

    /** The communication interface with the robot. */
    private CommunicationsManager communicationsManager;

    private static Logger logger = LogManager.getLogger(Robot.class);

    /** The maximum weight the robot can carry */
    public static float MAX_WEIGHT = 50.0f;

    /**
     * @param robotInfo The robot's information.
     * @param currentLocation The robot's starting location.
     * @param currentFacingDirection The robot's starting direction.
     */
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

    /**
     * Get the current location of the robot in the warehouse.
     *
     * @return The robot's location.
     */
    public Location getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Get the robot info
     *
     * @return The RobotInfo
     */
    public RobotInfo getRobotInfo() {
        return this.robotInfo;
    }

    /**
     * Set the current location of the robot in the warehouse.
     *
     * @param currentLocation The new location of the robot.
     */
    public void setCurrentLocation(Location currentLocation) {
        assert currentLocation != null;

        this.currentLocation = currentLocation;
    }

    /**
     * Get the direction the robot is currently facing.
     *
     * @return The direction the robot is facing.
     */
    public Facing getCurrentFacingDirection() {
    	return currentFacingDirection;
    }

    /**
     * Set the direction the robot is facing.
     *
     * @param currentFacingDirection The new direction the robot is facing.
     */
    public void setCurrentFacingDirection(Facing currentFacingDirection) {
    	this.currentFacingDirection = currentFacingDirection;
    }

    /**
     * Get the picks the robot is currently working on.
     *
     * @return The picks the robot is working on.
     */
    public Set<Pick> getCurrentPicks() {
    	return currentPicks;
    }

    /**
     * Setup the bluetooth connection to the robot.
     *
     * @return True if successfully connected.
     */
    public boolean connect() {
    	this.communicationsManager = new CommunicationsManager(this.robotInfo);
        this.communicationsManager.startServer();

    	if (this.communicationsManager.isConnected()) {
    		(new RobotThread(this, this.communicationsManager)).start();
    		return true;
    	}

    	return false;
    }

    /**
     * Tear down the bluetooth connection to the robot.
     */
    public void disconnect() {
        this.communicationsManager.stopServer();
    }

    /**
     * Returns true if the robot is still connected to the thread
     *
     * @return True if the robot is connected.
     */
    public boolean isConnected() {
        return this.communicationsManager.isConnected();
    }

    @Override
    public boolean isAvailable() {
        return this.getCurrentWeight() < MAX_WEIGHT;
    }

    /**
     * Get the current weight of the robot.
     *
     * @return The weight the robot is carrying.
     */
    public float getCurrentWeight() {
        return (float) this.currentPicks.stream()
            .filter(Pick::isPicked)
            .mapToDouble(Pick::getWeight)
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
        this.currentPickLocation = 

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

    /**
     * React to a job being cancelled
     *
     * @param job The job that has been cancelled.
     */
    public void jobCancelled(Job job) {
        // Drop any picks for the cancelled job
        boolean hadPicks = this.currentPicks.removeIf(p -> p.getJob().equals(job));

        if (hadPicks) {
            // This robot was affected
            // TODO cancel current movement if affected by the job cancellation
        }
    }

}
