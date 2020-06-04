
import static common.Message.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import common.Billboard;
import common.Message;
import common.MessageBuilder;
import common.Permission;
import common.Schedule;
import common.User;

/**
 * Listens the client requests. Every time the client sends {@link Message}},
 * this message is accepted here in run() method.
 */
public class ClientListener implements Runnable {

	/** The client socket. */
	private Socket clientSocket;

	/**
	 * Stores token as a key and username as a value. Once session expires, record
	 * removed.
	 */
	private Map<String, String> tokenUserMap = new HashMap<>();

	/**
	 * Stores token as a key and associated timer as a value. Needs to stop timer if
	 * the user logs out earlier than 24 hours.
	 */
	private Map<String, Timer> tokenTimerMap = new HashMap<>();

	/** The token permission map.
	 * Stores token as a key and User object as a value.Once session expires, record
	 * 	 * removed.
	 * */
	//private Map<String, String> tokenPermissionMap = new HashMap<>(); // Old code
	private Map<String, User> tokenPermissionMap = new HashMap<>(); 	// New code

	/** The Constant SESSION_PERIOD. */
	private final static long SESSION_PERIOD = 24 * 60 * 60 * 1000;

	/** The name of the buillboard sento to the clients. */
	private String billboardName = null;

	/**
	 * Instantiates a new client listener.
	 *
	 * @param clientSocket the client socket
	 */
	public ClientListener(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	/**
	 * Runs listener in a separate thread.
	 */
	@Override
	public void run() {

		try (ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream())) {
			try (ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream())) {
				while (true) {
					Message msg = (Message) ois.readObject();
					processCommand(msg, oos);
				}
			} catch (ClassNotFoundException e) {
				System.err.println(e);
			} catch (SQLException e) {
				System.err.println(e);
			} catch (NoSuchAlgorithmException e) {
				System.err.println(e);
			}
		} catch (IOException e) {
			System.out.println("Client disconnected");
		} finally {
			if (clientSocket != null) {
				try {
					clientSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Processes the command.
	 *
	 * @param msg the {@link Message}
	 * @param oos the output stream to write responses
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws SQLException             the SQL exception
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 */
	public void processCommand(Message msg, ObjectOutputStream oos)
			throws IOException, SQLException, NoSuchAlgorithmException {
		switch (msg.command()) {
			case GET_BILLBOARD:
				System.out.println("Get billboards request");
				sendBillboard(msg, oos);
				break;
			case LOGIN:
				System.out.println("Login request");
				login(msg, oos);
				break;
			case USERS:
				System.out.println("Users request");
				users(msg, oos);
				break;
			case UPDATE_USER:
				System.out.println("Update User request");
				updateUser(msg, oos);
				break;
			case ADD_USER:
				System.out.println("Add User request");
				addUser(msg, oos);
				break;
			case LOGOUT:
				System.out.println("Logout request");
				logout(msg, oos);
				break;
			case DELETE_USER:
				System.out.println("Delete User request");
				deleteUser(msg, oos);
				break;
			case ADD_SCHEDULE:
				System.out.println("Add Schedule request");
				addSchedule(msg, oos);
				break;
			case BILLBOARDS:
				System.out.println("Billboards request");
				billboards(msg, oos);
				break;
			case SCHEDULES:
				System.out.println("Schedules request");
				schedules(msg, oos);
				break;
			case ADD_BILLBOARD:
				System.out.println("Add Billboard request");
				addBillboard(msg, oos);
				break;
			case DELETE_BILLBOARD:
				System.out.println("Delete Billboard request");
				deleteBillboard(msg, oos);
				break;
			case EDIT_BILLBOARD:
				System.out.println("Edit Billboard request");
				editBillboard(msg, oos);
				break;
			case TEST_COMMAND:
				System.out.println("Test Command request");
				oos.writeObject("Test success");
			case DELETE_SCHEDULE:
				System.out.println("Delet schedule request");
				deleteSchedule(msg,oos);


		}
	}


	// -------------------------------------------- BILLBOARDS ----------------------------------------------------------
	/**
	 * Adds the billboard. First, checks permission, then adds author (username) who
	 * creates a billboard, finally connects to the database to add a new entry to
	 * 'billboard' table.
	 *
	 * @param msg the {@link Message}}
	 * @param oos the output stream to write responses
	 * @throws IOException  Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
	private void addBillboard(Message msg, ObjectOutputStream oos) throws IOException, SQLException {

		if (msg.token() != null && tokenPermissionMap.get(msg.token()).getCreate_billboards().equals(true)) {
			String username = tokenUserMap.get(msg.token());
			Billboard billboard = msg.billboard();
			billboard.setUsername(username);

			// If billboard name does not exist
			if( !DB.doesBillboardNameExist(billboard.getName())){
				DB.addBillboard(billboard);
			}
			else{
				oos.writeObject(MessageBuilder.build(null, null, FAILED_BILLBOARD_EXISTS, null, null,
						msg.token(), null, null, null, null, null, null, null));
			}


		} else {
			oos.writeObject(MessageBuilder.build(null, null, NO_PERMISSION, null, null,
					msg.token(), null, null, null, null, null, null, null));
		}
	}

	/**
	 * Billboards is returned by request to update table with them. All users will
	 * be able to access a list of all billboards on the system and preview their
	 * contents, so there is no checking permission.
	 * @param msg the {@link Message}}
	 * @param oos the output stream to write responses
	 * @throws SQLException the SQL exception
	 * @throws IOException  Signals that an I/O exception has occurred.
	 */
	private void billboards(Message msg, ObjectOutputStream oos) throws SQLException, IOException {
		List<Billboard> billboards = DB.getBillboards();
		oos.writeObject(MessageBuilder.build(null, null, BILLBOARDS, null, null, msg.token(),
				null, null, null, null, billboards, null, null));
	}

	/**
	 * Edits the billboard.
	 *
	 * @param msg the {@link Message}}
	 * @param oos the output stream to write responses
	 * @throws IOException  Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
	private void editBillboard(Message msg, ObjectOutputStream oos) throws IOException, SQLException {
		if (msg.token() != null) {
			//if (tokenPermissionMap.get(msg.token()).equals(Permission.CREATE_BILLBOARDS)) {  // Old code
			if (tokenPermissionMap.get(msg.token()).getCreate_billboards().equals(true)) {     // New code
				if (msg.billboard().getUsername().equals(tokenUserMap.get(msg.token()))) {
					if (!DB.isScheduled(msg.billboard().getName())) {
						DB.updateBillboard(msg.billboard());
						return;
					}
				}

				/*
				 * Users with the “Edit All Billboards” permission will be able to edit or
				 * delete any billboard on the system, including billboards that are currently
				 * scheduled.
				 */
				//} else if (tokenPermissionMap.get(msg.token()).equals(Permission.EDIT_ALL_BILLBOARDS)) {      // Old code
			}
			if (tokenPermissionMap.get(msg.token()).getEdit_all_billboards().equals(true)) {         		// New code
				// System.out.println("Calling DB.updateBillboard...");
				DB.updateBillboard(msg.billboard());
				System.out.println("DB.updateBillboard called!");
				return;
			}
		}

		oos.writeObject(MessageBuilder.build(null, null, NO_PERMISSION, null, null, msg.token(),
				null, null, null, null, null, null, null));
	}

