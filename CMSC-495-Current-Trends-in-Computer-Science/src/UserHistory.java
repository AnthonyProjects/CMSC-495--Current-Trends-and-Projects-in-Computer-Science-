import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class UserHistory extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton exitBTN;
	Database db = null;
	int userId = 0;
	String userName = "";
	String firstName = "";
	JFrame mainJFR;
	JPanel logoPNL, historyPNL, parentPNL;
	JLabel logoLBL;
	ImageIcon fitnessTrackerLogo;

	public UserHistory(Database db, int userID, String userName) {

		super(new GridLayout(1, 0));

		this.db = db;
		this.userId = userID;
		this.userName = userName;
		
		// Parent Panel
		parentPNL = new JPanel();
		parentPNL.setLayout(new BorderLayout());
		
		//Logo Panel
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
		
		// User History Panel
		historyPNL = new JPanel();
		historyPNL.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "User History Table",
				TitledBorder.CENTER, TitledBorder.TOP));

		Dimension d = new Dimension(225, 30);

		populate(historyPNL);
		exitBTN = new JButton("Return To Profile Navigation");
		exitBTN.setPreferredSize(d);
		exitBTN.addActionListener(this);
		historyPNL.add(exitBTN, BorderLayout.NORTH);
		parentPNL.add(historyPNL, BorderLayout.CENTER);

		mainJFR = new JFrame("Fitness Tracker - View Your 'Fitness Tracker' History");
		mainJFR.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainJFR.setPreferredSize(new Dimension(1300, 580));
		mainJFR.setResizable(false);
		mainJFR.add(parentPNL);
		mainJFR.pack();
		mainJFR.setVisible(true);
		mainJFR.setLocationRelativeTo(null);

	}

	public void populate(JPanel panel) {

		try {

			String userHistory = db.getUserHistory(userId);

			if (userHistory != null) {

				// split on delimiter and set variables equal and then assign
				// them to the text field
				String[] uInfo = userHistory.split("~|\\`");
				int count = 0;
				int size = uInfo.length;
				int arraySize = 0;
				if (size > 0) {
					arraySize = size / 10;
				}

				Object[][] data = new Object[arraySize][10];
				String[] columnNames = { "Date", "Total Calories", "Target Calories", "Foods Used", "Workouts Used",
						"Age", "Weight (lbs)", "Height (inches)", "Activity Level", "Goal" };
				while (count < size) {

					for (int i = 0; i < arraySize; i++) {

						for (int j = 0; j < 10; j++) {
							
							if(j == 8){
								if( uInfo[count].equals("1")){
									data[i][j] = "sedentary";
								}else if( uInfo[count].equals("2")){
									data[i][j] = "lightly active";
								}else if( uInfo[count].equals("3")){
									data[i][j] = "moderatley active";
								} else if( uInfo[count].equals("4")){
									data[i][j] = "very active";
								} else if( uInfo[count].equals("5")){
									data[i][j] = "extra active";
								} 
							}else if(j == 9){
								if( uInfo[count].equals("1")){
									data[i][j] = "maintain weight";
								}else if( uInfo[count].equals("2")){
									data[i][j] = "lose weight";
								}else if( uInfo[count].equals("3")){
									data[i][j] = "gain weight";
								}
							}else{
								data[i][j] = uInfo[count];
								
							}
							count++;
						}

					}
				}

				JTable table = new JTable(data, columnNames);
				table.setPreferredScrollableViewportSize(new Dimension(1200, 350));
				table.setFillsViewportHeight(true);
				panel.add(table);
				JScrollPane scrollPane = new JScrollPane(table);
				panel.add(scrollPane);
			} else {

				Object[][] data = new Object[10][10];
				String[] columnNames = { "Date", "Total Calories", "Target Calories", "Foods Used", "Workouts Used",
						"Age", "Weight (lbs)", "Height (inches)", "Activity Level", "Goal" };

				JTable table = new JTable(data, columnNames);
				table.setPreferredScrollableViewportSize(new Dimension(1200, 350));
				table.setFillsViewportHeight(true);
				panel.add(table);
				JScrollPane scrollPane = new JScrollPane(table);
				panel.add(scrollPane);
			}

		} catch (SQLException sqe) {

			JOptionPane.showMessageDialog(null, "Problem with Database\nPlease Try Again");
		}

	}

	@SuppressWarnings("unused")
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == exitBTN) {

			mainJFR.dispose();
			ProfileNavigation pN = new ProfileNavigation(db, userId, userName);

		}
	}
}