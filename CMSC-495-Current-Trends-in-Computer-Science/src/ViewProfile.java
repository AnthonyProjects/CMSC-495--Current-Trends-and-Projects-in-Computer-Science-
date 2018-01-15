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

public class ViewProfile extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel logoLBL, firstNameLBL, lastNameLBL, ageLBL, weightLBL, heightLBL, usernameLBL, passwordLBL, genderLBL;
	JTextField firstNameJTF, lastNameJTF, ageJTF, weightJTF, heightJTF, usernameJTF, passwordJTF;
	JPanel parentPNL, logoPNL, detailsPNL;
	JRadioButton maleJRB, femaleJRB;
	JButton exitBTN;
	Dimension d = new Dimension(200, 25);
	ButtonGroup bg = new ButtonGroup();
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
	
	public ViewProfile(Database db, int userId, String userName) {

		// Title Bar
		super("Fitness Tracker - View Personal Profile Details");
		
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
		parentPNL.add(logoPNL, BorderLayout.NORTH);

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
		firstNameJTF.setEditable(false);
		detailsPNL.add(firstNameJTF, gbc);

		// Last Name
		gbc.gridx = 0;
		gbc.gridy = 1;
		lastNameLBL = new JLabel("Last Name: ");
		detailsPNL.add(lastNameLBL, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		lastNameJTF = new JTextField(15);
		lastNameJTF.setEditable(false);
		detailsPNL.add(lastNameJTF, gbc);

		// Age
		gbc.gridx = 0;
		gbc.gridy = 2;
		ageLBL = new JLabel("Age: ");
		detailsPNL.add(ageLBL, gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		ageJTF = new JTextField(15);
		ageJTF.setEditable(false);
		detailsPNL.add(ageJTF, gbc);

		// Weight
		gbc.gridx = 0;
		gbc.gridy = 3;
		weightLBL = new JLabel("Weight (lbs): ");
		detailsPNL.add(weightLBL, gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		weightJTF = new JTextField(15);
		weightJTF.setEditable(false);
		detailsPNL.add(weightJTF, gbc);

		// Height
		gbc.gridx = 0;
		gbc.gridy = 4;
		heightLBL = new JLabel("Height (inches): ");
		detailsPNL.add(heightLBL, gbc);
		gbc.gridx = 1;
		gbc.gridy = 4;
		heightJTF = new JTextField(15);
		heightJTF.setEditable(false);
		detailsPNL.add(heightJTF, gbc);


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
		maleJRB.setEnabled(false);
		bg.add(maleJRB);
		detailsPNL.add(maleJRB, gbc);

		// Female
		gbc.gridx = 3;
		gbc.gridy = 1;
		femaleJRB = new JRadioButton("Female");
		femaleJRB.addActionListener(this);
		femaleJRB.setEnabled(false);
		bg.add(femaleJRB);
		detailsPNL.add(femaleJRB, gbc);

		// Exit Button
		gbc.gridx = 3;
		gbc.gridy = 4;
		exitBTN = new JButton("Return to Profile Navigation");
		exitBTN.addActionListener(this);
		detailsPNL.add(exitBTN, gbc);

		// Add Details Panel
		parentPNL.add(detailsPNL, BorderLayout.CENTER);

		add(parentPNL);

		setSize(650, 350);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EditProfile.EXIT_ON_CLOSE);
		
		populate();
	}
	
	
	public void populate(){
		
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
			 JOptionPane.showMessageDialog(null, "Problem with database please try again");
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==exitBTN){
			this.dispose();
		
			
		}
		
		//if(Username already exists in database){
			//if(username is current username field of the current object){
			//	*update fields of object and database entry*
			//	if(update is successful){
			// 		JOptionPane.showMessageDialog(null, "Profile successfully updated!");
			// 	}
			//}else{
			//   JOptionPane.showMessageDialog(null, "Username already exists. \nPick different username or log in to account.");
			//}
		//}
		
		//else{
			// Update username for object
			// JOptionPane.showMessageDialog(null, "New User Account Successfully added!");
			// Save udpated user information to database entry
			// Set AccountCreation GUI not visible
			// Set BMR Questionnaire GUI visible
		//}

	}
}
