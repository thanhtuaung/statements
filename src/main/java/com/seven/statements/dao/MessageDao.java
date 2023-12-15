package com.seven.statements.dao;

import java.sql.SQLException;
import java.sql.Statement;

import com.seven.statements.ConnectionManager;
import com.seven.statements.dto.Message;

public class MessageDao {

	private ConnectionManager manager;

	public MessageDao(ConnectionManager manager) {
		this.manager = manager;
	}

	public Message createMessage(Message message) {

		String query = "insert into message (title, message) values ('%s', '%s')".formatted(message.title(),
				message.message());

		try (var conn = manager.getConnection(); var stmt = conn.createStatement()) {

			stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

			var resultSet = stmt.getGeneratedKeys();
			if (resultSet.next()) {
				return message.copyWithId(resultSet.getInt(1));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public Message findMessageById(int id) {

		String query = "select * from message where id = %d".formatted(id);

		try (var conn = manager.getConnection(); var stmt = conn.createStatement()) {

			var resultSet = stmt.executeQuery(query);

			if (resultSet.next()) {
				return new Message(resultSet.getInt("id"), resultSet.getString("title"),
						resultSet.getString("message"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
