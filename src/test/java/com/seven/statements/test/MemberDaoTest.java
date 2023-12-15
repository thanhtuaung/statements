package com.seven.statements.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.seven.statements.ConnectionManager;
import com.seven.statements.MessageDBException;
import com.seven.statements.dao.MemberDao;
import com.seven.statements.dto.Member;
import com.seven.statements.dto.Member.Role;
import com.seven.statements.test.dbhelper.TestDBHelper;


@TestMethodOrder(OrderAnnotation.class)
class MemberDaoTest {
	static MemberDao dao;
	static Member input;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		TestDBHelper.truncateTable("message", "member");
		dao = new MemberDao(ConnectionManager.getInstance());
		input = new Member("test@gmail.com", "Tester", "tester", LocalDate.of(2001, 02, 21), Role.Admin);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	@Order(1)
	void testCreateMemberOk() {
		int result = dao.createMember(input);
		assertEquals(1, result);
	}
	
	@Test
	@Order(2)
	void testCreateMemberDuplicate() {
		var exception = assertThrows(MessageDBException.class, () -> dao.createMember(input));
		assertEquals("Email has already been used!", exception.getMessage());
	}
	
	@Test
	@Order(3)
	void testCreateMemberNull() {
		assertThrows(IllegalArgumentException.class, () -> dao.createMember(null));
	}
	
	@Test
	@Order(4)
	void testCreateMemberEmailNull() {
		var data = new Member(null, "Tester", "tester", LocalDate.of(2001, 02, 21), Role.Admin);
		var exception = assertThrows(MessageDBException.class, () -> dao.createMember(data));
		assertEquals("Email must not be empty", exception.getMessage());
		
		var emptyValue = new Member("", "Tester", "tester", LocalDate.of(2001, 02, 21), Role.Admin);
		exception = assertThrows(MessageDBException.class, () -> dao.createMember(emptyValue));
		assertEquals("Email must not be empty", exception.getMessage());
	}
	
	@Test
	@Order(5)
	void testCreateMemberNameNull() {
		var data = new Member("tester@gmail.com", null, "tester", LocalDate.of(2001, 02, 21), Role.Admin);
		var exception = assertThrows(MessageDBException.class, () -> dao.createMember(data));
		assertEquals("Name must not be empty", exception.getMessage());
		
		var emptyValue = new Member("tester@gmail.com", "", "tester", LocalDate.of(2001, 02, 21), Role.Admin);
		exception = assertThrows(MessageDBException.class, () -> dao.createMember(emptyValue));
		assertEquals("Name must not be empty", exception.getMessage());
	}
	
	@Test
	@Order(6)
	void testCreateMemberPassNull() {
		var data = new Member("tester@gmail.com", "Tester", null, LocalDate.of(2001, 02, 21), Role.Admin);
		var exception = assertThrows(MessageDBException.class, () -> dao.createMember(data));
		assertEquals("Password must not be empty", exception.getMessage());
		
		var emptyValue = new Member("tester@gmail.com", "Tester", "", LocalDate.of(2001, 02, 21), Role.Admin);
		exception = assertThrows(MessageDBException.class, () -> dao.createMember(emptyValue));
		assertEquals("Password must not be empty", exception.getMessage());
	}

	@Test
	@Order(7)
	void testCreateMemberDobNull() {
		var data = new Member("tester@gmail.com", "Tester", "tester", null, Role.Admin);
		var exception = assertThrows(MessageDBException.class, () -> dao.createMember(data));
		assertEquals("Date of Birthday must not be empty", exception.getMessage());
	}


	@Test
	@Order(8)
	void testFindByEmailFound() {
		Member result = dao.findByEmail(input.email());
		assertEquals(input, result);
	}
	
	@Test
	@Order(9)
	void testFindByEmailNotFound() {
		var exception = assertThrows(MessageDBException.class, () -> dao.findByEmail("%s1".formatted(input.email())));
		assertEquals("Member with this email not found", exception.getMessage());
	
	}
	
	@Test
	@Order(10)
	void testFindByEmailNull() {
		assertThrows(IllegalArgumentException.class, () -> dao.findByEmail(null));
	}

	@Test
	void testUpdatePassword() {
		var newPass = "updatedPassword";
	    var result = dao.updatePassword(input.email(), input.password() , newPass);
	    assertEquals(1, result);
	}
	
	@Test
	void testUpdatePasswordEmailNotFound() {
		var exception = assertThrows(MessageDBException.class, () -> dao.updatePassword("%s1".formatted(input.email()), "updatedPassword", "newpass"));
		assertEquals("Please check email!", exception.getMessage());
	}
	
	@Test
	void testUpdatePasswordEmailNull() {
		var exception = assertThrows(MessageDBException.class, () -> dao.updatePassword(null, "updatedPassword", "newpass"));
		assertEquals("Email must not be empty!", exception.getMessage());
		
		exception = assertThrows(MessageDBException.class, () -> dao.updatePassword("", "updatedPassword", "newpass"));
		assertEquals("Email must not be null!", exception.getMessage());
	}
	
	@Test
	void testUpdatePasswordOldPassNull() {
		var exception = assertThrows(MessageDBException.class, () -> dao.updatePassword(input.email(), null, "newpass"));
		assertEquals("Old password must not be empty!", exception.getMessage());
		
		exception = assertThrows(MessageDBException.class, () -> dao.updatePassword(input.email(), "", "newpass"));
		assertEquals("Old password must not be null!", exception.getMessage());
	}
	
	@Test
	void testUpdatePasswordNotMatch() {
		var exception = assertThrows(MessageDBException.class, () -> dao.updatePassword(input.email(), "wrongPass", "newpass"));
		assertEquals("Check old password!", exception.getMessage());
	}
	
	@Test
	void testUpdatePasswordNewPassNull() {
		var exception = assertThrows(MessageDBException.class, () -> dao.updatePassword(input.email(), "updatedPassword", null));
		assertEquals("New password must not be empty!", exception.getMessage());
		
		exception = assertThrows(MessageDBException.class, () -> dao.updatePassword(input.email(), "updatedPassword", ""));
		assertEquals("New password must not be null!", exception.getMessage());
	}
	
	@Test
	void testUpdatePasswordSame() {
		var exception = assertThrows(MessageDBException.class, () -> dao.updatePassword(input.email(), "updatedPassword", "updatedPassword"));
		assertEquals("Old and new passwords are the same!", exception.getMessage());
	}

}
