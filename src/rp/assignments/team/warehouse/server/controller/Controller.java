package rp.assignments.team.warehouse.server.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import rp.assignments.team.warehouse.server.Location;
import rp.assignments.team.warehouse.server.ServerThread;
import rp.assignments.team.warehouse.server.Warehouse;
import rp.assignments.team.warehouse.server.job.Job;
import rp.assignments.team.warehouse.server.job.input.Importer;
import rp.assignments.team.warehouse.server.job.selection.IJobSelector;
import rp.assignments.team.warehouse.server.job.selection.PriorityJobSelector;

// TODO
public class Controller {

    /** Can the application be started? Set to true when all inputs have been dealt with. */
    private boolean startable;

    private Warehouse warehouse;
    private Importer importer;

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
        importer = new Importer(jobsFile, cancellationsFile, locationsFile, itemsFile, dropsFile);

        importer.parse();

        List<Job> importedJobs = importer.getJobs();

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

    public void setupConnections() {

    }

    public void disconnectRobot() {

    }

    public void cancelCurrentJob() {

    }

    public Map<String, Location> getRobotLocations() {
        return null;
    }

    public void shutdown() {
        this.warehouse.shutdown();
    }
}
