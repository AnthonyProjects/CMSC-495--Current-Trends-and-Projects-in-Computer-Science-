import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class HomeScreen extends JFrame implements ActionListener, KeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel logoLBL, loginLBL, newUserLBL;
	JButton loginBTN;
	JButton createUserBTN;
	JPanel parentPNL, logoPNL, loginScreenPNL;
	Dimension d = new Dimension(150, 25);
	ButtonGroup bg = new ButtonGroup();
	ImageIcon fitnessTrackerLogo;

	GridBagConstraints gbc = new GridBagConstraints();
	
	Database db = null;

	public HomeScreen(Database db) {

		
		// Title Bar
		super("Fitness Tracker - Home Screen");
		
		this.db = db;

		// Spacing of Components
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.LINE_START;

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

		// Sign-in/Login Label/Button

		// Label
		loginLBL = new JLabel("If You Are A Returning User, Please Sign In: ");
		gbc.gridx = 0;
		gbc.gridy = 0;
		loginScreenPNL.add(loginLBL, gbc);

		// Button
		loginBTN = new JButton("Sign In");
		loginBTN.setPreferredSize(d);
		loginBTN.addActionListener(this);
		loginBTN.addKeyListener(this);
		gbc.gridx = 1;
		gbc.gridy = 0;
		loginScreenPNL.add(loginBTN, gbc);

		// Create User Label/Button

		// Label
		newUserLBL = new JLabel("If You Are A New User: ");
		gbc.gridx = 0;
		gbc.gridy = 1;
		loginScreenPNL.add(newUserLBL, gbc);

		// Button
		createUserBTN = new JButton("Create New User");
		createUserBTN.setPreferredSize(d);
		createUserBTN.addActionListener(this);
		createUserBTN.addKeyListener(this);
		gbc.gridx = 1;
		gbc.gridy = 1;
		loginScreenPNL.add(createUserBTN, gbc);

		parentPNL.add(logoPNL, BorderLayout.NORTH);
		parentPNL.add(loginScreenPNL, BorderLayout.CENTER);
		add(parentPNL);

		setSize(450, 250);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
	
	@SuppressWarnings("unused")
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginBTN) {
			this.dispose();
			LoginScreen lS = new LoginScreen(db);
			// Set HomeScreen GUI not visible
			// Set LoginScreen GUI visible
		}
		if (e.getSource() == createUserBTN) {
			this.dispose();
			AccountCreation newAC = new AccountCreation(db);
			// Set HomeScreen GUI not visible
			// Set AccountCreation GUI visible
		}
	}


	// allows user to press enter on the loginBTN button
	// hit the tab key on the keyboard to move to the createUserBTN
	// and then press enter
	
	@SuppressWarnings("unused")
	@Override
	public void keyPressed(KeyEvent e) 
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_ENTER:
				
				if(e.getSource() == loginBTN)
				{
					this.dispose();
					LoginScreen lS = new LoginScreen(db);
				}
				if(e.getSource() == createUserBTN)
				{
					this.dispose();
					AccountCreation newAC = new AccountCreation(db);
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