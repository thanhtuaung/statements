package com.seven.statements;

import java.sql.*;

public interface ConnectionManager {

	public Connection getConnection() throws SQLException;
	
	String URL = "jdbc:mysql://localhost:3306/message_db";
	String USER = "lucifer";
	String PASSWORD = "admin";
	
	public static ConnectionManager getInstance() {
		return () -> DriverManager.getConnection(URL, USER, PASSWORD);
	}
	
}
