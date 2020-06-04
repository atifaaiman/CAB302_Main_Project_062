
import common.Billboard;
import common.Schedule;
import common.User;

import javax.sql.rowset.serial.SerialBlob;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * The Class DB. Encapsulates database MariaDB connection.
 */
public class DB {

	/** The Constant DATABASE_PROPERTIES_FILENAME. */
	public final static String DATABASE_PROPERTIES_FILENAME = "db.props";

	/** The Constant DATABASE_SCRIPT_FILENAME. */
	public final static String DATABASE_SCRIPT_FILENAME = "db.sql";

	/** The props. */
	private static Properties props = new Properties();

	/** The props. */
	private static String currentBillboardName ;

	public static String getCurrentBillboardName(){
		return executeGetSchedule();
	}

	// ---------------------------------------- DATABASE CONNECTION ---------------------------------------------------

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 * @throws SQLException the SQL exception
	 */
	private static Connection getConnection() throws SQLException {
		//System.out.println("DB.getConnection: Test");  // Debug added by Fernando

		return DriverManager.getConnection(props.getProperty("jdbc.url") + props.getProperty("jdbc.schema"),
				props.getProperty("jdbc.username"), props.getProperty("jdbc.password"));

	}

	// -------------------------------------------- DATABASE CHECK ----------------------------------------------------

	/**
	 * Inits the connection. Here it tries to run database script to create tables
	 * if not exists.
	 *
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException           Signals that an I/O exception has occurred.
	 * @throws SQLException          the SQL exception
	 * @throws URISyntaxException    the URI syntax exception
	 */
	public static void init() throws FileNotFoundException, IOException, SQLException, URISyntaxException {

		try (InputStream fileStream = new FileInputStream(DATABASE_PROPERTIES_FILENAME)) {

			props.load(fileStream);

			try (Connection conn = DriverManager.getConnection(props.getProperty("jdbc.url"),
					props.getProperty("jdbc.username"), props.getProperty("jdbc.password"));
				 Statement stmt = conn.createStatement()) {
				List<String> lines = Files
						.readAllLines(Paths.get(DB.class.getResource(DATABASE_SCRIPT_FILENAME).toURI()));
				for (String line : lines) {
					stmt.executeUpdate(line);
				}
			}
		}
	}

	// ----------------------------------------------- BILLBOARD ------------------------------------------------------
	/**
	 * Adds the billboard.
	 *
	 * @param billboard the billboard
	 * @throws SQLException the SQL exception
	 */
	public static void addBillboard(Billboard billboard) throws SQLException {
		//System.out.println(billboard);
		LocalDateTime localDate = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
		String currentDate =  dtf.format(localDate).toString();
		//System.out.println(currentDate);

		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement("insert into billboard value(?,?,?,?)")) {
			stmt.setString(1, billboard.getName());
			stmt.setBlob(2, new SerialBlob(billboard.getXmlData()));
			stmt.setString(3, billboard.getUsername());
			stmt.setString(4, currentDate);
			stmt.executeUpdate();
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Delete billboard.
	 *
	 * @param blrdName the billboard name
	 * @throws SQLException the SQL exception
	 */
	public static void deleteBillboard(String blrdName) throws SQLException {
		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement("delete from billboard where " + "name_billboard=?")) {
			stmt.setString(1, blrdName);
			stmt.executeUpdate();
		}
	}

