package main;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class LoginFrontEnd extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static String LOGIN = "Login";
	private static String INFO = "Information";

	private JFrame mainFrame;
	private JPasswordField passwordField;
	private JTextField usernameField;
	
	public LoginFrontEnd(JFrame frame) {
		mainFrame = frame;
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setSize(100, 200);
		
		usernameField = new JTextField(10);
		usernameField.addActionListener(this);
		
		passwordField = new JPasswordField(10);
		passwordField.setActionCommand(LOGIN);
		passwordField.addActionListener(this);

		JLabel usernameLabel = new JLabel("Username: ");
		JLabel passwordLabel = new JLabel("Password: ");
		
		usernameLabel.setLabelFor(usernameField);
		passwordLabel.setLabelFor(passwordField);

		JComponent buttonPanel = createButtonPanel();

		JPanel textPanel = new JPanel(new GridLayout(0,1));
		
		textPanel.add(usernameLabel);
		textPanel.add(usernameField);
		textPanel.add(passwordLabel);
		textPanel.add(passwordField);

		add(textPanel);
		add(buttonPanel);
	}

	protected JComponent createButtonPanel() {
		JPanel panel = new JPanel(new GridLayout(0,1));
		JButton loginButton = new JButton("Login");
		JButton helpButton = new JButton("Help");

		loginButton.setActionCommand(LOGIN);
		helpButton.setActionCommand(INFO);
		loginButton.addActionListener(this);
		helpButton.addActionListener(this);

		panel.add(loginButton);
		panel.add(helpButton);

		return panel;
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		if (LOGIN.equals(action)) {
			char[] input = passwordField.getPassword();
			String username = usernameField.getText();
			if (username.equals("user") && isPasswordCorrect(input)) {
				JOptionPane.showMessageDialog(mainFrame, "Correct!");
			} 
			else {
				JOptionPane.showMessageDialog(mainFrame, 
						"Invalid username or password. ", "Error Message", JOptionPane.ERROR_MESSAGE);
			}

			Arrays.fill(input, '0');

			passwordField.selectAll();
			resetFocus();
		}
		else {
			JOptionPane.showMessageDialog(mainFrame, "Make sure server is started");
		}
	}

	private static boolean isPasswordCorrect(char[] input) {
		return true;
	}

	protected void resetFocus() {
		usernameField.requestFocusInWindow();
	}

	private static void GUI() {
		JFrame frame = new JFrame("Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// create and set up the content panel
		final LoginFrontEnd newContentPane = new LoginFrontEnd(frame);
		newContentPane.setOpaque(true);
		frame.setContentPane(newContentPane);

		//Make sure the focus goes to the right component
		//whenever the frame is initially given the focus.
		frame.addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				newContentPane.resetFocus();
			}
		});

		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI();
			}
		});
	}

}
