package rp.assignments.team.warehouse.server;

import rp.assignments.team.warehouse.server.job.selection.Location;

import java.io.File;
import java.util.Map;

public interface IController {

    public void loadFile(File file);

    public void setupConnections();

    public void disconnectRobot();

    public void cancelCurrentJob();

    public Map<String, Location> getRobotLocations();

    public void shutdown();
}
