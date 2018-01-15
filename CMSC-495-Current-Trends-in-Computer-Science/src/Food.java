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

public class Food implements ActionListener {

	String foodName;
	int calories;
	JButton modifyFoodBTN, deleteFoodBTN, saveBTN, addToHistoryBTN;
	JPanel panel, parentPNL;
	Dimension buttonDimension = new Dimension(110, 25);
	Dimension panelDimension = new Dimension(1050, 40);
	GridBagConstraints gbc = new GridBagConstraints();
	JTextField calorieJTF;
	Database db = null;
	int userId = 0;

	// Food Constructor accepting parent panel, name of food, caloric content
	public Food(JPanel parentPNL, String foodName, int calories, Database db, int userId) {

		this.foodName = foodName;
		this.calories = calories;
		this.parentPNL = parentPNL;

		modifyFoodBTN = new JButton("Modify Food");
		modifyFoodBTN.setMaximumSize(buttonDimension);
		modifyFoodBTN.setPreferredSize(buttonDimension);
		modifyFoodBTN.addActionListener(this);

		deleteFoodBTN = new JButton("Delete Food");
		deleteFoodBTN.setMaximumSize(buttonDimension);
		deleteFoodBTN.setPreferredSize(buttonDimension);
		deleteFoodBTN.addActionListener(this);

		saveBTN = new JButton("Save");
		saveBTN.setMaximumSize(buttonDimension);
		saveBTN.setPreferredSize(buttonDimension);
		saveBTN.setEnabled(false);
		saveBTN.addActionListener(this);

		addToHistoryBTN = new JButton("Add Daily Food");
		addToHistoryBTN.setPreferredSize(buttonDimension);
		addToHistoryBTN.setEnabled(true);
		addToHistoryBTN.addActionListener(this);

		this.db = db;
		this.userId = userId;

		createFoodInData();

		addPanel(parentPNL);
	//	parentPNL.add(theBox);

	}

	// food constructor used to populate previously added examples without
	// trying to make them again
	public Food(JPanel parentPNL, String foodName, int calories, Database db, int userId, String display) {

		this.foodName = foodName;
		this.calories = calories;
		this.parentPNL = parentPNL;

		modifyFoodBTN = new JButton("Modify Food");
		modifyFoodBTN.setMaximumSize(buttonDimension);
		modifyFoodBTN.setPreferredSize(buttonDimension);
		modifyFoodBTN.addActionListener(this);

		deleteFoodBTN = new JButton("Delete Food");
		deleteFoodBTN.setMaximumSize(buttonDimension);
		deleteFoodBTN.setPreferredSize(buttonDimension);
		deleteFoodBTN.addActionListener(this);

		saveBTN = new JButton("Save");
		saveBTN.setMaximumSize(buttonDimension);
		saveBTN.setPreferredSize(buttonDimension);
		saveBTN.setEnabled(false);
		saveBTN.addActionListener(this);

		addToHistoryBTN = new JButton("Add Daily Food");
		addToHistoryBTN.setPreferredSize(buttonDimension);
		addToHistoryBTN.setEnabled(true);
		addToHistoryBTN.addActionListener(this);

		this.db = db;
		this.userId = userId;

		addPanel(parentPNL);
	//	parentPNL.add(theBox);

	}

	// Add Display Panel for Each Food
	public void addPanel(JPanel parent) {

		// Create Panel and size/layout/borders
		this.panel = new JPanel();

		// Layout of each Food Panel
		this.panel.setLayout(new GridLayout(1, 0));

		// Set Minimum size of Food Panels
		this.panel.setMaximumSize(panelDimension);
		this.panel.setPreferredSize(panelDimension);

		// Create Border for Separation of Panels
		this.panel.setBorder(BorderFactory.createTitledBorder(""));

		// Add panel components
		this.panel.add(new JLabel("Food Type: "));
		this.panel.add(new JLabel(foodName));
		this.panel.add(new JLabel("Calories Count: "));
		calorieJTF = new JTextField(5);
		calorieJTF.setText(Integer.toString(calories));
		calorieJTF.setEditable(false);
		this.panel.add(calorieJTF);
		this.panel.add(modifyFoodBTN);
		this.panel.add(saveBTN);
		this.panel.add(deleteFoodBTN);
		this.panel.add(addToHistoryBTN);

		// Add fleshed out panel to box layout
	//	theBox.add(this.panel, boxFill);
		parentPNL.add(this.panel);

	}

