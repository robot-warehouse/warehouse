package rp.assignments.team.warehouse.server.gui;

import java.awt.*;
import java.io.File;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import rp.assignments.team.warehouse.server.Controller;
import rp.assignments.team.warehouse.server.Facing;
import rp.assignments.team.warehouse.server.Location;
import rp.assignments.team.warehouse.server.Robot;
import rp.assignments.team.warehouse.server.RobotInfo;
import rp.assignments.team.warehouse.server.Warehouse;
import rp.assignments.team.warehouse.server.job.Job;

public class ManagementInterface {

    private final int WINDOW_HEIGHT = 510;
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_START_POS = 100;
    private final int BUTTON_HEIGHT = 25;
    private final int BUTTON_WIDTH_WIDE = 153;
    private final int BUTTON_WIDTH_NARROW = 100;
    private final int LABEL_HEIGHT = 40;
    private final int LABEL_WIDTH = 155;
    private final int LABEL_WIDTH_WIDE = 400;
    private final int TABLE_WIDTH = 345;

    private final int Y_TOP = 60;

    private final int PANE_LEFT_X = 20;
    private final int PANE_MIDDLE_X = 200;
    private final int PANE_RIGHT_X = 425;

    private Controller controller;
    private JFrame frame;
    private JTable tblOnlineRobots;
    private JTable tblCurrentJobs;
    
    private File baseDirectory = new File("./input");

    /**
     * Create the application.
     */
    public ManagementInterface(Controller controller) {
        this.controller = controller;
        initialize();
        this.frame.setVisible(true);
    }

    // test method for gui
    public static void main(String[] args) {
        new ManagementInterface(new Controller(new Warehouse()));
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        this.frame = new JFrame();
        this.frame.setBounds(WINDOW_START_POS, WINDOW_START_POS, WINDOW_WIDTH, WINDOW_HEIGHT);
        this.frame.setTitle("Team 3.4 - Warehouse Management System");
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.getContentPane().setLayout(null);

        JLabel lblTitle = new JLabel("Warehouse Management Interface");
        lblTitle.setBounds((WINDOW_WIDTH / 2) - (LABEL_WIDTH_WIDE / 2), 10, LABEL_WIDTH_WIDE, LABEL_HEIGHT);
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        lblTitle.setFont(new Font(null, Font.PLAIN, 21));
        this.frame.getContentPane().add(lblTitle);

        // region LeftPane
        JButton btnUploadCancellationsFile = new JButton("Upload Cancellations");
        btnUploadCancellationsFile.addActionListener(event -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(this.baseDirectory);
            int returnVal = fileChooser.showOpenDialog(this.frame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                if (!controller.setCancellationsFile(fileChooser.getSelectedFile())) {
                    JOptionPane.showMessageDialog(this.frame, "That file does not exist!");
                }
            }
        });
        btnUploadCancellationsFile.setBounds(PANE_LEFT_X, Y_TOP, BUTTON_WIDTH_WIDE, BUTTON_HEIGHT);
        this.frame.getContentPane().add(btnUploadCancellationsFile);

