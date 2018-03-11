package rp.assignments.team.warehouse.server;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Set;

public class ManagementInterface {

	private Controller controller;
	private JFrame frame;
	private JTable tblOnlineRobots;
	private JTable tblCurrentJobs;

	private final int WINDOW_HEIGHT = 500;
	private final int WINDOW_WIDTH = 750;
	private final int WINDOW_START_POS = 100;
	private final int BUTTON_HEIGHT = 25;
	private final int BUTTON_WIDE_WIDTH = 155;
	private final int BUTTON_NARROW_WIDTH = 100;
	private final int LABEL_HEIGHT = 40;
	private final int LABEL_WIDTH = 155;
	private final int SEPARATOR_HEIGHT = 2;
	private final int SEPARATOR_WIDTH = 130;

	/**
	 * Create the application.
	 */
	public ManagementInterface(Controller controller) {
		this.controller = controller;
		initialize();
		this.frame.setVisible(true);
	}

	public static void main(String[] args) {
        ManagementInterface managementInterface = new ManagementInterface(new Controller(new Warehouse()));
    }

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.frame = new JFrame();
		this.frame.setBounds(100, 100, 758, 497);
		this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.frame.getContentPane().setLayout(null);

		JButton btnUploadCancellationsFile = new JButton("Upload Cancellations");
		btnUploadCancellationsFile.addActionListener(event -> {
		    JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showOpenDialog(this.frame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                controller.setCancellationsFile(fileChooser.getSelectedFile());
            }
        });
		btnUploadCancellationsFile.setBounds(12, 106, 154, 25);
		this.frame.getContentPane().add(btnUploadCancellationsFile);

