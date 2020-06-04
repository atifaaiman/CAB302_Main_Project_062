import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.Timer;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import common.Billboard;
import common.Message;
import common.Schedule;
import common.User;

import static common.Message.*;

/**
 * The Class Controller according to MVC pattern. Accepts all user actions, such
 * that click on buttons, select images etc.
 * Also it implements {@link Observable} interface to be registered on
 * updates from {@link InputCommandHandler}. The logic is the following:
 * once input message from server received, {@link InputCommandHandler}
 * catches this message (from {@link ServerListener}) and calls {@link Observable#update(Message)}
 * method inside {@link InputCommandHandler#processCommand(Message)}. After that
 * {@link Controller#update(Message)} is triggered and accepts update command, and
 * according to the accepted command, updates {@link GUI}.
 */
public class Controller implements Observable {

	/** The input command handler. */
	private static InputCommandHandler inputCommandHandler;

	/** The output command handler. */
	private static OutputCommandHandler outputCommandHandler;

	/** The GUI reference (or View). */
	private GUI gui;

	/** The timer update users. */
	private Timer timerUpdateUsers;

	/** The timer update billboards. */
	private Timer timerUpdateBillboards;

	/** The timer update schedules. */
	private Timer timerUpdateSchedules;

	/** The row selected by the user in the User Panel. */
	private int rowSelectedUserPanel = -1;

	/** The row selected by the user in the User Panel. */
	private int rowSelectedBillboardPanel = -1;

	/** The row selected by the user in the User Panel. */
	private int rowSelectedSchedulePanel = -1;
	private boolean wasAddClicked = false;

	/** Controle the action of add billboards from import XML button. */
	private boolean importXML = false;

	/** Store username from user */
	public static String userName;

	/**
	 * Instantiates a new controller.
	 *
	 * @param inputCommandHandler  the input command handler
	 * @param outputCommandHandler the output command handler
	 * @param gui                  the gui
	 */
	public Controller(InputCommandHandler inputCommandHandler, OutputCommandHandler outputCommandHandler, GUI gui) {
		this.inputCommandHandler = inputCommandHandler;
		this.outputCommandHandler = outputCommandHandler;
		this.gui = gui;
	}

	/**
	 * Inits the controller.
	 */
	public void init() {
		System.out.println("Init()");
		addListeners();
		//scheduleTimerUpdate();
	}

