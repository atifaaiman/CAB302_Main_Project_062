package common;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;


/**
 * The Class MessageBuilderTest.
 */
class MessageBuilderTest {

	/**
	 * Test build (full method).
	 */
	@Test
	final void testBuild() {
		byte[] file = new byte[4];
		List<User> users = new ArrayList<>();
		List<Schedule> schedules = new ArrayList<>();
		List<Billboard> billboards = new ArrayList<>();
		Billboard billboard = new Billboard();
		User user = new User();
		Schedule schedule = new Schedule();
		
		Message msg = MessageBuilder.build("file.txt", file, 
				Message.ADD_BILLBOARD, "user1", "12345", "tty-44", 
				Permission.CREATE_BILLBOARDS, users, 
				user, schedule, billboards, schedules, billboard);
		assertEquals("file.txt", msg.filename());
		assertEquals(file, msg.file());
		assertEquals(Message.ADD_BILLBOARD, msg.command());
		assertEquals("user1", msg.username());
		assertEquals("12345", msg.password());
		assertEquals("tty-44", msg.token());
		assertEquals(Permission.CREATE_BILLBOARDS, msg.permission());
		assertEquals(users, msg.users());
		assertEquals(user, msg.user());
		assertEquals(schedule, msg.schedule());
		assertEquals(billboards, msg.billboards());
		assertEquals(schedules, msg.schedules());
		assertEquals(billboard, msg.billboard());
	}

	/**
	 * Test build filename, file, command (overloading method).
	 */
	@Test
	final void testBuildFilenamFileCommand() {
		byte[] file = new byte[4];
		Message msg = MessageBuilder.build("file.txt", file, 
				Message.ADD_BILLBOARD);
		assertEquals("file.txt", msg.filename());
		assertEquals(file, msg.file());
		assertEquals(Message.ADD_BILLBOARD, msg.command());
	}

	/**
	 * Test build command (overloading method).
	 */
	@Test
	final void testBuildCommand() {
		Message msg = MessageBuilder.build(Message.ADD_BILLBOARD);
		assertEquals(Message.ADD_BILLBOARD, msg.command());
	}

	/**
	 * Test build command, username, password (overloading method).
	 */
	@Test
	final void testBuildCommandUsernamePassword() {
		Message msg = MessageBuilder.build(Message.ADD_BILLBOARD, "user1", "12345");
		assertEquals(Message.ADD_BILLBOARD, msg.command());
		assertEquals("user1", msg.username());
		assertEquals("12345", msg.password());
	}

	/**
	 * Test build command, token (overloading method).
	 */
	@Test
	final void testBuildCommandToken() {
		Message msg = MessageBuilder.build(Message.ADD_BILLBOARD, "tty-44");
		assertEquals("tty-44", msg.token());
		assertEquals(Message.ADD_BILLBOARD, msg.command());
	}

}
