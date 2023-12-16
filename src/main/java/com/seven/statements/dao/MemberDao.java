package com.seven.statements.dao;

import java.sql.Date;
import java.sql.SQLException;

import com.seven.statements.ConnectionManager;
import com.seven.statements.MessageDBException;
import com.seven.statements.dto.Member;
import com.seven.statements.dto.Member.Role;
import com.seven.statements.utils.StringUtils;

public class MemberDao {

	private ConnectionManager manager;

	public MemberDao(ConnectionManager manager) {
		this.manager = manager;
	}

	public int createMember(Member input) {
		
		if(input == null) {
			throw new IllegalArgumentException();
		}
		
		if(StringUtils.isEmpty(input.email())) {
			throw new MessageDBException("Email must not be empty");
		}
		
		if(StringUtils.isEmpty(input.name())) {
			throw new MessageDBException("Name must not be empty");
		}
		
		if(StringUtils.isEmpty(input.password())) {
			throw new MessageDBException("Password must not be empty");
		}
		
		if(input.dob() == null) {
			throw new MessageDBException("Date of Birthday must not be empty");
		}
		
		String query = "insert into member values (?, ?, ?, ?, ?)";
		
		try(var conn = manager.getConnection();
				var stmt = conn.prepareStatement(query)) {
			
			stmt.setString(1, input.email());
			stmt.setString(2, input.name());
			stmt.setString(3, input.password());
			stmt.setDate(4, Date.valueOf(input.dob()));
			stmt.setString(5, input.role().name());
			
			return stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new MessageDBException("Email has already been used!");
		}
		
	}
	
	public Member findByEmail(String email) {
		
		if(email == null) {
			throw new IllegalArgumentException();
		}
		
		try(var conn = manager.getConnection();
				var stmt = conn.prepareStatement("""
						select * from member where email = ?
						""")) {
			
			stmt.setString(1, email);
			var result = stmt.executeQuery();
			
			if(result.next()) {
				return new Member(
						result.getString("email"),
						result.getString("name"),
						result.getString("password"),
						result.getDate("dob").toLocalDate(),
						Role.valueOf(result.getString("role"))
						);
			} else {
				throw new MessageDBException("Member with this email not found");
			}
			
		} catch (SQLException e) {
			throw new MessageDBException("Member with this email not found");
		}
	}
	
	public int updatePassword(String email, String oldPass, String newPass) {
		return 0;
	}
	
}