	// Action Performeed
	// Handling Modify/Save/Delete Button Events
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == modifyFoodBTN) {
			this.saveBTN.setEnabled(true);
			this.calorieJTF.setEditable(true);
		}
		if (e.getSource() == saveBTN) {
			updateFood();
		}
		if (e.getSource() == deleteFoodBTN) {
			deleteFood();
		}
		if (e.getSource() == addToHistoryBTN) {
			addToDailyHistory();
		}

	} // End Action Performed

	// Method to Update Food Entry
	public void updateFood() {

		try {
			db.updateFood(userId, foodName, calorieJTF.getText(), "UPDATE");
			JOptionPane.showMessageDialog(null, "Changes to Food Item\nWere Saved!");
			this.calorieJTF.setEditable(false);
			this.saveBTN.setEnabled(false);

		} catch (SQLException sqe) {
			JOptionPane.showMessageDialog(null, "Problem with Database Please Try Again");
		} catch (NoNameException nne) {
			JOptionPane.showMessageDialog(null, "specified food is not in list");
		} catch (UserNameException une) {
			JOptionPane.showMessageDialog(null,
					"Food is already in the list please update that one or check to make sure food is entered correctly");
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null, "Please Enter Whole Number");
		}

	} // End Update Food Method

	// Method to Delete Food Entry
	public void deleteFood() {

		try {
			db.updateFood(userId, foodName, Integer.toString(calories), "REMOVE");
			parentPNL.remove(this.panel);
			parentPNL.repaint();
			parentPNL.validate();
			JOptionPane.showMessageDialog(null, "Food Successfully Deleted");

		} catch (SQLException sqe) {
			JOptionPane.showMessageDialog(null, "Problem with Database Please Try Again");
		} catch (NoNameException nne) {
			JOptionPane.showMessageDialog(null, "specified food is not in list");
		} catch (UserNameException une) {
			JOptionPane.showMessageDialog(null,
					"Food is already in the list please update that one or check to make sure food is entered correctly");
		}

	} // End Delete Food Method

	// Method to Create Food IN Data
	public void createFoodInData() throws UserNameException {

		try {
			db.updateFood(userId, foodName, Integer.toString(calories), "ADD");
		} catch (SQLException sqe) {
			JOptionPane.showMessageDialog(null, "Problem with Database Please Try Again");
		} catch (NoNameException nne) {
			JOptionPane.showMessageDialog(null, "Specified Food is Not in List");
		} 
		
	} // End Create FoodInData Method

	// Method to Add to Daily History
	public void addToDailyHistory() {
		try{
			
			String dailyHistory = db.getDailyHistory(userId);
			
			if(dailyHistory == null){
				
				int calsTot = calories;
				String foodsUsed = foodName + "_" + Integer.toString(calories) + " ";
				String workoutsUsed = "NoWorkout_0 ";
				
				db.updateDailyHistory(userId, calsTot, foodsUsed, workoutsUsed);
				
				JOptionPane.showMessageDialog(null, "Food Succesfully Added To Daily User History!");
			}else{
			
				String []uInfo = dailyHistory.split("~"); //split on delimiter and set variables equal and then assign them to the text field
				
				int calsTot = Integer.parseInt(uInfo[0]);
				String foodsUsed = uInfo[1];
				String workoutsUsed = uInfo[2]; 
				
				if(foodsUsed.equals("NoFood_0 ")){
					String foodToAdd = foodName + "_" + Integer.toString(calories);
					foodsUsed = foodToAdd + " ";
				
					db.updateDailyHistory(userId, calsTot, foodsUsed, workoutsUsed);
				}else{
				
						calsTot = calsTot + calories;
						String foodToAdd = foodName + "_" + Integer.toString(calories)+ " ";
						foodsUsed = foodsUsed + " " + foodToAdd;
			
					db.updateDailyHistory(userId, calsTot, foodsUsed, workoutsUsed);
				}
				JOptionPane.showMessageDialog(null, "Food Succesfully Added To Daily User History!");
			}
		
		}catch(SQLException sqe){
			JOptionPane.showMessageDialog(null, "Problem with database please try again");
		}
	} // End Add To Daily History Method

} // End Food Class
