package rp.assignments.team.warehouse.server;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rp.assignments.team.warehouse.server.gui.ManagementInterface;
import rp.assignments.team.warehouse.server.job.Job;
import rp.assignments.team.warehouse.server.job.comparators.CompareByRewardComparator;
import rp.assignments.team.warehouse.server.job.input.Importer;
import rp.assignments.team.warehouse.server.job.selection.CancellationClassifier;
import rp.assignments.team.warehouse.server.job.selection.IJobSelector;
import rp.assignments.team.warehouse.server.job.selection.PriorityJobSelector;
import rp.assignments.team.warehouse.server.route.planning.RoutePlanning;
import rp.assignments.team.warehouse.shared.Facing;

public class Controller {

    /** Can the application be started? Set to true when all inputs have been dealt with. */
    private boolean startable;

    /** Stops anything from happening once you've imported the files once */
    private boolean alreadyImported;

    /** Stops classification happening more than once */
    private boolean alreadyClassified;

    private Importer importer;

    /** The Warehouse. */
    private Warehouse warehouse;

    /** The management interface GUI. */
    private ManagementInterface managementInterface;

    /** Directory for default files (below) - doesn't need to exist, GUI will require valid files to be uploaded */
    private static final String DEFAULT_INPUT_DIR = "./input/final/";

    /** The file to import the jobs from. */
    private File jobsFile = new File(DEFAULT_INPUT_DIR + "jobs.csv");

    /** The file to import the cancellation history from. */
    private File cancellationsFile = new File(DEFAULT_INPUT_DIR + "cancellations.csv");

    /** The file to import the training jobs from. */
    private File trainingFile = new File(DEFAULT_INPUT_DIR + "training_jobs.csv");

    /** The file to import the item locations from. */
    private File locationsFile = new File(DEFAULT_INPUT_DIR + "locations.csv");

    /** The file to import the items from. */
    private File itemsFile = new File(DEFAULT_INPUT_DIR + "items.csv");

    /** The file to import the drop locations from. */
    private File dropsFile = new File(DEFAULT_INPUT_DIR + "drops.csv");


    /** Set of all imported jobs. */
    private Set<Job> jobs;

    /** Set of all imported drop location */
    private Set<Location> dropLocations;

    /** The job selector */
    private IJobSelector jobSelector;

    /** The instance of the route planner passed to every robot */
    private RoutePlanning routePlanner;

    private static final Logger logger = LogManager.getLogger(Controller.class);

    /**
     * @param warehouse The Warehouse.
     */
    public Controller(Warehouse warehouse) {
        this.warehouse = warehouse;
        this.startable = false;
        this.alreadyImported = false;
        this.routePlanner = new RoutePlanning(warehouse);
    }

    /**
     * Set the management interface for the controller.
     *
     * @param managementInterface The management interface GUI.
     */
    public void setManagementInterface(ManagementInterface managementInterface) {
        this.managementInterface = managementInterface;
    }

    /**
     * Set the file to import the jobs from.
     *
     * @param jobsFile The file to import the jobs from
     * @return True if successfully set.
     */
    public boolean setJobsFile(File jobsFile) {
        return jobsFile.exists() && (this.jobsFile = jobsFile).exists();
    }

    /**
     * Set the file to import the cancellation history from.
     *
     * @param cancellationsFile The file to import the cancellation history from.
     * @return True if successfully set.
     */
    public boolean setCancellationsFile(File cancellationsFile) {
        return cancellationsFile.exists() && (this.cancellationsFile = cancellationsFile).exists();
    }

    /**
     * Set the file to import the training jobs from.
     *
     * @param trainingFile The file to import the training jobs from.
     * @return True if successfully set.
     */
    public boolean setTrainingFile(File trainingFile) {
        return trainingFile.exists() && (this.trainingFile = trainingFile).exists();
    }

    /**
     * Set the file to import the item locations from.
     *
     * @param locationsFile The file to import the item locations from.
     * @return True if successfully set.
     */
    public boolean setLocationsFile(File locationsFile) {
        return locationsFile.exists() && (this.locationsFile = locationsFile).exists();
    }

    /**
     * Set the file to import the items from.
     *
     * @param itemsFile The file to import the items from.
     * @return True if successfully set.
     */
    public boolean setItemsFile(File itemsFile) {
        return itemsFile.exists() && (this.itemsFile = itemsFile).exists();
    }

    /**
     * Set the file to import the drop locations from.
     *
     * @param dropsFile The file to import the drop locations from.
     * @return True if successfully set.
     */
    public boolean setDropsFile(File dropsFile) {
        return dropsFile.exists() && (this.dropsFile = dropsFile).exists();
    }

