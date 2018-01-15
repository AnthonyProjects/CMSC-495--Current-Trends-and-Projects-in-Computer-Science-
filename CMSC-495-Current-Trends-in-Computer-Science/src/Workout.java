import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Workout implements ActionListener {

	String workoutName;
	int caloriesBurned;
	JButton modifyWorkoutBTN, deleteWorkoutBTN, saveBTN, addToHistoryBTN;
	JTextField caloriesBurnedJTF;
	JPanel parentPNL, panel;
	Dimension buttonDimension = new Dimension(140, 25);
	Dimension panelDimension = new Dimension(1100, 40);
	GridBagConstraints gbc = new GridBagConstraints();
	Database db = null;
	int userId = 0;

	public Workout(JPanel parentPNL, String workoutName, int caloriesBurned, Database db, int userId) {

		this.workoutName = workoutName;
		this.caloriesBurned = caloriesBurned;
		this.parentPNL = parentPNL;

		modifyWorkoutBTN = new JButton("Modify Workout");
		modifyWorkoutBTN.setMaximumSize(buttonDimension);
		modifyWorkoutBTN.setPreferredSize(buttonDimension);
		modifyWorkoutBTN.addActionListener(this);

		deleteWorkoutBTN = new JButton("Delete Workout");
		deleteWorkoutBTN.setMaximumSize(buttonDimension);
		deleteWorkoutBTN.setPreferredSize(buttonDimension);
		deleteWorkoutBTN.addActionListener(this);

		saveBTN = new JButton("Save");
		saveBTN.setMaximumSize(buttonDimension);
		saveBTN.setPreferredSize(buttonDimension);
		saveBTN.setEnabled(false);
		saveBTN.addActionListener(this);

		addToHistoryBTN = new JButton("Add Daily W/O");
		addToHistoryBTN.setPreferredSize(buttonDimension);
		addToHistoryBTN.setEnabled(true);
		addToHistoryBTN.addActionListener(this);

		this.db = db;
		this.userId = userId;

		createWorkoutInData();

		addPanel(parentPNL);

	}

	public Workout(JPanel parentPNL, String workoutName, int caloriesBurned, Database db, int userId, String display) {

		this.workoutName = workoutName;
		this.caloriesBurned = caloriesBurned;
		this.parentPNL = parentPNL;

		modifyWorkoutBTN = new JButton("Modify Workout");
		modifyWorkoutBTN.setMaximumSize(buttonDimension);
		modifyWorkoutBTN.setPreferredSize(buttonDimension);
		modifyWorkoutBTN.addActionListener(this);

		deleteWorkoutBTN = new JButton("Delete Workout");
		deleteWorkoutBTN.setMaximumSize(buttonDimension);
		deleteWorkoutBTN.setPreferredSize(buttonDimension);
		deleteWorkoutBTN.addActionListener(this);

		saveBTN = new JButton("Save");
		saveBTN.setMaximumSize(buttonDimension);
		saveBTN.setPreferredSize(buttonDimension);
		saveBTN.setEnabled(false);
		saveBTN.addActionListener(this);

		addToHistoryBTN = new JButton("Add Daily W/O");
		addToHistoryBTN.setPreferredSize(buttonDimension);
		addToHistoryBTN.setEnabled(true);
		addToHistoryBTN.addActionListener(this);

		this.db = db;
		this.userId = userId;

		addPanel(parentPNL);	

	}

	// Add Display Panel for Each Workout
	public void addPanel(JPanel parentPNL) {

		// Create Panel and size/layout/borders
		this.panel = new JPanel();

		// Layout of each Workout Panel
		this.panel.setLayout(new GridLayout(1, 0));

		// Set Minimum size of Workout Panels
		this.panel.setMaximumSize(panelDimension);
		this.panel.setPreferredSize(panelDimension);

		// Create Border for Separation of Panels
		this.panel.setBorder(BorderFactory.createTitledBorder(""));

		// Add panel components
		this.panel.add(new JLabel("Workout Type: "));
		this.panel.add(new JLabel(workoutName));
		this.panel.add(new JLabel("Calories Burned: "), gbc);
		caloriesBurnedJTF = new JTextField(5);
		caloriesBurnedJTF.setText(Integer.toString(caloriesBurned));
		caloriesBurnedJTF.setEditable(false);
		this.panel.add(caloriesBurnedJTF);
		this.panel.add(modifyWorkoutBTN);
		this.panel.add(saveBTN);
		this.panel.add(deleteWorkoutBTN);
		this.panel.add(addToHistoryBTN);

		// Add fleshed out panel to box layout
		parentPNL.add(this.panel);

	}

	// Action Performeed
	// Handling Modify/Delete/Save Button Events
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == modifyWorkoutBTN) {
			this.caloriesBurnedJTF.setEditable(true);
			this.saveBTN.setEnabled(true);
		}
		if (e.getSource() == saveBTN) {
			updateWorkout();
		}
		if (e.getSource() == deleteWorkoutBTN) {
			deleteWorkout();
		}
		if (e.getSource() == addToHistoryBTN) {
			addToDailyHistory();
		}
	} // End Action Performed

	// Method to Update Workout Entry
	public void updateWorkout() {

		try {
			db.updateWorkout(userId, workoutName, caloriesBurnedJTF.getText(), "UPDATE");
			JOptionPane.showMessageDialog(null, "Changes to Workout\nWere Saved!");
			this.saveBTN.setEnabled(false);
			this.caloriesBurnedJTF.setEditable(false);
		} catch (SQLException sqe) {
			JOptionPane.showMessageDialog(null, "Problem with Database Please Try Again");
		} catch (NoNameException nne) {
			JOptionPane.showMessageDialog(null, "specified workout is not in list");
		} catch (UserNameException une) {
			JOptionPane.showMessageDialog(null,
					"workout is already in the list please update that one or check to make sure workout is entered correctly");
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null, "Please only enter whole numbers");
		}

	} // End Update Workout Method

	// Method to Delete Workout Entry
	public void deleteWorkout() {

		try {
			db.updateWorkout(userId, workoutName, Integer.toString(caloriesBurned), "REMOVE");
			parentPNL.remove(this.panel);
			parentPNL.repaint();
			parentPNL.validate();
			JOptionPane.showMessageDialog(null, "Workout Successfully Deleted.");
		} catch (SQLException sqe) {
			JOptionPane.showMessageDialog(null, "Problem with Database Please Try Again");
		} catch (NoNameException nne) {
			JOptionPane.showMessageDialog(null, "specified workout is not in list");
		} catch (UserNameException une) {
			JOptionPane.showMessageDialog(null,
					"workout is already in the list please update that one or check to make sure workout is entered correctly");
		}

	} // End Delete Workout Method

	// Method to Create Workout IN Data
	public void createWorkoutInData() throws UserNameException {

		try {
			db.updateWorkout(userId, workoutName, Integer.toString(caloriesBurned), "ADD");
		} catch (SQLException sqe) {
			JOptionPane.showMessageDialog(null, "Problem with Database\nPlease Try Again");
		} catch (NoNameException nne) {
			JOptionPane.showMessageDialog(null, "specified workout is not in list");
		}

	} // End CreateWorkoutInData Method

	// Method to Add to Daily History
	public void addToDailyHistory() {
		try{
			
			String dailyHistory = db.getDailyHistory(userId);
			
			if(dailyHistory == null){
				
				int calsTot = 0 - caloriesBurned;
				String foodsUsed = "NoFood_0 ";
				String workoutsUsed =  workoutName + "_" + Integer.toString(caloriesBurned) + " ";
				
				db.updateDailyHistory(userId, calsTot, foodsUsed, workoutsUsed);
				
				JOptionPane.showMessageDialog(null, "Workout Succesfully Added To Daily User History!");
			}else{
			
				String []uInfo = dailyHistory.split("~"); //split on delimiter and set variables equal and then assign them to the text field
			
				int calsTot = Integer.parseInt(uInfo[0]);
				String foodsUsed = uInfo[1];
				String workoutsUsed = uInfo[2]; 
				
				if(workoutsUsed.equals("NoWorkout_0 ")){
					calsTot = calsTot - caloriesBurned;
					String workoutToAdd = workoutName + "_" + Integer.toString(caloriesBurned);
					workoutsUsed = workoutToAdd + " ";
				
					db.updateDailyHistory(userId, calsTot, foodsUsed, workoutsUsed);
				}else{
					

						calsTot = calsTot - caloriesBurned;
						String workoutToAdd = workoutName + "_" + Integer.toString(caloriesBurned)+ " ";
						workoutsUsed = workoutsUsed + " " + workoutToAdd;
						
					db.updateDailyHistory(userId, calsTot, foodsUsed, workoutsUsed);
				}
			
				JOptionPane.showMessageDialog(null, "Workout Succesfully Added To Daily User History!");
			}
		}catch(SQLException sqe){
			JOptionPane.showMessageDialog(null, "Problem with database please try again");
		
		}
	} // End Add To Daily History Method

} // End Workout Class
