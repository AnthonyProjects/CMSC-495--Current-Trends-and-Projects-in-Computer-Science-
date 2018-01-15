import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginScreen extends JFrame implements ActionListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel logoLBL, userNameLBL, passwordLBL;
	JButton signInBTN, backBTN;
	JPanel parentPNL, logoPNL, loginScreenPNL;
	JTextField userJTF, passwordJTF;
	JPasswordField passwordField = new JPasswordField(15);
	Dimension d = new Dimension(150, 25);
	ButtonGroup bg = new ButtonGroup();
	ImageIcon fitnessTrackerLogo;

	GridBagConstraints gbc = new GridBagConstraints();
	
	Database db = null;

	public LoginScreen(Database db) {

		// Title Bar
		super("Fitness Tracker - Sign In");
		
		this.db = db;

		// Spacing of Components
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;

		// Master Panel
		parentPNL = new JPanel();
		parentPNL.setLayout(new BorderLayout());

		// Logo Panel
		try{
			fitnessTrackerLogo = new ImageIcon("FitnessTrackerSmallest.png");
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Logo Failed To Load.");
		}
		logoPNL = new JPanel();
		logoPNL.setLayout(new BorderLayout());
		logoLBL = new JLabel(fitnessTrackerLogo, SwingConstants.CENTER);
		logoPNL.add(logoLBL, BorderLayout.NORTH);

		// Panel to add login/create user components
		loginScreenPNL = new JPanel();
		loginScreenPNL.setLayout(new GridBagLayout());
		loginScreenPNL.setBorder(BorderFactory.createTitledBorder(""));

		// Username/Password Label/Field
		gbc.anchor = GridBagConstraints.LINE_START;

		// Username Label
		userNameLBL = new JLabel("Please Enter Your UserName: ");
		gbc.gridx = 0;
		gbc.gridy = 0;
		loginScreenPNL.add(userNameLBL, gbc);

		// Username Field
		userJTF = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 0;
		loginScreenPNL.add(userJTF, gbc);

		// Password Label
		passwordLBL = new JLabel("Please Enter Your Password (Case-Sensitive!): ");
		gbc.gridx = 0;
		gbc.gridy = 1;
		loginScreenPNL.add(passwordLBL, gbc);

		// Password Field
		//passwordJTF = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 1;
		loginScreenPNL.add(passwordField, gbc);

		// Back Button
		backBTN = new JButton("Back");
		backBTN.setPreferredSize(d);
		backBTN.addActionListener(this);
		gbc.gridx=0;
		gbc.gridy=2;
		loginScreenPNL.add(backBTN, gbc);

		// Sign In Button
		signInBTN = new JButton("Sign In");
		signInBTN.setPreferredSize(d);
		signInBTN.addActionListener(this);
		signInBTN.addKeyListener(this);
		gbc.gridx = 1;
		gbc.gridy = 2;
		loginScreenPNL.add(signInBTN, gbc);

		parentPNL.add(logoPNL, BorderLayout.NORTH);
		parentPNL.add(loginScreenPNL, BorderLayout.CENTER);
		add(parentPNL);

		setSize(550, 250);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@SuppressWarnings({ "unused", "deprecation" })
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == signInBTN) {
			// Check Database for matching user, store user as object and store database match as boolean value
			
			String userName = userJTF.getText();
			String password = passwordField.getText();
			
			try{
				int userId  =  db.loginAttempt(userName, password);
				if(userId > 0){
				
					JOptionPane.showMessageDialog(null, "Login Successful!");
					this.dispose();
					ProfileNavigation pD = new ProfileNavigation(db, userId, userName);
					// Set Login Screen GUI not visible
					// Set Profile Display GUI Visible
				}

				else{
					JOptionPane.showMessageDialog(null, "No matching user found, Please try again");
				}
			}catch(SQLException sqe){
				System.out.println(sqe.getMessage());	
				JOptionPane.showMessageDialog(null, "Incorrect Username/Password Combo");
			}
		}
		if(e.getSource() == backBTN){
			this.dispose();
			HomeScreen hS = new HomeScreen(db);
		}
	}
	
	// allows user to press enter button to sign in
	
	@SuppressWarnings({ "deprecation", "unused" })
	public void keyPressed(KeyEvent e) 
	{
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			// Check Database for matching user, store user as object and store database match as boolean value
			
			String userName = userJTF.getText();
			String password = passwordField.getText();
			
			try{
				int userId  =  db.loginAttempt(userName, password);
				if(userId > 0){
				
					JOptionPane.showMessageDialog(null, "Login Successful!");
					this.dispose();
					ProfileNavigation pD = new ProfileNavigation(db, userId, userName);
					// Set Login Screen GUI not visible
					// Set Profile Display GUI Visible
				}

				else{
					JOptionPane.showMessageDialog(null, "No matching user found, Please try again");
				}
			}catch(SQLException sqe){
				System.out.println(sqe.getMessage());
				JOptionPane.showMessageDialog(null, "Incorrect Username/Password Combo");
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
