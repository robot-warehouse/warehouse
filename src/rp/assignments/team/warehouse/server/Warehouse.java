package rp.assignments.team.warehouse.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import rp.assignments.team.warehouse.server.job.Job;

public class Warehouse {

    /** True if the warehouse is running */
    private boolean running;

    /** The jobs being worked on */
    private Set<Job> workingOnJobs;

    /** The robots in the warehouse */
    private Set<Robot> robots;

    /** Instance of the controller class */
    private Controller controller;

    /** List of drop locations */
    private List<Location> dropLocations;

    public Warehouse() {
        this.running = true;
        this.robots = new HashSet<>();
        this.workingOnJobs = new HashSet<>();
    }

    /**
     * Add the controller to the warehouse.
     *
     * @param controller The controller for the warehouse.
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Check if the warehouse running.
     *
     * @return True if the warehouse is running.
     */
    public boolean isRunning() {
        return this.running;
    }

    /**
     * Get the jobs currently being worked on.
     *
     * @return List of jobs being worked on.
     */
    public Set<Job> getWorkingOnJobs() {
        return this.workingOnJobs;
    }

    /**
     * Add a job to the list of jobs currently being worked on.
     *
     * @param job The job which is now being worked on.
     */
    public void addWorkingOnJob(Job job) {
        this.workingOnJobs.add(job);
        this.controller.addJobToCurrentJobsTable(job);
    }

    /**
     * Marks the job as completed, removing from the workingOnJobs set and updating the GUI
     *
     * @param job The completed job
     */
    public void completeJob(Job job) {
        this.controller.removeJobFromCurrentJobsTable(job);

        this.controller.addJobToCompletedJobsTable(job);
    }

    /**
     * Cancel a job which may or may not have work started on it.
     *
     * @param job The job to be cancelled.
     */
    public void cancelJob(Job job) {
        if (this.workingOnJobs.contains(job)) {
            // Uh-oh! Better find any affected robots...
            // this.robots.forEach(r -> r.jobCancelled(job));

            this.workingOnJobs.remove(job);
        }

        job.setCancelled();
        this.controller.removeJobFromCurrentJobsTable(job);
    }

    /**
     * Add a robot to the warehouse.
     *
     * @param robot The robot to add.
     */
    public void addRobot(Robot robot) {
        this.robots.add(robot);
        this.controller.addRobotToCurrentRobotTable(robot);
    }

    /**
     * Get the robots in the warehouse.
     *
     * @return Set of robots in the warehouse.
     */
    public Set<Robot> getRobots() {
        return this.robots;
    }

    /**
     * Get information of all known robots.
     *
     * @return Set of RobotInfo for known robots.
     */
    public RobotInfo[] getKnownRobots() {
        return RobotInfo.values();
    }

    /**
     * Get set of online robots' RobotInfo.
     *
     * @return Set of online robots' RobotInfo.
     */
    public Set<RobotInfo> getOnlineRobots() {
        return this.getRobots().stream().map(Robot::getRobotInfo).collect(Collectors.toSet());
    }

    /**
     * Get set of offline robots' RobotInfo.
     *
     * @return Set of offline robots' RobotInfo.
     */
    public Set<RobotInfo> getOfflineRobots() {
        Set<RobotInfo> onlineRobots = this.getOnlineRobots();

        return Arrays.stream(this.getKnownRobots()).filter(r -> !onlineRobots.contains(r)).collect(Collectors.toSet());
    }

    /**
     * Get a map of robot names mapped to their location in the warehouse.
     *
     * @return Map of robot names to locations
     */
    public Map<String, Location> getRobotLocations() {
        return this.robots.stream().collect(Collectors.toMap(Robot::getName, Robot::getCurrentLocation));
    }

    /**
     * Get list of drop locations.
     *
     * @return List of drop locations.
     */
    public List<Location> getDropLocations() {
        if (this.dropLocations == null) {
            this.dropLocations = new ArrayList<>(this.controller.getDropLocations());
        }

        return this.dropLocations;
    }

    /**
     * Remove a robot from the warehouse.
     *
     * @param robot The robot to remove.
     */
    public void removeRobot(Robot robot) {
        this.robots.remove(robot);
    }

    /**
     * Announces to the user that all jobs have been assigned to robots
     */
    public void assignedAllJobs() {
        this.running = false;
        this.controller.assignedAllJobs();
    }

    /**
     * Shutdown the warehouse. Lights off!
     */
    public void shutdown() {
        this.running = false;
        System.exit(1); // bit iffy but sure
    }
}
