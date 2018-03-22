package rp.assignments.team.warehouse.server.gui;

import java.awt.Font;
import java.io.File;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.WindowConstants;

import rp.assignments.team.warehouse.server.Controller;
import rp.assignments.team.warehouse.server.Location;
import rp.assignments.team.warehouse.server.Robot;
import rp.assignments.team.warehouse.server.RobotInfo;
import rp.assignments.team.warehouse.server.job.Job;
import rp.assignments.team.warehouse.shared.Facing;

public class ManagementInterface {

	/** The height of the window */
	private final int WINDOW_HEIGHT = 510;

	/** The width of the window */
	private final int WINDOW_WIDTH = 800;

	/** The x and y start pos of the window on the screen */
	private final int WINDOW_START_POS = 100;

	/** The height of a button */
	private final int BUTTON_HEIGHT = 25;

	/** The wider width of a button */
	private final int BUTTON_WIDTH_WIDE = 153;

	/** The height of a label */
	private final int LABEL_HEIGHT = 40;

	/** The width of a label */
	private final int LABEL_WIDTH = 155;

	/** The wider width of a label */
	private final int LABEL_WIDTH_WIDE = 400;

	/** The instance of the controller */
	private Controller controller;

	/** The frame itself */
	private JFrame frame;

	/** Table showing online robots */
	private JTable tblOnlineRobots;

	/** Table showing the current jobs */
	private JTable tblCurrentJobs;

	/** Table showing the loaded jobs */
	private JTable tblLoadedJobs;
	private JScrollPane tblLoadedJobsScrollPane;

	/** Table showing the completed jobs */
	private JTable tblCompletedJobs;

	/** Label showing the total score */
	private JLabel lblTotalScore;

	/** The path to the input directory in the repo */
	private File baseDirectory = new File("./input");

	/** The total score accumulated from completed jobs */
	private float totalScore = 0;

	/**
	 * Create the application.
	 */
	public ManagementInterface(Controller controller) {
		this.controller = controller;
		initialize();
		this.frame.setVisible(true);

		tblOnlineRobots.setModel(new RobotTableModel());
		tblCompletedJobs.setModel(new JobTableModel());
		tblCurrentJobs.setModel(new JobTableModel());
		tblLoadedJobs.setModel(new JobTableModel());

		// Table Updater
		(new Thread(() -> {
			while (true) {
				this.tblLoadedJobs.repaint();
				this.tblCurrentJobs.repaint();
				this.tblCompletedJobs.repaint();
				this.tblOnlineRobots.repaint();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ignored) {
				}
			}
		})).start();
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

		JLabel lblNewLabel_1 = new JLabel("Job ID");
		lblNewLabel_1.setBounds(425, 372, 56, 16);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Pick Count");
		lblNewLabel_2.setBounds(547, 372, 72, 16);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Reward");
		lblNewLabel_3.setBounds(673, 372, 56, 16);
		frame.getContentPane().add(lblNewLabel_3);

		JLabel label = new JLabel("Job ID");
		label.setBounds(425, 207, 56, 16);
		frame.getContentPane().add(label);

		JLabel label_1 = new JLabel("Pick Count");
		label_1.setBounds(547, 207, 72, 16);
		frame.getContentPane().add(label_1);

		JLabel label_2 = new JLabel("Reward");
		label_2.setBounds(673, 207, 56, 16);
		frame.getContentPane().add(label_2);

		JSeparator separator = new JSeparator();
		separator.setBounds(422, 27, 88, 1);
		frame.getContentPane().add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(425, 192, 78, 2);
		frame.getContentPane().add(separator_1);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(422, 358, 101, 2);
		frame.getContentPane().add(separator_2);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(126, 431, 140, 2);
		frame.getContentPane().add(separator_3);

		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(204, 81, 107, 2);
		frame.getContentPane().add(separator_4);

		JLabel lblNewLabel_4 = new JLabel("Robot Name");
		lblNewLabel_4.setBounds(425, 48, 75, 16);
		frame.getContentPane().add(lblNewLabel_4);

