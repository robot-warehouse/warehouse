package rp.assignments.team.warehouse.server;

import rp.assignments.team.warehouse.server.job.selection.Location;

import java.util.ArrayList;
import java.util.Map;

public class Warehouse {

    private ArrayList<Robot> robots;

    public Warehouse(ArrayList<Robot> robots) {
        this.robots = robots;
    }

    public Map<String, Location> getLocations() {
        return null; // TODO
    }
}
