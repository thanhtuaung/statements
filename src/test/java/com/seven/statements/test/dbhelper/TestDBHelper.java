package com.seven.statements.test.dbhelper;

import java.sql.SQLException;

import com.seven.statements.ConnectionManager;

public class TestDBHelper {
	
	public static void truncateTable(String ...tables ) {
		
		try(var con = ConnectionManager.getInstance().getConnection();
				var stmt = con.createStatement()) {
			
			stmt.execute("foreign_key_checks = 0");
			
			for(var table : tables) {
				stmt.execute("truncate table %s".formatted(table));
			}
			
			stmt.execute("foreign_key_checks = 1");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
