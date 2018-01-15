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

public class AccountCreation extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel logoLBL, firstNameLBL, lastNameLBL, ageLBL, weightLBL, heightLBL, usernameLBL, passwordLBL, genderLBL;
	JTextField firstNameJTF, lastNameJTF, ageJTF, weightJTF, heightJTF, usernameJTF, passwordJTF;
	JPanel parentPNL, logoPNL, detailsPNL;
	JRadioButton maleJRB, femaleJRB;
	ButtonGroup genderBG;
	JButton createNewUserBTN, backBTN;
	Dimension d = new Dimension(165, 25);
	ButtonGroup bg = new ButtonGroup();
	GridBagConstraints gbc = new GridBagConstraints();
	ImageIcon fitnessTrackerLogo;
	
	Database db = null;

	public AccountCreation(Database db) {

		// Title Bar
		super("Fitness Tracker - Create New Account");

		this.db = db; //connects db to this class so its methods can be used 
		
		// Spacing of Components
		gbc.insets = new Insets(5, 5, 5, 5);
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
		detailsPNL.add(firstNameJTF, gbc);

		// Last Name
		gbc.gridx = 0;
		gbc.gridy = 1;
		lastNameLBL = new JLabel("Last Name: ");
		detailsPNL.add(lastNameLBL, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		lastNameJTF = new JTextField(15);
		detailsPNL.add(lastNameJTF, gbc);

		// Age
		gbc.gridx = 0;
		gbc.gridy = 2;
		ageLBL = new JLabel("Age: ");
		detailsPNL.add(ageLBL, gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		ageJTF = new JTextField(15);
		detailsPNL.add(ageJTF, gbc);

		// Weight
		gbc.gridx = 0;
		gbc.gridy = 3;
		weightLBL = new JLabel("Weight (lbs): ");
		detailsPNL.add(weightLBL, gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		weightJTF = new JTextField(15);
		detailsPNL.add(weightJTF, gbc);

		// Height
		gbc.gridx = 0;
		gbc.gridy = 4;
		heightLBL = new JLabel("Height (inches): ");
		detailsPNL.add(heightLBL, gbc);
		gbc.gridx = 1;
		gbc.gridy = 4;
		heightJTF = new JTextField(15);
		detailsPNL.add(heightJTF, gbc);

		// Username
		gbc.gridx = 0;
		gbc.gridy = 5;
		usernameLBL = new JLabel("Username: ");
		detailsPNL.add(usernameLBL, gbc);
		gbc.gridx = 1;
		gbc.gridy = 5;
		usernameJTF = new JTextField(15);
		detailsPNL.add(usernameJTF, gbc);

		// Password
		gbc.gridx = 0;
		gbc.gridy = 6;
		passwordLBL = new JLabel("Password: ");
		detailsPNL.add(passwordLBL, gbc);
		gbc.gridx = 1;
		gbc.gridy = 6;
		passwordJTF = new JTextField(15);
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
		
		genderBG = new ButtonGroup(); //Gender Button Group so only one radio button is selected 
		genderBG.add(maleJRB);
		genderBG.add(femaleJRB); 

		// Create New User Button
		gbc.gridx = 3;
		gbc.gridy = 5;
		createNewUserBTN = new JButton("Create New Account");
		createNewUserBTN.setPreferredSize(d);
		createNewUserBTN.addActionListener(this);
		detailsPNL.add(createNewUserBTN, gbc);
		
		// Back Button to Return to Home Screen
		gbc.gridy++;
		backBTN = new JButton("Back");
		backBTN.setPreferredSize(d);
		backBTN.addActionListener(this);
		detailsPNL.add(backBTN, gbc);

		// Add Details Panel
		parentPNL.add(detailsPNL, BorderLayout.CENTER);

		add(parentPNL);

		setSize(580, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	@SuppressWarnings("unused")
	@Override
	public void actionPerformed(ActionEvent e) {
	
		if (e.getSource() == createNewUserBTN) {
			
			try{
				//makes sure all fields are filled out or throws exception 
				if((usernameJTF.getText().isEmpty()) || (passwordJTF.getText().isEmpty()) || (firstNameJTF.getText().isEmpty()) || (lastNameJTF.getText().isEmpty())
						|| (ageJTF.getText().isEmpty()) || (heightJTF.getText().isEmpty()) || (weightJTF.getText().isEmpty())){
					
					
					throw new EmptyFieldException();
				}
				//try to create a new user 
				 db.newUser(usernameJTF.getText(), passwordJTF.getText());
				JOptionPane.showMessageDialog(null, "Username and password set please continue answering \nthe following questions to finish setting user info ");
				
				int userId = db.getUserId(usernameJTF.getText());
				//if good create user profile info 
				if(maleJRB.isSelected()){
					
					String male = "M";
					
					this.dispose();
				
					BMRQuestionnaire bmr = new BMRQuestionnaire(db, userId, usernameJTF.getText(), firstNameJTF.getText(), lastNameJTF.getText(), Integer.parseInt(ageJTF.getText()),  
					male, Integer.parseInt(weightJTF.getText()), Integer.parseInt(heightJTF.getText()));
			
				}else if(femaleJRB.isSelected()){
				
					String female = "F";
					
					this.dispose();
				
					BMRQuestionnaire bmr = new BMRQuestionnaire(db, userId, usernameJTF.getText(), firstNameJTF.getText(), lastNameJTF.getText(), Integer.parseInt(ageJTF.getText()),  
					female, Integer.parseInt(weightJTF.getText()), Integer.parseInt(heightJTF.getText()));
			
				}
				
			}catch(UserNameException une){
				JOptionPane.showMessageDialog(null, "Username already exists.  \nPick different username or log in to account.");
			}catch(SQLException sqe){
				JOptionPane.showMessageDialog(null, "Problem with DataBase Please Try again");
			}catch(NumberFormatException nfe){
				JOptionPane.showMessageDialog(null, "Please only enter Whole number values for age, height, and weight");
			}catch(EmptyFieldException eFE){
				JOptionPane.showMessageDialog(null, "Please fill in ALL fields with proper values");
			}
		}
		if(e.getSource() == backBTN){
			this.dispose();
			HomeScreen hS = new HomeScreen(db);
		}

	}
}
