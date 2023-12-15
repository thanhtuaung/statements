package com.seven.statements.dao;

import com.seven.statements.ConnectionManager;
import com.seven.statements.dto.Member;

public class MemberDao {

	private ConnectionManager manager;

	public MemberDao(ConnectionManager manager) {
		this.manager = manager;
	}

	public int createMember(Member input) {
		return 0;
	}
	
	public Member findByEmail(String email) {
		return null;
	}
	
	public int updatePassword(String email, String oldPass, String newPass) {
		return 0;
	}
	
}
