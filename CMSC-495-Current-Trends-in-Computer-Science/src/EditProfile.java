import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class EditProfile extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel logoLBL, greetingLBL, firstNameLBL, lastNameLBL, ageLBL, weightLBL, heightLBL, usernameLBL, passwordLBL, genderLBL;
	JTextField firstNameJTF, lastNameJTF, ageJTF, weightJTF, heightJTF, usernameJTF, passwordJTF;
	JPanel logoPNL, parentPNL, topParentPNL, instructionsPNL, detailsPNL;
	JRadioButton maleJRB, femaleJRB, userDetailsJRB, loginDetailsJRB;
	JButton saveAndContinueBTN, exitBTN;
	Dimension d = new Dimension(200, 25);
	ButtonGroup bg = new ButtonGroup();
	ButtonGroup bg2 = new ButtonGroup();
	GridBagConstraints gbc = new GridBagConstraints();
	ImageIcon fitnessTrackerLogo;
	
	Database db = null;
	int userId = 0;
	String userName = "";
	
	String firstName = "";
	String lastName = "";
	String age = "";
	String weight = "";
	String height = "";
	String gender = "";
	String activityLevel = "";
	String goal = "";
	String calReq = "";

	public EditProfile(Database db, int userId, String userName) {

		// Title Bar
		super("Fitness Tracker - Edit Your Profile");
		
		this.db = db; 
		this.userId = userId;
		this.userName = userName; 
		
		// Spacing of Components
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;

		// Master Panel
		parentPNL = new JPanel();
		parentPNL.setLayout(new BorderLayout());
		
		// Top Parent Panel (Will Contain Logo/Instructions Panel)
		topParentPNL = new JPanel();
		topParentPNL.setLayout(new BorderLayout());

		// Logo Panel
		try{
			fitnessTrackerLogo = new ImageIcon("FitnessTrackerLogoMedium.png");
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Logo Failed To Load.");
		}
		logoPNL = new JPanel();
		logoPNL.setLayout(new BorderLayout());
		logoLBL = new JLabel(fitnessTrackerLogo, SwingConstants.CENTER);
		logoPNL.add(logoLBL, BorderLayout.NORTH);
		
		topParentPNL.add(logoPNL, BorderLayout.NORTH);
		
		
		// Instructions Panel
		instructionsPNL = new JPanel();
		instructionsPNL.setLayout(new GridBagLayout());
		instructionsPNL.setBorder(BorderFactory.createTitledBorder(""));

		gbc.gridx = 0;
		gbc.gridy = 0;
		greetingLBL = new JLabel("Select Details to Edit:");
		instructionsPNL.add(greetingLBL, gbc);

		gbc.gridx++;
		userDetailsJRB = new JRadioButton("Personal Information", true);
		userDetailsJRB.addActionListener(this);
		bg2.add(userDetailsJRB);
		instructionsPNL.add(userDetailsJRB, gbc);

		gbc.gridx++;
		loginDetailsJRB = new JRadioButton("Username/Password");
		loginDetailsJRB.addActionListener(this);
		bg2.add(loginDetailsJRB);
		instructionsPNL.add(loginDetailsJRB, gbc);
		
		topParentPNL.add(instructionsPNL, BorderLayout.CENTER);

		parentPNL.add(topParentPNL, BorderLayout.NORTH);

		// Details Panel
		detailsPNL = new JPanel();
		detailsPNL.setLayout(new GridBagLayout());
		detailsPNL.setBorder(BorderFactory.createTitledBorder(""));

		// Labels and Fields
		gbc.anchor = GridBagConstraints.LINE_START;

		// First Name
		gbc.gridx = 0;
		gbc.gridy = 0;
		firstNameLBL = new JLabel("First Name: ");
		detailsPNL.add(firstNameLBL, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		firstNameJTF = new JTextField(15);
		firstNameJTF.setEditable(true);
		detailsPNL.add(firstNameJTF, gbc);

		// Last Name
		gbc.gridx = 0;
		gbc.gridy = 1;
		lastNameLBL = new JLabel("Last Name: ");
		detailsPNL.add(lastNameLBL, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		lastNameJTF = new JTextField(15);
		lastNameJTF.setEditable(true);
		detailsPNL.add(lastNameJTF, gbc);

		// Age
		gbc.gridx = 0;
		gbc.gridy = 2;
		ageLBL = new JLabel("Age: ");
		detailsPNL.add(ageLBL, gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		ageJTF = new JTextField(15);
		ageJTF.setEditable(true);
		detailsPNL.add(ageJTF, gbc);

		// Weight
		gbc.gridx = 0;
		gbc.gridy = 3;
		weightLBL = new JLabel("Weight (lbs): ");
		detailsPNL.add(weightLBL, gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		weightJTF = new JTextField(15);
		weightJTF.setEditable(true);
		detailsPNL.add(weightJTF, gbc);

		// Height
		gbc.gridx = 0;
		gbc.gridy = 4;
		heightLBL = new JLabel("Height (inches): ");
		detailsPNL.add(heightLBL, gbc);
		gbc.gridx = 1;
		gbc.gridy = 4;
		heightJTF = new JTextField(15);
		heightJTF.setEditable(true);
		detailsPNL.add(heightJTF, gbc);

		// Username
		gbc.gridx = 0;
		gbc.gridy = 5;
		usernameLBL = new JLabel("Username: ");
		detailsPNL.add(usernameLBL, gbc);
		gbc.gridx = 1;
		gbc.gridy = 5;
		usernameJTF = new JTextField(15);
		usernameJTF.setEditable(false);
		detailsPNL.add(usernameJTF, gbc);

		// Password
		gbc.gridx = 0;
		gbc.gridy = 6;
		passwordLBL = new JLabel("Password: ");
		detailsPNL.add(passwordLBL, gbc);
		gbc.gridx = 1;
		gbc.gridy = 6;
		passwordJTF = new JTextField(15);
		passwordJTF.setEditable(false);
		detailsPNL.add(passwordJTF, gbc);

		// Gender Label/JRBs
		gbc.gridx = 2;
		gbc.gridy = 0;
		genderLBL = new JLabel("Gender: ");
		detailsPNL.add(genderLBL, gbc);

		// Male
		gbc.gridx = 3;
		gbc.gridy = 0;
		maleJRB = new JRadioButton("Male", true);
		maleJRB.addActionListener(this);
		bg.add(maleJRB);
		detailsPNL.add(maleJRB, gbc);

		// Female
		gbc.gridx = 3;
		gbc.gridy = 1;
		femaleJRB = new JRadioButton("Female");
		femaleJRB.addActionListener(this);
		bg.add(femaleJRB);
		detailsPNL.add(femaleJRB, gbc);

		// Save and Continue
		gbc.gridx = 3;
		gbc.gridy = 5;
		saveAndContinueBTN = new JButton("Save and Continue");
		saveAndContinueBTN.setPreferredSize(d);
		saveAndContinueBTN.addActionListener(this);
		detailsPNL.add(saveAndContinueBTN, gbc);

		// Exit Button
		gbc.gridy++;
		exitBTN = new JButton("Exit Without Saving");
		exitBTN.setPreferredSize(d);
		exitBTN.addActionListener(this);
		detailsPNL.add(exitBTN, gbc);

		// Add Details Panel
		parentPNL.add(detailsPNL, BorderLayout.CENTER);

		add(parentPNL);

		setSize(700, 475);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EditProfile.EXIT_ON_CLOSE);
				
		populate();
	}

	public void setLoginDetails() {
				
		usernameJTF.setEditable(true);
		passwordJTF.setEditable(true);
		firstNameJTF.setEditable(false);
		lastNameJTF.setEditable(false);
		ageJTF.setEditable(false);
		weightJTF.setEditable(false);
		heightJTF.setEditable(false);
		maleJRB.setEnabled(false);
		femaleJRB.setEnabled(false);
	}

	public void setUserDetails() {
				
		usernameJTF.setEditable(false);
		passwordJTF.setEditable(false);
		firstNameJTF.setEditable(true);
		lastNameJTF.setEditable(true);
		ageJTF.setEditable(true);
		weightJTF.setEditable(true);
		heightJTF.setEditable(true);
		maleJRB.setEnabled(true);
		femaleJRB.setEnabled(true);
	}
	
	public void populate(){//populate the text fields with data 
	try{
		
		String info = db.getUserInfo(userName, userId);
		String []uInfo = info.split("~"); //split on delimiter and set variables equal and then assign them to the text field
		
		firstName = uInfo[0];
		lastName = uInfo[1];
		userName = uInfo[2];
		age = uInfo[3];
		weight = uInfo[4];
		height = uInfo[5];
		gender = uInfo[6];
		activityLevel = uInfo[7];
		goal = uInfo[8];
		calReq = uInfo[9];
		
		firstNameJTF.setText(firstName);
		lastNameJTF.setText(lastName);
		ageJTF.setText(age);
		weightJTF.setText(weight);
		heightJTF.setText(height);
		
		if(gender.equalsIgnoreCase("M")){
			maleJRB.setSelected(true);
		}else if(gender.equalsIgnoreCase("F")){
			femaleJRB.setSelected(true);
		}
		
		
	}catch(SQLException sqe){
		sqe.printStackTrace();//JOptionPane.showMessageDialog(null, "Problems With Database Please Try again");
	}
	}

	@SuppressWarnings("unused")
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// Editing Username/Password
		if (loginDetailsJRB.isSelected()) {
			setLoginDetails();
			
			// Updating User Log-In Information
			if (e.getSource() == saveAndContinueBTN) {
				// If Users log-in details are updated with no formatting
				// issues/duplicates
				
				try{
				
					if((usernameJTF.getText().isEmpty()) || (passwordJTF.getText().isEmpty())){

						throw new EmptyFieldException();
						
					}else{
						
						db.updateLoginInfo(userId, usernameJTF.getText(), passwordJTF.getText());
						
						JOptionPane.showMessageDialog(null, "Log-In Information\nSuccessfully Updated!");
						
						this.dispose();
						ProfileNavigation pN = new ProfileNavigation(db, userId, userName);
					}
				
				}catch(SQLException sqe){
					
					JOptionPane.showMessageDialog(null, "Problem with database please try again");
			
				}catch(UserNameException une){
				
					JOptionPane.showMessageDialog(null, "Username Already Exisits");
				
				}catch(EmptyFieldException efe){
					JOptionPane.showMessageDialog(null, "Please Make sure both password and username fields are filled out");
				}
			}
		}
		// Editing Personal Information
		if (userDetailsJRB.isSelected()) {
			setUserDetails();
			// Updating User Details
			if (e.getSource() == saveAndContinueBTN) {
						
				try{
					
					if((firstNameJTF.getText().isEmpty()) || (lastNameJTF.getText().isEmpty())|| (ageJTF.getText().isEmpty()) || (heightJTF.getText().isEmpty()) 
							|| (weightJTF.getText().isEmpty())){
						
						
						throw new EmptyFieldException();
					}else{
					
						if(maleJRB.isSelected()){
								
							String male = "M";
							
							BMRQuestionnaire bmr = new BMRQuestionnaire(db, userId, userName, firstNameJTF.getText(), lastNameJTF.getText(), Integer.parseInt(ageJTF.getText()),  
							male, Integer.parseInt(weightJTF.getText()), Integer.parseInt(heightJTF.getText()));
						
							// Set BMR Questionnaire GUI visible
						}else if(femaleJRB.isSelected()){
							
							String female = "F";
							
							BMRQuestionnaire bmr = new BMRQuestionnaire(db, userId, userName, firstNameJTF.getText(), lastNameJTF.getText(), Integer.parseInt(ageJTF.getText()),  
							female, Integer.parseInt(weightJTF.getText()), Integer.parseInt(heightJTF.getText()));
								
							//set BMR Questionnaire GUI visible
						}
					}
					
					// If Users details are successfully updated with no formatting
					// issues
					this.dispose();
					JOptionPane.showMessageDialog(null, "User Details\nSuccessfully Updated!");
					
				}catch(NumberFormatException nfe){
					JOptionPane.showMessageDialog(null, "Please only input whole numbers for age weight and height");
				}catch(EmptyFieldException efe){
					JOptionPane.showMessageDialog(null, "Please fill out ALL fields");
				}
						
			}

		}
				// Exit
				if (e.getSource() == exitBTN) {
					// User exits without any changes to information
					JOptionPane.showMessageDialog(null, "No changes were saved.");
					this.dispose();
					ProfileNavigation pN = new ProfileNavigation(db, userId, userName);
				}
	}
}

