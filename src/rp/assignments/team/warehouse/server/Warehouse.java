package rp.assignments.team.warehouse.server;

import rp.assignments.team.warehouse.server.job.Job;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// TODO what else is warehouse class going to hold?
public class Warehouse {

    private Queue<Job> jobQueue;
    private ArrayList<Robot> robots;

    public Map<String, Location> getLocations() {
        return null; // TODO Get map of robot name and location from list of robots
    }

    public void addJobsToQueue(List<Job> jobs) {
        jobs.forEach(job -> jobQueue.offer(job));
    }
}
