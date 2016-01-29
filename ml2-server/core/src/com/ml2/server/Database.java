package com.ml2.server;
import com.ml2.shared.resources.Constants;
import java.sql.*;

public class Database {
	private static String connectionString ="jdbc:mysql://localhost:3306/ml2";
	private static Connection connection;
	private static Statement command;
	private static ResultSet data;
	private static String query;
	
	
	public static void newAccount(String Name, String Password){
		
		try {
			connection = DriverManager.getConnection(connectionString, Constants.DB_USERNAME, Constants.DB_PASSWORD);
			command = connection.createStatement();
		    query = String.format("INSERT INTO account(name,password) VALUES('%s','%s');", Name, Password);
			command.execute(query);
		} 
		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{ System.out.println("Account Created."); }

	}
}

