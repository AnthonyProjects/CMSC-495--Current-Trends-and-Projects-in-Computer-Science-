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
import javax.swing.SwingConstants;

public class WeightGoals extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel parentPNL, logoPNL, selectionPNL;
	JLabel logoLBL;
	JRadioButton maintainJRB, loseJRB, gainJRB;
	JButton nextBTN;
	Dimension d = new Dimension(150, 25);
	ButtonGroup bg = new ButtonGroup();
	GridBagConstraints gbc = new GridBagConstraints();
	ImageIcon fitnessTrackerLogo;
	
	Database db = null;
	int userId = 0;
	String userName = "";
	String firstName= "";
	String lastName = "";
	int age = 0;
	String gender = "";
	int weight = 0;
	int height = 0; 
	int activityLevel = 0;
	int goal = 0;
	int dailyCaloricNeeds= 0;

	public WeightGoals(Database db, int userId, String userName, String firstName, String lastName, int age, String gender, int weight, int height, int activityLevel) {

		// Title Bar
		super("Fitness Tracker - Select Weight Goals");
		
		this.db = db;
		this.userId = userId;
		this.userName = userName; 
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.weight = weight;
		this.height = height;
		this.gender = gender; 
		this.activityLevel = activityLevel; 
		
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

		// Selection Panel
		selectionPNL = new JPanel();
		selectionPNL.setLayout(new GridBagLayout());
		selectionPNL.setBorder(BorderFactory.createTitledBorder(""));

		// Anchor Components
		gbc.anchor = GridBagConstraints.LINE_START;

		// Weight Goal JRB selections

		// Maintain Weight
		maintainJRB = new JRadioButton("Maintain Weight", true);
		maintainJRB.addActionListener(this);
		bg.add(maintainJRB);
		selectionPNL.add(maintainJRB, gbc);

		// Lose Weight
		gbc.gridy++;
		loseJRB = new JRadioButton("Lose Weight");
		loseJRB.addActionListener(this);
		bg.add(loseJRB);
		selectionPNL.add(loseJRB, gbc);

		// Gain Weight
		gbc.gridy++;
		gainJRB = new JRadioButton("Gain Weight");
		gainJRB.addActionListener(this);
		bg.add(gainJRB);
		selectionPNL.add(gainJRB, gbc);

		// Next Button
		gbc.gridx = 3;
		nextBTN = new JButton("Next");
		nextBTN.addActionListener(this);
		selectionPNL.add(nextBTN, gbc);

		parentPNL.add(selectionPNL, BorderLayout.CENTER);
		add(parentPNL);

		setSize(400, 240);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	public int calReq (int uWeight, int uHeight, int uAge, int uActLevel, String uGender){
		
		String male = "M";
		String female = "F"; 
		double bmr = 0;
		int calReq = 0;
		
		if(uGender.equalsIgnoreCase(male)){
			bmr = 66 + (6.23 * uWeight) + (12.7 * uHeight) - (6.8 * uAge);
			
			if(uActLevel == 1){
				
				calReq = (int)(bmr * 1.2);
			
			}else if(uActLevel == 2){
				
				calReq = (int)(bmr * 1.375);
			
			}else if(uActLevel == 3){
				
				calReq = (int)(bmr * 1.55);
			
			}else if(uActLevel == 4){
				
				calReq = (int)(bmr * 1.725);
			
			}else if(uActLevel == 5){
				
				calReq = (int)(bmr * 1.9);
			
			}
			
		
		}else if (uGender.equalsIgnoreCase(female)){
			
			bmr = 665 + (4.35 * uWeight) + (4.7 * uHeight) - (4.7 * uAge);
			
			if(uActLevel == 1){
				
				calReq = (int)(bmr * 1.2);
			
			}else if(uActLevel == 2){
				
				calReq = (int)(bmr * 1.375);
			
			}else if(uActLevel == 3){
				
				calReq = (int)(bmr * 1.55);
			
			}else if(uActLevel == 4){
				
				calReq = (int)(bmr * 1.725);
			
			}else if(uActLevel == 5){
				
				calReq = (int)(bmr * 1.9);
			
			}
		
		}
		return calReq;
	
	}
	@SuppressWarnings("unused")
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if(e.getSource()==nextBTN){
			
			if(maintainJRB.isSelected()){
				
				 dailyCaloricNeeds = calReq(weight, height, age, activityLevel, gender) - 0;
				 goal = 1;
				
			}else if(loseJRB.isSelected()){
				
				 dailyCaloricNeeds = calReq(weight, height, age, activityLevel, gender) - 500;
				 goal = 2;
			
			}else if(gainJRB.isSelected()){
				
				 dailyCaloricNeeds = calReq(weight, height, age, activityLevel, gender) + 500;
				 goal = 3;
				
			}
			
			try{
			
				db.updateUserInfo(userId, userName, firstName, lastName, age, weight, height, gender, activityLevel, goal, dailyCaloricNeeds);
				
				//int userId = db.getUserId(userName);
				this.dispose();
				
				JOptionPane.showMessageDialog(null, "User Information Successfully Added ");
				ProfileNavigation pD = new ProfileNavigation(db, userId, userName);
			
			}catch(SQLException sqe){
			
				JOptionPane.showMessageDialog(null, "Problem with DataBase Please Try again");

			
			}
			
		}

	}
	
}