		JButton btnUploadDropsFile = new JButton("Upload Drops");
		btnUploadDropsFile.addActionListener(event -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showOpenDialog(this.frame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                controller.setDropsFile(fileChooser.getSelectedFile());
            }
        });
		btnUploadDropsFile.setBounds(34, 144, 118, 25);
		this.frame.getContentPane().add(btnUploadDropsFile);

		JButton btnUploadItemsFile = new JButton("Upload Items");
		btnUploadItemsFile.addActionListener(event -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showOpenDialog(this.frame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                controller.setItemsFile(fileChooser.getSelectedFile());
            }
        });
		btnUploadItemsFile.setBounds(12, 182, 154, 25);
		this.frame.getContentPane().add(btnUploadItemsFile);

		JButton btnUploadJobsFile = new JButton("Upload Jobs");
		btnUploadJobsFile.addActionListener(event -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showOpenDialog(this.frame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                controller.setJobsFile(fileChooser.getSelectedFile());
            }
        });
		btnUploadJobsFile.setBounds(34, 220, 128, 25);
		this.frame.getContentPane().add(btnUploadJobsFile);

		JButton btnUploadLocationsFile = new JButton("Upload Locations");
		btnUploadLocationsFile.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showOpenDialog(this.frame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                controller.setLocationsFile(fileChooser.getSelectedFile());
            }
        });
		btnUploadLocationsFile.setBounds(34, 266, 129, 25);
		this.frame.getContentPane().add(btnUploadLocationsFile);

		JButton btnDisconnectRobot = new JButton("Disconnect Robot");
		btnDisconnectRobot.addActionListener(event -> {
		    if (!this.tblOnlineRobots.getSelectionModel().isSelectionEmpty()) {
                this.controller.disconnectRobot(this.tblOnlineRobots.getSelectedRow());
            }
        });
		btnDisconnectRobot.setBounds(585, 71, 131, 25);
		this.frame.getContentPane().add(btnDisconnectRobot);

		JButton btnConnectRobot = new JButton("Connect Robot");
		btnConnectRobot.addActionListener(event -> {
            Set offlineRobots = this.controller.getOfflineRobots();
            if (offlineRobots.isEmpty()) {
                JOptionPane.showMessageDialog(this.frame, "We've run out of robots to connect to!");
            } else {
                RobotInfo[] offlineRobotArray = (RobotInfo[]) offlineRobots.toArray();
                RobotInfo robotInfo = (RobotInfo) JOptionPane.showInputDialog(this.frame, "Select a robot to connect to", "Connect Robot", JOptionPane.PLAIN_MESSAGE, new ImageIcon(), offlineRobotArray, offlineRobotArray[0]);

                if (robotInfo != null) {
                    Integer x = (Integer) JOptionPane.showInputDialog(this.frame, "Select x start position", "Select x", JOptionPane.PLAIN_MESSAGE, new ImageIcon(), getOrderedIntArray(Location.MIN_X, Location.MAX_X), Location.MIN_X);

                    if (x != null) {
                        Integer y = (Integer) JOptionPane.showInputDialog(this.frame, "Select y start position", "Select y", JOptionPane.PLAIN_MESSAGE, new ImageIcon(), getOrderedIntArray(Location.MIN_Y, Location.MAX_Y), Location.MIN_Y);

                        if (y != null) {
                            Facing facingDirection = (Facing) JOptionPane.showInputDialog(this.frame, "Select facing direction", "Select facing", JOptionPane.PLAIN_MESSAGE, new ImageIcon(), Facing.values(), Facing.North);

                            if (facingDirection != null) {
                                this.controller.connectRobot(robotInfo, new Location(x, y), facingDirection);
                            }
                        }
                    }
                }
            }
        });
		btnConnectRobot.setBounds(445, 71, 128, 25);
		this.frame.getContentPane().add(btnConnectRobot);

		JList lstCurrentJobs = new JList();
		lstCurrentJobs.setBounds(483, 109, 203, 86);
		this.frame.getContentPane().add(lstCurrentJobs);

		JButton btnCancelJob = new JButton("Cancel Job");
		btnCancelJob.addActionListener(event -> {
		    if (!tblCurrentJobs.getSelectionModel().isSelectionEmpty()) {
		        this.controller.cancelCurrentJob(tblCurrentJobs.getSelectedRow());
            }
        });
		btnCancelJob.setBounds(536, 204, 97, 25);
		this.frame.getContentPane().add(btnCancelJob);

		JButton btnStartWarehouse = new JButton("Start Warehouse");
		btnStartWarehouse.addActionListener(event -> {
		    this.controller.startApplication();
        });
		btnStartWarehouse.setBounds(264, 332, 147, 25);
		this.frame.getContentPane().add(btnStartWarehouse);

		JButton btnShutdown = new JButton("Exit");
		btnShutdown.addActionListener(e -> {
			JFrame fromInterface = new JFrame("Exit");
			if (JOptionPane.showConfirmDialog(fromInterface, "Are you sure you want to exit?", "Confirm Exit",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
				this.controller.shutdown();
			}
		});
		btnShutdown.setBounds(293, 370, 97, 25);
		this.frame.getContentPane().add(btnShutdown);

		JList lstLoadedJobs = new JList();
		lstLoadedJobs.setBounds(186, 87, 175, 220);
		this.frame.getContentPane().add(lstLoadedJobs);

		JLabel lblTitle = new JLabel("Warehouse Management Interface");
		lblTitle.setBounds(264, 13, 210, 30);
		this.frame.getContentPane().add(lblTitle);

		JLabel lblLoadedJobs = new JLabel("Loaded Jobs");
		lblLoadedJobs.setBounds(234, 56, 80, 18);
		this.frame.getContentPane().add(lblLoadedJobs);

		JLabel lblCurrentJobs = new JLabel("Online Robots");
		lblCurrentJobs.setBounds(483, 291, 56, 16);
		this.frame.getContentPane().add(lblCurrentJobs);

		// TODO add import button
        // TODO generalise button size
        // TODO add more update methods
        // TODO some more stuff
	}

	//region PublicMethodsForController

    /**
     * Adds
     * @param robot
     */
	public void addRobotToTable(Robot robot) {
        ((DefaultTableModel) this.tblOnlineRobots.getModel()).addRow(new Object[]{robot.getName(), robot.getCurrentLocation(), robot.getCurrentPicks(), robot.getCurrentFacingDirection(), robot.getCurrentWeight()});
    }

    /**
     *
     * @param index
     */
	public void removeRobotFromTable(int index) {
        this.tblOnlineRobots.remove(this.tblOnlineRobots.getSelectedRow());
    }

    //endregion

    /**
     * Makes array consisting of numbers ascending from minValue to maxValue (e.g. [0, 1, 2, 3, 4])
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
