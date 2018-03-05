package rp.assignments.team.warehouse.server;

import rp.assignments.team.warehouse.server.job.Job;
import rp.assignments.team.warehouse.server.job.Location;

public class Robot {

    private String name;
    private Location location;
    private Job currentJob;

    public Robot(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Job getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(Job currentJob) {
        this.currentJob = currentJob;
    }

    public String getName() {
        return name;
    }
}
