package com.seven.statements.dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.seven.statements.ConnectionManager;
import com.seven.statements.dto.Member;
import com.seven.statements.dto.Message;

public class MessageDao {

	private ConnectionManager manager;

	public MessageDao(ConnectionManager manager) {
		this.manager = manager;
	}

	public Message createMessage(Message message) {
		return null;
	}
	
	public List<Message> search(String searchName, String keyword) {
		return null;
	}
	
	public List<Message> searchByMember(Member member) {
		return null;
	}
	

	public Message findById(int id) {
		return null;
	}
}
