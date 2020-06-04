import common.User;

import java.awt.CardLayout;

import javax.swing.*;

/**
 * The Class GUI that encapsulates user interface
 * for control panel. Starts from Login panel.
 */
public class GUI extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * All billboards, users, schedules are being updated every 3 seconds.
	 */
	public final static int UPDATE_DELAY = 3_000;  // Old code 3_000
	/** The main panel. */
	private JPanel mainPanel = new JPanel();
	/** The login panel. */
	private LoginPanel loginPanel = new LoginPanel();
	/** The users panel. */
	private UsersPanel usersPanel = new UsersPanel();
	/** The billboard panel. */
	private BillboardsPanel billboardPanel = new BillboardsPanel();
	/** The schedules panel. */
	private SchedulesPanel schedulesPanel = new SchedulesPanel();
	/** The card layout. */
	private CardLayout cardLayout = new CardLayout();
	/** The home panel. */
	private HomePanel homePanel = new HomePanel();

	/**
	 * Instantiates a new gui.
	 * @param title the title
	 */
	public GUI(String title) {
		super(title);

		initGUIComponents();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
		pack();
		setLocationRelativeTo(null);
	}

	/**
	 * Inits the GUI components.
	 */
	private void initGUIComponents() {

		mainPanel.setLayout(cardLayout);

		mainPanel.add(loginPanel, "Login");
		mainPanel.add(homePanel,"Home Panel");
		mainPanel.add(usersPanel, "Users");
		mainPanel.add(billboardPanel, "Billboards");
		mainPanel.add(schedulesPanel, "Schedules");

		add(mainPanel);

		showLogin(); // Firstly login window should be shown.

	}


	/**
	 * Shows login panel.
	 */
	public void showLogin() { cardLayout.show(mainPanel, "Login"); }

	/**
	 * Shows login panel.
	 */
	public void showHome() { cardLayout.show(mainPanel, "Home Panel"); }

	/**
	 * Shows billboards panel.
	 */
	public void showBillboards() {
		cardLayout.show(mainPanel, "Billboards");
	}

	/**
	 * Shows schedules panel.
	 */
	public void showSchedules() {
		cardLayout.show(mainPanel, "Schedules");
	}

	/**
	 * Shows users panel.
	 */
	public void showUsers() {
		cardLayout.show(mainPanel, "Users");
	}

	/**
	 * Displays error.
	 *
	 * @param error the error
	 */
	public static void displayError(String error) {
		JOptionPane.showMessageDialog(null, error, "Failed", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Gets the login panel.
	 * @return the login panel
	 */
	public LoginPanel getLoginPanel() {
		return loginPanel;
	}

	/**
	 * Gets the users panel.
	 * @return the users panel
	 */
	public UsersPanel getUsersPanel() {
		return usersPanel;
	}

	/**
	 * Gets the schedules panel.
	 * @return the schedules panel
	 */
	public SchedulesPanel getSchedulesPanel() {
		return schedulesPanel;
	}

	/**
	 * Gets the billboard panel.
	 * @return the billboard panel
	 */
	public BillboardsPanel getBillboardPanel() {
		return billboardPanel;
	}

	/**
	 * Gets the home panel.
	 * @return the home panel
	 */
	public HomePanel getHomePanel() {
		return homePanel;
	}

}
