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

public class TrackFood extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Food food;
	JPanel parentPNL, logoPNL, foodPNL, addFoodPNL;
	JScrollPane foodJSP;
	JLabel logoLBL, foodNameLBL, calorieLBL;
	JTextField foodNameJTF, caloriesJTF;
	JButton addFoodBTN, exitBTN;
	Dimension d = new Dimension(150, 25);
	ButtonGroup bg = new ButtonGroup();
	GridBagConstraints gbc = new GridBagConstraints();
	ImageIcon fitnessTrackerLogo;
	
	Database db = null;
	int userId = 0;
	String userName = "";

	public TrackFood(Database db, int userId, String userName) {

		// Title Bar
		super("Fitness Tracker - Track Food");
		
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

		// Food Panel
		foodPNL = new JPanel();
		foodPNL.setLayout(new BoxLayout(foodPNL, BoxLayout.PAGE_AXIS));
		foodPNL.setBorder(BorderFactory.createTitledBorder(""));
		foodPNL.setSize(1060, 160);
		foodPNL.setVisible(true);
		
		// Workout J Scroll Pane
		foodJSP = new JScrollPane(foodPNL);
		foodJSP.setVisible(true);
		foodJSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		foodJSP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		// Add Food Panel
		addFoodPNL = new JPanel();
		addFoodPNL.setLayout(new GridBagLayout());
		addFoodPNL.setBorder(BorderFactory.createTitledBorder(""));

		// Food Name Label
		gbc.gridx = 0;
		gbc.gridy = 0;
		foodNameLBL = new JLabel("Enter Name of Food: ", SwingConstants.CENTER);
		addFoodPNL.add(foodNameLBL, gbc);

		// Food Name Entry Field
		gbc.gridy++;
		foodNameJTF = new JTextField(15);
		foodNameJTF.setEditable(true);
		foodNameJTF.setText("");
		addFoodPNL.add(foodNameJTF, gbc);

		// Calorie Label
		gbc.gridy++;
		calorieLBL = new JLabel("Enter Calories of Food: ", SwingConstants.CENTER);
		addFoodPNL.add(calorieLBL, gbc);

		// Calorie Entry Field
		gbc.gridy++;
		caloriesJTF = new JTextField(15);
		caloriesJTF.setEditable(true);
		caloriesJTF.setText("");
		addFoodPNL.add(caloriesJTF, gbc);

		// Add Food Button
		gbc.gridy++;
		addFoodBTN = new JButton("Add Food");
		addFoodBTN.setPreferredSize(d);
		addFoodBTN.addActionListener(this);
		addFoodPNL.add(addFoodBTN, gbc);

		// "Save and Exit" Button
		// Makes user feel comfortable exiting even though information will
		// already be saved
		gbc.gridy++;
		exitBTN = new JButton("Save and Exit");
		exitBTN.setPreferredSize(d);
		exitBTN.addActionListener(this);
		addFoodPNL.add(exitBTN, gbc);

		// Panel Management
		parentPNL.add(foodJSP, BorderLayout.CENTER);
		parentPNL.add(addFoodPNL, BorderLayout.EAST);
		add(parentPNL);
		
		populate();
		
		// Frame Details
		setSize(1275, 400);
		setResizable(false);
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

		if (e.getSource() == exitBTN) {
			exitTrackFood();
		}
		if (e.getSource() == addFoodBTN) {
			addFood();
		}
		
	} // End Action Performed
	
	// Add Food Method to add a new Food Object/Panel
	public void addFood(){
		
		if (caloriesJTF.getText().isEmpty() || foodNameJTF.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Do not leave Food Name or Calories Empty!");
		} else if(caloriesJTF.getText().contains("_")){
			JOptionPane.showMessageDialog(null, "Do Not Include Commas Or Underscores In Calories!");
		} else if(foodNameJTF.getText().contains("_")){
			JOptionPane.showMessageDialog(null, "Do Not Include Commas Or Underscores In Food Name!");
		}else {
			int calorieCount = 0;
			try {
				calorieCount = Integer.parseInt(caloriesJTF.getText());
				// Create New Food Object
				String foodToSend = foodNameJTF.getText().replaceAll("\\s+", "-");
				food = new Food(foodPNL, foodToSend, calorieCount, db, userId);
				JOptionPane.showMessageDialog(null, "Food successfully added!");
				foodPNL.setVisible(true);
				this.validate();
				// Clear Fields
				foodNameJTF.setText("");
				caloriesJTF.setText("");
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Calories must be a whole integer!");
			}catch (UserNameException Une){
				JOptionPane.showMessageDialog(null,
							"Food is already in the list please update that one or check to make sure food is entered correctly");
			}

		}
		
	} // End Add Food Method

	
	// Populate Output Panel/Scroll Pane with individual Food Panels
	// from saved DB Food entries
	@SuppressWarnings("unused")
	public void populate(){
		
		try{
			String foods = db.getFoods(userId);
			if(foods != null){
				//split on delimiter and set variables equal and then assign them to the text field
				String []uInfo = foods.split("~|\\`"); 
				int count = 0;
				int size = uInfo.length;
				while(count < size){
					String foodname = uInfo[count];
					count ++;
					int calsToDisplay = Integer.parseInt(uInfo[count]);
					Food foodb = new Food(foodPNL, foodname, calsToDisplay, db, userId, "Display");
					count ++; 
				}
			}
		}catch(SQLException sqe){
			JOptionPane.showMessageDialog(null, "Problem with database please try again");
		}
		
	} // End Populate Method
	
	
	// Exit Track Food 
	@SuppressWarnings("unused")
	public void exitTrackFood(){
		
		JOptionPane.showMessageDialog(null, "Food Changes Saved!\nReturning to\nProfile Navigation");
		this.dispose();
		ProfileNavigation pN = new ProfileNavigation(db, userId, userName);
		
	} // End Exit Track Food Method
	
	
} // End Track Food Class
