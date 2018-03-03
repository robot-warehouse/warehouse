import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;

public class WMInterface {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WMInterface window = new WMInterface();
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
	public WMInterface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel Application = new JLabel("Warehouse Management Interface");
		Application.setBackground(SystemColor.desktop);
		Application.setFont(new Font("Papyrus", Font.PLAIN, 29));
		Application.setHorizontalAlignment(SwingConstants.CENTER);
		Application.setBounds(81, 13, 426, 75);
		frame.getContentPane().add(Application);
		
		JTextArea CurrentActivityField = new JTextArea();
		CurrentActivityField.setText("Connecting...");
		CurrentActivityField.setBounds(41, 106, 494, 180);
		frame.getContentPane().add(CurrentActivityField);
		
		JButton btnNewButton = new JButton("Run Search");
		btnNewButton.setBounds(360, 350, 105, 25);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Cancel Current Job");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton_1.setBounds(346, 401, 141, 25);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton exitButton = new JButton("Exit");
		exitButton.setBounds(244, 451, 97, 25);
		frame.getContentPane().add(exitButton);
		
		JLabel lblSearch = new JLabel("Search");
		lblSearch.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearch.setFont(new Font("Papyrus", Font.PLAIN, 16));
		lblSearch.setBounds(371, 309, 82, 28);
		frame.getContentPane().add(lblSearch);
		
		JLabel lblNewLabel = new JLabel("Communications");
		lblNewLabel.setFont(new Font("Papyrus", Font.PLAIN, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(69, 304, 157, 38);
		frame.getContentPane().add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(81, 335, 128, 2);
		frame.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(360, 335, 106, 2);
		frame.getContentPane().add(separator_1);
		
		JButton btnNewButton_2 = new JButton("Set up Connections");
		btnNewButton_2.setBounds(60, 350, 179, 25);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Disconnect a Robot");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_3.setBounds(69, 401, 157, 25);
		frame.getContentPane().add(btnNewButton_3);
		
		exitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame fromInterface = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(fromInterface, "Are you sure you want to exit?", "Confirm Exit",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
				
			}
		});
		
		
	}
}
