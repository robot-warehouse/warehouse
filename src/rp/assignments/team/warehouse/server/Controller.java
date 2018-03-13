package rp.assignments.team.warehouse.server;

import java.io.File;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import rp.assignments.team.warehouse.server.gui.ManagementInterface;
import rp.assignments.team.warehouse.server.job.Job;
import rp.assignments.team.warehouse.server.job.input.Importer;
import rp.assignments.team.warehouse.server.job.selection.IJobSelector;
import rp.assignments.team.warehouse.server.job.selection.PriorityJobSelector;

// TODO
public class Controller {

    /** Can the application be started? Set to true when all inputs have been dealt with. */
    private boolean startable;

    private Warehouse warehouse;
    private ManagementInterface managementInterface;

    private File jobsFile;
    private File cancellationsFile;
    private File locationsFile;
    private File itemsFile;
    private File dropsFile;

    private Set<Job> importedJobs;

    private IJobSelector jobSelector;

    public Controller(Warehouse warehouse) {
        this.warehouse = warehouse;
        this.startable = false;
    }

    public void setManagementInterface(ManagementInterface managementInterface) {
        this.managementInterface = managementInterface;
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

            jobSelector = new PriorityJobSelector(importedJobs);

            Thread server = new ServerThread(this.warehouse, jobSelector);
            server.start();
        }
    }

    public boolean connectRobot(RobotInfo robotInfo, Location currentLocation, Facing currentFacing) {
    	Robot robot = new Robot(robotInfo, currentLocation, currentFacing);

    	if (robot.connect()) {
    		this.warehouse.addRobot(robot);
    		this.managementInterface.addRobotToTable(robot);
    		return true;
    	}

    	return false;
    }

    public void disconnectRobot(String robotName) {
        // TODO we need to safely disconnect the robot, preserving it's current job/pick if it had one and remove it from the warehouse list

        Optional<Robot> optionalRobot = this.warehouse.getRobots().stream().filter(r -> r.getName().equals(robotName)).findFirst();

        if (optionalRobot.isPresent()) {
            Robot robot = optionalRobot.get();

            robot.disconnect();

            if (!robot.isConnected()) {
                this.managementInterface.removeRobotFromTable();
            }
        }
    }

    /**
     * Cancel a job which may or may not have work started on it.
     *
     * @param job The job to be cancelled.
     */
    public void cancelJob(Job job) {
        // Remove job from the selector (may not do anything if we've already started the job)
        this.jobSelector.remove(job);

        // Notify the warehouse of the job cancellation
        this.warehouse.cancelJob(job);
    }

    public Map<String, Location> getRobotLocations() {
        return this.warehouse.getRobotLocations();
    }

    public RobotInfo[] getOfflineRobots() {
        return this.warehouse.getOfflineRobots();
    }

    public void shutdown() {
        this.warehouse.shutdown();
    }

    // region ManagementInterfaceUpdateMethods

    public void addRobotToCurrentRobotTable(Robot robot) {
        this.managementInterface.addRobotToTable(robot);
    }

    public void removeRobotFromCurrentRobotTable() {
        this.managementInterface.removeRobotFromTable();
    }

    // endregion

    public void addJobToCurrentJobsTable(Job job) {
        this.managementInterface.addJobToCurrentJobsTable(job);
    }

    public void removeJobFromCurrentJobsTable(Job job) {
        this.managementInterface.removeJobFromCurrentJobsTable(job);
    }
}
