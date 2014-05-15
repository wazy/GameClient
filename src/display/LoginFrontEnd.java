package display;

import handlers.LoginHandler;

import javax.swing.*;

import utils.BCrypt;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Arrays;

public class LoginFrontEnd extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static String LOGIN = "Login";
	private static String REGISTER = "Register";

	private JFrame mainFrame;
	private JPasswordField passwordField;
	private JTextField usernameField;
	private static JFrame frame;

	// for registration frame
	private JFrame frame2;
	private JLabel createUsernameLabel;
	private JLabel createPasswordLabel;
	private JLabel createPasswordLabel2;
	private JTextField createUsernameField;
	private JPasswordField createPasswordField;
	private JPasswordField createPasswordField2;

	public LoginFrontEnd(JFrame frame) {
		mainFrame = frame;
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setSize(100, 200);

		usernameField = new JTextField(10);
		usernameField.setActionCommand(LOGIN);
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
		JButton registerButton = new JButton("Register");

		loginButton.setActionCommand(LOGIN);
		loginButton.addActionListener(this);

		registerButton.setActionCommand(REGISTER);
		registerButton.addActionListener(new Register());

		panel.add(loginButton);
		panel.add(registerButton);

		return panel;
	}

	class Register implements ActionListener {        
		public void actionPerformed (ActionEvent e) {     
			frame2 = new JFrame("Game Registration");
			frame2.setVisible(true);
			frame2.setSize(400,100);
			frame2.setLocationRelativeTo(null);

			JPanel panel = new JPanel(new GridLayout(1,1));
			JButton createButton = new JButton("Create");

			createButton.setActionCommand("Create");
			createButton.addActionListener(new Create());

			panel.add(createButton);

			createUsernameField = new JTextField(10);
			createPasswordField = new JPasswordField(10);
			createPasswordField2 = new JPasswordField(10);

			createUsernameLabel = new JLabel("Username: ");
			createPasswordLabel = new JLabel("Password: ");
			createPasswordLabel2 = new JLabel("Repeat: ");

			createUsernameLabel.setLabelFor(createUsernameField);
			createPasswordLabel.setLabelFor(createPasswordField);
			createPasswordLabel2.setLabelFor(createPasswordField2);

			JPanel textPanel = new JPanel(new GridLayout(0,2));

			textPanel.add(createUsernameLabel);
			textPanel.add(createUsernameField);
			textPanel.add(createPasswordLabel);
			textPanel.add(createPasswordField);
			textPanel.add(createPasswordLabel2);
			textPanel.add(createPasswordField2);

			frame2.add(textPanel);
			textPanel.add(panel);
		}
	}

	class Create implements ActionListener {        
		public void actionPerformed (ActionEvent e) {
			String password = "", password2 = "";
			String username = createUsernameField.getText();
			
			char[] input = createPasswordField.getPassword();
			
			for (int i = 0; i < input.length; i++) {
				password = password + input[i];
			}

			input = createPasswordField2.getPassword();
			
			for (int i = 0; i < input.length; i++) {
				password2 = password2 + input[i];
			}
			
			// user left a part empty of form
			if (password.isEmpty() || password2.isEmpty() || username.isEmpty()) {
				JOptionPane.showMessageDialog(mainFrame, 
						"You left something blank!", "Error Message", JOptionPane.ERROR_MESSAGE);
				createUsernameField.requestFocusInWindow();
			}
			
			// not matching passwords
			else if (!password.equals(password2)) {
				JOptionPane.showMessageDialog(mainFrame, 
						"Passwords do not match!", "Error Message", JOptionPane.ERROR_MESSAGE);
				createUsernameField.requestFocusInWindow();
			}
			else { // lets try to register
				String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
				// System.out.println("Username: " + username + " Hash: " + hashed);
				boolean success = LoginHandler.register(username, hashed);
				
				if (!success) {
					JOptionPane.showMessageDialog(mainFrame, 
							"An error occurred while registering.. duplicate username?", "Error Message", JOptionPane.ERROR_MESSAGE);
					createUsernameField.requestFocusInWindow();
				}
				else { // registered successfully
					JOptionPane.showMessageDialog(mainFrame, "Successful registration! You may now login.");
					frame2.dispose();
				}
				
			}
		}
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

		boolean result = LoginHandler.authenticate(username, password);
		
		return result; // true for success / false for fail
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