    /**
     * Checks if all the required files have been set and exist.
     *
     * @return True if {@link #importFiles} can be called.
     */
    public boolean readyForImport() {
        return !this.alreadyImported && this.jobsFile != null && this.cancellationsFile != null && this.trainingFile != null &&
        this.locationsFile != null && this.itemsFile != null && this.dropsFile != null && this.jobsFile.exists() && this.cancellationsFile.exists() && this.trainingFile.exists() &&
        this.locationsFile.exists() && this.itemsFile.exists() && this.dropsFile.exists();
    }

    public boolean readyForClassification() {
        return this.alreadyImported && !this.alreadyClassified;
    }

    /**
     * Run the importer on the specified input files.
     */
    public void importFiles() {
        if (this.readyForImport()) {
            importer = new Importer(this.jobsFile, this.cancellationsFile, this.trainingFile,
                    this.locationsFile, this.itemsFile, this.dropsFile);
            importer.parse();

            this.jobs = importer.getJobs();
            this.dropLocations = importer.getDrops();

            List<Job> jobs = new ArrayList<Job>(this.jobs);
            Collections.sort(jobs, new CompareByRewardComparator());

            this.managementInterface.addJobsToLoadedJobsTable(jobs);

            this.startable = true;
            this.alreadyImported = true;
        }
    }

    public void runClassification() {
        if (this.readyForClassification()) {
            CancellationClassifier classifier = new CancellationClassifier(importer);
            try {
                classifier.train();
                classifier.classify();
            } catch (Exception e) {
                e.printStackTrace();
                logger.fatal("Exception thrown during cancellation classification thus aborting.", e);
            }

            this.alreadyClassified = true;
        }
    }

    /**
     * Start the warehouse up.
     */
    public void startApplication() {
        if (this.startable) {
            assert jobs != null;

            jobSelector = new PriorityJobSelector(jobs);

            (new ServerThread(this.warehouse, jobSelector)).start();
        }
    }

    /**
     * Add a robot to the warehouse.
     *
     * @param robotInfo The robot's information.
     * @param currentLocation The robot's starting location.
     * @param currentFacingDirection The robot's starting direction.
     * @return True if successfully connected & added.
     */
    public boolean addRobot(RobotInfo robotInfo, Location currentLocation, Facing currentFacingDirection) {
        Robot robot = new Robot(robotInfo, currentLocation, currentFacingDirection);

        if (robot.connect(this.routePlanner)) {
            this.warehouse.addRobot(robot);
            return true;
        }

        return false;
    }

    /**
     * Remove a robot from the warehouse.
     *
     * @param robot The robot to remove.
     */
    public void removeRobot(Robot robot) {
        robot.removeFromWarehouse();
        this.warehouse.removeRobot(robot);

        if (!robot.isConnected()) {
            this.managementInterface.removeRobotFromOnlineRobotsTable(robot);
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

    /**
     * Get set of offline robots' RobotInfo.
     *
     * @return Set of offline robots' RobotInfo.
     */
    public Set<RobotInfo> getOfflineRobots() {
        return this.warehouse.getOfflineRobots();
    }

    /**
     * Get the drop locations.
     *
     * @return Set of drop locations.
     */
    public Set<Location> getDropLocations() {
        return this.dropLocations;
    }

    /**
     * Shutdown the warehouse. Lights off!
     */
    public void shutdown() {
        this.warehouse.shutdown();
    }

    /**
     * Add a robot to the robot table in the GUI.
     *
     * @param robot The robot to add to the table.
     */
    public void addRobotToCurrentRobotTable(Robot robot) {
        this.managementInterface.addRobotToOnlineRobotsTable(robot);
    }

    /**
     * Add a job to the current jobs table in the GUI.
     *
     * @param job The job to add to the table.
     */
    public void addJobToCurrentJobsTable(Job job) {
        this.managementInterface.addJobToCurrentJobsTable(job);
    }

    /**
     * Remove a job from the current jobs table in the GUI.
     *
     * @param job The job to remove from the table.
     */
    public void removeJobFromCurrentJobsTable(Job job) {
        this.managementInterface.removeJobFromCurrentJobsTable(job);
    }

    /**
     * Add a job to the current jobs table in the GUI. Also removes it from the current jobs table and updates the score
     * with the score of the completed job added to the total score
     *
     * @param job The job to add to the table.
     */
    public void addJobToCompletedJobsTable(Job job) {
        this.managementInterface.addJobToCompletedJobsTable(job);
    }

    /**
     * Announces to the user that all jobs have been assigned to robots
     */
    public void assignedAllJobs() {
        this.managementInterface.assignedAllJobs();
    }
}
