package rp.assignments.team.warehouse.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import rp.assignments.team.warehouse.server.job.Job;

// TODO what else is warehouse class going to hold?
public class Warehouse {

    private boolean running;

    private Queue<Job> jobQueue;
    private ArrayList<Robot> robots;

    public Warehouse() {
        this.running = true;
    }

    public boolean isRunning() {
        return this.running;
    }

    public Map<String, Location> getLocations() {
        return null; // TODO Get map of robot name and location from list of robots
    }

    public void addJobsToQueue(List<Job> jobs) {
        jobs.forEach(job -> jobQueue.offer(job));
    }

    public void shutdown() {
        this.running = false;
    }
}
