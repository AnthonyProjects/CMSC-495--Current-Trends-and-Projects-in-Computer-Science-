import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class TrackWorkout extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Workout workout;
	JPanel parentPNL, logoPNL, workoutPNL, addWorkoutPNL;
	JScrollPane workoutJSP;
	JLabel logoLBL, workoutNameLBL, caloriesBurnedLBL;
	JTextField workoutNameJTF, caloriesBurnedJTF;
	JButton addWorkoutBTN, exitBTN;
	Dimension d = new Dimension(150, 25);
	ButtonGroup bg = new ButtonGroup();
	GridBagConstraints gbc = new GridBagConstraints();
	ImageIcon fitnessTrackerLogo;

	Database db = null;
	int userId = 0;
	String userName = "";

	public TrackWorkout(Database db, int userId, String userName) {

		// Title Bar
		super("Fitness Tracker - Track Workouts");

		this.db = db;
		this.userId = userId;
		this.userName = userName;

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
			fitnessTrackerLogo = new ImageIcon("FitnessTrackerLogoTFTW.png");
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Logo Failed To Load.");
		}
		logoPNL = new JPanel();
		logoPNL.setLayout(new BorderLayout());
		logoLBL = new JLabel(fitnessTrackerLogo, SwingConstants.CENTER);
		logoPNL.add(logoLBL, BorderLayout.NORTH);
		parentPNL.add(logoPNL, BorderLayout.NORTH);

		// Workout Panel
		workoutPNL = new JPanel();
		workoutPNL.setLayout(new BoxLayout(workoutPNL, BoxLayout.PAGE_AXIS));
		workoutPNL.setBorder(BorderFactory.createTitledBorder(""));
		workoutPNL.setSize(1150, 160);
		workoutPNL.setVisible(true);

		// Workout J Scroll Pane
		workoutJSP = new JScrollPane(workoutPNL);
		workoutJSP.setVisible(true);
		workoutJSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		workoutJSP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		// Add Workout Panel
		addWorkoutPNL = new JPanel();
		addWorkoutPNL.setLayout(new GridBagLayout());
		addWorkoutPNL.setBorder(BorderFactory.createTitledBorder(""));

		// Workout Name Label
		gbc.gridx = 0;
		gbc.gridy = 0;
		workoutNameLBL = new JLabel("Enter Name of Workout: ", SwingConstants.CENTER);
		addWorkoutPNL.add(workoutNameLBL, gbc);

		// Workout Name Entry Field
		gbc.gridy++;
		workoutNameJTF = new JTextField(15);
		workoutNameJTF.setEditable(true);
		workoutNameJTF.setText("");
		addWorkoutPNL.add(workoutNameJTF, gbc);

		// Calories Burned Label
		gbc.gridy++;
		caloriesBurnedLBL = new JLabel("Enter Calories Burned: ", SwingConstants.CENTER);
		addWorkoutPNL.add(caloriesBurnedLBL, gbc);

		// Calories Burned Entry Field
		gbc.gridy++;
		caloriesBurnedJTF = new JTextField(15);
		caloriesBurnedJTF.setEditable(true);
		caloriesBurnedJTF.setText("");
		addWorkoutPNL.add(caloriesBurnedJTF, gbc);

		// Add Workout Button
		gbc.gridy++;
		addWorkoutBTN = new JButton("Add Workout");
		addWorkoutBTN.setPreferredSize(d);
		addWorkoutBTN.addActionListener(this);
		addWorkoutPNL.add(addWorkoutBTN, gbc);

		// "Save and Exit" Button
		// Makes user feel comfortable exiting even though information will
		// already be saved
		gbc.gridy++;
		exitBTN = new JButton("Save and Exit");
		exitBTN.setPreferredSize(d);
		exitBTN.addActionListener(this);
		addWorkoutPNL.add(exitBTN, gbc);

		// Panel Management
		parentPNL.add(workoutJSP, BorderLayout.CENTER);
		parentPNL.add(addWorkoutPNL, BorderLayout.EAST);
		add(parentPNL);

		populate();

		// Frame Details
		setSize(1325, 400);
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(TrackFood.EXIT_ON_CLOSE);

		validate();

	}

	// Action Performeed
	// Handling Add/Exit Button Events
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == addWorkoutBTN) {
			addWorkout();
		}
		if (e.getSource() == exitBTN) {
			exitTrackWorkout();
		}

	} // End Action Performed

	// Add Workout Method to add a new Workout Object/Panel
	public void addWorkout() {

		if (caloriesBurnedJTF.getText().isEmpty() || workoutNameJTF.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Do not leave Workout Name or Calories Burned Empty!");
		} else if (caloriesBurnedJTF.getText().contains("_")) {
			JOptionPane.showMessageDialog(null, "Do Not Include Commas Or Underscores In Calories Burned!");
		} else if (workoutNameJTF.getText().contains("_")) {
			JOptionPane.showMessageDialog(null, "Do Not Include Commas Or Underscores In Workout Name!");
		} else {
			int calorieCount = 0;
			try {
				calorieCount = Integer.parseInt(caloriesBurnedJTF.getText());
				// Create New Workout Object
				String workoutToSend = workoutNameJTF.getText().replaceAll("\\s+", "-");
				workout = new Workout(workoutPNL, workoutToSend, calorieCount, db, userId);
				JOptionPane.showMessageDialog(null, "Workout successfully added!");
				workoutPNL.setVisible(true);
				this.validate();

				// Clear Fields
				workoutNameJTF.setText("");
				caloriesBurnedJTF.setText("");
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Calories Burned must be a whole integer!");
			} catch (UserNameException une) {
				JOptionPane.showMessageDialog(null,
						"workout is already in the list please update that one or check to make sure food is entered correctly");
			}
		}

	} // End Add Workout Method
	

	// Populate Output Panel/Scroll Pane with individual Workout panels
	// from saved DB Workout entries
	@SuppressWarnings("unused")
	public void populate() {

		try {
			String works = db.getWorkouts(userId);
			if (works != null) {
				// Split on delimiter and set variables equal
				// and then assign them to the text field
				String[] uInfo = works.split("~|\\`");
				int count = 0;
				int size = uInfo.length;
				while (count < size) {
					String workoutname = uInfo[count];
					count++;
					int calsToDisplay = Integer.parseInt(uInfo[count]);
					Workout workb = new Workout(workoutPNL, workoutname, calsToDisplay, db, userId, "Display");
					count++;
				}
			}
		} catch (SQLException sqe) {
			JOptionPane.showMessageDialog(null, "Problem with database\n Please try again");
			System.out.println(sqe);
		}

	} // End Populate Method
	

	// Exit Track Workout
	@SuppressWarnings("unused")
	public void exitTrackWorkout() {

		JOptionPane.showMessageDialog(null, "Workout Changes Saved!\nReturning to\nProfile Navigation");
		this.dispose();
		ProfileNavigation pN = new ProfileNavigation(db, userId, userName);

	} // End Exit Track Workout Method
	

} // End Track Workout Class //
