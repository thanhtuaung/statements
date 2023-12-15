package com.seven.statements.test.dbhelper;

import java.sql.SQLException;

import com.seven.statements.ConnectionManager;

public class TestDBHelper {
	
	public static void truncateTable() {
		
		try(var con = ConnectionManager.getInstance().getConnection();
				var stmt = con.createStatement()) {
			
			stmt.executeUpdate("truncate table message");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
