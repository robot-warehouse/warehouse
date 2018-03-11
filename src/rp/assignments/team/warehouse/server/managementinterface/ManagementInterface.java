package rp.assignments.team.warehouse.server.managementinterface;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class ManagementInterface {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagementInterface window = new ManagementInterface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ManagementInterface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 758, 497);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("Upload Canellations");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(12, 106, 154, 25);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Upload Drops");
		btnNewButton_1.setBounds(34, 144, 118, 25);
		frame.getContentPane().add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Upload Items");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_2.setBounds(12, 182, 154, 25);
		frame.getContentPane().add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("Upload Jobs");
		btnNewButton_3.setBounds(34, 220, 128, 25);
		frame.getContentPane().add(btnNewButton_3);

		JButton btnNewButton_4 = new JButton("Upload Locations");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_4.setBounds(34, 266, 129, 25);
		frame.getContentPane().add(btnNewButton_4);

		JButton btnNewButton_5 = new JButton("Disconnect Robot");
		btnNewButton_5.setBounds(585, 71, 131, 25);
		frame.getContentPane().add(btnNewButton_5);

		JButton btnNewButton_6 = new JButton("Connect Robot");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_6.setBounds(445, 71, 128, 25);
		frame.getContentPane().add(btnNewButton_6);

		JList list = new JList();
		list.setBounds(483, 109, 203, 86);
		frame.getContentPane().add(list);

		JButton btnNewButton_7 = new JButton("Cancel Job");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_7.setBounds(536, 204, 97, 25);
		frame.getContentPane().add(btnNewButton_7);

		JButton btnNewButton_8 = new JButton("Start Warehouse");
		btnNewButton_8.setBounds(264, 332, 147, 25);
		frame.getContentPane().add(btnNewButton_8);

		JButton btnNewButton_9 = new JButton("Exit");
		btnNewButton_9.addActionListener(e -> {
			JFrame fromInterface = new JFrame("Exit");
			if (JOptionPane.showConfirmDialog(fromInterface, "Are you sure you want to exit?", "Confirm Exit",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
				controller.shutdown();
			}
		});
		btnNewButton_9.setBounds(293, 370, 97, 25);
		frame.getContentPane().add(btnNewButton_9);

		JList list_1 = new JList();
		list_1.setBounds(186, 87, 175, 220);
		frame.getContentPane().add(list_1);

		JLabel lblNewLabel = new JLabel("Warehouse Management Interface");
		lblNewLabel.setBounds(264, 13, 210, 30);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblCurrentJobs = new JLabel("Loaded Jobs");
		lblCurrentJobs.setBounds(234, 56, 80, 18);
		frame.getContentPane().add(lblCurrentJobs);

		JLabel label = new JLabel("New label");
		label.setBounds(483, 291, 56, 16);
		frame.getContentPane().add(label);
	}
}
