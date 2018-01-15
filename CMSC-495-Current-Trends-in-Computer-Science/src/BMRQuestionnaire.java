import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class BMRQuestionnaire extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel parentPNL, logoPNL, questionPNL;
	JLabel logoLBL;
	JRadioButton sedentaryJRB, lightlyActiveJRB, moderatelyActiveJRB, veryActiveJRB, extraActiveJRB;
	ButtonGroup group; 
	JButton nextBTN;
	Dimension d = new Dimension(150, 25);
	ButtonGroup bg = new ButtonGroup();
	GridBagConstraints gbc = new GridBagConstraints();
	ImageIcon fitnessTrackerLogo;
	
	Database db = null; //database connection allows it to be passed to next class 
	int userId = 0;
	String userName = "";
	String firstName = "";
	String lastName = "";
	int age= 0;
	String gender = "";
	int weight = 0;
	int height = 0; // variables to keep passing info until it is ready to be used 

	public BMRQuestionnaire(Database db, int userId, String userName, String firstName, String lastName, int age, String gender, int weight, int height) {

		// Title Bar
		super("Fitness Tracker - Select Your Activity Level To Continue");

		this.db = db;
		this.userId = userId;
		this.userName = userName; 
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.weight = weight;
		this.height = height;
		this.gender = gender; 
		
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
			fitnessTrackerLogo = new ImageIcon("FitnessTrackerLogoMedium.png");
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Logo Failed To Load.");
		}
		logoPNL = new JPanel();
		logoPNL.setLayout(new BorderLayout());
		logoLBL = new JLabel(fitnessTrackerLogo, SwingConstants.CENTER);
		logoPNL.add(logoLBL, BorderLayout.NORTH);
		parentPNL.add(logoPNL, BorderLayout.NORTH);

		// Questions Panel
		questionPNL = new JPanel();
		questionPNL.setLayout(new GridBagLayout());
		questionPNL.setBorder(BorderFactory.createTitledBorder(""));

		// Anchor Components
		gbc.anchor = GridBagConstraints.LINE_START;
		
		//Activity level JRB selections
		
		//Sedentary
		sedentaryJRB = new JRadioButton("Sedentary (Little to no exercise)", true);
		sedentaryJRB.addActionListener(this);
		bg.add(sedentaryJRB);
		questionPNL.add(sedentaryJRB, gbc);
		
		//Lightly Active
		gbc.gridy++;
		lightlyActiveJRB = new JRadioButton("Lightly Active (Light exercise 1 to 3 days/week)");
		lightlyActiveJRB.addActionListener(this);
		bg.add(lightlyActiveJRB);
		questionPNL.add(lightlyActiveJRB, gbc);
		
		//Moderately Active
		gbc.gridy++;
		moderatelyActiveJRB = new JRadioButton("Moderately Active (Moderate exercise 3 to 5 days/week)");
		moderatelyActiveJRB.addActionListener(this);
		bg.add(moderatelyActiveJRB);
		questionPNL.add(moderatelyActiveJRB, gbc);
		
		//Very Active
		gbc.gridy++;
		veryActiveJRB = new JRadioButton("Very Active (Hard exercise 6 to 7 days/week)");
		veryActiveJRB.addActionListener(this);
		bg.add(veryActiveJRB);
		questionPNL.add(veryActiveJRB, gbc);
		
		//Extra Active
		gbc.gridy++;
		extraActiveJRB = new JRadioButton("Extra Active (Very hard exercise, physical job, or training 2x/day ");
		extraActiveJRB.addActionListener(this);
		bg.add(extraActiveJRB);
		questionPNL.add(extraActiveJRB, gbc);
		
		//creates button group so only one can be selected
		
		group = new ButtonGroup();
		
		group.add(sedentaryJRB);
		group.add(lightlyActiveJRB);
		group.add(moderatelyActiveJRB);
		group.add(veryActiveJRB);
		group.add(extraActiveJRB);
		
		// Next Button
		gbc.gridx = 3;
		nextBTN = new JButton("Next");
		nextBTN.addActionListener(this);
		questionPNL.add(nextBTN, gbc);
		
		parentPNL.add(questionPNL, BorderLayout.CENTER);
		add(parentPNL);
		
		setSize(600, 380);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	@SuppressWarnings("unused")
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource()==nextBTN){
					
			if(sedentaryJRB.isSelected()){
				
				this.dispose();
				WeightGoals wG = new WeightGoals(db, userId, userName, firstName, lastName, age, gender, weight, height, 1);
				// Set BMR Questionnaire GUI not visible
				// Set WeightGoals GUI visible
				
			}else if(lightlyActiveJRB.isSelected()){
				
				this.dispose();
				WeightGoals wG = new WeightGoals(db, userId, userName, firstName, lastName, age, gender, weight, height, 2);
				// Set BMR Questionnaire GUI not visible
				// Set WeightGoals GUI visible
				
			}else if(moderatelyActiveJRB.isSelected()){
				
				this.dispose();
				WeightGoals wG = new WeightGoals(db, userId, userName, firstName, lastName, age, gender, weight, height, 3);
				// Set BMR Questionnaire GUI not visible
				// Set WeightGoals GUI visible
				
			}else if(veryActiveJRB.isSelected()){
				
				this.dispose();
				WeightGoals wG = new WeightGoals(db, userId, userName, firstName, lastName, age, gender, weight, height, 4);
				// Set BMR Questionnaire GUI not visible
				// Set WeightGoals GUI visible
				
			}else if(extraActiveJRB.isSelected()){
				
				this.dispose();
				WeightGoals wG = new WeightGoals(db, userId, userName, firstName, lastName, age, gender, weight, height, 5);
				// Set BMR Questionnaire GUI not visible
				// Set WeightGoals GUI visible
				
			}
		}
	}
}