import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class Database{
	
	private Connection conn;  //connection varibale
	
	public Database(){ //initalizes db connection whenever instance of the db class is created. That way whenever the class methods are called it is ready to work 
	
	try
	{
			Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e)
		{
			System.out.println("There is no MySQL JDBC Driver. Please get one.");
			e.printStackTrace();
			//return;
		}

		System.out.println("MySQL JDBC Driver is now registered");
	
	
		// Tries to connect to the database through the connectionURL, and the user name
		// and password that was given to the database. If it makes a connection, it will
		// let the user know they have been granted permission to access the database. If it does
		// not make a connection, it will display a message letting the user know it failed, and will
		// provide you with that message in the output console.

		try 
		{
			String connectionURL = "jdbc:mysql://cmsc495.cyqrnvhacg77.us-east-1.rds.amazonaws.com:3306/cmsc495alpha?user=alphauser";
			String userName = "alphauser";
			String password = "T3amalpha!";
			conn = DriverManager.getConnection(connectionURL, userName, password);

		} 
		catch(SQLException sqlE) 
		{
			System.out.println("The Connection to the database Failed! Check the console output, to see why it failed");
			sqlE.printStackTrace();
			return;
		}

		if(conn != null) 
		{
			System.out.println("You have successful established a connection to the database");
			
		} 
		else
		{
			System.out.println("Failed to make connection to the database!");
		}

	
	}
	public void newUser(String userName, String password)throws SQLException, UserNameException{ //method to create a new user
		
		CallableStatement call = conn.prepareCall("{call create_user(?, ?, ?)}"); //javas way of using stored procedures roger wrote 
		 
		call.setString(1, userName); //sets the first input value
		call.setString(2, password); //sets the second input value 
		call.registerOutParameter(3, Types.VARCHAR);
		call.execute();   //runs the procedure. So that means that after this step the user should be in the DB
		String msg = call.getString(3);
		if(msg.equals("Error: Username already exists.")){
			throw new UserNameException();
		}
	
		
	}
	
	public void updateUserInfo(int userId, String userName, String firstName, String lastName, int age, int weight, int height, String gender, int activityLevel, int goal, int calReq )throws SQLException{
		//method to add or update personal information 
		
		CallableStatement call = conn.prepareCall("{call enter_user_info(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
		/*int userId = 0; //need to find this for the username so the procedure can be called 
		
		 Statement state = conn.createStatement ();     //performing search of the db table login_info for all the id and userName 
		 state.executeQuery ("SELECT id, username FROM login_info");
		 ResultSet rSet = state.getResultSet ();
		   
		   while (rSet.next ()) //to find the id created when account was made, the username of user  must match the one supplied in the by the user on the profile page
		   {
			   //loop through all combos until the usernames match. save the id as variable so it can be used in the user_info table 
		       int idNums = rSet.getInt ("id");
		       String uName = rSet.getString("username");
		       if(uName.equals(userName)){
		    	   userId = idNums;
		    	   break;
		       }
		   }*/
		   call.setInt(1, userId);
		   call.setString(2, userName);
		   call.setString(3, firstName);
		   call.setString(4, lastName);
		   call.setInt(5, age);
		   call.setInt(6, weight);
		   call.setInt(7, height);
		   call.setString(8, gender);
		   call.setInt(9, activityLevel);
		   call.setInt(10, goal);
		   call.setInt(11, calReq);
		   call.execute();
		   	
	}
	
	
	public String getUserInfo(String userName, int userId)throws SQLException{ //method to get the personal info for a user 
		
		CallableStatement call = conn.prepareCall("{call get_user_info(?, ?)}");  //part to execute stored procedure 
		   
		   call.setInt(1, userId); // picks back up at calling stored procedure 
		   call.registerOutParameter(2, Types.VARCHAR);
		   
		   call.execute(); //execute the call to the stored procedure 
		   String output = call.getString(2); // set a string value to the value thats in the set 
		   return output; //return the string 
	}
	
	
	
	public int getUserId(String userName)throws SQLException{ // not sure if i need this method pretty sure i do so that user id can be passed and make other actions easier 
		
		int userId = 0;
		
		 Statement state = conn.createStatement ();     //performing search of the db table login_info for all the id and userName 
		 state.executeQuery ("SELECT id, username FROM login_info");
		 ResultSet rSet = state.getResultSet ();
		   
		   while (rSet.next ()) //to find the id created when account was made, the user name of user  must match the one supplied in the by the user on the profile page
		   {
			   //loop through all combos until the user names match. save the id as variable so it can be used in the user_info table 
		       int idNums = rSet.getInt ("id");
		       String uName = rSet.getString("username");
		       if(uName.equals(userName)){
		    	   userId = idNums;
		    	   break;
		       }
		   }
		
		return userId;
	}
	
	
	public int loginAttempt(String userName, String password)throws SQLException{
		int userId = 0;
	
		CallableStatement call = conn.prepareCall("{ call login(?, ?, ?)}");  //part to execute stored procedure 
		
		call.setString(1, userName); // picks back up at calling stored procedure 
		call.setString(2, password);
		call.registerOutParameter(3, Types.INTEGER);
		   
		call.execute(); //execute the call to the stored procedure  
		userId = call.getInt(3); // set a string value to the value thats in the set 
		 
		
		return userId;
	}
	
	public void updateLoginInfo(int userId, String userName, String password)throws SQLException, UserNameException{
		
		
		CallableStatement call = conn.prepareCall("{ call update_login_info(?, ?, ?, ?)}");  //part to execute stored procedure 
		
		call.setInt(1, userId); // picks back up at calling stored procedure 
		call.setString(2, userName);
		call.setString(3, password);
		call.registerOutParameter(4, Types.VARCHAR);
		   
		call.execute(); //execute the call to the stored procedure  
		String success = call.getString(4); // set a string value to the value thats in the set 
		
		if(success.equals("Username already exists.")){
			throw new UserNameException();
		}
	}
	
	public void updateFood(int userId, String foodName, String calories, String action)throws SQLException, UserNameException, NoNameException{
		
		CallableStatement call = conn.prepareCall("{ call update_food(?, ?, ?, ?, ?)}");  //part to execute stored procedure 
		
		call.setInt(1, userId); // picks back up at calling stored procedure 
		call.setString(2, foodName);
		call.setString(3, calories);
		call.setString(4, action);
		call.registerOutParameter(5, Types.VARCHAR);
		   
		call.execute(); //execute the call to the stored procedure  
		String success = call.getString(5); // set a string value to the value thats in the set 
		String already = foodName + " already exists.";
		String none = foodName + " is not a stored food.";
		if(success.equals(already)){
			throw new UserNameException();
		}else if(success.equals(none)){
			throw new NoNameException();
		}
	}
	
	public void updateWorkout(int userId, String workoutName, String calories, String action)throws SQLException, UserNameException, NoNameException{
		
		CallableStatement call = conn.prepareCall("{ call update_workout(?, ?, ?, ?, ?)}");  //part to execute stored procedure 
		
		call.setInt(1, userId); // picks back up at calling stored procedure 
		call.setString(2, workoutName);
		call.setString(3, calories);
		call.setString(4, action);
		call.registerOutParameter(5, Types.VARCHAR);
		   
		call.execute(); //execute the call to the stored procedure  
		String success = call.getString(5); // set a string value to the value thats in the set 
		String already = workoutName + " already exists.";
		String none = workoutName + " is not a stored food.";
		if(success.equals(already)){
			throw new UserNameException();
		}else if(success.equals(none)){
			throw new NoNameException();
		}
		
	}
	
	public String getFoods(int userID)throws SQLException{
		
		CallableStatement call = conn.prepareCall("{ call get_foods(?, ?)}");  //part to execute stored procedure 
		
		call.setInt(1, userID); // picks back up at calling stored procedure 
		call.registerOutParameter(2, Types.VARCHAR);
		   
		call.execute(); //execute the call to the stored procedure  
		String foods = call.getString(2); // set a string value to the value thats in the set 
		
		return foods;
	}
	
	public String getWorkouts(int userId)throws SQLException{
		
		CallableStatement call = conn.prepareCall("{ call get_workouts(?, ?)}");  //part to execute stored procedure 
		
		call.setInt(1, userId); // picks back up at calling stored procedure 
		call.registerOutParameter(2, Types.VARCHAR);
		   
		call.execute(); //execute the call to the stored procedure  
		String workouts = call.getString(2); // set a string value to the value thats in the set 
		
		return workouts;
	}
	
	public String getUserHistory(int userId)throws SQLException{
		
		CallableStatement call = conn.prepareCall("{ call get_user_history(?, ?)}");  //part to execute stored procedure 
		
		call.setInt(1, userId); // picks back up at calling stored procedure 
		call.registerOutParameter(2, Types.VARCHAR);
		   
		call.execute(); //execute the call to the stored procedure  
		String userHistory = call.getString(2); // set a string value to the value thats in the set 
		
		return userHistory;
	}
	
	public String getDailyHistory(int userId)throws SQLException{
		
		CallableStatement call = conn.prepareCall("{ call get_daily_history(?, ?)}");  //part to execute stored procedure 
		
		call.setInt(1, userId); // picks back up at calling stored procedure 
		call.registerOutParameter(2, Types.VARCHAR);
		   
		call.execute(); //execute the call to the stored procedure  
		String dailyHistory = call.getString(2); // set a string value to the value thats in the set 
		
		return dailyHistory;
	}
	
	
	public void updateDailyHistory(int userId, int calsTotal, String foods, String workouts)throws SQLException{
		
		CallableStatement call = conn.prepareCall("{ call update_daily_history(?, ?, ?, ?, ?)}");  //part to execute stored procedure 
		
		call.setInt(1, userId); // picks back up at calling stored procedure 
		call.setInt(2, calsTotal);
		call.setString(3, foods);
		call.setString(4, workouts);
		call.registerOutParameter(5, Types.VARCHAR);
		   
		call.execute(); //execute the call to the stored procedure  
	
	}
}