	/**
	 * Users with the “Create Billboards” permission will also be able to edit or
	 * delete any billboards they created, as long as those billboards are not
	 * presently scheduled.
	 * @param msg the {@link Message}}
	 * @param oos the output stream to write responses
	 * @throws IOException  Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
	private void deleteBillboard(Message msg, ObjectOutputStream oos) throws IOException, SQLException {

		if (msg.token() != null) {
			//if (tokenPermissionMap.get(msg.token()).equals(Permission.CREATE_BILLBOARDS)) {   // Old code
			if (tokenPermissionMap.get(msg.token()).getCreate_billboards().equals(true)) {		// New code
				if (msg.billboard().getUsername().equals(tokenUserMap.get(msg.token()))) {
					if (!DB.isScheduled(msg.billboard().getName())) {
						DB.deleteBillboard(msg.billboard().getName());
						return;
					}
				}

				/*
				 * Users with the “Edit All Billboards” permission will be able to edit or
				 * delete any billboard on the system, including billboards that are currently
				 * scheduled.
				 */
				//} else if (tokenPermissionMap.get(msg.token()).equals(Permission.EDIT_ALL_BILLBOARDS)) {  // Old code
			} else if (tokenPermissionMap.get(msg.token()).getEdit_all_billboards().equals(true)) {		// New code

				DB.deleteBillboard(msg.billboard().getName());
				return;
			}
		}

		oos.writeObject(MessageBuilder.build(null, null, NO_PERMISSION, null, null, msg.token(),
				null, null, null, null, null, null, null));
	}

	/**
	 * Sends billboard xml file to the viewer.
	 * @param msg the {@link Message}}
	 * @param oos the output stream to write responses
	 * @throws IOException  Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
	private void sendBillboard(Message msg, ObjectOutputStream oos) throws IOException, SQLException {
		Billboard b = msg.billboard();
		String bName = DB.getCurrentBillboardName();
		b.setName(bName);

		byte[] xml = DB.getXML(b.getName());
		oos.writeObject(MessageBuilder.build("billboard.xml", xml, msg.command(), null, null,
				null, null, null, null, null, null, null, b));
	}


	// -------------------------------------------- SCHEDULE  ----------------------------------------------------------

	/**
	 * Adds the schedule. First, check permission, then connect to the database to
	 * add new record to 'schedule' table. Otherwise responds with 'no permission'.
	 * @param msg the {@link Message}}
	 * @param oos the output stream to write responses
	 * @throws IOException  Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
	private void addSchedule(Message msg, ObjectOutputStream oos) throws IOException, SQLException {
		if (msg.token() != null && tokenPermissionMap.get(msg.token()).getSchedule_billboards().equals(true)) {			// New code
			DB.addSchedule(msg.schedule());
		} else {
			oos.writeObject(MessageBuilder.build(null, null, NO_PERMISSION, null,
					null, msg.token(), null, null, null,
					null, null, null, null));
		}
	}

	/**
	 * Delete the schedule. First, check permission, then connect to the database to
	 * delete new record from 'schedule' table. Otherwise responds with 'no permission'.
	 * @param msg the {@link Message}}
	 * @param oos the output stream to write responses
	 * @throws IOException  Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
	private void deleteSchedule(Message msg, ObjectOutputStream oos) throws IOException, SQLException {
		if (msg.token() != null && tokenPermissionMap.get(msg.token()).getSchedule_billboards().equals(true)) {			// New code
			DB.deleteSchedule(msg.schedule().getId());
		} else {
			oos.writeObject(MessageBuilder.build(null, null, NO_PERMISSION, null,
					null, msg.token(), null, null, null,
					null, null, null, null));
		}
	}

	/**
	 * Schedules to be sent by request. Here the 'viewer' is special token that
	 * allows receiving schedules for client to display viewers (see BillboardViewer
	 * project). Control Panel client must have
	 * {@link Permission#SCHEDULE_BILLBOARDS} permission to access schedules.
	 * @param msg the {@link Message}}
	 * @param oos the output stream to write responses
	 * @throws SQLException the SQL exception
	 * @throws IOException  Signals that an I/O exception has occurred.
	 */
	private void schedules(Message msg, ObjectOutputStream oos) throws SQLException, IOException {
		if (msg.token().equals("viewer")																					// New code
				|| msg.token() != null && tokenPermissionMap.get(msg.token()).getSchedule_billboards().equals(true)) {		// New code
			List<Schedule> schedules = DB.getSchedules();
			oos.writeObject(MessageBuilder.build(null, null, SCHEDULES, null, null,
					msg.token(), null, null, null, null, null, schedules, null));
		} else {
			oos.writeObject(MessageBuilder.build(null, null, NO_PERMISSION, null, null,
					msg.token(), null, null, null, null, null, null, null));
		}
	}

	// --------------------------------------------   USER    ----------------------------------------------------------

	/**
	 * Adds the user. First, checks permission to add a new user, then hashes
	 * password with salt (salt is generated randomly, but stored in a separate
	 * table 'salt' in database).
	 *
	 * @param msg the {@link Message}}
	 * @param oos the output stream to write responses
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 */
	private void addUser(Message msg, ObjectOutputStream oos) throws IOException, NoSuchAlgorithmException {
		//if (msg.token() != null && tokenPermissionMap.get(msg.token()).equals("Edit Users")) {   						// Old code
		if (msg.token() != null && tokenPermissionMap.get(msg.token()).getEdit_users().equals(true)) {					// New code

			try {
				User user = msg.user();

				// Generate salt.
				byte salt[] = new byte[16];
				SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
				sr.nextBytes(salt);
				String saltStr = String.format("%032X", new BigInteger(1, salt));
				user.setPassword(user.getPassword() + saltStr); // Add salt.

				// Hash second time.
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] hash = md.digest(user.getPassword().getBytes());
				user.setPassword(String.format("%032X", new BigInteger(1, hash)));

				// First, try to add user (possible case when username already exists).
				DB.addUser(msg.user());

				// If everything is OK, add salt to database in a separate table.
				DB.addSalt(user.getUsername(), saltStr);

			} catch (SQLException exc) {
				oos.writeObject(MessageBuilder.build(null, null, FAILED_USERNAME_EXISTS, null,
						null, msg.token(), null, null, null, null, null,
						null, null));
			}
		} else {
			oos.writeObject(MessageBuilder.build(null, null, NO_PERMISSION, null, null,
					msg.token(), null, null, null, null, null, null,
					null));
		}
	}

	/**
	 * Deletes user. First, checks whether permission allows to delete user, checks
	 * whether the user wants to delete himself (if so, respond with 'no
	 * permission'), finally connects to database to delete user entry from 'user'
	 * table. Note, salt for that user is deleted as well, but in
	 * {@link DB#deleteUser(User)} method.
	 *
	 * @param msg the {@link Message}}
	 * @param oos the output stream to write responses
	 * @throws IOException  Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
	private void deleteUser(Message msg, ObjectOutputStream oos) throws IOException, SQLException {
		//if (msg.token() != null && tokenPermissionMap.get(msg.token()).equals("Edit Users")) {		// Old code
		if (msg.token() != null && tokenPermissionMap.get(msg.token()).getEdit_users().equals(true)) {	// New code

			// Cannot delete himself.
			if (msg.user().getUsername().equals(tokenUserMap.get(msg.token()))) {
				oos.writeObject(MessageBuilder.build(null, null, NO_PERMISSION, null, null,
						msg.token(), null, null, null, null, null, null,
						null));
			} else {
				DB.deleteUser(msg.user());
			}
		} else {
			oos.writeObject(MessageBuilder.build(null, null, NO_PERMISSION, null, null,
					msg.token(), null, null, null,
					null, null, null, null));
		}
	}

	/**
	 * Updates user. Checks whether the user has a permission to update user, then
	 * connects to database to update entry in 'user' table by username. Before the
	 * storing password, it's hashed with salt.
	 *
	 * @param msg the {@link Message}}
	 * @param oos the output stream to write responses
	 * @throws SQLException             the SQL exception
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 */
	private void updateUser(Message msg, ObjectOutputStream oos)
			throws SQLException, IOException, NoSuchAlgorithmException {
		User user = msg.user();

		if ((msg.token() != null && tokenPermissionMap.get(msg.token()).getEdit_users().equals(true))  ||
				((msg.token() != null && tokenPermissionMap.get(msg.token()).getUsername().equals(msg.user().getUsername())))
		) {
			/*
			 * Administrators will not be able to remove their own “Edit Users” permission-
			 * however they can remove the “Edit Users” permission from other
			 * administrators.
			 */
			if (msg.user().getUsername().equals(tokenUserMap.get(msg.token()))) {
				if (!msg.user().getEdit_users().equals(tokenPermissionMap.get(msg.token()).getEdit_users())) {
					oos.writeObject(MessageBuilder.build(null, null, NO_PERMISSION,
							null, null, msg.token(), null, null,
							null, null, null, null, null));
					return;
				}
			}
			if (!user.getOldPassword().equals(user.getPassword())) {
				String salt = DB.getSalt(msg.user().getUsername()); // Obtain salt from database.
				user.setPassword(user.getPassword() + salt); 		// Add salt.

				// Hash second time.
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] hash = md.digest(user.getPassword().getBytes());
				user.setPassword(String.format("%032X", new BigInteger(1, hash)));
			}
			DB.updateUser(msg.user());
		} else {
			oos.writeObject(MessageBuilder.build(null, null, NO_PERMISSION, null, null,
					msg.token(), null, null, null, null, null, null,
					null));
		}
	}

	/**
	 * Checks whether permission is "Edit Users" and if so, sends list of
	 * {@link common.User} objects back to the client. Otherwise "No Permission"
	 * message.
	 *
	 * @param msg {@link common.Message}
	 * @param oos output stream to write object to be send
	 * @throws IOException  Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
	private void users(Message msg, ObjectOutputStream oos) throws IOException, SQLException {

		//if (msg.token() != null && tokenPermissionMap.get(msg.token())..equals("Edit Users")) {   						// Old code
		if (msg.token() != null){ //&& tokenPermissionMap.get(msg.token()).getEdit_users().equals(true)) {  					// New user
			oos.writeObject(MessageBuilder.build(null, null, USERS, null, null, msg.token(),
					null, DB.getUsers(), null, null, null, null, null));
		} else {
			if (msg.token() != null) {
				oos.writeObject(MessageBuilder.build(null, null, NO_PERMISSION, null, null,
						msg.token(), null, null, null, null, null, null,
						null));
			}
		}
	}

	// ------------------------------------------------  LOGIN  --------------------------------------------------------

	/**
	 * Logins the user. Checks credentials in database, the retrieves permission and
	 * generates token to be send back to the user.
	 *
	 * @param msg the {@link Message}}
	 * @param oos the output stream to write responses
	 * @throws SQLException             the SQL exception
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws IOException              Signals that an I/O exception has occurred.
	 */
	private void login(Message msg, ObjectOutputStream oos) throws SQLException, NoSuchAlgorithmException, IOException {

		String username = msg.username();
		String password = msg.password(); // Hashed password from Control Panel.

		// First, make a query to database to retrieve salt for this username.
		String salt = DB.getSalt(username);

		if (salt != null) {

			// Add salt to password.
			password += salt;

			// Hash once more.
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(password.getBytes());

			// Update password (this value should be stored in 'user' table if
			// login and password are correct).
			password = String.format("%032X", new BigInteger(1, hash));

			// Now we need to retrieve password record from database and
			// compare it with current password.
			String actualPassword = DB.getPassword(username);

			if (actualPassword != null) {
				if (password.equals(actualPassword)) {

					// Everything is OK, passwords match.
					// Next, generate unique session token.
					String token = UUID.randomUUID().toString();
					tokenUserMap.put(token, username);

					// Next, it needs to set timer to expire session after 24 hours.
					// Use separate thread (background process).
					new Thread(() -> {
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {

							@Override
							public void run() {

								// Expire session, cancel timer after 24 hours.
								tokenUserMap.remove(token);
								tokenTimerMap.remove(token);
								tokenPermissionMap.remove(token);
								timer.cancel();
							}

						}, SESSION_PERIOD);

						tokenTimerMap.put(token, timer);

					}).start();

					// Finally, retrieve permission from database.
					//String permission = DB.getPermission(username);   // Old code
					User user = DB.getPermission(username);			    // New code

					//tokenPermissionMap.put(token, permission);		// Old code
					tokenPermissionMap.put(token, user);  				// New code

					oos.writeObject(MessageBuilder.build(null, null, LOGIN, msg.username(), null, token, user.getPermission(),  // Old code: permission
							null, user, null, null, null, null));
					return;
				}
			}
		}
		oos.writeObject(MessageBuilder.build(INVALID_CREDENTIALS));
	}

	// -----------------------------------------------  LOGOUT  --------------------------------------------------------

	/**
	 * Logouts the user. Removes tokens from all maps (see
	 * {@link ClientListener#tokenUserMap}, etc.) to make sure no one can access
	 * data with this token anymore.
	 *
	 * @param msg the {@link Message}}
	 * @param oos the output stream to write responses
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void logout(Message msg, ObjectOutputStream oos) throws IOException {
		if (msg.token() != null && tokenUserMap.containsKey(msg.token())) {
			tokenUserMap.remove(msg.token());
			tokenTimerMap.remove(msg.token()).cancel();
			tokenPermissionMap.remove(msg.token());
			oos.writeObject(MessageBuilder.build(null, null, LOGOUT, null, null,
					msg.token(), null, null, null, null, null, null, null));
		} else {
			oos.writeObject(MessageBuilder.build(null, null, NO_PERMISSION, null, null,
					msg.token(), null, null, null, null, null, null, null));
		}
	}



}
