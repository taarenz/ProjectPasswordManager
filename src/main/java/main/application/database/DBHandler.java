package main.application.database;

import java.sql.*;


public class DBHandler extends Configs {

	static Connection dbConnection;

	public static Connection getConnection()
	{
		String connectionString = "jdbc:mysql://" + Configs.dbHost + ":" + Configs.dbPort + "/" + Configs.dbName;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			dbConnection = DriverManager.getConnection(connectionString,Configs.dbUser,Configs.dbPass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dbConnection;
	}

}
