package com.seven.statements.dto;

public record Message(int id, String title, String message) {
	
	public Message(String title, String message) {
		this(0, title, message);
	}

	public Message copyWithId(int id) {
		return new Message(id, this.title, this.message);
	}
	
}
