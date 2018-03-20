package rp.assignments.team.warehouse.server.gui;

import java.awt.Font;
import java.io.File;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.WindowConstants;

import rp.assignments.team.warehouse.server.Controller;
import rp.assignments.team.warehouse.server.Location;
import rp.assignments.team.warehouse.server.Robot;
import rp.assignments.team.warehouse.server.RobotInfo;
import rp.assignments.team.warehouse.server.Warehouse;
import rp.assignments.team.warehouse.server.job.Job;
import rp.assignments.team.warehouse.shared.Facing;

public class ManagementInterface {

	/** */
	private final int WINDOW_HEIGHT = 510;

	/** */
	private final int WINDOW_WIDTH = 800;

	/** */
	private final int WINDOW_START_POS = 100;

	/** */
	private final int BUTTON_HEIGHT = 25;

	/** */
	private final int BUTTON_WIDTH_WIDE = 153;

	/** */
	private final int LABEL_HEIGHT = 40;

	/** */
	private final int LABEL_WIDTH = 155;

	/** */
	private final int LABEL_WIDTH_WIDE = 400;

	/** */
	private Controller controller;

	/** */
	private JFrame frame;

	/** */
	private JTable tblOnlineRobots;

	/** */
	private JTable tblCurrentJobs;

	/** */
	private JTable tblLoadedJobs;

	/** */
	private JTable tblCompletedJobs;

	/** */
	private JLabel lblTotalScore;

	/** */
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
		Controller controller = new Controller(new Warehouse());

		ManagementInterface managementInterface = new ManagementInterface(controller);

		controller.setManagementInterface(managementInterface);
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
		lblTitle.setBounds(23, 10, LABEL_WIDTH_WIDE, LABEL_HEIGHT);
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
		btnUploadCancellationsFile.setBounds(12, 238, BUTTON_WIDTH_WIDE, BUTTON_HEIGHT);
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
		btnUploadDropsFile.setBounds(12, 200, BUTTON_WIDTH_WIDE, BUTTON_HEIGHT);
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
		btnUploadItemsFile.setBounds(12, 162, BUTTON_WIDTH_WIDE, BUTTON_HEIGHT);
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
		btnUploadJobsFile.setBounds(12, 124, BUTTON_WIDTH_WIDE, BUTTON_HEIGHT);
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
		btnUploadLocationsFile.setBounds(12, 86, BUTTON_WIDTH_WIDE, BUTTON_HEIGHT);
		this.frame.getContentPane().add(btnUploadLocationsFile);

		JButton btnImportFiles = new JButton("Import Files");
		btnImportFiles.addActionListener(event -> this.controller.importFiles());
		btnImportFiles.setBounds(12, 276, BUTTON_WIDTH_WIDE, BUTTON_HEIGHT * 2);
		this.frame.getContentPane().add(btnImportFiles);
		// endregion

		// region MiddlePane
		JLabel lblLoadedJobs = new JLabel("Loaded Jobs");
		lblLoadedJobs.setBounds(229, 63, 101, 18);
		this.frame.getContentPane().add(lblLoadedJobs);

		this.tblLoadedJobs = new JTable(new JobTableModel());
		this.tblLoadedJobs.setBounds(173, 84, 201, 170);
		this.frame.getContentPane().add(this.tblLoadedJobs);
		// endregion