        JButton btnUploadDropsFile = new JButton("Upload Drops");
        btnUploadDropsFile.addActionListener(event -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(this.baseDirectory);
            int returnVal = fileChooser.showOpenDialog(this.frame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                if (!controller.setDropsFile(fileChooser.getSelectedFile())) {
                    JOptionPane.showMessageDialog(this.frame, "That file does not exist!");
                }
            }
        });
        btnUploadDropsFile.setBounds(PANE_LEFT_X, getYBelow(btnUploadCancellationsFile), BUTTON_WIDTH_WIDE,
                BUTTON_HEIGHT);
        this.frame.getContentPane().add(btnUploadDropsFile);

        JButton btnUploadItemsFile = new JButton("Upload Items");
        btnUploadItemsFile.addActionListener(event -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(this.baseDirectory);
            int returnVal = fileChooser.showOpenDialog(this.frame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                if (!controller.setItemsFile(fileChooser.getSelectedFile())) {
                    JOptionPane.showMessageDialog(this.frame, "That file does not exist!");
                }
            }
        });
        btnUploadItemsFile.setBounds(PANE_LEFT_X, getYBelow(btnUploadDropsFile), BUTTON_WIDTH_WIDE, BUTTON_HEIGHT);
        this.frame.getContentPane().add(btnUploadItemsFile);

        JButton btnUploadJobsFile = new JButton("Upload Jobs");
        btnUploadJobsFile.addActionListener(event -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(this.baseDirectory);
            int returnVal = fileChooser.showOpenDialog(this.frame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                if (!this.controller.setJobsFile(fileChooser.getSelectedFile())) {
                    JOptionPane.showMessageDialog(this.frame, "That file does not exist!");
                }
            }
        });
        btnUploadJobsFile.setBounds(PANE_LEFT_X, getYBelow(btnUploadItemsFile), BUTTON_WIDTH_WIDE, BUTTON_HEIGHT);
        this.frame.getContentPane().add(btnUploadJobsFile);

        JButton btnUploadLocationsFile = new JButton("Upload Locations");
        btnUploadLocationsFile.addActionListener(event -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(this.baseDirectory);
            int returnVal = fileChooser.showOpenDialog(this.frame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                if (!this.controller.setLocationsFile(fileChooser.getSelectedFile())) {
                    JOptionPane.showMessageDialog(this.frame, "That file does not exist!");
                }
            }
        });
        btnUploadLocationsFile.setBounds(PANE_LEFT_X, getYBelow(btnUploadJobsFile), BUTTON_WIDTH_WIDE, BUTTON_HEIGHT);
        this.frame.getContentPane().add(btnUploadLocationsFile);

        JButton btnImportFiles = new JButton("Import Files");
        btnImportFiles.addActionListener(event -> this.controller.importFiles());
        btnImportFiles.setBounds(PANE_LEFT_X, getYBelow(btnUploadLocationsFile) + 50, BUTTON_WIDTH_WIDE,
                BUTTON_HEIGHT * 2);
        this.frame.getContentPane().add(btnImportFiles);
        // endregion

        // region MiddlePane
        JLabel lblLoadedJobs = new JLabel("Loaded Jobs");
        lblLoadedJobs.setBounds(PANE_MIDDLE_X, Y_TOP, 80, 18);
        this.frame.getContentPane().add(lblLoadedJobs);

        JTable tblLoadedJobs = new JTable();
        // TODO setup data connection
        tblLoadedJobs.setBounds(PANE_MIDDLE_X, getYBelow(lblLoadedJobs), 200, 220);
        this.frame.getContentPane().add(tblLoadedJobs);
        // endregion

        // region RightPane
        JButton btnConnectRobot = new JButton("Connect Robot");
        btnConnectRobot.addActionListener(event -> {
            RobotInfo[] offlineRobots = this.controller.getOfflineRobots();
            if (offlineRobots.length == 0) {
                JOptionPane.showMessageDialog(this.frame, "We've run out of robots to connect to!");
            } else {
                RobotInfo robotInfo = (RobotInfo) JOptionPane.showInputDialog(
                        this.frame,
                        "Select a robot to connect to",
                        "Connect Robot",
                        JOptionPane.PLAIN_MESSAGE,
                        new ImageIcon(),
                        offlineRobots,
                        offlineRobots[0]);

                if (robotInfo != null) {
                    Integer x = (Integer) JOptionPane.showInputDialog(
                            this.frame,
                            "Select x start position",
                            "Select x",
                            JOptionPane.PLAIN_MESSAGE,
                            new ImageIcon(),
                            getOrderedIntArray(Location.MIN_X, Location.MAX_X),
                            Location.MIN_X);

                    if (x != null) {
                        Integer y = (Integer) JOptionPane.showInputDialog(
                                this.frame,
                                "Select y start position",
                                "Select y",
                                JOptionPane.PLAIN_MESSAGE,
                                new ImageIcon(),
                                getOrderedIntArray(Location.MIN_Y, Location.MAX_Y),
                                Location.MIN_Y);

                        if (y != null) {
                            Facing facingDirection = (Facing) JOptionPane.showInputDialog(
                                    this.frame,
                                    "Select facing direction",
                                    "Select facing",
                                    JOptionPane.PLAIN_MESSAGE,
                                    new ImageIcon(),
                                    Facing.values(),
                                    Facing.NORTH);

                            if (facingDirection != null) {
                                if (!this.controller.connectRobot(robotInfo, new Location(x, y), facingDirection)) {
                                    JOptionPane.showMessageDialog(this.frame, "Failed to connect to robot.");
                                }
                            }
                        }
                    }
                }
            }
        });
        btnConnectRobot.setBounds(PANE_RIGHT_X, Y_TOP, BUTTON_WIDTH_WIDE, BUTTON_HEIGHT);
        this.frame.getContentPane().add(btnConnectRobot);

        JButton btnDisconnectRobot = new JButton("Disconnect Robot");
        btnDisconnectRobot.addActionListener(event -> {
            if (!this.tblOnlineRobots.getSelectionModel().isSelectionEmpty()) {
                this.tblOnlineRobots.setRowSelectionAllowed(false); // supposed to lock selection while robot is being disconnected
                this.controller.disconnectRobot((String) this.tblOnlineRobots.getValueAt(this.tblOnlineRobots.getSelectedRow(), 0));
                this.tblOnlineRobots.setRowSelectionAllowed(true);
            }
        });
        btnDisconnectRobot.setBounds(PANE_RIGHT_X + TABLE_WIDTH - BUTTON_WIDTH_WIDE, Y_TOP, BUTTON_WIDTH_WIDE,
                BUTTON_HEIGHT);
        this.frame.getContentPane().add(btnDisconnectRobot);

        JLabel lblOnlineRobots = new JLabel("Online Robots");
        lblOnlineRobots.setBounds(PANE_RIGHT_X, getYBelow(btnConnectRobot) - 10, LABEL_WIDTH, LABEL_HEIGHT);
        this.frame.getContentPane().add(lblOnlineRobots);

        this.tblOnlineRobots = new JTable();
        this.tblOnlineRobots.setBounds(PANE_RIGHT_X, getYBelow(lblOnlineRobots) - 15, TABLE_WIDTH, 80);
        this.frame.add(this.tblOnlineRobots);

        JLabel lblCurrentJobs = new JLabel("Current Jobs");
        lblCurrentJobs.setBounds(PANE_RIGHT_X, getYBelow(tblOnlineRobots) - 10, LABEL_WIDTH, LABEL_HEIGHT);
        this.frame.getContentPane().add(lblCurrentJobs);

        this.tblCurrentJobs = new JTable(new JobTableModel());
        this.tblCurrentJobs.setBounds(PANE_RIGHT_X, getYBelow(lblCurrentJobs) - 15, TABLE_WIDTH, 200);
        this.frame.getContentPane().add(this.tblCurrentJobs);

        JButton btnCancelJob = new JButton("Cancel Job");
        btnCancelJob.addActionListener(event -> {
            if (tblCurrentJobs.getSelectionModel().isSelectionEmpty()) {
                JOptionPane.showMessageDialog(this.frame, "Please select a job to cancel");
            } else {
                int index = tblCurrentJobs.getSelectedRow();
                Job job = getJobFromCurrentJobsTable(index);
                this.controller.cancelJob(job);
            }
        });
        btnCancelJob.setBounds(PANE_RIGHT_X + (TABLE_WIDTH / 2) - (BUTTON_WIDTH_WIDE / 2), getYBelow(tblCurrentJobs),
                BUTTON_WIDTH_WIDE, BUTTON_HEIGHT);
        this.frame.getContentPane().add(btnCancelJob);
        // endregion

        // region bottomPanel
        JButton btnStartWarehouse = new JButton("Start Warehouse");
        btnStartWarehouse.addActionListener(event -> this.controller.startApplication());
        btnStartWarehouse.setBounds(PANE_MIDDLE_X + 20, 350, BUTTON_WIDTH_WIDE, BUTTON_HEIGHT);
        this.frame.getContentPane().add(btnStartWarehouse);

        JButton btnShutdown = new JButton("Shutdown");
        btnShutdown.addActionListener(event -> {
            if (JOptionPane.showConfirmDialog(this.frame, "Are you sure you want to shutdown and exit the program?",
                    "Confirm Shutdown", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
                this.controller.shutdown();
            }
        });
        btnShutdown.setBounds(PANE_MIDDLE_X + 50, getYBelow(btnStartWarehouse), BUTTON_WIDTH_NARROW, BUTTON_HEIGHT);
        this.frame.getContentPane().add(btnShutdown);
        // endregion

        // TODO create RobotTableModel, take JobTableModel as example
        // TODO create updates for RobotTableModel
    }

    //region PublicMethodsForController

    /**
     * Adds a connected robot to the online robots table.
     *
     * @param robot The robot to add to the table
     */
    public void addRobotToTable(Robot robot) {
        ((DefaultTableModel) this.tblOnlineRobots.getModel()).addRow(
                new Object[]{
                        robot.getName(),
                        robot.getCurrentLocation(),
                        robot.getCurrentPicks(),
                        robot.getCurrentFacingDirection(),
                        robot.getCurrentWeight()});
    }

    /**
     * Updates the information of a robot on the online robots table in the GUI.
     *
     * @param robot The robot to update
     */
    public void updateRobotInRobotTable(Robot robot) {
        // TODO
    }

    /**
     * Remove the selected robot from the robot table in the GUI.
     */
    public void removeRobotFromTable() {
        this.tblOnlineRobots.remove(this.tblOnlineRobots.getSelectedRow());
    }

    /**
     * Add a job to the current jobs table in the GUI.
     *
     * @param job The job to add to the table.
     */
    public void addJobToCurrentJobsTable(Job job) {
        JobTableModel model = (JobTableModel) this.tblCurrentJobs.getModel();
        model.addRow(job);
    }

    /**
     * Get a job from the current jobs table in the GUI.
     *
     * @param rowIndex The row index of the job.
     */
    public Job getJobFromCurrentJobsTable(int rowIndex) {
        return ((JobTableModel) this.tblCurrentJobs.getModel()).getRow(rowIndex);
    }

    /**
     * Remove a job from the current jobs table in the GUI.
     *
     * @param job The job to remove from the table.
     */
    public void removeJobFromCurrentJobsTable(Job job) {
        JobTableModel model = (JobTableModel) this.tblCurrentJobs.getModel();
        model.removeRow(job);
    }

    //endregion

    /**
     * Gets a Y value 10 pixels below the bottom of the passed component
     *
     * @param component The component to get the Y value underneath
     * @return int
     */
    private int getYBelow(JComponent component) {
        return component.getY() + component.getHeight() + 10;
    }

    /**
     * Makes array consisting of numbers ascending from minValue to maxValue (e.g. [0, 1, 2, 3, 4])
     *
     * @param minValue Value to start array from
     * @param maxValue Value to end array at
     * @return Integer array
     */
    private Integer[] getOrderedIntArray(int minValue, int maxValue) {
        Integer[] array = new Integer[maxValue - minValue + 1];

        for (int i = minValue; i <= maxValue; i++) {
            array[i] = minValue + i;
        }

        return array;
    }
}
