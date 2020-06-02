import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import common.Permission;
import common.User;

/**
 * The Class UsersPanel that encapsulates user interface to display users.
 */
public class UsersPanel extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Components. */
	private JCheckBox cbAdministrator 		 = new JCheckBox("Administrator");
	private JCheckBox cbCreateBillboard 	 = new JCheckBox("Create Billboards");
	private JCheckBox cbEditAllBillboads	 = new JCheckBox("Edit All Billboards");
	private JCheckBox cbScheduleBillboards 	 = new JCheckBox("Schedule Billboards");
	private JCheckBox cbEditUsers 			 = new JCheckBox("Edit Users");
	private JButton btnShowUsers 			 = new JButton("Show Users");
	private JButton btnAddUser 				 = new JButton("Add User");
	private JButton btnEditUser 			 = new JButton("Edit User");
	private JButton btnDeleteUser 			 = new JButton("Delete User");
	private JButton btnLogout 				 = new JButton("Logout");
	private JPanel pnlAddUser 				 = new JPanel();
	private JTextField tfUsername 			 = new JTextField(15);
	private JPasswordField pfPassword 		 = new JPasswordField(15);
	private JComboBox<String> jcbPermissions = new JComboBox<>(new String[] { Permission.EDIT_USERS,
			Permission.SCHEDULE_BILLBOARDS, Permission.EDIT_ALL_BILLBOARDS, Permission.CREATE_BILLBOARDS });
	private DefaultTableModel tblMdlAllUsers = new DefaultTableModel(
			new String[] { "Username", "Administrator", "Create Billboard", "Edit All Billboards", "Schedule Billboards",
					"Edit User" }, 0) {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
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
		// North Layout
		Box boxNorth = Box.createHorizontalBox();
			JPanel pnlLeft = new JPanel(new GridLayout(1, 2));
				pnlLeft.add(new JLabel("List of users", SwingConstants.LEFT));
				boxNorth.add(pnlLeft);
			JPanel pnlRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
				pnlRight.add(btnLogout);
				boxNorth.add(pnlRight);
		add(boxNorth, BorderLayout.NORTH);

		// Center Layout
		add(new JScrollPane(tblAllUsers), BorderLayout.CENTER);

		// South Layout
		Box boxSouth = Box.createHorizontalBox();
			JPanel pnlLeftSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
				pnlLeftSouth.add(btnShowUsers);
				pnlLeftSouth.add(btnAddUser);
				pnlLeftSouth.add(btnEditUser);
				pnlLeftSouth.add(btnDeleteUser);
				boxSouth.add(pnlLeftSouth);
		add(boxSouth, BorderLayout.SOUTH);


		/*
		 * ADD USER PANEL
		 * Init components for AddUser panel required for JOptionPane to be displayed.
		 */
		Box boxAddUser = Box.createVerticalBox();
		JPanel panel_0 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		BufferedImage image;
		try{
			image = ImageIO.read(new File("./adduser.png"));
			panel_0.add(new JLabel(new ImageIcon(image)));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		JPanel panel_1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel_1.add(new JLabel("Username"));
		JPanel panel_2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel_2.add(tfUsername);
		JPanel panel_3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel_3.add(new JLabel("Password"));
		JPanel panel_4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel_4.add(pfPassword);
		JPanel panel_5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel panel_6 = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JPanel layout = new JPanel();
		layout.setBorder(BorderFactory.createDashedBorder(Color.darkGray));
		layout.setBorder(BorderFactory.createTitledBorder("Permissions:"));
		layout.setLayout(new GridLayout(0,2));
			layout.add(cbAdministrator);
			layout.add(cbEditUsers);
			layout.add(cbCreateBillboard);
			layout.add(cbScheduleBillboards);
			layout.add(cbEditAllBillboads);
		JPanel panel_7 = new JPanel(new FlowLayout(FlowLayout.CENTER));

		boxAddUser.add(panel_0);
		boxAddUser.add(panel_1);
		boxAddUser.add(panel_2);
		boxAddUser.add(panel_3);
		boxAddUser.add(panel_4);
		boxAddUser.add(panel_5);
		boxAddUser.add(panel_6);
		boxAddUser.add(layout);
		boxAddUser.add(panel_7);
		pnlAddUser.add(boxAddUser);
	}

	/**
	 * Update table.
	 * @param users the users
	 */
	public void updateTable(List<User> users) {

		tblMdlAllUsers.setRowCount(0);
		for (User user : users) {
			tblMdlAllUsers.addRow(
					new Object[] { user.getUsername(), user.getAdministrator(),user.getCreate_billboards(),
							user.getEdit_all_billboards(), user.getSchedule_billboards(), user.getEdit_users() });
		}
		revalidate();
		updateUI();
	}

	/**
	 * Adds the user.
	 * @return the user
	 */
	public User addUser() {
		Boolean administrator 			= false;
		Boolean create_billboards		= false;
		Boolean edit_all_billboards 	= false;
		Boolean schedule_billboards 	= false;
		Boolean edit_users 				= false;

		User user = null;
		int add = JOptionPane.showConfirmDialog(this, pnlAddUser, "Add User",
				JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
		if (add == 0) { // If OK.

			String username = tfUsername.getText().trim();
			String password = new String(pfPassword.getPassword()).trim();
			// New Fernando -----------------------------------------------------
			administrator 			    = cbAdministrator.isSelected();
			if (administrator == true){
				create_billboards		=  true;
				edit_all_billboards 	=  true;
				schedule_billboards 	=  true;
				edit_users 				=  true;
			}else{
				create_billboards		= cbCreateBillboard.isSelected();
				edit_all_billboards 	= cbEditAllBillboads.isSelected();
				schedule_billboards 	= cbScheduleBillboards.isSelected();
				edit_users 				= cbEditUsers.isSelected();
			}

			// New Fernando -----------------------------------------------------

			if (username.length() < 4 || password.length() < 4) {
				GUI.displayError("Username and Password must contain at least 4 characters!");
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
	 * @param row the row
	 * @return the user
	 */
	public User editUser(int row) {

		User user = null;
		String username = 	(String) tblAllUsers.getValueAt(row, 0);
		//String permission = (String) tblAllUsers.getValueAt(row, 2);	        // Original code
		String permission = "";											        // Test code
		//String password = 	(String) tblAllUsers.getValueAt(row, 1);  		// Original code
		String password = 	"";												    // Original code


		// New code Sasitheran and Fernando -----------------------------------------------------
		Boolean administrator 			= cbAdministrator.isSelected();
		Boolean create_billboards		= cbCreateBillboard.isSelected();
		Boolean edit_all_billboards 	= cbEditAllBillboads.isSelected();
		Boolean schedule_billboards 	= cbScheduleBillboards.isSelected();
		Boolean edit_users 				= cbEditUsers.isSelected();
		// New code Sasitheran and Fernando -----------------------------------------------------

		pfPassword.setText(password);
		tfUsername.setText(username);
		tfUsername.setEditable(false);
		jcbPermissions.setSelectedItem(permission);

		int update = JOptionPane.showConfirmDialog(this, pnlAddUser, "Edit User",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (update == 0) { 										// If OK.
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
	 * @return the button add user
	 */
	public JButton getBtnAddUser() {
		return btnAddUser;
	}

	/**
	 * Gets the button logout.
	 * @return the button logout
	 */
	public JButton getBtnLogout() {
		return btnLogout;
	}

	/**
	 * Gets the table all users.
	 * @return the table all users
	 */
	public JTable getTblAllUsers() {
		return tblAllUsers;
	}

	/**
	 * Gets the button edit users.
	 * @return the table all users
	 */
	public JButton getBtnEditUser(){
		return  btnEditUser;
	}

	/**
	 * Gets the button delete users.
	 * @return the table all users
	 */
	public JButton getBtnDeleteUser(){
		return  btnDeleteUser;
	}

	/**
	 * Gets all users from the database.
	 * @return the table all users
	 */
	public JButton getBtnShowUsers(){
		return  btnShowUsers;
	}


}
