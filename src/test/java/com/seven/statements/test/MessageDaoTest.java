package com.seven.statements.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.seven.statements.ConnectionManager;
import com.seven.statements.dao.MessageDao;
import com.seven.statements.dto.Message;
import com.seven.statements.test.dbhelper.TestDBHelper;


@TestMethodOrder(OrderAnnotation.class)
class MessageDaoTest {
	Message message = new Message("Than", "Hello Evotags");
	static MessageDao dao;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		TestDBHelper.truncateTable();
		dao = new MessageDao(ConnectionManager.getInstance());
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	@Order(1)
	void testCreateMessage() {		
		Message result = dao.createMessage(message);		
		assertEquals(1, result.id());
	}
	
	@Test
	@Order(2)
	void testFindMessageByIdFound() {
		Message result = dao.findMessageById(1);
		assertNotNull(result);
		assertEquals(message.title(), result.title());
		assertEquals(message.message(), result.message());
	}
	
	@Test
	@Order(3)
	void testFindMessageByIdNotFound() {
		Message result = dao.findMessageById(2);
		assertNull(result);
	}
	

}