	/**
	 * Adds the listeners.
	 */
	private void addListeners() {
		// Home Panel
		gui.getHomePanel().getBtnLogout().addActionListener(e -> logout());
		gui.getHomePanel().getBtnBillboards().addActionListener(e-> {
			gui.showBillboards();
		});
		gui.getHomePanel().getBtnUsers().addActionListener(e-> {
			gui.showUsers();
		});
		gui.getHomePanel().getBtnSchedules().addActionListener(e-> {
			gui.showSchedules();
		});


		// Login Logout
		gui.getLoginPanel().getBtnLogin().addActionListener(e -> login());
		gui.getSchedulesPanel().getBtnLogout().addActionListener(e -> logout());
		gui.getLoginPanel().getBtnClose().addActionListener(e -> closeLogin() );

		// Schedule
		gui.getSchedulesPanel().getBtnHome().addActionListener(e-> gui.showHome());
		gui.getSchedulesPanel().getBtnAddSchedule().addActionListener(e -> {
			updateScheduleList();
			addSchedule();
		});
		gui.getSchedulesPanel().getBtnDeleteSchedule().addActionListener(e-> deleteSchedule(rowSelectedSchedulePanel));
		gui.getSchedulesPanel().getTblAllSchedules().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				rowSelectedSchedulePanel = gui.getSchedulesPanel().getTblAllSchedules().getSelectedRow();
				gui.getSchedulesPanel().getBtnDeleteSchedule().setEnabled(true);
				//System.out.println("Row number: " + rowSelectedSchedulePanel);
			}
		});
		gui.getSchedulesPanel().getBtnShowSchedule().addActionListener(e-> {
			updateScheduleList();
			gui.getSchedulesPanel().getBtnShowSchedule().setText("Update list");
			gui.getSchedulesPanel().getBtnWeeksSchedules().setEnabled(true);
			gui.getSchedulesPanel().getBtnAddSchedule().setEnabled(true);
		});

		// User Panel
		gui.getUsersPanel().getBtnHome().addActionListener(e-> gui.showHome());
		gui.getUsersPanel().getBtnShowUsers().addActionListener(e -> showUsers());												// Added by Fernando
		gui.getUsersPanel().getBtnDeleteUser().addActionListener(e -> {
			deleteUser(rowSelectedUserPanel);
			gui.getUsersPanel().getBtnDeleteUser().setEnabled(false);
			gui.getUsersPanel().getBtnEditUser().setEnabled(false);
			rowSelectedUserPanel = -1;
			showUsers();

		});
		gui.getUsersPanel().getBtnEditUser().addActionListener(e -> editUser(rowSelectedUserPanel));				    		// Added by Fernando
		gui.getUsersPanel().getBtnLogout().addActionListener(e -> logout());
		gui.getUsersPanel().getBtnAddUser().addActionListener(e -> addUser());
		gui.getUsersPanel().getTblAllUsers().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				rowSelectedUserPanel = gui.getUsersPanel().getTblAllUsers().getSelectedRow();
				gui.getUsersPanel().getBtnEditUser().setEnabled(true);
				gui.getUsersPanel().getBtnDeleteUser().setEnabled(true);
				//System.out.println("Row number: " + rowSelectedUserPanel);
			}
		});
		// Billboard Panel
		gui.getBillboardPanel().getBtnHome().addActionListener(e-> gui.showHome());
		gui.getBillboardPanel().getBtnShowBillboards().addActionListener(e -> showBillboards());							    // Added by Fernando
		gui.getBillboardPanel().getBtnDeleteBillboard().addActionListener(e -> deleteBillboard(rowSelectedBillboardPanel));		// Added by Fernando
		gui.getBillboardPanel().getBtnEditBillboard().addActionListener(e -> editBillboard(rowSelectedBillboardPanel));			// Added by Fernando
		gui.getBillboardPanel().getBtnPreviewBillboard().addActionListener(e -> {
			preview(rowSelectedBillboardPanel);
			rowSelectedBillboardPanel = -1;
			showBillboards();
		});
		gui.getBillboardPanel().getTblAllBillboards().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				rowSelectedBillboardPanel= gui.getBillboardPanel().getTblAllBillboards().getSelectedRow();
				gui.getBillboardPanel().getBtnEditBillboard().setEnabled(true);		// Test
				gui.getBillboardPanel().getBtnDeleteBillboard().setEnabled(true);	// Test
				gui.getBillboardPanel().getBtnPreviewBillboard().setEnabled(true);	// Test
				gui.getBillboardPanel().getBtnExportXml().setEnabled(true);			// Test
			}
		});
		gui.getBillboardPanel().getBtnLogout().addActionListener(e -> logout());
		gui.getBillboardPanel().getBtnAddBillboard().addActionListener(e -> addBillboard());
		gui.getBillboardPanel().getBtnInfoColour().addActionListener(e -> gui.getBillboardPanel().addInfoColour());
		gui.getBillboardPanel().getBtnMsgColour().addActionListener(e -> gui.getBillboardPanel().addMsgColour());
		gui.getBillboardPanel().getBtnBackground().addActionListener(e -> gui.getBillboardPanel().addBackground());
		gui.getBillboardPanel().getBtnPreview().addActionListener(e -> {
			try {
				gui.getBillboardPanel().preview();
			} catch (Exception e1) {
				GUI.displayError(e1.getMessage());
			}
		});
		gui.getBillboardPanel().getJrbBase64().addActionListener(e -> {
			gui.getBillboardPanel().getTfPicURL().setVisible(false);
			gui.getBillboardPanel().getLblSelectImage().setVisible(true);
		});
		gui.getBillboardPanel().getJrbURL().addActionListener(e -> {
			gui.getBillboardPanel().getLblSelectImage().setVisible(false);
			gui.getBillboardPanel().getTfPicURL().setVisible(true);
		});
		gui.getBillboardPanel().getBtnAddImage().addActionListener(e -> {
				try {
					gui.getBillboardPanel().selectImage();
				} catch (IOException e2) {
					GUI.displayError(e2.getMessage());
				}
		});
		gui.getBillboardPanel().getBtnImportXml().addActionListener(e-> {
			try {
				importXML = true;
				addBillboard();
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
			}
		});
		gui.getBillboardPanel().getBtnExportXml().addActionListener(e -> {
			try {
				gui.getBillboardPanel().exportXML(rowSelectedBillboardPanel);
			} catch (Exception e2) {
				GUI.displayError(("Export XML Failed: " + e2.getMessage()));
			}

		});
		inputCommandHandler.addObserver(this);
	}

	/**
	 * Previews the billboard view.
	 * @param row the row
	 */
	private void preview(int row) {
		if(row == -1){
			GUI.displayError("Please select a billboard.");
		}
		else {
			try {
				gui.getBillboardPanel().preview(row);
				rowSelectedBillboardPanel = -1;
			} catch (ParserConfigurationException | SAXException | IOException e) {
				rowSelectedBillboardPanel = -1;
				GUI.displayError(e.getMessage());
			}
		}
	}

	/**
	 * Edits the billboard by selected row (cell)
	 * @param row the row the user selected in table
	 */
	private void editBillboard(int row) {
		if(row == -1){
			GUI.displayError("Please select a billboard.");
		}
		else {
			try {
				Billboard billboard = gui.getBillboardPanel().editBillboard(row);
				if (billboard != null) {
					outputCommandHandler.editBillboard(billboard, inputCommandHandler.getSessionToken());
					// Update Billboard Panel
					try {
						outputCommandHandler.allBillboards(inputCommandHandler.getSessionToken());
						rowSelectedBillboardPanel = -1;
					} catch (Exception exc) {
						rowSelectedBillboardPanel = -1;
						GUI.displayError(exc.getMessage());
					}
				}
			} catch (Exception e) {
				rowSelectedBillboardPanel = -1;
				GUI.displayError("SERVER IS NOT AVAILABLE.\nPlease contact the system administrator.");
			}
		}
	}

	/**
	 * Deletes billboard by selected row (cell)
	 * @param row the row the user selected in table
	 */
	private void deleteBillboard(int row) {
		if(row == -1){
			GUI.displayError("Please select a billboard.");
		}
		else {
			Billboard blbrd = gui.getBillboardPanel().deleteBillboard(row);
			try {
				outputCommandHandler.deleteBillboard(blbrd, inputCommandHandler.getSessionToken());
				// Update Billboard Panel
				try {
					outputCommandHandler.allBillboards(inputCommandHandler.getSessionToken());
					rowSelectedBillboardPanel = -1;
				} catch (Exception exc) {
					rowSelectedBillboardPanel = -1;
					GUI.displayError("SERVER IS NOT AVAILABLE.\nPlease contact the system administrator.");
				}
			} catch (IOException e) {
				rowSelectedBillboardPanel = -1;
				GUI.displayError("SERVER IS NOT AVAILABLE.\nPlease contact the system administrator.");
			}
		}
	}

	/**
	 * Adds the billboard.
	 */
	private void addBillboard() {
		try {
			Billboard billboard = null;
			if (importXML) {
				billboard = gui.getBillboardPanel().importXML();
			}
			else {
				billboard = gui.getBillboardPanel().addBuilboard();
			}
			if (billboard != null) {
				outputCommandHandler.addBillboard(billboard, inputCommandHandler.getSessionToken());
				// Update Billboard Panel
				try {
					outputCommandHandler.allBillboards(inputCommandHandler.getSessionToken());
				} catch (Exception exc) {
					GUI.displayError("SERVER IS NOT AVAILABLE.\nPlease contact the system administrator.");
				}

			}
		} catch (SQLException | IOException e) {
			GUI.displayError(e.getMessage());
		}
		finally {
			importXML = false;
		}
	}

	/**
	 * Show the Billboard.
	 */
	public void showBillboards() {
		try {
			outputCommandHandler.allBillboards(inputCommandHandler.getSessionToken());
			gui.getBillboardPanel().getBtnShowBillboards().setText("Update List");
		} catch (Exception exc) {
			GUI.displayError("SERVER IS NOT AVAILABLE.\nPlease contact the system administrator.");
		}
	}



	// ----------------------------------------------------- LOGIN -----------------------------------------------------
	/**
	 * Logins the user.
	 */
	private void login() {
		try {
			outputCommandHandler.login(gui.getLoginPanel().getTfUsername().getText(),
					new String(gui.getLoginPanel().getPfPassword().getPassword()));
					userName = gui.getLoginPanel().getTfUsername().getText();
		} catch (NoSuchAlgorithmException | IOException e) {
			GUI.displayError("SERVER IS NOT AVAILABLE.\nPlease contact the system administrator.");
		}
	}

	// ----------------------------------------------------- LOGOUT ----------------------------------------------------
	/**
	 * Logouts the user.
	 */
	private void logout() {
		try {
			outputCommandHandler.logout(inputCommandHandler.getSessionToken());
		} catch (IOException e) {
			GUI.displayError("SERVER IS NOT AVAILABLE.\nPlease contact the system administrator.");
		}
	}

	// --------------------------------------------------- SCHEDULES ---------------------------------------------------
	/**
	 * Adds the schedule.
	 */
	private void addSchedule() {
		List<Schedule> scheds = gui.getSchedulesPanel().addSchedule();
		try {
			if (scheds != null) {
				for (int i = 0 ; i < scheds.size(); i++){
					Schedule schedule = scheds.get(i);
					outputCommandHandler.addSchedule(schedule, inputCommandHandler.getSessionToken());
				}
			}
		} catch (Exception e) {
			GUI.displayError("SERVER IS NOT AVAILABLE.\nPlease contact the system administrator.");
		}
		updateScheduleList();
	}

	/**
	 * Deletes user by selected row
	 * @param row the row selected by user
	 */
	private void deleteSchedule(int row) {
		if(rowSelectedSchedulePanel != -1){
			int scheduleId = Integer.parseInt(String.valueOf(gui.getSchedulesPanel().getTblAllSchedules().getValueAt(row, 0)));
			Schedule schedule = new Schedule();
			schedule.setId(scheduleId);
			try {
				outputCommandHandler.deleteSchedule(schedule, inputCommandHandler.getSessionToken());
				rowSelectedSchedulePanel = -1;
				// Update user list
				try {
					outputCommandHandler.allUsers(inputCommandHandler.getSessionToken());
					rowSelectedSchedulePanel = -1;
				} catch (IOException exc) {
					rowSelectedSchedulePanel = -1;
					GUI.displayError("SERVER IS NOT AVAILABLE.\nPlease contact the system administrator.");
				}
			} catch (NoSuchAlgorithmException | IOException e) {
				rowSelectedSchedulePanel = -1;
				GUI.displayError("SERVER IS NOT AVAILABLE.\nPlease contact the system administrator.");
			}
		}
		updateScheduleList();
		gui.getSchedulesPanel().getBtnDeleteSchedule().setEnabled(false);
	}

	/**
	 * Show the users.
	 */
	public static void updateScheduleList() {
		try {
			outputCommandHandler.allBillboards(inputCommandHandler.getSessionToken());
			outputCommandHandler.allSchedules(inputCommandHandler.getSessionToken());

		} catch (IOException exc) {
			GUI.displayError("SERVER IS NOT AVAILABLE.\nPlease contact the system administrator.");
		}
	}

	// --------------------------------------------------- USER --------------------------------------------------------
	/**
	 * Adds the user.
	 */
	private void addUser() {
		User user = gui.getUsersPanel().addUser();
		if (user != null) {
			try {
				outputCommandHandler.addUser(user, inputCommandHandler.getSessionToken());
				// Update user list
				try {
					outputCommandHandler.allUsers(inputCommandHandler.getSessionToken());
				} catch (IOException exc) {
					GUI.displayError(exc.getMessage());
				}
			} catch (NoSuchAlgorithmException | IOException e) {
				GUI.displayError("SERVER IS NOT AVAILABLE.\nPlease contact the system administrator.");
			}
		}
		showUsers();
	}

	/**
	 * Edits the user by selected row (cell)
	 * @param row the row the user selected
	 */
	private void editUser(int row) {
		if(rowSelectedUserPanel != -1) {
			User user = gui.getUsersPanel().editUser(row);
			if (user != null) {
				try {
					outputCommandHandler.editUser(user, inputCommandHandler.getSessionToken());
					rowSelectedUserPanel = -1;
					// Update user list
					try {
						outputCommandHandler.allUsers(inputCommandHandler.getSessionToken());
					} catch (IOException exc) {
						rowSelectedUserPanel = -1;
						GUI.displayError(exc.getMessage());
					}
				} catch (NoSuchAlgorithmException | IOException e) {
					rowSelectedUserPanel = -1;
					GUI.displayError("SERVER IS NOT AVAILABLE.\nPlease contact the system administrator.");
				}
			}
		}

	}

	/**
	 * Deletes user by selected row
	 * @param row the row selected by user
	 */
	private void deleteUser(int row) {
		if(rowSelectedUserPanel != -1){
			String username = (String) gui.getUsersPanel().getTblAllUsers().getValueAt(row, 0);
			User user = new User();
			user.setUsername(username);
			try {
				outputCommandHandler.deleteUser(user, inputCommandHandler.getSessionToken());
				rowSelectedUserPanel = -1;
				// Update user list
				try {
					outputCommandHandler.allUsers(inputCommandHandler.getSessionToken());
					rowSelectedUserPanel = -1;
				} catch (IOException exc) {
					rowSelectedUserPanel = -1;
					GUI.displayError(exc.getMessage());
				}
			} catch (NoSuchAlgorithmException | IOException e) {
				rowSelectedUserPanel = -1;
				GUI.displayError("SERVER IS NOT AVAILABLE.\nPlease contact the system administrator.");
			}
		}
	}

	/**
	 * Show the users.
	 */
	private void showUsers() {
		try {
			outputCommandHandler.allUsers(inputCommandHandler.getSessionToken());
			gui.getUsersPanel().getBtnShowUsers().setText("Update List");
		} catch (IOException exc) {
			GUI.displayError("SERVER IS NOT AVAILABLE.\nPlease contact the system administrator.");
		}
	}

	//------------------------------------------------- TIMERS UPDATE --------------------------------------------------
	/**
	 * Schedules timer update.
	 */
	private void scheduleTimerUpdate() {
		timerUpdateUsers = new Timer(GUI.UPDATE_DELAY, e -> {
			try {
				outputCommandHandler.allUsers(inputCommandHandler.getSessionToken());
			} catch (IOException exc) {
				GUI.displayError(exc.getMessage());
			}
		});
		timerUpdateBillboards = new Timer(GUI.UPDATE_DELAY, e -> {
			try {
				outputCommandHandler.allBillboards(inputCommandHandler.getSessionToken());
			} catch (Exception exc) {
				GUI.displayError(exc.getMessage());
			}
		});
		timerUpdateSchedules = new Timer(GUI.UPDATE_DELAY, e -> {
			try {
				outputCommandHandler.allSchedules(inputCommandHandler.getSessionToken());
			} catch (IOException exc) {
				GUI.displayError(exc.getMessage());
			}
		});
	}

	//-------------------------------------------------- END APPLICATION -----------------------------------------------

	/**
	 * Close the program the application.
	 */
	public void closeLogin(){
		System.exit(0);
	}

	//--------------------------------- UPDATE COMPONENTS FROM SERVER RESPONSE------------------------------------------
	/**
	 * Updates the view according to the input {@link Message}.
	 * @param msg the input {@link Message}
	 */
	@Override
	public void update(Message msg) {
		User user = msg.user();

		switch (msg.command()) {
			case LOGIN:

				gui.setTitle("Home page");
				gui.showHome();

				if (user.getAdministrator().equals(true)){
					gui.getHomePanel().getBtnSchedules().setEnabled(true);
					gui.getHomePanel().getBtnBillboards().setEnabled(true);
					gui.getHomePanel().getBtnUsers().setEnabled(true);
				}
				else{
					if (user.getSchedule_billboards().equals(true)){
						gui.getHomePanel().getBtnSchedules().setEnabled(true);
						gui.getHomePanel().getBtnUsers().setEnabled(true);
						gui.getHomePanel().getBtnBillboards().setEnabled(true);
					}
					if(user.getEdit_users().equals(true)){
						gui.getHomePanel().getBtnUsers().setEnabled(true);
					}
					if(user.getEdit_all_billboards().equals(true)){
						gui.getHomePanel().getBtnUsers().setEnabled(true);
						gui.getHomePanel().getBtnBillboards().setEnabled(true);
					}
					if(user.getCreate_billboards().equals(true))
					// Home panel buttons
					gui.getHomePanel().getBtnBillboards().setEnabled(true);
					gui.getHomePanel().getBtnUsers().setEnabled(true);

				}


			break;

			case INVALID_CREDENTIALS:
				GUI.displayError("Invalid credentials!");
				break;
			case BILLBOARDS:
				gui.getBillboardPanel().updateTable(msg.billboards());
				gui.getSchedulesPanel().updateBillboards(msg.billboards());
				break;
			case SCHEDULES:
				gui.getSchedulesPanel().updateTable(msg.schedules());
				break;
			case USERS:
				gui.getUsersPanel().updateTable(msg.users());

				break;
			case LOGOUT:
				gui.getLoginPanel().getPfPassword().setText("");
				gui.showLogin();
				gui.setTitle("Login");
				gui.getHomePanel().getBtnSchedules().setEnabled(false);
				gui.getHomePanel().getBtnBillboards().setEnabled(false);
				gui.getHomePanel().getBtnUsers().setEnabled(false);
				break;
			case FAILED_USERNAME_EXISTS:
				GUI.displayError("User not added. Username already exists.");
				break;
			case NO_PERMISSION:
				GUI.displayError("No permission!");
				break;
			case FAILED_BILLBOARD_EXISTS:																	// Code added by Fernando
				GUI.displayError("Billboard not added. Billboard name already exist");						// Code added by Fernando
				break;																						// Code added by Fernando

		}
	}

}
