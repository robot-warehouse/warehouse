package rp.assignments.team.warehouse.server.controller;

import java.io.File;
import java.util.Map;

import rp.assignments.team.warehouse.server.Location;

public interface IController {

    public void loadFile(File file);

    public void setupConnections();

    public void disconnectRobot();

    public void cancelCurrentJob();

    public Map<String, Location> getRobotLocations();

    public void shutdown();
}
