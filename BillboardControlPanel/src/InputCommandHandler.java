import static common.Message.BILLBOARDS;
import static common.Message.FAILED_USERNAME_EXISTS;
import static common.Message.INVALID_CREDENTIALS;
import static common.Message.LOGIN;
import static common.Message.LOGOUT;
import static common.Message.NO_PERMISSION;
import static common.Message.SCHEDULES;
import static common.Message.USERS;
import static common.Message.FAILED_BILLBOARD_EXISTS;

import common.Message;

/**
 * The Class InputCommandHandler that encapsulates
 * handler for all input {@link Message} objects
 * from server.
 * Uses Observer pattern to update {@link Observable}
 * once new input {@link Message} received.
 * Observable in this case is {@link Controller}
 */
public class InputCommandHandler {

	/** The session token. */
	private String sessionToken;

	/** The permission. */
	private String permission;
	
	/** The observer. */
	private Observable observer;

	/**
	 * Processes the command.
	 * Whenever this method is called,
	 * it updates observer ({@link GUI}) by
	 * invoking {@link Observable#update(Message)}
	 * method.
	 * {@link InputCommandHandler#login(Message)} and
	 * {@link InputCommandHandler#logout(Message)} methods
	 * are helper methods and they also call 
	 * {@link Observable#update(Message)} method at the end.
	 *
	 * @param msg the {@link Message} object
	 */
	public void processCommand(Message msg) {

		switch (msg.command()) {

			case LOGIN:
				login(msg);
				break;
			case LOGOUT:
				logout(msg);
				break;
			case INVALID_CREDENTIALS:
			case USERS:
			case NO_PERMISSION:
			case FAILED_USERNAME_EXISTS:
			case FAILED_BILLBOARD_EXISTS:  				// Added by fernando
			case BILLBOARDS:
			case SCHEDULES:
				observer.update(msg);
		}
	}
	
	/**
	 * Performs logout.
	 *
	 * @param msg the {@link Message}
	 */
	private void logout(Message msg) {
		sessionToken = null;
		permission = null;
		observer.update(msg);
	}
	
	/**
	 * Performs login.
	 *
	 * @param msg the {@link Message}
	 */
	private void login(Message msg) {
		
		sessionToken = msg.token();
		permission = msg.permission();
		observer.update(msg);
	}
	
	/**
	 * Adds the observer.
	 *
	 * @param observer the observer
	 */
	public void addObserver(Observable observer) {
		this.observer = observer;
	}

	/**
	 * Gets the session token.
	 *
	 * @return the session token
	 */
	public String getSessionToken() {
		return sessionToken;
	}

	/**
	 * Gets the permission.
	 *
	 * @return the permission
	 */
	public String getPermission() {
		return permission;
	}

}
