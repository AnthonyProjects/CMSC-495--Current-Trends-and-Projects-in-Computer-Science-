import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class DailyHistory extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JFrame mainJFR;
	JButton exitBTN, removeFoodBTN, removeWorkoutBTN;
	JButton deleteFoodBTN, deleteWorkoutBTN;
	JTextField removeFoodJTF, removeWorkoutJTF;
	JLabel logoLBL;
	JPanel logoPNL, topParentPNL, parentPNL, foodPNL, workoutPNL, foodAndWorkoutPNL, removalPNL, exitPNL, southPNL;
	JScrollPane workoutJSP, foodJSP;
	JTable foodTable, workoutTable;
	GridBagConstraints gbc = new GridBagConstraints();
	Dimension d = new Dimension(200, 25);
	ImageIcon fitnessTrackerLogo;

	 Database db = null;
	 int userId = 0;
	 String userName = "";

	// public UserHistoryTable(Database db, int userID, String userName)
	public DailyHistory(Database db, int userID, String userName) {

		super(new GridLayout(4, 0));

		 this.db = db;
		 this.userId = userID;
		 this.userName = userName;

		// Main/Master Daily History JFrame
		
		mainJFR = new JFrame("Fitness Tracker - Daily Food and Workout Log");
		mainJFR.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainJFR.setPreferredSize(new Dimension(850, 600));
		
		// Food/Workout Daily History Panel Which Will Contain JTables
		
		foodAndWorkoutPNL = new JPanel();
		foodAndWorkoutPNL.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"Daily Food And Workout History Table", TitledBorder.CENTER, TitledBorder.TOP));
		
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
		
		
		// Define Tables //
		
		populate();
		
		// Start 'South' Fields and Buttons
		
		// Panel to Hold Remove Food/Workout Components
	
		removalPNL = new JPanel();
		removalPNL.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"Delete Daily Items - Enter Name of Food or Workout to Remove From Daily History", TitledBorder.CENTER, TitledBorder.TOP));
		removalPNL.setLayout(new GridBagLayout());
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 0;

		// 'Remove Food' JTextField
		
		removeFoodJTF = new JTextField(15);
		removeFoodJTF.setEditable(true);
		removeFoodJTF.setPreferredSize(d);
		removeFoodJTF.addMouseListener(new MouseAdapter() { 
			public void mouseReleased(MouseEvent removeFoodMCE) {
				if(removeFoodMCE.getSource() == removeFoodJTF){
					removeFoodBTN.setEnabled(true);
					removeWorkoutBTN.setEnabled(false);
				}
			}
		});
		removalPNL.add(removeFoodJTF, gbc);

		// 'Remove Food' JButton
		
		gbc.gridy++;
		removeFoodBTN = new JButton("Remove Food");
		removeFoodBTN.setPreferredSize(d);
		removeFoodBTN.addActionListener(this);
		removeFoodBTN.setEnabled(false);
		removalPNL.add(removeFoodBTN, gbc);

		// 'Remove Workout' JTextField
		
		gbc.gridx++;
		gbc.gridy = 0;
		removeWorkoutJTF = new JTextField(15);
		removeWorkoutJTF.setEditable(true);
		removeWorkoutJTF.setPreferredSize(d);
		removeWorkoutJTF.addMouseListener(new MouseAdapter() { 
			public void mouseReleased(MouseEvent removeWorkoutMCE) {
				if(removeWorkoutMCE.getSource() == removeWorkoutJTF){
					removeWorkoutBTN.setEnabled(true);
					removeFoodBTN.setEnabled(false);
				}
			}
		});
		removalPNL.add(removeWorkoutJTF, gbc);

		// 'Remove Workout' JButton
		
		gbc.gridy++;
		removeWorkoutBTN = new JButton("Remove Workout");
		removeWorkoutBTN.setPreferredSize(d);
		removeWorkoutBTN.addActionListener(this);
		removeWorkoutBTN.setEnabled(false);
		removalPNL.add(removeWorkoutBTN, gbc);

		// 'Exit' Button
		
		exitBTN = new JButton("Exit to Profile Navigation");
		exitBTN.setPreferredSize(new Dimension(225, 25));
		exitBTN.setMaximumSize(d);
		exitBTN.addActionListener(this);
		exitPNL = new JPanel();
		exitPNL.add(exitBTN);
		
		// Parent Panel of South Border Layout Panels/Components
		
		southPNL = new JPanel();
		southPNL.setLayout(new BorderLayout());
		southPNL.add(removalPNL, BorderLayout.CENTER);
		southPNL.add(exitPNL, BorderLayout.SOUTH);

		// Panel Management
		
		parentPNL = new JPanel();
		parentPNL.setLayout(new BorderLayout());
		foodAndWorkoutPNL.add(foodPNL, BorderLayout.CENTER);
		foodAndWorkoutPNL.add(workoutPNL, BorderLayout.CENTER);
		parentPNL.add(logoPNL, BorderLayout.NORTH);
		parentPNL.add(foodAndWorkoutPNL, BorderLayout.CENTER);
		parentPNL.add(southPNL, BorderLayout.SOUTH);

		// Frame Management
		
		mainJFR.add(parentPNL);
		mainJFR.setResizable(false);
		mainJFR.pack();
		mainJFR.validate();
		mainJFR.setLocationRelativeTo(null);
		mainJFR.setVisible(true);

	}
	
	public boolean isInteger( String input ) {
	    try {
	        Integer.parseInt( input );
	        return true;
	    }
	    catch( Exception e ) {
	        return false;
	    }
	}
	
	public void populate(){
		try{
			String dailyHis = db.getDailyHistory(userId);
			System.out.println(dailyHis);
			
			if(dailyHis != null){
				
				String []uInfo = dailyHis.split("~"); 
				String []foods = uInfo[1].split("\\s+");
				//System.out.println(uInfo[0]);
				int foodTableSize = foods.length;
				int count = 0;
				
				String[] foodColumns = { "Number", "Food Name", "Total Calories Consumed" };
				Object[][] foodData = new Object[foodTableSize][3];  
				
				for(String str : foods){
					
					String []foodInfo = str.split("_");
					foodData[count][0] = count+1;
					foodData[count][1] = foodInfo[0];
					foodData[count][2] = foodInfo[1];
					count++;
					
				}
			

				foodTable = new JTable(foodData, foodColumns);
				foodTable.setPreferredScrollableViewportSize(new Dimension(800, 110));
				foodTable.setFillsViewportHeight(true);
				foodPNL = new JPanel();
				foodPNL.add(foodTable);
				foodJSP = new JScrollPane(foodTable);
				foodPNL.add(foodJSP);
		
		
				// Define Workout Table //
				
				String []workouts = uInfo[2].split("\\s+");
				int workoutTableSize = workouts.length;
				int countA = 0;

				String[] workoutColumns = { "Number", "Workout Name", "Total Calories Burned" };
				Object[][] workoutData = new Object[workoutTableSize][3];
				
				for(String str : workouts){
					String []workoutInfo = str.split("_");
					int num = countA+1;
					workoutData[countA][0] = num;
					workoutData[countA][1] = workoutInfo[0];
					workoutData[countA][2] = workoutInfo[1];
					countA++;
				}
			
				workoutTable = new JTable(workoutData, workoutColumns);
				workoutTable.setPreferredScrollableViewportSize(new Dimension(800, 110));
				workoutTable.setFillsViewportHeight(true);
				workoutPNL = new JPanel();
				workoutPNL.add(workoutTable);
				workoutJSP = new JScrollPane(workoutTable);
				workoutPNL.add(workoutJSP);
			}else{
				String[] foodColumns = { "Number", "Food Name", "Total Calories Consumed" };
				Object[][] foodData = new Object[10][3];  
				


				foodTable = new JTable(foodData, foodColumns);
				foodTable.setPreferredScrollableViewportSize(new Dimension(800, 110));
				foodTable.setFillsViewportHeight(true);
				foodPNL = new JPanel();
				foodPNL.add(foodTable);
				foodJSP = new JScrollPane(foodTable);
				foodPNL.add(foodJSP);
		

				String[] workoutColumns = { "Number", "Workout Name", "Total Calories Burned" };
				Object[][] workoutData = new Object[10][3];
			
				workoutTable = new JTable(workoutData, workoutColumns);
				workoutTable.setPreferredScrollableViewportSize(new Dimension(800, 110));
				workoutTable.setFillsViewportHeight(true);
				workoutPNL = new JPanel();
				workoutPNL.add(workoutTable);
				workoutJSP = new JScrollPane(workoutTable);
				workoutPNL.add(workoutJSP);
			}
			
		}catch(SQLException sqe){
			JOptionPane.showMessageDialog(null, "Problems with datbase please try again");
		}
	}
	
	public int calCalc(String entry, int cals){
		int answer = 0;
		if(entry.equals("Wrk")){
			int wsum = 0;
			int fsum = 0;
			int tsum = 0;
			for(int i =0; i<workoutTable.getRowCount(); i++){
				wsum += Integer.parseInt((String)workoutTable.getValueAt(i, 2));
			}
			for(int i =0; i<foodTable.getRowCount(); i++){
				fsum += Integer.parseInt((String)foodTable.getValueAt(i, 2));
			}
			tsum = fsum - wsum;
			tsum += cals;
			answer = tsum;
		
		}else if(entry.equals("Food")){
			
			int wsum = 0;
			int fsum = 0;
			int tsum = 0;
			for(int i =0; i<workoutTable.getRowCount(); i++){
				wsum += Integer.parseInt((String)workoutTable.getValueAt(i, 2));
			}
			for(int i =0; i<foodTable.getRowCount(); i++){
				fsum += Integer.parseInt((String)foodTable.getValueAt(i, 2));
			}
			tsum = fsum - wsum;
			//System.out.println(tsum);
			tsum -= cals;
			answer = tsum;
			
		}
		
		return answer;
	}

	@SuppressWarnings("unused")
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == exitBTN) {

			JOptionPane.showMessageDialog(null, "Changes Saved. Returning to Profile Navigation.");
			mainJFR.dispose();
			ProfileNavigation pN = new ProfileNavigation(db, userId, userName);
			// Display profile details
		}
		if (e.getSource() == removeFoodBTN){
			
			if(removeFoodJTF.getText().isEmpty()){
				JOptionPane.showMessageDialog(null, "Please Enter a Food To Remove");
			}else{
				removeFood();
			}
		}
		if (e.getSource() == removeWorkoutBTN){
			
			if(removeWorkoutJTF.getText().isEmpty()){
				JOptionPane.showMessageDialog(null, "Please Enter a Workout To Remove");
			}else{
				removeWorkout();
			}
		}
	}
	
	public void removeFood(){
		
		// Remove Food From Daily History Database Table
		
		try{
			String foodToRemove = removeFoodJTF.getText();
			if(isInteger(foodToRemove)){
				JOptionPane.showMessageDialog(null, "Enter the Name of the Workout.");
				return;
			}
			if(foodToRemove.equals("NoFood")){
				JOptionPane.showMessageDialog(null, "Nothing To Remove From Food");
				return;
			}
			String foodToRemoveFromDB = "NoMatch";
			int row = foodTable.getRowCount();
			int col = foodTable.getColumnCount();
			int arrayLocOfFoodToRemove = 0; 
			
			search:
			for(int i = 0; i < row; i++){
				
				for(int j = 0; j < col; j++){
					
					Object cell = foodTable.getValueAt(i, j);
					if(cell.equals(foodToRemove)){
						foodToRemoveFromDB = cell + "_" + (String)foodTable.getValueAt(i, j+1);
						//System.out.println(foodToRemoveFromDB);
						break search;
						
					}
				}
			}
			if(foodToRemoveFromDB.equals("NoMatch")){
				JOptionPane.showMessageDialog(null, "Food Is Not In Table");
				return;
			}
			
			String dailyHistory = db.getDailyHistory(userId);
			//System.out.println(dailyHistory);
			if(dailyHistory != null){
				
				String []uInfo = dailyHistory.split("~");
				String []foodInfo = uInfo[1].split("\\s+");
				
				for(int i = 0; i < foodInfo.length; i++){
					if(foodInfo[i].equals(foodToRemoveFromDB)){
						arrayLocOfFoodToRemove = i;
						String newFoodsUsed = "";
						
						if(foodInfo.length <= 1){
							newFoodsUsed = "NoFood_0 ";
							String []foodCal = foodInfo[i].split("_");
							int calsToReturn = calCalc("Food", Integer.parseInt(foodCal[1]));
							//System.out.println(calsToReturn);
							db.updateDailyHistory(userId, calsToReturn, newFoodsUsed, uInfo[2]);
						}else{
		
							for(int j = 0; j <arrayLocOfFoodToRemove; j++){
								
									String food = foodInfo[j] +  " ";
									newFoodsUsed = newFoodsUsed + food;						
							}
							for(int k = arrayLocOfFoodToRemove + 1; k < foodInfo.length; k++){
								String food = foodInfo[k] + " ";
								newFoodsUsed = newFoodsUsed + food;	
							}
							String []foodCal = foodToRemoveFromDB.split("_");
							int calsToReturn = calCalc("Food", Integer.parseInt(foodCal[1]));
							//System.out.println(calsToReturn);
							db.updateDailyHistory(userId, calsToReturn, newFoodsUsed, uInfo[2]);
						}
					
					}
				}				
				
				JOptionPane.showMessageDialog(null, "Food Removed From Daily Log.");
				mainJFR.dispose();
				
				@SuppressWarnings("unused")
				// Create New 'Daily History' Page With Updated Daily History Information
				DailyHistory dH = new DailyHistory(db, userId, userName);
			}else{
				
				JOptionPane.showMessageDialog(null, "Daily Log Empty Plese Enter Foods to Be Able To Remove Any");
				
			}
		
		}catch(SQLException sqe){
			JOptionPane.showMessageDialog(null, "Problem With Database Please Try Again");
		}
		
}
	
	public void removeWorkout(){
		
		// Remove Workout From Daily History Database Table
		try{
			String workoutToRemove = removeWorkoutJTF.getText();
			if(isInteger(workoutToRemove)){
				JOptionPane.showMessageDialog(null, "Enter the Name of the Workout.");
				return;
			}
			if(workoutToRemove.equals("NoWorkout")){
				JOptionPane.showMessageDialog(null, "Nothing To Remove From Workouts");
				return;
			}
			String workoutToRemoveFromDB = "NoMatch";
			int row = workoutTable.getRowCount();
			int col = workoutTable.getColumnCount();
			int arrayLocOfFoodToRemove = 0; 
			
			search:
			for(int i = 0; i < row; i++){
				
				for(int j = 0; j < col; j++){
					
					Object cell = workoutTable.getValueAt(i, j);
					if(cell.equals(workoutToRemove)){
						workoutToRemoveFromDB = cell + "_" + (String)workoutTable.getValueAt(i, j+1);
						//System.out.println(workoutToRemoveFromDB);
						break search;
						
					}
				}
			}
			if(workoutToRemoveFromDB.equals("NoMatch")){
				JOptionPane.showMessageDialog(null, "Workout Is Not In Table");
				return;
			}
			
			String dailyHistory = db.getDailyHistory(userId);
			//System.out.println(dailyHistory);
			if(dailyHistory != null){
				
				String []uInfo = dailyHistory.split("~");
				String []workoutInfo = uInfo[2].split("\\s+");
				
				for(int i = 0; i < workoutInfo.length; i++){
					if(workoutInfo[i].equals(workoutToRemoveFromDB)){
						arrayLocOfFoodToRemove = i;
						String newWorkoutsUsed = "";
						
						if(workoutInfo.length <= 1){
							newWorkoutsUsed = "NoWorkout_0 ";
							String []workoutCal = workoutInfo[i].split("_");
							int calsToReturn = calCalc("Wrk", Integer.parseInt(workoutCal[1]));
							//System.out.println(calsToReturn);
							db.updateDailyHistory(userId, calsToReturn, uInfo[1], newWorkoutsUsed);
						}else{
		
							for(int j = 0; j <arrayLocOfFoodToRemove; j++){
								
									String workout = workoutInfo[j] + " ";
									newWorkoutsUsed = newWorkoutsUsed + workout;						
							}
							for(int k = arrayLocOfFoodToRemove + 1; k < workoutInfo.length; k++){
								String workout = workoutInfo[k] + " ";
								newWorkoutsUsed = newWorkoutsUsed + workout;	
							}
							String []workoutCal = workoutToRemoveFromDB.split("_");
							int calsToReturn = calCalc("Wrk", Integer.parseInt(workoutCal[1]));
							//System.out.println(calsToReturn);
							db.updateDailyHistory(userId, calsToReturn, uInfo[1], newWorkoutsUsed);
						}
					
					}
				}	
		
		JOptionPane.showMessageDialog(null, "Workout Removed From Daily Log.");
		mainJFR.dispose();
		@SuppressWarnings("unused")
		
		// Create New 'Daily History' Page With Updated Daily History Information
		DailyHistory dH = new DailyHistory(db, userId, userName);
		
		}else{
				
			JOptionPane.showMessageDialog(null, "Daily Log Empty Plese Enter Foods to Be Able To Remove Any");
				
		}
		
		}catch(SQLException sqe){
			JOptionPane.showMessageDialog(null, "Problem With Database Please Try Again");
		}
	}

}

