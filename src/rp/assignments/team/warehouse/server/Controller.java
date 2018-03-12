package rp.assignments.team.warehouse.server;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import rp.assignments.team.warehouse.server.job.Job;
import rp.assignments.team.warehouse.server.job.input.Importer;
import rp.assignments.team.warehouse.server.job.selection.IJobSelector;
import rp.assignments.team.warehouse.server.job.selection.PriorityJobSelector;

// TODO
public class Controller {

    /** Can the application be started? Set to true when all inputs have been dealt with. */
    private boolean startable;

    private Warehouse warehouse;

    private File jobsFile;
    private File cancellationsFile;
    private File locationsFile;
    private File itemsFile;
    private File dropsFile;

    private List<Job> importedJobs;

    public Controller(Warehouse warehouse) {
        this.warehouse = warehouse;
        this.startable = false;
    }

    public boolean setJobsFile(File jobsFile) {
        return jobsFile.exists() && (this.jobsFile = jobsFile).exists();
    }

    public boolean setCancellationsFile(File cancellationsFile) {
        return cancellationsFile.exists() && (this.cancellationsFile = cancellationsFile).exists();
    }

    public boolean setLocationsFile(File locationsFile) {
        return locationsFile.exists() && (this.locationsFile = locationsFile).exists();
    }

    public boolean setItemsFile(File itemsFile) {
        return itemsFile.exists() && (this.itemsFile = itemsFile).exists();
    }

    public boolean setDropsFile(File dropsFile) {
        return dropsFile.exists() && (this.dropsFile = dropsFile).exists();
    }

    public void importFiles() {
        Importer importer = new Importer(jobsFile, cancellationsFile, locationsFile, itemsFile, dropsFile);
        importer.parse();

        this.importedJobs = importer.getJobs();

        this.startable = true;
    }

    public void startApplication() {
        if (this.startable) {
            assert importedJobs != null;

            IJobSelector jobSelector = new PriorityJobSelector(importedJobs);

            Thread server = new ServerThread(this.warehouse, jobSelector);
            server.start();
        }
    }

    public boolean connectRobot(RobotInfo robotInfo, Location currentLocation, Facing currentFacing) {
    	Robot robot = new Robot(robotInfo, currentLocation, currentFacing);

    	if (robot.connect()) {
    		warehouse.addRobot(robot);
    		return true;
    	}

    	return false;
    }

    public void disconnectRobot(int robotIndex) {
        // TODO we need to safely disconnect the robot, preserving it's current job/pick if it had one and remove it from the warehouse list
    }

    public void cancelCurrentJob(int jobID) {
        // TODO use jobID(?) to remove job from list of current jobs and send cancel commands to any robot that has a pick for it
    }

    public Map<String, Location> getRobotLocations() {
        return this.warehouse.getRobotLocations();
    }

    public Set<RobotInfo> getOnlineRobots() {
        return this.warehouse.getRobots().stream().map(Robot::getRobotInfo).collect(Collectors.toSet());
    }

    public Set<RobotInfo> getOfflineRobots() {
        return this.warehouse.getOfflineRobots();
    }

    public void shutdown() {
        this.warehouse.shutdown();
    }
}