		// region RightPane
		JButton btnConnectRobot = new JButton("Connect Robot");
		btnConnectRobot.addActionListener(event -> {
			RobotInfo[] offlineRobots = this.controller.getOfflineRobots().toArray(new RobotInfo[0]);
			if (offlineRobots.length == 0) {
				JOptionPane.showMessageDialog(this.frame, "We've run out of robots to connect to!");
			} else {
				RobotInfo robotInfo = (RobotInfo) JOptionPane.showInputDialog(this.frame,
						"Select a robot to connect to", "Connect Robot", JOptionPane.PLAIN_MESSAGE, new ImageIcon(),
						offlineRobots, offlineRobots[0]);

				if (robotInfo != null) {
					Integer x = (Integer) JOptionPane.showInputDialog(this.frame, "Select x start position", "Select x",
							JOptionPane.PLAIN_MESSAGE, new ImageIcon(),
							getOrderedIntArray(Location.MIN_X, Location.MAX_X), Location.MIN_X);

					if (x != null) {
						Integer y = (Integer) JOptionPane.showInputDialog(this.frame, "Select y start position",
								"Select y", JOptionPane.PLAIN_MESSAGE, new ImageIcon(),
								getOrderedIntArray(Location.MIN_Y, Location.MAX_Y), Location.MIN_Y);

						if (y != null) {
							Facing facingDirection = (Facing) JOptionPane.showInputDialog(this.frame,
									"Select facing direction", "Select facing", JOptionPane.PLAIN_MESSAGE,
									new ImageIcon(), Facing.values(), Facing.NORTH);

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
		btnConnectRobot.setBounds(458, 114, 132, 25);
		this.frame.getContentPane().add(btnConnectRobot);

		JButton btnDisconnectRobot = new JButton("Disconnect Robot");
		btnDisconnectRobot.addActionListener(event -> {
			if (this.tblOnlineRobots.getSelectionModel().isSelectionEmpty()) {
				JOptionPane.showMessageDialog(this.frame, "Please select a robot to disconnect");
			} else {
				this.controller.disconnectRobot(((RobotTableModel) this.tblOnlineRobots.getModel())
						.getRow(this.tblOnlineRobots.getSelectedRow()));
			}
		});
		btnDisconnectRobot.setBounds(602, 114, 140, 25);
		this.frame.getContentPane().add(btnDisconnectRobot);

		JLabel lblOnlineRobots = new JLabel("Online Robots");
		lblOnlineRobots.setBounds(425, 0, LABEL_WIDTH, LABEL_HEIGHT);
		this.frame.getContentPane().add(lblOnlineRobots);

		this.tblOnlineRobots = new JTable();
		this.tblOnlineRobots.setBounds(425, 41, 345, 60);
		this.frame.getContentPane().add(this.tblOnlineRobots);

		JLabel lblCurrentJobs = new JLabel("Completed Jobs");
		lblCurrentJobs.setBounds(425, 308, LABEL_WIDTH, LABEL_HEIGHT);
		this.frame.getContentPane().add(lblCurrentJobs);

		this.tblCurrentJobs = new JTable(new JobTableModel());
		this.tblCurrentJobs.setBounds(425, 178, 345, 85);
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
		btnCancelJob.setBounds(530, 276, BUTTON_WIDTH_WIDE, BUTTON_HEIGHT);
		this.frame.getContentPane().add(btnCancelJob);
		// endregion

		// region bottomPanel
		JButton btnStartWarehouse = new JButton("Start Warehouse");
		btnStartWarehouse.addActionListener(event -> this.controller.startApplication());
		btnStartWarehouse.setBounds(196, 267, BUTTON_WIDTH_WIDE, BUTTON_HEIGHT);
		this.frame.getContentPane().add(btnStartWarehouse);

		JButton btnShutdown = new JButton("Shutdown");
		btnShutdown.addActionListener(event -> {
			if (JOptionPane.showConfirmDialog(this.frame, "Are you sure you want to shutdown and exit the program?",
					"Confirm Shutdown", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
				this.controller.shutdown();
			}
		});
		btnShutdown.setBounds(217, 305, 113, 25);
		this.frame.getContentPane().add(btnShutdown);

		JSeparator separator = new JSeparator();
		separator.setBounds(12, 48, 400, 2);
		frame.getContentPane().add(separator);

		tblCompletedJobs = new JTable(null);
		tblCompletedJobs.setBounds(425, 346, 345, 77);
		frame.getContentPane().add(tblCompletedJobs);

		JLabel lblNewLabel = new JLabel("Current Jobs");
		lblNewLabel.setBounds(425, 138, 155, 40);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblTotalScore = new JLabel("Grand Total Score");
		lblTotalScore.setBounds(156, 375, 113, 25);
		frame.getContentPane().add(lblTotalScore);

		this.lblTotalScore = new JLabel("0");
		this.lblTotalScore.setHorizontalAlignment(JLabel.CENTER);
		this.lblTotalScore.setBounds(141, 397, 122, 60);
		frame.getContentPane().add(this.lblTotalScore);
		// endregion

		// TODO create updates for RobotTableModel
	}

	// region PublicMethodsForController

	/**
	 * Adds a connected robot to the online robots table.
	 *
	 * @param robot
	 *            The robot to add to the table
	 */
	public void addRobotToOnlineRobotsTable(Robot robot) {
		RobotTableModel model = (RobotTableModel) this.tblOnlineRobots.getModel();
		model.addRow(robot);
	}

	/**
	 * Updates the information of a robot on the online robots table in the GUI.
	 *
	 * @param robot
	 *            The robot to update
	 */
	public void updateRobotInRobotTable(Robot robot) {
		// TODO maybe we don't need this
	}

	/**
	 * Remove the selected robot from the robot table in the GUI.
	 */
	public void removeRobotFromOnlineRobotsTable(Robot robot) {
		RobotTableModel model = (RobotTableModel) this.tblOnlineRobots.getModel();
		model.removeRow(robot);
	}

	/**
	 * Adds a list of jobs to the loaded jobs table
	 *
	 * @param jobs
	 *            The list of jobs
	 */
	public void addJobsToLoadedJobsTable(Set<Job> jobs) {
		JobTableModel model = (JobTableModel) this.tblLoadedJobs.getModel();
		jobs.forEach(job -> {
			if (job != null) {
				model.addRow(job);
			}
		});
	}

	/**
	 * Add a job to the current jobs table in the GUI.
	 *
	 * @param job
	 *            The job to add to the table.
	 */
	public void addJobToCurrentJobsTable(Job job) {
		JobTableModel model = (JobTableModel) this.tblCurrentJobs.getModel();
		model.addRow(job);
	}

	/**
	 * Get a job from the current jobs table in the GUI.
	 *
	 * @param rowIndex
	 *            The row index of the job.
	 */
	public Job getJobFromCurrentJobsTable(int rowIndex) {
		return ((JobTableModel) this.tblCurrentJobs.getModel()).getRow(rowIndex);
	}

	/**
	 * Remove a job from the current jobs table in the GUI.
	 *
	 * @param job
	 *            The job to remove from the table.
	 */
	public void removeJobFromCurrentJobsTable(Job job) {
		JobTableModel model = (JobTableModel) this.tblCurrentJobs.getModel();
		model.removeRow(job);
	}

	/**
	 * Add a job to the current jobs table in the GUI.
	 *
	 * @param job
	 *            The job to add to the table.
	 */
	public void addJobToCompletedJobsTable(Job job) {
		JobTableModel model = (JobTableModel) this.tblCompletedJobs.getModel();
		model.addRow(job);
	}

	/**
	 * Updates the total score display on the GUI
	 *
	 * @param score
	 *            The new total score
	 */
	public void updateTotalScore(int score) {
		this.lblTotalScore.setText(String.valueOf(score));
	}

	// endregion

	/**
	 * Makes array consisting of numbers ascending from minValue to maxValue
	 * (e.g. [0, 1, 2, 3, 4])
	 *
	 * @param minValue
	 *            Value to start array from
	 * @param maxValue
	 *            Value to end array at
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