	/**
	 * Gets the billboards.
	 *
	 * @return the billboards
	 * @throws SQLException the SQL exception
	 */
	public static List<Billboard> getBillboards() throws SQLException {
		List<Billboard> billboards = new ArrayList<>();
		try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
			try (ResultSet rs = stmt.executeQuery("select * from billboard")) {
				while (rs.next()) {
					Billboard billboard = new Billboard();
					billboard.setName(rs.getString(1));
					billboard.setXmlData(rs.getBytes(2));
					billboard.setUsername(rs.getString(3));
					billboard.setDataTime(rs.getString(4));
					billboards.add(billboard);
				}
			}
		}
		return billboards;
	}

	/**
	 * Update billboard.
	 *
	 * @param billboard the billboard
	 * @throws SQLException the SQL exception
	 */
	public static void updateBillboard(Billboard billboard) throws SQLException {

		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn
					 .prepareStatement("update billboard set xml_data=? where " + "name_billboard=?")) {
			stmt.setBlob(1, new SerialBlob(billboard.getXmlData()));
			stmt.setString(2, billboard.getName());
			stmt.executeUpdate();
		}
	}

	// Added by Fernando  27/05
	/**
	 * Checks if billboard name exist.
	 * @param billboardName the billboard name
	 * @return true, if is scheduled
	 * @throws SQLException the SQL exception
	 */
	public static boolean doesBillboardNameExist(String billboardName) throws SQLException {

		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement("SELECT * FROM billboard WHERE name_billboard=?")) {
			stmt.setString(1, billboardName);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return true;
				}
			}
		}
		return false;
	}


	// -------------------------------------------------- PASSWORD ----------------------------------------------------

	/**
	 * Gets the password.
	 *
	 * @param username the username
	 * @return the password
	 * @throws SQLException the SQL exception
	 */
	public static String getPassword(String username) throws SQLException {
		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement("select password from user where username=?")) {
			stmt.setString(1, username);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getString(1);
				}
			}
		}
		return null;
	}

	// ------------------------------------------------ PERMISSION ----------------------------------------------------

	/**
	 * Gets the permission.
	 * @param username the username
	 * @return the permission
	 * @throws SQLException the SQL exception
	 */
	public static User getPermission(String username) throws SQLException {   /// Old code: public static String getPermission(String username) throws SQLException {
		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement("select * from user where username=?")) {  //Old code: "select permission from user where username=?"
			stmt.setString(1, username);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					String userName = rs.getString(1);										// New code
					String password = rs.getString(2);										// New code
					String permission = rs.getString(3);									// Old code: String permission = rs.getString(1);
					Boolean administrator = rs.getBoolean(4);								// New code
					Boolean createBillboards = rs.getBoolean(5);							// New code
					Boolean editAllBillboards = rs.getBoolean(6);							// New code
					Boolean scheduleBillboards = rs.getBoolean(7);							// New code
					Boolean editUser = rs.getBoolean(8);									// New code
					User user = new User(userName,password,permission,administrator,createBillboards,	// New code
							editAllBillboards,scheduleBillboards,editUser);								// New code
					return user;																		// Old code: return permission;
				}
			}
		}
		return null;
	}

	// ------------------------------------------------ SALT ----------------------------------------------------------

	/**
	 * Adds the salt.
	 *
	 * @param username the username
	 * @param salt     the salt
	 * @throws SQLException the SQL exception
	 */
	public static void addSalt(String username, String salt) throws SQLException {

		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement("insert into salt value(?,?)")) {
			stmt.setString(1, username);
			stmt.setString(2, salt);
			stmt.executeUpdate();
		}
	}

	/**
	 * Gets the salt.
	 *
	 * @param username the username
	 * @return the salt
	 * @throws SQLException the SQL exception
	 */
	public static String getSalt(String username) throws SQLException {
		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement("select salt from salt where username=?")) {
			stmt.setString(1, username);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getString(1);
				}
			}
		}
		return null;
	}

	// ----------------------------------------------- SCHEDULE -------------------------------------------------------

	/**
	 * Adds the schedule.
	 * @param sched the schedule
	 * @throws SQLException the SQL exception
	 */
	public static void addSchedule(Schedule sched) throws SQLException {
		//System.out.println("Schedule name_billboard: " + sched.getIdBillboard());
		//System.out.println("Schedule date_time_start: " + sched.getDateTimeStart());
		//System.out.println("Schedule date_time_finish: " + sched.getDateTimeFinish());
		//System.out.println("Schedule created_by: " + sched.getScheduleCreatedBy());


		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement(
					 //"insert into schedule (date_time, duration, `repeat`, name_billboard) value(?,?,?,?)")) {		// Old code
					 "insert into schedule (name_billboard, date_time_start," + "date_time_finish, schedule_create_by) value(?,?,?,?)")) {									// New code
			stmt.setString   (1, sched.getIdBillboard());
			stmt.setString   (2, sched.getDateTimeStart());												// New code
			stmt.setString   (3, sched.getDateTimeFinish());												// New code
			stmt.setString   (4, sched.getScheduleCreatedBy());											// New code
			stmt.executeUpdate();
		}
	}

	/**
	 * Delete schedule.
	 * @param idSchedule the id schedule
	 * @throws SQLException the SQL exception
	 */
	public static void deleteSchedule(int idSchedule) throws SQLException {

		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement("delete from schedule where id_schedule=?")) {
			stmt.setInt(1, idSchedule);
			stmt.executeUpdate();
		}
	}

	/**
	 * Gets the schedules.
	 *
	 * @return the schedules
	 * @throws SQLException the SQL exception
	 */
	public static List<Schedule> getSchedules() throws SQLException {
		List<Schedule> schedules = new ArrayList<>();
		try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
			try (ResultSet rs = stmt.executeQuery("select * from schedule order by date_time_start")) {
				while (rs.next()) {
					Schedule schedule = new Schedule();
					schedule.setId(rs.getInt(1));
					schedule.setIdBillboard(rs.getString(2));
					schedule.setDateTimeStart(rs.getString(3));
					schedule.setDateTimeFinish(rs.getString(4));
					schedule.setScheduleCreatedBy(rs.getString(5));
					schedule.setScheduleCreateDate(rs.getString(6));
					schedules.add(schedule);														 					// Added by Fernando
				}
			}
		}
		return schedules;
	}

	/**
	 * Checks if is scheduled.
	 *
	 * @param billboardName the billboard name
	 * @return true, if is scheduled
	 * @throws SQLException the SQL exception
	 */
	public static boolean isScheduled(String billboardName) throws SQLException {

		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement("SELECT * FROM billboard b INNER JOIN schedule s ON "
					 + "b.name_billboard=s.name_billboard and b.name_billboard=?")) {
			stmt.setString(1, billboardName);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Update schedule.
	 *
	 * @param sched the schedule
	 * @throws SQLException the SQL exception
	 */
	public static void updateSchedule(Schedule sched) throws SQLException {

		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement(
					 "update schedule set date_time=?, duration=?, repeat=? " + "where id_schedule=?")) {
			stmt.setTimestamp(1, Timestamp.valueOf(sched.getDateTime()));
			stmt.setInt(2, sched.getDuration());
			stmt.setString(3, sched.getRepeat());
			stmt.setInt(4, sched.getId());
			stmt.executeUpdate();
		}
	}

	// -------------------------------------------------- USER --------------------------------------------------------

	/**
	 * Adds the user.
	 *
	 * @param user the user
	 * @throws SQLException the SQL exception
	 */
	public static void addUser(User user) throws SQLException {
		//System.out.println("Method addUser: DB Class");
		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement("insert into user value(?,?,?,?,?,?,?,?)")) {
			stmt.setString (1, user.getUsername());
			stmt.setString (2, user.getPassword());
			stmt.setString (3, user.getPermission());
			stmt.setBoolean(4, user.getAdministrator());  	    // Fernando added
			stmt.setBoolean(5, user.getCreate_billboards());  	// Fernando added
			stmt.setBoolean(6, user.getEdit_all_billboards());    // Fernando added
			stmt.setBoolean(7, user.getSchedule_billboards());    // Fernando added
			stmt.setBoolean(8, user.getEdit_users());  	        // Fernando added
			stmt.executeUpdate();
		}
	}

	/**
	 * Deletes user.
	 * Note, first it needs to delete salt (foreign key constraint)
	 * from 'salt' table, and
	 * only after that delete user.
	 *
	 * @param user the user
	 * @throws SQLException the SQL exception
	 */
	public static void deleteUser(User user) throws SQLException {

		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement("delete from salt where " + "username=?")) {
			stmt.setString(1, user.getUsername());
			stmt.executeUpdate();
		}

		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement("delete from user where " + "username=?")) {
			stmt.setString(1, user.getUsername());
			stmt.executeUpdate();
		}
	}

	/**
	 * @author Fernando Barbosa Silva
	 * Gets the users.
	 * @return the users
	 * @throws SQLException the SQL exception0
	 */
	public static List<User> getUsers() throws SQLException {
		List<User> users = new ArrayList<>();
		try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
			try (ResultSet rs = stmt.executeQuery("select * from user")) {
				while (rs.next()) {
					users.add(new User(rs.getString(1), rs.getString(2), rs.getString(3),
							rs.getBoolean(4),rs.getBoolean(5), rs.getBoolean(6),
							rs.getBoolean(7), rs.getBoolean(8)));
				}
			}
		}
		return users;
	}

	/**
	 * @author Fernando Barbosa Silva
	 * Update user.
	 * @param user the user
	 * @throws SQLException the SQL exception
	 */
	public static void updateUser(User user) throws SQLException {
		if(user.getPassword().isEmpty()){
			//System.out.println("Password is empty");
			try (Connection conn = getConnection();
				 PreparedStatement stmt = conn
						 .prepareStatement("update user set permission=?, administrator=?, " +
								 "create_billboards=?, edit_all_billboards=?, schedule_billboards=?, edit_users=?" +
								 " where " + "username=?")) {

				stmt.setString (1, user.getPermission());
				stmt.setBoolean(2, user.getAdministrator());
				stmt.setBoolean(3, user.getCreate_billboards());
				stmt.setBoolean(4, user.getEdit_all_billboards());
				stmt.setBoolean(5, user.getSchedule_billboards());
				stmt.setString (7, user.getUsername());
				stmt.executeUpdate();
			}
		}else{
			try (Connection conn = getConnection();
				 PreparedStatement stmt = conn
						 .prepareStatement("update user set password=?, permission=?, administrator=?, " +
								 "create_billboards=?, edit_all_billboards=?, schedule_billboards=?, edit_users=?" +
								 " where " + "username=?")) {
				stmt.setString (1, user.getPassword());
				stmt.setString (2, user.getPermission());
				stmt.setBoolean(3, user.getAdministrator());
				stmt.setBoolean(4, user.getCreate_billboards());
				stmt.setBoolean(5, user.getEdit_all_billboards());
				stmt.setBoolean(6, user.getSchedule_billboards());
				stmt.setBoolean(7, user.getEdit_users());
				stmt.setString (8, user.getUsername());
				stmt.executeUpdate();
			}
		}

	}

	// --------------------------------------------------- XML --------------------------------------------------------

	/**
	 * @author Fernando Barbosa Silva
	 * Gets the xml if there is some billboard scheduled.
	 * @param nameBillboard the name billboard
	 * @return the xml
	 * @throws SQLException the SQL exception
	 */
	public static byte[] getXML(String nameBillboard) throws SQLException {

		String billboard = executeGetSchedule();

		//System.out.println("BillboardName :" + billboard);

		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn
					 .prepareStatement("select xml_data from billboard where name_billboard=?")) {
			stmt.setString(1, billboard);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getBytes(1);
				}
			}
		}
		return null;
	}

	/**
	 * @author Fernando Barbosa Silva
	 * Get scheduled billboardName from the database.
	 * @return an string with the billboardName, returns an empty object if
	 * no scheduled Billboard was found in the database.
	 * @throws SQLException if there is an error in the query or database connection.
	 */
	public static String executeGetSchedule() {
		// Get the current date and time in the format "2020-05-21 19:00:00":
		LocalDateTime localDate = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
		String currentDate =  dtf.format(localDate).toString();
		//System.out.println(currentDate);

		// Query
		String query = "SELECT * FROM schedule WHERE schedule_create_date=(" +
				"SELECT MAX(schedule_create_date) FROM schedule WHERE date_time_start <='"+currentDate+"' AND '"+currentDate+"'<= date_time_finish)";

		String billboardName = "";

		try{
			// get connection
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			// Check each row of the table
			while (resultSet.next()) {
				billboardName= resultSet.getString(2);
				//System.out.println("Billboard name: " + billboardName);
			}
			return billboardName;
		}
		catch (SQLException e){
			System.out.println("DB.executeGetSchedule(): " + e.getMessage());
		}
		//System.out.println("Billboard name:" + billboardName);
		currentBillboardName = billboardName;
		return billboardName;
	}



}