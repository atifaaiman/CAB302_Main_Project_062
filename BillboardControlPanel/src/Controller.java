import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.Timer;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import common.Billboard;
import common.Message;
import common.Permission;
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
	private InputCommandHandler inputCommandHandler;

	/** The output command handler. */
	private OutputCommandHandler outputCommandHandler;

	/** The GUI reference (or View). */
	private GUI gui;

	/** The timer update users. */
	private Timer timerUpdateUsers;

	/** The timer update billboards. */
	private Timer timerUpdateBillboards;

	/** The timer update schedules. */
	private Timer timerUpdateSchedules;

	/** The row selected by the user. */
	private int rowSelected =0;

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
		gui.getLoginPanel().getBtnLogin().addActionListener(e -> login());
		gui.getLoginPanel().getBtnClose().addActionListener(e -> closeLogin() );
		// Schedule
		gui.getSchedulesPanel().getBtnAddSchedule().addActionListener(e -> addSchedule());
		gui.getSchedulesPanel().getBtnLogout().addActionListener(e -> logout());
		// User Panel
		gui.getUsersPanel().getBtnShowUsers().addActionListener(e -> showUsers());										// Added by Fernando
		gui.getUsersPanel().getBtnDeleteUser().addActionListener(e -> deleteUser(rowSelected));						    // Added by Fernando
		gui.getUsersPanel().getBtnEditUser().addActionListener(e -> editUser(rowSelected));						        // Added by Fernando
		//gui.getUsersPanel().getCbChangePassword().addActionListener(e-> ());
		gui.getUsersPanel().getBtnLogout().addActionListener(e -> logout());
		gui.getUsersPanel().getBtnAddUser().addActionListener(e -> addUser());
		gui.getUsersPanel().getTblAllUsers().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				int row = gui.getUsersPanel().getTblAllUsers().rowAtPoint(me.getPoint());
				int colNumber = gui.getUsersPanel().getTblAllUsers().columnAtPoint(me.getPoint());
				String columnName = gui.getUsersPanel().getTblAllUsers().getColumnName(colNumber);
				rowSelected = row;
				System.out.println("Row number: " + rowSelected);
				//System.out.println( "ColumnNAme: " + columnName);
				// Added bu Fernando
				if (columnName == "Edit") {
					//System.out.println("Edit");																		// Added by Fernando
					editUser(row);
				} else if (columnName == "Delete") {
					//System.out.println("Delete: ");																	// Added by Fernando
					deleteUser(row);
				}
			}
		});
		// Billboard Panel
		gui.getBillboardPanel().getTblAllBillboards().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				int row = gui.getBillboardPanel().getTblAllBillboards().rowAtPoint(me.getPoint());
				int col = gui.getBillboardPanel().getTblAllBillboards().columnAtPoint(me.getPoint());
				if (col == 2) {
					preview(row);
				} else if (col == 4) {
					deleteBillboard(row);
				} else if (col == 3) {
					editBillboard(row);
				}
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
		gui.getBillboardPanel().getPnlPicture().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				try {
					gui.getBillboardPanel().selectImage();
				} catch (IOException e) {
					GUI.displayError(e.getMessage());
				}
			}
		});
		inputCommandHandler.addObserver(this);
	}

	/**
	 * Previews the billboard view.
	 * @param row the row
	 */
	private void preview(int row) {
		try {
			gui.getBillboardPanel().preview(row);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			GUI.displayError(e.getMessage());
		}
	}

	/**
	 * Edits the billboard by selected row (cell)
	 * @param row the row the user selected in table
	 */
	private void editBillboard(int row) {
		try {
			Billboard billboard = gui.getBillboardPanel().editBillboard(row);
			if (billboard != null) {
				outputCommandHandler.editBillboard(billboard, inputCommandHandler.getSessionToken());
			}
		} catch (Exception e) {
			GUI.displayError(e.getMessage());

		}
	}

	/**
	 * Deletes billboard by selected row (cell)
	 * @param row the row the user selected in table
	 */
	private void deleteBillboard(int row) {
		Billboard blbrd = gui.getBillboardPanel().deleteBillboard(row);

		try {
			outputCommandHandler.deleteBillboard(blbrd, inputCommandHandler.getSessionToken());
		} catch (IOException e) {
			GUI.displayError(e.getMessage());
		}
	}

	/**
	 * Adds the billboard.
	 */
	private void addBillboard() {
		try {
			Billboard billboard = gui.getBillboardPanel().addBuilboard();
			if (billboard != null) {
				outputCommandHandler.addBillboard(billboard, inputCommandHandler.getSessionToken());
			}
		} catch (SQLException | IOException e) {
			GUI.displayError(e.getMessage());
		}
	}

	/**
	 * Edits the user by selected row (cell)
	 * @param row the row the user selected
	 */
	private void editUser(int row) {
		if(rowSelected != 0) {
			User user = gui.getUsersPanel().editUser(row);
			if (user != null) {
				try {
					outputCommandHandler.editUser(user, inputCommandHandler.getSessionToken());
					rowSelected = 0;
					// Update user list
					try {
						outputCommandHandler.allUsers(inputCommandHandler.getSessionToken());
					} catch (IOException exc) {
						GUI.displayError(exc.getMessage());
					}
				} catch (NoSuchAlgorithmException | IOException e) {
					GUI.displayError(e.getMessage());
				}
			}
		}

	}

	/**
	 * Deletes user by selected row
	 * @param row the row selected by user
	 */
	private void deleteUser(int row) {
		if(rowSelected != 0){
			String username = (String) gui.getUsersPanel().getTblAllUsers().getValueAt(row, 0);
			User user = new User();
			user.setUsername(username);
			try {
				outputCommandHandler.deleteUser(user, inputCommandHandler.getSessionToken());
				rowSelected = 0;
				// Update user list
				try {
					outputCommandHandler.allUsers(inputCommandHandler.getSessionToken());
				} catch (IOException exc) {
					GUI.displayError(exc.getMessage());
				}
			} catch (NoSuchAlgorithmException | IOException e) {
				GUI.displayError(e.getMessage());
			}
		}
	}
	/**
	 * Adds the user.
	 */
	private void showUsers() {
		try {
			outputCommandHandler.allUsers(inputCommandHandler.getSessionToken());
			gui.getUsersPanel().getBtnShowUsers().setText("Update List");
		} catch (IOException exc) {
			GUI.displayError(exc.getMessage());
		}
	}

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
				GUI.displayError(e.getMessage());
			}
		}
	}

	/**
	 * Adds the schedule.
	 */
	private void addSchedule() {
		try {
			Schedule sched = gui.getSchedulesPanel().addSchedule();
			if (sched != null) {
				outputCommandHandler.addSchedule(sched, inputCommandHandler.getSessionToken());
			}
		} catch (Exception e) {
			GUI.displayError(e.getMessage());
		}
	}

	/**
	 * Logouts the user.
	 */
	private void logout() {
		try {
			outputCommandHandler.logout(inputCommandHandler.getSessionToken());
		} catch (IOException e) {
			GUI.displayError(e.getMessage());
		}
	}

	/**
	 * Logins the user.
	 */
	private void login() {
		try {
			outputCommandHandler.login(gui.getLoginPanel().getTfUsername().getText(),
					new String(gui.getLoginPanel().getPfPassword().getPassword()));
		} catch (NoSuchAlgorithmException | IOException e) {
			GUI.displayError(e.getMessage());
		}
	}

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

	/**
	 * Close the program the application.
	 */
	public void closeLogin(){
		System.exit(0);
	}

	/**
	 * Updates the view according to the input {@link Message}.
	 * @param msg the input {@link Message}
	 */
	@Override
	public void update(Message msg) {

		switch (msg.command()) {
		case LOGIN:

			gui.setTitle(msg.permission());
			switch (msg.permission()) {
			case Permission.EDIT_USERS:
				gui.showUsers();
				//timerUpdateUsers.start();         ///// Test **********************
				break;
			case Permission.CREATE_BILLBOARDS:
				gui.showBillboards();
				//timerUpdateBillboards.start();    ///// Test **********************
				break;
			case Permission.EDIT_ALL_BILLBOARDS:
				gui.showBillboards();
				timerUpdateBillboards.start();
				break;
			case Permission.SCHEDULE_BILLBOARDS:
				gui.showSchedules();
				timerUpdateSchedules.start();
				timerUpdateBillboards.start();
				break;
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
				//timerUpdateUsers.stop();																	// Test
				//timerUpdateSchedules.stop();																// Test
				//timerUpdateBillboards.stop();																// Test
				gui.getLoginPanel().getPfPassword().setText("");
				gui.showLogin();
				gui.setTitle("Login");
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