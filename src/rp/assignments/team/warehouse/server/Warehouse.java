package rp.assignments.team.warehouse.server;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import rp.assignments.team.warehouse.server.job.Job;

// TODO what else is warehouse class going to hold?
public class Warehouse {

    /** True if the warehouse is running */
    private boolean running;

    /** The jobs being worked on */
    private List<Job> workingOnJobs;

    /** The robots in the warehouse */
    private Set<Robot> robots;

    /** Any robots that aren't currently connected */
    private RobotInfo[] offlineRobots;

    /** Instance of the controller class */
    private Controller controller;

    public Warehouse() {
        this.running = true;
        this.robots = new HashSet<>();

        this.offlineRobots = RobotInfo.values();
    }

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
     * Add a job to the list of jobs currently being worked on.
     *
     * @param job The job which is now being worked on.
     */
    public void addWorkingOnJob(Job job) {
    	this.workingOnJobs.add(job);
    	// TODO update current job table on gui
    }

    /**
     * Cancel a job which may or may not have work started on it.
     *
     * @param job The job to be cancelled.
     */
    public void cancelJob(Job job) {
        if (this.workingOnJobs.contains(job)) {
            // Uh-oh! Better find any affected robots...
            robots.stream()
                .forEach(r -> r.jobCancelled(job));

            this.workingOnJobs.remove(job);
        }

        job.setCancelled();
    }

    /**
     * Get the jobs currently being worked on.
     *
     * @return List of jobs being worked on.
     */
    public List<Job> getWorkingOnJobs() {
        return this.workingOnJobs;
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
     * Get the offline robots
     *
     * @return Set of robots not in the warehouse
     */
    public RobotInfo[] getOfflineRobots() {
        return this.offlineRobots;
    }

    /**
     * Get a map of robot names mapped to their location in the warehouse.
     *
     * @return Map of robot names to locations
     */
    public Map<String, Location> getRobotLocations() {
        return robots.stream().collect(Collectors.toMap(Robot::getName, Robot::getCurrentLocation));
    }

    /**
     * Shutdown the warehouse. Lights off!
     */
    public void shutdown() {
        this.running = false;
    }
}
