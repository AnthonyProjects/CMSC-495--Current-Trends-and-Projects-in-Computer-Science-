import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ProfileNavigation extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel parentPNL, logoPNL, interactionPNL, southPNL;
	JLabel logoLBL, caloricIntakeLBL;
	JTextField caloriesPerDayJTF;
	JButton viewProfileBTN, editProfileBTN, trackFoodBTN, trackWorkoutBTN, dailyLogBTN, userHistoryBTN, logoutBTN,
			exitBTN;
	Dimension d = new Dimension(150, 25);
	ButtonGroup bg = new ButtonGroup();
	GridBagConstraints gbc = new GridBagConstraints();
	ImageIcon fitnessTrackerLogo;

	Database db = null;
	int userId = 0;
	String userName = "";
	String firstName = "";
	String calsRequired = "";

	public ProfileNavigation(Database db, int userId, String userName) {

		// Title Bar
		super("Fitness Tracker - Profile Navigation");

		this.db = db;
		this.userId = userId;
		this.userName = userName;
		populate();

		// Spacing of Components
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;

		// Master Panel
		parentPNL = new JPanel();
		parentPNL.setLayout(new BorderLayout());

		// Logo Panel/Label
		try{
			fitnessTrackerLogo = new ImageIcon("FitnessTrackerSmallest.png");
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Logo Failed To Load.");
		}
		logoPNL = new JPanel();
		logoPNL.setLayout(new BorderLayout());
		logoLBL = new JLabel(fitnessTrackerLogo, SwingConstants.CENTER);
		logoPNL.add(logoLBL, BorderLayout.NORTH);
		parentPNL.add(logoPNL, BorderLayout.NORTH);

		// South Panel
		southPNL = new JPanel();
		southPNL.setLayout(new FlowLayout());

		// Interaction Panel
		interactionPNL = new JPanel();
		interactionPNL.setLayout(new GridBagLayout());
		interactionPNL.setBorder(BorderFactory.createTitledBorder(""));

		// Anchor GUI Components
		gbc.anchor = GridBagConstraints.CENTER;

		// Profile Interactions //

		// Exercise Profile Button
		viewProfileBTN = new JButton("View Profile");
		viewProfileBTN.setPreferredSize(d);
		viewProfileBTN.addActionListener(this);
		interactionPNL.add(viewProfileBTN, gbc, SwingConstants.CENTER);

		// Edit Profile Button
		gbc.gridx++;
		editProfileBTN = new JButton("Edit Profile");
		editProfileBTN.setPreferredSize(d);
		editProfileBTN.addActionListener(this);
		interactionPNL.add(editProfileBTN, gbc, SwingConstants.CENTER);

		// Track Food
		gbc.gridx--;
		gbc.gridy++;
		trackFoodBTN = new JButton("Track Food");
		trackFoodBTN.setPreferredSize(d);
		trackFoodBTN.addActionListener(this);
		interactionPNL.add(trackFoodBTN, gbc, SwingConstants.CENTER);

		// Track Workout
		gbc.gridx++;
		trackWorkoutBTN = new JButton("Track Workouts");
		trackWorkoutBTN.setPreferredSize(d);
		trackWorkoutBTN.addActionListener(this);
		interactionPNL.add(trackWorkoutBTN, gbc, SwingConstants.CENTER);

		// Daily Log
		gbc.gridx--;
		gbc.gridy++;
		dailyLogBTN = new JButton("Daily Log");
		dailyLogBTN.setPreferredSize(d);
		dailyLogBTN.addActionListener(this);
		interactionPNL.add(dailyLogBTN, gbc, SwingConstants.CENTER);

		// User History
		gbc.gridx++;
		userHistoryBTN = new JButton("User History");
		userHistoryBTN.setPreferredSize(d);
		userHistoryBTN.addActionListener(this);
		interactionPNL.add(userHistoryBTN, gbc, SwingConstants.CENTER);

		// Logout
		gbc.gridx--;
		gbc.gridy++;
		logoutBTN = new JButton("Log Out");
		logoutBTN.setPreferredSize(d);
		logoutBTN.addActionListener(this);
		interactionPNL.add(logoutBTN, gbc, SwingConstants.CENTER);

		// Exit
		gbc.gridx++;
		exitBTN = new JButton("Exit");
		exitBTN.setPreferredSize(d);
		exitBTN.addActionListener(this);
		interactionPNL.add(exitBTN, gbc, SwingConstants.CENTER);

		// Caloric Intake Greeting
		gbc.gridx++;
		gbc.gridy++;
		caloricIntakeLBL = new JLabel(firstName, SwingConstants.CENTER);
		southPNL.add(caloricIntakeLBL);

		// Calories Needed Per Day
		gbc.gridy++;
		caloriesPerDayJTF = new JTextField(calsRequired);
		caloriesPerDayJTF.setEditable(false);
		southPNL.add(caloriesPerDayJTF);

		parentPNL.add(interactionPNL, BorderLayout.CENTER);
		parentPNL.add(southPNL, BorderLayout.SOUTH);
		add(parentPNL);

		setSize(420, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(ProfileNavigation.EXIT_ON_CLOSE);

	}

	@SuppressWarnings("unused")
	public void populate() {

		try {

			String info = db.getUserInfo(userName, userId);
			String[] uInfo = info.split("~"); // split on delimiter and set
												// variables equal and then
												// assign them to the text field

			firstName = uInfo[0];
			firstName = firstName + "'s Remaining Recommended Daily Calories:";
			String lastName = uInfo[1];
			userName = uInfo[2];
			String age = uInfo[3];
			String weight = uInfo[4];
			String height = uInfo[5];
			String gender = uInfo[6];
			String activityLevel = uInfo[7];
			String goal = uInfo[8];	
			String dailyHis = db.getDailyHistory(userId);
			
			if(dailyHis != null){
				String []dailyInfo = dailyHis.split("~");
				String calsToDisplay = dailyInfo[0];
				int calsForDisplay = Integer.parseInt(uInfo[9]) - Integer.parseInt(calsToDisplay);
				calsRequired = Integer.toString(calsForDisplay);
			}else{
				calsRequired = uInfo[9];
			}

			// caloriesPerDayJTF.setText(calsRequired);

		} catch (SQLException sqe) {
			JOptionPane.showMessageDialog(null, "Problem with database please try again");
		}

	}

	@Override
	@SuppressWarnings("unused")
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == viewProfileBTN) {
			ViewProfile vP = new ViewProfile(db, userId, userName);
			// Display profile details
		}
		if (e.getSource() == editProfileBTN) {
			this.dispose();
			EditProfile uP = new EditProfile(db, userId, userName);
			// Move on to Editing Profile
		}
		if (e.getSource() == trackFoodBTN) {
			this.dispose();
			TrackFood tF = new TrackFood(db, userId, userName);
		}
		if (e.getSource() == trackWorkoutBTN) {
			this.dispose();
			TrackWorkout tW = new TrackWorkout(db, userId, userName);
		}
		if (e.getSource() == dailyLogBTN) {
			this.dispose();
			DailyHistory dH = new DailyHistory(db, userId, userName);
		}
		if (e.getSource() == userHistoryBTN) {
			this.dispose();
			UserHistory uH = new UserHistory(db, userId, userName);
		}
		if (e.getSource() == logoutBTN) {
			// Log-out user & save user info in database
			// Return to Log-In Screen
			JOptionPane.showMessageDialog(null, "Successfully Logged Out. Returning To Fitness Tracker Home Screen.");
			this.dispose();
			HomeScreen hS = new HomeScreen(db);
		}
		if (e.getSource() == exitBTN) {
			JOptionPane.showMessageDialog(null, "Thank you for using Fitness Tracker");
			this.dispose();
			System.exit(0);
		}

	}

}
