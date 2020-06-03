package common;

import java.io.Serializable;
import java.util.List;

/**
 * The Interface Message. Defines a behavior of transferable object through the
 * network. It contains only constants (server protocol) and methods to get
 * required data once received.
 */
public interface Message extends Serializable {

	/** The Constant GET_BILLBOARD. */
	public static final int GET_BILLBOARD = 1;

	/** The Constant LOGIN. */
	public static final int LOGIN = 2;

	/** The Constant INVALID_CREDENTIALS. */
	public static final int INVALID_CREDENTIALS = 3;

	/** The Constant USERS. */
	public static final int USERS = 4;

	/** The Constant NO_PERMISSION. */
	public static final int NO_PERMISSION = 5;

	/** The Constant UPDATE_USER. */
	public static final int UPDATE_USER = 6;

	/** The Constant DELETE_USER. */
	public static final int DELETE_USER = 7;

	/** The Constant ADD_USER. */
	public static final int ADD_USER = 8;

	/** The Constant FAILED_USERNAME_EXISTS. */
	public static final int FAILED_USERNAME_EXISTS = 9;

	/** The Constant LOGOUT. */
	public static final int LOGOUT = 10;

	/** The Constant ADD_SCHEDULE. */
	public static final int ADD_SCHEDULE = 11;

	/** The Constant BILLBOARDS. */
	public static final int BILLBOARDS = 12;

	/** The Constant SCHEDULES. */
	public static final int SCHEDULES = 13;

	/** The Constant ADD_BILLBOARD. */
	public static final int ADD_BILLBOARD = 14;

	/** The Constant DELETE_BILLBOARD. */
	public static final int DELETE_BILLBOARD = 15;

	/** The Constant EDIT_BILLBOARD. */
	public static final int EDIT_BILLBOARD = 16;
	
	/** The Constant TEST_COMMAND. 
	 * Used for testing purpose.*/
	public static final int TEST_COMMAND = 17;

	/** The Constant BILLBOARD_EXISTS */
	public static final int FAILED_BILLBOARD_EXISTS = 18;  // 9 FOR TEST BUT MUT BE 18

	/** The Constant DELETE_SCHEDULE. */
	public static final int DELETE_SCHEDULE = 19;


	/**
	 * Command to (or from) the server. For example, if it is LOGIN, then server
	 * tries to login the user and if success, it sends {@link Message} with this
	 * command back to the sender along with the unique token (see
	 * {@link Message#token()}).
	 *
	 * @return the command constant
	 */
	public int command();

	/**
	 * File to specify xml data for billboard to be displayed.
	 *
	 * @return the byte[]
	 */
	public byte[] file();

	/**
	 * Filename of xml file.
	 *
	 * @return the string representation of filename
	 */
	public String filename();

	/**
	 * Username when user tries to login.
	 *
	 * @return the string representation of username
	 */
	public String username();

	/**
	 * Password when user tries to login.
	 *
	 * @return the string representation of password
	 */
	public String password();

	/**
	 * Token - unique session identifier between server and client (control panel).
	 *
	 * @return the string representation of token
	 */
	public String token();

	/**
	 * Permission as a string (see {@link Permission}). Server sends permission
	 * after {@link Message#LOGIN} to identify what the user is able to do (edit,
	 * add, delete etc.). To get permission, server refers to database (table
	 * 'user', attribute 'permission').
	 *
	 * @return the string representation of permission
	 */
	public String permission();

	/**
	 * Users objects once requested by the user with {@link Permission#EDIT_USERS}
	 *
	 * @return the list of {@link User} objects
	 */
	public List<User> users();

	/**
	 * User to add/edit/delete {@link User}.
	 *
	 * @return the user
	 */
	public User user();

	/**
	 * Schedule to add new {@link Schedule}
	 *
	 * @return the schedule
	 */
	public Schedule schedule();

	/**
	 * Billboards list to update table on GUI.
	 *
	 * @return the list
	 */
	public List<Billboard> billboards();

	/**
	 * Schedules list to update table on GUI.
	 *
	 * @return the list
	 */
	public List<Schedule> schedules();

	/**
	 * Billboard to add/edit/delete billboard.
	 *
	 * @return the billboard
	 */
	public Billboard billboard();
}
