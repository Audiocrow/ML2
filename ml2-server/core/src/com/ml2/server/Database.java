package com.ml2.server;
import com.ml2.shared.resources.Constants;
import java.sql.*;

public class Database {
	private static Database instance;
	
	private String connectionString ="jdbc:mysql://localhost:3306/ml2";
	private Connection connection;
	private Statement command;
	private ResultSet data;
	private String query;
	
	private Database() { }
	/** Since Database is a singleton: whenever you need it, do <p>
	 * {@code Database db = Database.getInstance(); db.newAccount(whatever, whatever2);} <p>
	 * to get the only run-time Database
	 */
	public static Database getInstance() {
		if(instance == null) instance = new Database();
		return instance;
	}
	
	public void newAccount(String Name, String Password){
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

