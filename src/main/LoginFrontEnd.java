package main;

import javax.swing.*;
import jBCrypt.BCrypt;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Arrays;

public class LoginFrontEnd extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static String LOGIN = "Login";
	private static String INFO = "Information";

	private JFrame mainFrame;
	private JPasswordField passwordField;
	private JTextField usernameField;
	private static JFrame frame;
	
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

		JPanel textPanel = new JPanel(new GridLayout(0,2));
		
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
			String password = "";
			
			for (int i = 0; i < input.length; i++) {
				password = password + input[i];
			}
			String username = usernameField.getText();
			try {
				if (userValidate(username, password)) { // everything is ok then we can render things
					JOptionPane.showMessageDialog(mainFrame, "Welcome to Game!");
					frame.dispose();
					GameDisplay.run();
				} 
				else {
					JOptionPane.showMessageDialog(mainFrame, 
							"Invalid username or password. ", "Error Message", JOptionPane.ERROR_MESSAGE);
				}
			} 
			catch (Exception e1) {
				e1.printStackTrace();
			}

			Arrays.fill(input, '0');

			passwordField.selectAll();
			resetFocus();
		}
		else {
			JOptionPane.showMessageDialog(mainFrame, "Make sure server is started");
		}
	}

	private static boolean userValidate(String username, String password) throws SQLException {
		if (password.isEmpty() || username.isEmpty()) {
			return false;
		}
		// Hash a password for the first time
		//String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
		
		// this hash == "pass"
		//String hashed = "$2a$10$8iH4Y/JPBgcmZ0hbFIJMz.YpuEhVqhCZRyCvtaEDFFdx4eNCW/EEu";

		String hashedPassword = LoginHandler.authenticate(username);

		if (hashedPassword == null) {
			return false;
		}
		else if (BCrypt.checkpw(password, hashedPassword)) {
			return true;
		}
		else {
			return false;
		}
	}

	protected void resetFocus() {
		usernameField.requestFocusInWindow();
	}

	private static void GUI() {
		frame = new JFrame("Game");
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

	public static void init() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI();
			}
		});
	}

}
