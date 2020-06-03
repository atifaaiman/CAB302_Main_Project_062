package common;

import java.util.List;

/**
 * The Class MessageBuilder.
 * Builds {@link Message} object
 * to be transferred over the network.
 * Contains overloading methods for 
 * convenience.
 */
public class MessageBuilder {

	/**
	 * Builds the {@link Message}.
	 *
	 * @param filename the filename
	 * @param file the file
	 * @param command the command
	 * @param username the username
	 * @param password the password
	 * @param token the token
	 * @param permission the permission
	 * @param users the users
	 * @param user the user
	 * @param schedule the schedule
	 * @param billboards the billboards
	 * @param schedules the schedules
	 * @param billboard the billboard
	 * @return the message
	 */
	public static Message build(String filename, byte[] file, int command, String username, String password, 
			String token, String permission, List<User> users, User user, Schedule schedule, 
			List<Billboard> billboards, List<Schedule> schedules, Billboard billboard) {
		
		return new Message() {

			private static final long serialVersionUID = 1L;

			@Override
			public String filename() {
				return filename;
			}
			
			@Override
			public byte[] file() {
				return file;
			}
			
			@Override
			public int command() {
				return command;
			}

			@Override
			public String username() {
				return username;
			}

			@Override
			public String password() {
				return password;
			}

			@Override
			public String token() {
				return token;
			}

			@Override
			public String permission() {
				return permission;
			}

			@Override
			public List<User> users() {
				return users;
			}

			@Override
			public User user() {
				return user;
			}

			@Override
			public Schedule schedule() {
				return schedule;
			}

			@Override
			public List<Billboard> billboards() {
				return billboards;
			}

			@Override
			public List<Schedule> schedules() {
				return schedules;
			}

			@Override
			public Billboard billboard() {
				return billboard;
			}
		};
	}
	
	/**
	 * Builds the {@link Message}.
	 * @param filename the filename
	 * @param file the file
	 * @param command the command
	 * @return the message
	 */
	public static Message build(String filename, byte[] file, int command) {
		return build(filename, file, command, null, null, null, null, null, null, null, null, null, null);
	}
	
	/**
	 * Builds the {@link Message}.
	 * @param command the command
	 * @return the message
	 */

	public static Message build(int command) {
		return build(null, null, command, null, null, null, null, null, null, null, null, null, null);
	}
	
	/**
	 * Builds the {@link Message}.
	 * @param command the command
	 * @param username the username
	 * @param password the password
	 * @return the message
	 */
	public static Message build(int command, String username, String password) {
		return build(null, null, command, username, password, null, null, null, null, null, null, null, null);
	}
	
	/**
	 * Builds the {@link Message}.
	 * @param command the command
	 * @param token the token
	 * @return the message
	 */
	public static Message build(int command, String token) {
		return build(null, null, command, null, null, token, null, null, null, null, null, null, null);
	}




}
