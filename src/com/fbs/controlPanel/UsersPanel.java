import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import common.Permission;
import common.User;

/**
 * The Class UsersPanel that encapsulates user interface to display users.
 */
public class UsersPanel extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The button add user. */
	private JButton btnAddUser = new JButton("Add User");

	/** The button logout. */
	private JButton btnLogout = new JButton("Logout");

	/** The panel add user. */
	private JPanel pnlAddUser = new JPanel();

	/** The text field username. */
	private JTextField tfUsername = new JTextField(15);

	/** The password field password. */
	private JPasswordField pfPassword = new JPasswordField(15);

	/** The combo box with permissions. */
	private JComboBox<String> jcbPermissions = new JComboBox<>(new String[] { Permission.EDIT_USERS,
			Permission.SCHEDULE_BILLBOARDS, Permission.EDIT_ALL_BILLBOARDS, Permission.CREATE_BILLBOARDS });

	/** The table model all users. */
	private DefaultTableModel tblMdlAllUsers = new DefaultTableModel(
			new String[] { "Username", "Password", "Permission", "Edit", "Delete" }, 0) {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	/** The table all users. */
	private JTable tblAllUsers = new JTable(tblMdlAllUsers);

	/**
	 * Instantiates a new users panel.
	 */
	public UsersPanel() {
		initGUIComponents();
	}

	/**
	 * Inits the GUI components.
	 */
	private void initGUIComponents() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JPanel pnlNorth = new JPanel(new GridLayout(1, 2));
		pnlNorth.add(new JLabel("All Users", SwingConstants.LEFT));
		JPanel pnlLogout = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlLogout.add(btnLogout);
		pnlNorth.add(pnlLogout);
		add(pnlNorth, BorderLayout.NORTH);
		add(new JScrollPane(tblAllUsers), BorderLayout.CENTER);
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSouth.add(btnAddUser);
		add(pnlSouth, BorderLayout.SOUTH);

		/*
		 * Init components for AddUser panel required for JOptionPane to be displayed.
		 */
		pnlAddUser.setLayout(new BoxLayout(pnlAddUser, BoxLayout.Y_AXIS));
		pnlAddUser.add(new JLabel("Enter username:"));
		pnlAddUser.add(Box.createVerticalStrut(5));
		pnlAddUser.add(tfUsername);
		pnlAddUser.add(Box.createVerticalStrut(10));
		pnlAddUser.add(new JLabel("Enter password:"));
		pnlAddUser.add(Box.createVerticalStrut(5));
		pnlAddUser.add(pfPassword);
		pnlAddUser.add(Box.createVerticalStrut(10));
		pnlAddUser.add(new JLabel("Select permission:"));
		pnlAddUser.add(jcbPermissions);
	}

	/**
	 * Update table.
	 *
	 * @param users the users
	 */
	public void updateTable(List<User> users) {

		tblMdlAllUsers.setRowCount(0);
		for (User user : users) {
			tblMdlAllUsers.addRow(
					new Object[] { user.getUsername(), user.getPassword(), user.getPermission(), "Edit", "Delete" });
		}
		revalidate();
		updateUI();
	}

	/**
	 * Adds the user.
	 *
	 * @return the user
	 */
	public User addUser() {

		User user = null;

		int add = JOptionPane.showConfirmDialog(this, pnlAddUser, "Add User", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (add == 0) { // If OK.

			String username = tfUsername.getText().trim();
			String password = new String(pfPassword.getPassword()).trim();

			// New code Sasitheran
			Boolean administrator = true;
			Boolean create_billboards = true;
			Boolean edit_all_billboards = true;
			Boolean schedule_billboards = true;
			Boolean edit_users = true;

			if (username.isEmpty() || password.isEmpty()) {
				GUI.displayError("Username and Password must contain at least 1 symbol!");
			} else {
				String permission = (String) jcbPermissions.getSelectedItem();
				//user = new User(username, password, permission); // Old Code
				user = new User(username, password, permission, administrator, create_billboards,
						edit_all_billboards, schedule_billboards, edit_users); // New Code
			}
		}

		/*
		 * Reset components.
		 */
		tfUsername.setText("");
		pfPassword.setText("");
		jcbPermissions.setSelectedIndex(0);

		return user;
	}

	/**
	 * Edits the user.
	 *
	 * @param row the row
	 * @return the user
	 */
	public User editUser(int row) {

		User user = null;
		String username = (String) tblAllUsers.getValueAt(row, 0);
		String permission = (String) tblAllUsers.getValueAt(row, 2);
		String password = (String) tblAllUsers.getValueAt(row, 1);

		// New code Sasitheran
		Boolean administrator = true;
		Boolean create_billboards = true;
		Boolean edit_all_billboards = true;
		Boolean schedule_billboards = true;
		Boolean edit_users = true;

		pfPassword.setText(password);
		tfUsername.setText(username);
		tfUsername.setEditable(false);
		jcbPermissions.setSelectedItem(permission);

		int update = JOptionPane.showConfirmDialog(this, pnlAddUser, "Edit User", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (update == 0) { // If OK.
			String pswrd = new String(pfPassword.getPassword());
			pswrd = pswrd.trim();
			if (pswrd.isEmpty()) {
				GUI.displayError("Password cannot be empty!");
			} else {
				user = new User(username, pswrd, (String) jcbPermissions.getSelectedItem(),administrator, create_billboards,
						edit_all_billboards, schedule_billboards, edit_users); // New Code
				user.setOldPassword(password);
			}
		}
		/*
		 * Reset components.
		 */
		tfUsername.setText("");
		pfPassword.setText("");
		jcbPermissions.setSelectedIndex(0);
		tfUsername.setEditable(true);

		return user;
	}

	/**
	 * Gets the button add user.
	 *
	 * @return the button add user
	 */
	public JButton getBtnAddUser() {
		return btnAddUser;
	}

	/**
	 * Gets the button logout.
	 *
	 * @return the button logout
	 */
	public JButton getBtnLogout() {
		return btnLogout;
	}

	/**
	 * Gets the table all users.
	 *
	 * @return the table all users
	 */
	public JTable getTblAllUsers() {
		return tblAllUsers;
	}

}
