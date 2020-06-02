package common;

import java.io.Serializable;

/**
 * @author Fernando Barbosa Silva
 * The Class User. Encapsulates user object to be transferred over
 * network and to be stored into database.
 */
public class User implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The username. */
	private String username;
	
	/** The password. */
	private String password;
	
	/** The permission. */
	private String permission;
	
	/** The old password. */
	private String oldPassword;

	// Fernando's changes Start ----------------------------------

	/** The old password. */
	private Boolean administrator  ;

	/** The old password. */
	private Boolean create_billboards;

	/** The old password. */
	private Boolean edit_all_billboards;

	/** The old password. */
	private Boolean  schedule_billboards ;

	/** The old password. */
	private Boolean   edit_users  ;

	// Fernando's changes Finish ------------------------------------




	/**
	 * Instantiates a new user.
	 * @param username the username
	 * @param password the password
	 * @param permission the permission
	 * @param administrator
	 * @param create_billboards
	 * @param edit_all_billboards
	 * @param schedule_billboards
	 * @param edit_users
	 */
	public User(String username, String password, String permission, Boolean administrator,
				Boolean create_billboards, Boolean edit_all_billboards, Boolean schedule_billboards, Boolean edit_users) {
		this.username = username;
		this.password = password;
		this.permission = permission;

		// Fernando Changes start -----------------------------------
		this.administrator = administrator;
		this.create_billboards = create_billboards;
		this.edit_all_billboards = edit_all_billboards;
		this.schedule_billboards = schedule_billboards;
		this.edit_users = edit_users;
		// Fernando Changes finish -----------------------------------
	}

	/**
	 * Instantiates a new user.
	 */
	public User() {

	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the permission.
	 *
	 * @return the permission
	 */
	public String getPermission() {
		return permission;
	}

	/**
	 * Sets the permission.
	 *
	 * @param permission the new permission
	 */
	public void setPermission(String permission) {
		this.permission = permission;
	}

	/**
	 * Gets the serialversionuid.
	 *
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Gets the old password.
	 *
	 * @return the old password
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * Sets the old password.
	 *
	 * @param oldPassword the new old password
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}


	// Fernando's changes start----------------------------------------------

	/**
	 * @author Fernando Barbosa Silva.
	 * Gets the administrator permission.
	 * @return the administrator permission "true" or "false".
	 */
	public Boolean getAdministrator() {
		return administrator;
	}

	/**
	 *  @author Fernando Barbosa Silva.
	 * Sets the administrator permission.
	 * @param administrator permission "true" or "false"
	 */
	public void setAdministrator(Boolean administrator) {
		this.administrator= administrator;
	}

	/**
	 * @author Fernando Barbosa Silva.
	 * Gets the Create_billboards permission.
	 * @return the administrator permission "true" or "false"
	 */
	public Boolean getCreate_billboards() {
		return create_billboards;
	}

	/**
	 *  @author Fernando Barbosa Silva.
	 * Sets the Create_billboards permission.
	 * @param create_billboards permission "true" or "false"
	 */
	public void setCreate_billboards(Boolean create_billboards) {
		this.create_billboards = create_billboards;
	}

	/**
	 * @author Fernando Barbosa Silva.
	 * Gets the Edit_all_billboards permission.
	 * @return the dit_all_billboards permission "true" or "false".
	 */
	public Boolean getEdit_all_billboards() {
		return edit_all_billboards;
	}

	/**
	 *  @author Fernando Barbosa Silva.
	 * Sets the Edit_all_billboards permission.
	 * @param edit_all_billboards permission "true" or "false".
	 */
	public void setEdit_all_billboards(Boolean edit_all_billboards) {
		this.edit_all_billboards = edit_all_billboards;
	}

	/**
	 * @author Fernando Barbosa Silva.
	 * Gets the Schedule_billboards permission.
	 * @return the schedule_billboards permission "true" or "false".
	 */
	public Boolean getSchedule_billboards() {
		return schedule_billboards;
	}

	/**
	 * @author Fernando Barbosa Silva.
	 * Sets the Schedule_billboards permission.
	 * @param schedule_billboards permission "true" or "false".
	 */
	public void setSchedule_billboards(Boolean schedule_billboards) {
		this.schedule_billboards = schedule_billboards;
	}

	/**
	 * @author Fernando Barbosa Silva.
	 * Gets the Edit_users permission.
	 * @return the edit_users permission "true" or "false".
	 */
	public Boolean getEdit_users() {
		return edit_users;
	}

	/**
	 *  @author Fernando Barbosa Silva.
	 * Sets the Edit_users permission.
	 * @param edit_users permission "true" or "false".
	 */
	public void setEdit_users(Boolean edit_users) {
		this.edit_users = edit_users;
	}
	// Fernando's Changes finish----------------------------------------------

}