		JLabel lblCurrentLocation = new JLabel("Current Location");
		lblCurrentLocation.setBounds(539, 48, 100, 16);
		frame.getContentPane().add(lblCurrentLocation);

		JLabel lblCurrentFacing = new JLabel("Current Facing");
		lblCurrentFacing.setBounds(673, 44, 85, 25);
        frame.getContentPane().add(lblCurrentFacing);

        JButton btnImportFiles = new JButton("Import Files");
		JButton btnRunClassification = new JButton("Run Classification");

		JButton btnUploadTrainingFile = new JButton("Upload Training");
		btnUploadTrainingFile.addActionListener(event -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(this.baseDirectory);
			int returnVal = fileChooser.showOpenDialog(this.frame);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				if (!controller.setTrainingFile(fileChooser.getSelectedFile())) {
					JOptionPane.showMessageDialog(this.frame, "That file does not exist!");
				} else {
                    btnImportFiles.setEnabled(controller.readyForImport());
                }
			}
		});
		btnUploadTrainingFile.setBounds(12, 303, BUTTON_WIDTH_WIDE, BUTTON_HEIGHT);
		this.frame.getContentPane().add(btnUploadTrainingFile);

		JButton btnUploadDropsFile = new JButton("Upload Drops");
		btnUploadDropsFile.addActionListener(event -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(this.baseDirectory);
			int returnVal = fileChooser.showOpenDialog(this.frame);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				if (!controller.setDropsFile(fileChooser.getSelectedFile())) {
					JOptionPane.showMessageDialog(this.frame, "That file does not exist!");
				} else {
                    btnImportFiles.setEnabled(controller.readyForImport());
                }
			}
		});

		// region LeftPane
		JButton btnUploadCancellationsFile = new JButton("Upload Cancellations");
		btnUploadCancellationsFile.addActionListener(event -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(this.baseDirectory);
			int returnVal = fileChooser.showOpenDialog(this.frame);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				if (!controller.setCancellationsFile(fileChooser.getSelectedFile())) {
					JOptionPane.showMessageDialog(this.frame, "That file does not exist!");
				} else {
                    btnImportFiles.setEnabled(controller.readyForImport());
                }
			}
		});
		btnUploadCancellationsFile.setBounds(12, 264, BUTTON_WIDTH_WIDE, BUTTON_HEIGHT);
		this.frame.getContentPane().add(btnUploadCancellationsFile);
		btnUploadDropsFile.setBounds(12, 226, BUTTON_WIDTH_WIDE, BUTTON_HEIGHT);
		this.frame.getContentPane().add(btnUploadDropsFile);

		JButton btnUploadItemsFile = new JButton("Upload Items");
		btnUploadItemsFile.addActionListener(event -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(this.baseDirectory);
			int returnVal = fileChooser.showOpenDialog(this.frame);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				if (!controller.setItemsFile(fileChooser.getSelectedFile())) {
					JOptionPane.showMessageDialog(this.frame, "That file does not exist!");
				} else {
                    btnImportFiles.setEnabled(controller.readyForImport());
                }
			}
		});
		btnUploadItemsFile.setBounds(12, 188, BUTTON_WIDTH_WIDE, BUTTON_HEIGHT);
		this.frame.getContentPane().add(btnUploadItemsFile);

		JButton btnUploadJobsFile = new JButton("Upload Jobs");
		btnUploadJobsFile.addActionListener(event -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(this.baseDirectory);
			int returnVal = fileChooser.showOpenDialog(this.frame);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				if (!this.controller.setJobsFile(fileChooser.getSelectedFile())) {
					JOptionPane.showMessageDialog(this.frame, "That file does not exist!");
				} else {
                    btnImportFiles.setEnabled(controller.readyForImport());
                }
			}
		});
		btnUploadJobsFile.setBounds(12, 150, BUTTON_WIDTH_WIDE, BUTTON_HEIGHT);
		this.frame.getContentPane().add(btnUploadJobsFile);

		JButton btnUploadLocationsFile = new JButton("Upload Locations");
		btnUploadLocationsFile.addActionListener(event -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(this.baseDirectory);
			int returnVal = fileChooser.showOpenDialog(this.frame);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				if (!this.controller.setLocationsFile(fileChooser.getSelectedFile())) {
					JOptionPane.showMessageDialog(this.frame, "That file does not exist!");
				} else {
                    btnImportFiles.setEnabled(controller.readyForImport());
                }
			}
		});
		btnUploadLocationsFile.setBounds(12, 114, BUTTON_WIDTH_WIDE, BUTTON_HEIGHT);
		this.frame.getContentPane().add(btnUploadLocationsFile);

		btnImportFiles.setEnabled(controller.readyForImport());
		btnImportFiles.addActionListener(event -> {
            this.controller.importFiles();
            btnRunClassification.setEnabled(true);
            btnImportFiles.setEnabled(false);
            btnUploadCancellationsFile.setEnabled(false);
            btnUploadDropsFile.setEnabled(false);
            btnUploadItemsFile.setEnabled(false);
            btnUploadJobsFile.setEnabled(false);
            btnUploadLocationsFile.setEnabled(false);
            btnUploadTrainingFile.setEnabled(false);
        });
		btnImportFiles.setBounds(12, 341, BUTTON_WIDTH_WIDE, BUTTON_HEIGHT);
        this.frame.getContentPane().add(btnImportFiles);

        btnRunClassification.setEnabled(false);
		btnRunClassification.addActionListener(event -> this.controller.runClassification());
		btnRunClassification.setBounds(12, 379, BUTTON_WIDTH_WIDE, BUTTON_HEIGHT);
		this.frame.getContentPane().add(btnRunClassification);
		// endregion

		// region MiddlePane
		JLabel lblLoadedJobs = new JLabel("Loaded Jobs");
		lblLoadedJobs.setBounds(217, 65, 101, 18);
		this.frame.getContentPane().add(lblLoadedJobs);

		this.tblLoadedJobs = new JTable(new JobTableModel());
		this.tblLoadedJobs.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.tblLoadedJobsScrollPane = new JScrollPane(this.tblLoadedJobs);
		this.tblLoadedJobsScrollPane.setBounds(173, 90, 245, 240);
		this.frame.getContentPane().add(tblLoadedJobsScrollPane);
		// endregion

		// region RightPane
		JButton btnAddRobot = new JButton("Add Robot");
		btnAddRobot.addActionListener(event -> {
			RobotInfo[] offlineRobots = this.controller.getOfflineRobots().toArray(new RobotInfo[0]);
			if (offlineRobots.length == 0) {
				JOptionPane.showMessageDialog(this.frame, "We've run out of robots to add!");
			} else {
				RobotInfo robotInfo = (RobotInfo) JOptionPane.showInputDialog(this.frame, "Select a robot to add",
						"Add Robot", JOptionPane.PLAIN_MESSAGE, new ImageIcon(), offlineRobots, offlineRobots[0]);

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
								if (!this.controller.addRobot(robotInfo, new Location(x, y), facingDirection)) {
									JOptionPane.showMessageDialog(this.frame, "Failed to add robot.");
								}
							}
						}
					}
				}
			}
		});
		btnAddRobot.setBounds(458, 135, 132, 25);
		this.frame.getContentPane().add(btnAddRobot);

		JButton btnRemoveRobot = new JButton("Remove Robot");
		btnRemoveRobot.addActionListener(event -> {
			if (this.tblOnlineRobots.getSelectionModel().isSelectionEmpty()) {
				JOptionPane.showMessageDialog(this.frame, "Please select a robot to remove");
			} else {
				this.controller.removeRobot(((RobotTableModel) this.tblOnlineRobots.getModel())
						.getRow(this.tblOnlineRobots.getSelectedRow()));
			}
		});
		btnRemoveRobot.setBounds(602, 135, 140, 25);
		this.frame.getContentPane().add(btnRemoveRobot);

		JLabel lblOnlineRobots = new JLabel("Online Robots");
		lblOnlineRobots.setBounds(425, 0, LABEL_WIDTH, LABEL_HEIGHT);
		this.frame.getContentPane().add(lblOnlineRobots);

		this.tblOnlineRobots = new JTable();
		this.tblOnlineRobots.setBounds(425, 67, 345, 60);
		this.frame.getContentPane().add(this.tblOnlineRobots);

		JLabel lblCurrentJobs = new JLabel("Completed Jobs");
		lblCurrentJobs.setBounds(425, 327, LABEL_WIDTH, LABEL_HEIGHT);
		this.frame.getContentPane().add(lblCurrentJobs);

		this.tblCurrentJobs = new JTable(new JobTableModel());
		this.tblCurrentJobs.setBounds(425, 226, 345, 77);
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
		btnCancelJob.setBounds(527, 315, BUTTON_WIDTH_WIDE, BUTTON_HEIGHT);
		this.frame.getContentPane().add(btnCancelJob);
		// endregion

		// region bottomPanel
		JButton btnStartWarehouse = new JButton("Start Warehouse");
		btnStartWarehouse.addActionListener(event -> this.controller.startApplication());
		btnStartWarehouse.setBounds(193, 338, BUTTON_WIDTH_WIDE, BUTTON_HEIGHT);
		this.frame.getContentPane().add(btnStartWarehouse);

		JButton btnShutdown = new JButton("Shutdown");
		btnShutdown.addActionListener(event -> {
			if (JOptionPane.showConfirmDialog(this.frame, "Are you sure you want to shutdown and exit the program?",
					"Confirm Shutdown", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
				this.controller.shutdown();
			}
		});
		btnShutdown.setBounds(217, 368, 113, 25);
		this.frame.getContentPane().add(btnShutdown);

		JSeparator jobSeparator = new JSeparator();
		jobSeparator.setBounds(12, 48, 400, 2);
		frame.getContentPane().add(jobSeparator);

		tblCompletedJobs = new JTable(null);
		tblCompletedJobs.setBounds(425, 390, 345, 72);
		frame.getContentPane().add(tblCompletedJobs);

		JLabel lblNewLabel = new JLabel("Current Jobs");
		lblNewLabel.setBounds(425, 162, 155, 40);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblTotalScore = new JLabel("Grand Total Score");
		lblTotalScore.setBounds(142, 408, 113, 25);
		frame.getContentPane().add(lblTotalScore);

		this.lblTotalScore = new JLabel(String.valueOf(this.totalScore));
		this.lblTotalScore.setHorizontalAlignment(JLabel.CENTER);
		this.lblTotalScore.setBounds(136, 415, 122, 60);
		frame.getContentPane().add(this.lblTotalScore);
		// endregion
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
	public void addJobsToLoadedJobsTable(List<Job> jobs) {
		JobTableModel model = (JobTableModel) this.tblLoadedJobs.getModel();
		jobs.forEach(job -> {
			if (job != null) {
				model.addRow(job);
			}
		});
		this.tblLoadedJobs.revalidate();
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
	 * Add a job to the current jobs table in the GUI. Also removes it from the
	 * current jobs table and updates the score with the score of the completed
	 * job added to the total score
	 *
	 * @param job
	 *            The job to add to the table.
	 */
	public void addJobToCompletedJobsTable(Job job) {
		JobTableModel model = (JobTableModel) this.tblCompletedJobs.getModel();
		model.addRow(job);

		removeJobFromCurrentJobsTable(job);

		this.totalScore += job.getReward();
		updateTotalScore();
	}

	/**
	 * Updates the total score display on the GUI
	 */
	public void updateTotalScore() {
		this.lblTotalScore.setText(String.valueOf(this.totalScore));
	}

	/**
	 * Announces to the user that all jobs have been completed
	 */
	public void assignedAllJobs() {
		JOptionPane.showMessageDialog(this.frame, "All jobs have been assigned");
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
