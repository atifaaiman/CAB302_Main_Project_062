import static common.Message.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import common.Billboard;
import common.MessageBuilder;
import common.Schedule;
import common.User;

/**
 * The Class OutputCommandHandler. It's responsible only for the sending
 * requests to the server, nothing more.
 */
public class OutputCommandHandler {

	/** The oubput stream to write to messages. */
	private ObjectOutputStream oos;

	/**
	 * Login request.
	 *
	 * @param username the username
	 * @param password the password
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws IOException              Signals that an I/O exception has occurred.
	 */
	public void login(String username, String password) throws NoSuchAlgorithmException, IOException {
		password = hashPassword(password);
		oos.writeObject(MessageBuilder.build(LOGIN, username, password));
	}

	/**
	 * Logout request.
	 *
	 * @param token the token
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void logout(String token) throws IOException {
		oos.writeObject(
				MessageBuilder.build(null, null, LOGOUT, null, null, token, null,
						null, null, null, null, null, null));
	}

	/**
	 * Adds the user.
	 *
	 * @param user  the user
	 * @param token the token
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws IOException              Signals that an I/O exception has occurred.
	 */
	public void addUser(User user, String token) throws NoSuchAlgorithmException, IOException {
		user.setPassword(hashPassword(user.getPassword()));
		oos.writeObject(MessageBuilder.build(null, null, ADD_USER, null, null, token,
				null, null, user, null, null,
				null, null));
	}

	/**
	 * Adds the schedule.
	 * @param sched the sched
	 * @param token the token
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws IOException              Signals that an I/O exception has occurred.
	 */
	public void addSchedule(Schedule sched, String token) throws NoSuchAlgorithmException, IOException {
		oos.writeObject(MessageBuilder.build(null, null, ADD_SCHEDULE, null, null, token,
				null, null, null, sched, null,
				null, null));
	}

	/**
	 * delete the schedule.
	 * @param sched the sched
	 * @param token the token
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws IOException              Signals that an I/O exception has occurred.
	 */
	public void daddSchedule(Schedule sched, String token) throws NoSuchAlgorithmException, IOException {
		oos.writeObject(MessageBuilder.build(null, null, DELETE_SCHEDULE, null, null, token,
				null, null, null, sched, null,
				null, null));
	}

	/**
	 * Adds the billboard.
	 * @param billboard the billboard
	 * @param token     the token
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void addBillboard(Billboard billboard, String token) throws IOException {
		oos.writeObject(MessageBuilder.build(null, null, ADD_BILLBOARD, null, null, token,
				null, null, null, null, null,
				null, billboard));
	}

	/**
	 * Sends command to the server to edit user. First, it checks whether old
	 * password and current password are equal and if so, hashing is not needed,
	 * otherwise hash because this is new password and needs to be updated.
	 *
	 * @param user  {@link common.User} to be updated
	 * @param token unique session token
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws IOException              Signals that an I/O exception has occurred.
	 */
	public void editUser(User user, String token) throws NoSuchAlgorithmException, IOException {
		if (!user.getOldPassword().equals(user.getPassword())) {
			user.setPassword(hashPassword(user.getPassword()));
		}
		oos.writeObject(MessageBuilder.build(null, null, UPDATE_USER, null, null, token,
				null, null, user, null, null,
				null, null));
	}

	/**
	 * Deletes user.
	 *
	 * @param user  the user
	 * @param token the token
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws IOException              Signals that an I/O exception has occurred.
	 */
	public void deleteUser(User user, String token) throws NoSuchAlgorithmException, IOException {
		oos.writeObject(MessageBuilder.build(null, null, DELETE_USER, null, null, token,
				null, null, user, null, null,
				null, null));
	}

	/**
	 * Deletes schedule.
	 * @param schedule  the user
	 * @param token the token
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws IOException              Signals that an I/O exception has occurred.
	 */
	public void deleteSchedule(Schedule schedule, String token) throws NoSuchAlgorithmException, IOException {
		oos.writeObject(MessageBuilder.build(null, null, DELETE_SCHEDULE, null, null, token,
				null, null, null, schedule, null,
				null, null));
	}

	/**
	 * Deletes billboard.
	 *
	 * @param blbrd the blbrd
	 * @param token the token
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void deleteBillboard(Billboard blbrd, String token) throws IOException {

		oos.writeObject(MessageBuilder.build(null, null, DELETE_BILLBOARD, null, null,
				token, null, null, null, null,
				null, null, blbrd));
	}

	/**
	 * Edits the billboard.
	 *
	 * @param blrd  the billboard
	 * @param token the token
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void editBillboard(Billboard blrd, String token) throws IOException {
		oos.writeObject(MessageBuilder.build(null, null, EDIT_BILLBOARD, null, null,
				token, null, null, null, null,
				null, null, blrd));
	}

	/**
	 * All users request.
	 *
	 * @param token the token
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void allUsers(String token) throws IOException {
		oos.writeObject(
				MessageBuilder.build(null, null, USERS, null, null, token, null,
						null, null, null, null, null, null));
	}

	/**
	 * All schedules request.
	 *
	 * @param token the token
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void allSchedules(String token) throws IOException {
		oos.writeObject(MessageBuilder.build(null, null, SCHEDULES, null, null, token,
				null, null, null, null, null,
				null, null));
	}

	/**
	 * All billboards request.
	 *
	 * @param token the token
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void allBillboards(String token) throws IOException {
		oos.writeObject(MessageBuilder.build(null, null, BILLBOARDS, null, null, token,
				null, null, null, null, null,
				null, null));
	}

	/**
	 * Hashes password.
	 *
	 * @param password the password
	 * @return the string
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 */
	public String hashPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] hash = md.digest(password.getBytes());
		return String.format("%032X", new BigInteger(1, hash));
	}

	/**
	 * Sets the output stream.
	 *
	 * @param oos the new output stream
	 */
	public void setOos(ObjectOutputStream oos) {
		this.oos = oos;
	}

}
