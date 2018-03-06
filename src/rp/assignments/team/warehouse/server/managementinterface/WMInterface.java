package rp.assignments.team.warehouse.server.managementinterface;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.*;

import rp.assignments.team.warehouse.server.controller.Controller;

public class WMInterface {

    private Controller controller;
    private JFrame frame;

    private final int WINDOW_HEIGHT = 550;
    private final int WINDOW_WIDTH = 600;
    private final int WINDOW_START_POS = 100;
    private final int BUTTON_HEIGHT = 25;
    private final int BUTTON_WIDE_WIDTH = 155;
    private final int BUTTON_NARROW_WIDTH = 100;
    private final int LABEL_HEIGHT = 40;
    private final int LABEL_WIDTH = 155;
    private final int SEPARATOR_HEIGHT = 2;
    private final int SEPARATOR_WIDTH = 130;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Controller controller = new Controller(); // we might not want to be testing from here anymore check
                                                           // Server class
                WMInterface window = new WMInterface(controller);
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public WMInterface(Controller controller) {
        this.controller = controller;
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(WINDOW_START_POS, WINDOW_START_POS, WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel title = new JLabel("Warehouse Management Interface");
        title.setBackground(SystemColor.desktop);
        title.setFont(new Font("Papyrus", Font.PLAIN, 29));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(81, 13, 426, 75);
        frame.getContentPane().add(title);

        JTextArea simulation = new JTextArea();
        simulation.setText("Connecting...");
        simulation.setBounds(41, 106, 494, 180);
        frame.getContentPane().add(simulation);

        // Communications label, separator underneath, and connect + disconnect buttons
        JLabel lblCommunications = new JLabel("Communications");
        lblCommunications.setFont(new Font("Papyrus", Font.PLAIN, 16));
        lblCommunications.setHorizontalAlignment(SwingConstants.CENTER);
        lblCommunications.setBounds(69, 304, LABEL_WIDTH, LABEL_HEIGHT);
        frame.getContentPane().add(lblCommunications);

        JSeparator sepCommunications = new JSeparator();
        sepCommunications.setBounds(360, 335, SEPARATOR_WIDTH, SEPARATOR_HEIGHT);
        frame.getContentPane().add(sepCommunications);

        JButton btnSetupConnections = new JButton("Set up Connections");
        btnSetupConnections.addActionListener(e -> controller.setupConnections());
        btnSetupConnections.setBounds(60, 350, BUTTON_WIDE_WIDTH, BUTTON_HEIGHT);
        frame.getContentPane().add(btnSetupConnections);

        JButton btnDisconnect = new JButton("Disconnect a Robot");
        btnDisconnect.addActionListener(e -> controller.disconnectRobot());
        btnDisconnect.setBounds(69, 401, BUTTON_WIDE_WIDTH, BUTTON_HEIGHT);
        frame.getContentPane().add(btnDisconnect);

        // Search label, separator underneath, and run search and cancel job buttons
        JLabel lblSearch = new JLabel("Search");
        lblSearch.setHorizontalAlignment(SwingConstants.CENTER);
        lblSearch.setFont(new Font("Papyrus", Font.PLAIN, 16));
        lblSearch.setBounds(371, 309, LABEL_WIDTH, LABEL_HEIGHT);
        frame.getContentPane().add(lblSearch);

        JSeparator sepSearch = new JSeparator();
        sepSearch.setBounds(81, 335, SEPARATOR_WIDTH, SEPARATOR_HEIGHT);
        frame.getContentPane().add(sepSearch);

        JButton btnRunSearch = new JButton("Run Search");
        btnRunSearch.setBounds(360, 350, BUTTON_WIDE_WIDTH, BUTTON_HEIGHT);
        frame.getContentPane().add(btnRunSearch);

        JButton btnCancelJob = new JButton("Cancel Current Job");
        btnCancelJob.addActionListener(e -> controller.cancelCurrentJob());
        btnCancelJob.setBounds(346, 401, BUTTON_WIDE_WIDTH, BUTTON_HEIGHT);
        frame.getContentPane().add(btnCancelJob);

        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(e -> {
            JFrame fromInterface = new JFrame("Exit");
            if (JOptionPane.showConfirmDialog(fromInterface, "Are you sure you want to exit?", "Confirm Exit",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
                System.exit(0);
            }
        });
        btnExit.setBounds(244, 451, BUTTON_NARROW_WIDTH, BUTTON_HEIGHT);
        frame.getContentPane().add(btnExit);
    }
}
