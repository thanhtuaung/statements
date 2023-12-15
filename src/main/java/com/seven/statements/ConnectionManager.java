package com.seven.statements;

import java.sql.*;

public interface ConnectionManager {

	public Connection getConnection() throws SQLException;
	
	String URL = "jdbc:mysql://localhost:3306/chat";
	String USER = "root";
	String PASSWORD = "101101";
	
	public static ConnectionManager getInstance() {
		return () -> DriverManager.getConnection(URL, USER, PASSWORD);
	}
	
}
