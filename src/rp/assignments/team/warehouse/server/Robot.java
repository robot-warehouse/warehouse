package rp.assignments.team.warehouse.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rp.assignments.team.warehouse.server.communications.CommunicationsManager;
import rp.assignments.team.warehouse.server.job.Job;
import rp.assignments.team.warehouse.server.job.Pick;
import rp.assignments.team.warehouse.server.job.assignment.Bidder;
import rp.assignments.team.warehouse.server.job.assignment.Picker;
import rp.assignments.team.warehouse.server.route.planning.AStar;
import rp.assignments.team.warehouse.shared.Facing;

public class Robot implements Picker, Bidder {

    /** The maximum weight the robot can carry */
    public static float MAX_WEIGHT = 50.0f;

    private static Logger logger = LogManager.getLogger(Robot.class);

    /** The robot's information */
    private RobotInfo robotInfo;

    /** The current location of the robot in the warehouse */
    private Location currentLocation;

    /** The direction the robot is facing in the warehouse */
    private Facing currentFacingDirection;

    /** The current route being taken by the robot */
    private List<Location> currentRoute;

    /** The picks the robot is assigned */
    private Set<Pick> currentPicks;

    /** The location of the set of items to pickup */
    private Location currentPickLocation;

    /** The location to drop the items at */
    private Location currentDropLocation;

    /** Whether the current job has been cancelled */
    private boolean isJobCancelled;

    /** Whether the robot has finished picking up the assigned items */
    private boolean hasFinishedPickup;

    /** Whether the robot has finished dropping off the items exc */
    private boolean hasFinishedDropOff;

    /** The communication interface with the robot. */
    private CommunicationsManager communicationsManager;

    /**
     * @param robotInfo The robot's information.
     * @param currentLocation The robot's starting location.
     * @param currentFacingDirection The robot's starting direction.
     */
    public Robot(RobotInfo robotInfo, Location currentLocation, Facing currentFacingDirection) {
        assert robotInfo != null;
        assert currentLocation != null;
        assert currentFacingDirection != null;

        this.robotInfo = robotInfo;
        this.currentLocation = currentLocation;
        this.currentFacingDirection = currentFacingDirection;

        this.currentPicks = new HashSet<>();
        this.currentRoute = new ArrayList<>();
        this.hasFinishedDropOff = false;
        this.hasFinishedPickup = false;
        this.isJobCancelled = false;
    }

    /**
     * Get the name of the robot.
     *
     * @return The name of the robot.
     */
    @Override
    public String getName() {
        return this.robotInfo.getName();
    }

    @Override
    public boolean isAvailable(Pick pick) {
        return (this.currentPicks.size() == 0 || this.currentPicks.stream().anyMatch(
            p -> p.isSameJobAndItem(pick))) && this.getCurrentWeight() < MAX_WEIGHT;
    }

    @Override
    public void assignPick(Pick pick) {
        assert this.currentPicks != null;

        this.currentPicks.add(pick);
        this.currentPickLocation = pick.getPickLocation();
        this.currentDropLocation = pick.getDropLocation();
        logger.trace("Adding pick to robot %s.", this.getName());

        // TODO this needs updating for having a set of picks
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
     * Get the {@link RobotInfo} enumeration value for this robot.
     *
     * @return The {@link RobotInfo} enumeration value for this robot.
     */
    public RobotInfo getRobotInfo() {
        return this.robotInfo;
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
     * Set the current location of the robot in the warehouse.
     *
     * @param currentLocation The new location of the robot.
     */
    public void setCurrentLocation(Location currentLocation) {
        assert currentLocation != null;

        this.currentRoute.removeIf(l -> l.equals(currentLocation));

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
        assert currentFacingDirection != null;

        this.currentFacingDirection = currentFacingDirection;
    }

    /**
     * Gets the current route of the robot
     *
     * @return The current route of the robot
     */
    public List<Location> getCurrentRoute() {
        return currentRoute;
    }

    /**
     * Sets the current route for the robot
     *
     * @param currentRoute The route of the robot
     */
    public void setCurrentRoute(List<Location> currentRoute) {
        this.currentRoute = currentRoute;
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
     * Gets the current pick up location for the set of picks
     *
     * @return pickup location
     */
    public Location getCurrentPickLocation() {
        return this.currentPickLocation;
    }

    /**
     * Gets the current drop off location for the pick
     *
     * @return drop location
     */
    public Location getCurrentDropLocation() {
        return this.currentDropLocation;
    }

    /**
     * Gets whether the current job has been cancelled
     *
     * @return true/false
     */
    public boolean isJobCancelled() {
        return this.isJobCancelled;
    }

    /**
     * Whether the robot has finished it's pickup off of items
     *
     * @return true/false
     */
    public boolean hasFinishedPickup() {
        return this.hasFinishedPickup;
    }

    /**
     * Sets whether the robot has finished it's pickup of items
     *
     * @param hasFinishedPickup well has it?
     */
    public void setHasFinishedPickup(boolean hasFinishedPickup) {
        this.hasFinishedPickup = hasFinishedPickup;
    }

    /**
     * Whether the robot has finished it's drop off of items
     *
     * @return true/false
     */
    public boolean hasFinishedDropOff() {
        return this.hasFinishedDropOff;
    }

    /**
     * Sets whether the robot has finished it's drop off of items
     *
     * @param hasFinishedDropOff well has it?
     */
    public void setHasFinishedDropOff(boolean hasFinishedDropOff) {
        this.hasFinishedDropOff = hasFinishedDropOff;
    }

    /**
     * Returns whether the robot has completed it's current job
     *
     * @return true/false
     */
    public boolean hasFinishedJob() {
        return this.hasFinishedPickup() && this.hasFinishedDropOff();
    }

    /**
     * Advances the robot's state to the next stage
     */
    public void finishedJob() {
        if (!this.hasFinishedPickup() && !this.hasFinishedDropOff()) {
            this.setHasFinishedPickup(true);
        } else if (this.hasFinishedPickup() && !this.hasFinishedDropOff()) {
            this.setHasFinishedDropOff(true);
        }

        // checks this state is never reached
        assert !(this.hasFinishedDropOff() && !this.hasFinishedPickup());
    }

    /**
     * Setup the bluetooth connection to the robot.
     *
     * @return True if successfully connected.
     */
    public boolean connect() {
        this.communicationsManager = new CommunicationsManager(this);
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

    /**
     * Get the current weight of the robot.
     *
     * @return The weight the robot is carrying.
     */
    public float getCurrentWeight() {
        return (float) this.currentPicks.stream().filter(Pick::isPicked).mapToDouble(Pick::getWeight).sum();
    }

    /**
     * Get the number of items to pick up at the location.
     *
     * @param location The location
     * @return The number of items to take at this location.
     */
    public int getNumPicksAtLocation(Location location) {
        return (int) this.currentPicks.stream().filter(p -> location.equals(p.getPickLocation())).count();
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
            this.isJobCancelled = true;
        }
    }

    /**
     * Clears the current pick so that a new one can be picked up
     */
    public void clearCurrentPicks() {
        this.currentPicks = new HashSet<>();
    }
}
