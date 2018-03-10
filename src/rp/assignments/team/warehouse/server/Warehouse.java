package rp.assignments.team.warehouse.server;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import rp.assignments.team.warehouse.server.job.Job;

// TODO what else is warehouse class going to hold?
public class Warehouse {

    private boolean running;

    private List<Job> workingOnJobs;

    private Set<Robot> robots;

    public Warehouse() {
        this.running = true;
        this.robots = new HashSet<Robot>();
    }

    public boolean isRunning() {
        return this.running;
    }
    
    public void addWorkingOnJob(Job job) {
    	this.workingOnJobs.add(job);
    }

    public List<Job> getWorkingOnJobs() {
        return this.workingOnJobs;
    }

    public void addRobot(Robot robot) {
        this.robots.add(robot);
    }

    public Set<Robot> getRobots() {
        return this.robots;
    }

    public Map<String, Location> getRobotLocations() {
        return robots.stream().collect(Collectors.toMap(Robot::getName, Robot::getCurrentLocation));
    }

    public void shutdown() {
        this.running = false;
    }
}
