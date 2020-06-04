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

	/** The Name of the columns of the table in Edit User. */
	String [] columnNames = {"Username", "Administrator", "Create Billboard", "Edit All Billboards", "Schedule Billboards",
			"Edit User"};

	/** Components. */
	// Components used by AddUserPanel
	private JCheckBox cbChangePassword 		 = new JCheckBox("Change Password");
	private JCheckBox cbAdministrator 		 = new JCheckBox("Administrator");
	private JCheckBox cbCreateBillboard 	 = new JCheckBox("Create Billboards");
	private JCheckBox cbEditAllBillboads	 = new JCheckBox("Edit All Billboards");
	private JCheckBox cbScheduleBillboards 	 = new JCheckBox("Schedule Billboards");
	private JCheckBox cbEditUsers 			 = new JCheckBox("Edit Users");
	private JTextField tfUsername 			 = new JTextField(15);
	private JPasswordField pfPassword 		 = new JPasswordField(15);

	// Components used by EditUserPanel
	private JCheckBox cbChangePasswordEditUser 		 = new JCheckBox("Change Password");
	private JCheckBox cbAdministratorEditUser  		 = new JCheckBox("Administrator");
	private JCheckBox cbCreateBillboardEditUser  	 = new JCheckBox("Create Billboards");
	private JCheckBox cbEditAllBillboadsEditUser 	 = new JCheckBox("Edit All Billboards");
	private JCheckBox cbScheduleBillboardsEditUser   = new JCheckBox("Schedule Billboards");
	private JCheckBox cbEditUsersEditUser  			 = new JCheckBox("Edit Users");
	private JTextField tfUsernameEditUser  			 = new JTextField(15);
	private JPasswordField pfPasswordEditUser  		 = new JPasswordField(15);


	// Components used by UserPanel
	private JButton btnShowUsers 			 = new JButton("Show Users");
	private JButton btnAddUser 				 = new JButton("Add User");
	private JButton btnEditUser 			 = new JButton("Edit User");
	private JButton btnDeleteUser 			 = new JButton("Delete User");
	private JButton btnLogout 				 = new JButton("Logout");
	private JButton btnHome				 	 = new JButton("Home");
	private JPanel pnlAddUser 				 = new JPanel();
	private JPanel pnlEditUser 				 = new JPanel();
	private JComboBox<String> jcbPermissions = new JComboBox<>(new String[] { Permission.EDIT_USERS,
			Permission.SCHEDULE_BILLBOARDS, Permission.EDIT_ALL_BILLBOARDS, Permission.CREATE_BILLBOARDS });
	private DefaultTableModel tblMdlAllUsers = new DefaultTableModel(
			new String[] { columnNames[0], columnNames[1], columnNames[2], columnNames[3], columnNames[4],
					columnNames[5] }, 0) {

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
	 * Initiates the GUI components.
	 */
	private void initGUIComponents() {

		//--------------------------------------------------------------------------------------------------------------
		// MAIN PANEL
		//--------------------------------------------------------------------------------------------------------------
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		// North Layout
		Box boxNorth = Box.createHorizontalBox();
			JPanel pnlLeft = new JPanel(new GridLayout(1, 2));
				pnlLeft.add(new JLabel("List of users", SwingConstants.LEFT));
				boxNorth.add(pnlLeft);
			JPanel pnlRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
				pnlRight.add(btnHome);
				pnlRight.add(btnLogout);
				boxNorth.add(pnlRight);
		add(boxNorth, BorderLayout.NORTH);

		// Center Layout
		add(new JScrollPane(tblAllUsers), BorderLayout.CENTER);
		tblAllUsers.getTableHeader().setReorderingAllowed(false);
		//tblAllUsers.getTableHeader().setDraggedColumn(false);


		// South Layout
		Box boxSouth = Box.createHorizontalBox();
			JPanel pnlLeftSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
				pnlLeftSouth.add(btnShowUsers);
				pnlLeftSouth.add(btnAddUser);
				pnlLeftSouth.add(btnEditUser);
				pnlLeftSouth.add(btnDeleteUser);
				boxSouth.add(pnlLeftSouth);
		add(boxSouth, BorderLayout.SOUTH);

		//--------------------------------------------------------------------------------------------------------------
		/*
		 * AddUser Panel
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
		panel_2.add(tfUsername );
		JPanel panel_3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel_3.add(new JLabel("Password"));
		JPanel panel_4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel_4.add(pfPassword );
		JPanel panel_5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel panel_6 = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JPanel layout = new JPanel();
		layout.setBorder(BorderFactory.createDashedBorder(Color.darkGray));
		layout.setBorder(BorderFactory.createTitledBorder("Permissions:"));
		layout.setLayout(new GridLayout(0,2));
		layout.add(cbAdministrator );
		layout.add(cbEditUsers );
		layout.add(cbCreateBillboard );
		layout.add(cbScheduleBillboards );
		layout.add(cbEditAllBillboads );
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

		//--------------------------------------------------------------------------------------------------------------
		/*
		 * EditUSer Panel
		 * Init components for AddUser panel required for JOptionPane to be displayed.
		 */
		Box boxEditUser = Box.createVerticalBox();
		JPanel panelEditUser_0 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		BufferedImage image1;
		try{
			image1 = ImageIO.read(new File("./adduser.png"));
			panelEditUser_0.add(new JLabel(new ImageIcon(image1)));
		}
		catch (IOException e1) {
			System.out.println(e1.getMessage());
		}
		JPanel panelEditUser_1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelEditUser_1.add(new JLabel("Username"));
		JPanel panelEditUser_2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelEditUser_2.add(tfUsernameEditUser);
		JPanel panelEditUser_3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelEditUser_3.add(cbChangePasswordEditUser);
		JPanel panelEditUser_4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelEditUser_4.add(pfPasswordEditUser);
		JPanel panelEditUser_5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel panelEditUser_6 = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JPanel layoutEditUser = new JPanel();
		layoutEditUser.setBorder(BorderFactory.createDashedBorder(Color.darkGray));
		layoutEditUser.setBorder(BorderFactory.createTitledBorder("Permissions:"));
		layoutEditUser.setLayout(new GridLayout(0,2));
		layoutEditUser.add(cbAdministratorEditUser);
		layoutEditUser.add(cbEditUsersEditUser);
		layoutEditUser.add(cbCreateBillboardEditUser);
		layoutEditUser.add(cbScheduleBillboardsEditUser);
		layoutEditUser.add(cbEditAllBillboadsEditUser);
		JPanel panelEditUser_7 = new JPanel(new FlowLayout(FlowLayout.CENTER));

		boxEditUser.add(panelEditUser_0);
		boxEditUser.add(panelEditUser_1);
		boxEditUser.add(panelEditUser_2);
		boxEditUser.add(panelEditUser_3);
		boxEditUser.add(panelEditUser_4);
		boxEditUser.add(panelEditUser_5);
		boxEditUser.add(panelEditUser_6);
		boxEditUser.add(layoutEditUser);
		boxEditUser.add(panelEditUser_7);
		pnlEditUser.add(boxEditUser);

		// Action Listener to control change password label
		disableButtons();
		getCbChangePasswordEditUser().addActionListener(e->setPassword());
	}

	/**
	 * Disable buttons "Edit user" and "Delete User" until user select a row in the table.
	 */
	private void disableButtons(){
		btnDeleteUser.setEnabled(false);
		btnEditUser.setEnabled(false);
	}

	/**
	 * Make the editable the password.
	 */
	private void setPassword(){
		if(cbChangePasswordEditUser.isSelected() == true)pfPasswordEditUser.setEditable(true);
		else pfPasswordEditUser.setEditable(false);
	}

	private void setAdministrator(){
		if(cbChangePassword.isSelected() == true)pfPassword.setEditable(true);
		else pfPassword.setEditable(false);
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
							user.getEdit_all_billboards(), user.getSchedule_billboards(), user.getEdit_users(),
							user.getPassword() });
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
		String permission = "Create Billboards";   // Debug code - Must be improved

		User user = null;
		int add = JOptionPane.showConfirmDialog(this, pnlAddUser, "Add User",
				JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
		if (add == 0) { // If OK.

			String username = tfUsername.getText().trim();
			String password = new String(pfPassword.getPassword()).trim();

			// New  ---------------------------------------------------------------------
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
			// New  ---------------------------------------------------------------------

			if (username.length() < 4 || password.length() < 4) {
				GUI.displayError("Username and Password must contain at least 4 characters!");
			} else {
				//String permission = (String) jcbPermissions.getSelectedItem();
				//user = new User(username, password, permission); // Old Code
				user = new User(username, password, permission, administrator, create_billboards,
						edit_all_billboards, schedule_billboards, edit_users); // New Code
			}
		}

		/*
		 * Reset components.
		 */
		disableButtons();
		tfUsername.setText("");
		pfPassword.setText("");
		jcbPermissions.setSelectedIndex(0);
		cbAdministrator.setSelected(false);
		cbCreateBillboard.setSelected(false);
		cbEditAllBillboads.setSelected(false);
		cbScheduleBillboards.setSelected(false);
		cbEditUsers.setSelected(false);
		return user;
	}

	/**
	 * Edits the user.
	 * @param row the row
	 * @return the user
	 */
	public User editUser(int row) {

		System.out.println("Edit user");
		User user = null;
		tfUsernameEditUser.setEditable(false);
		pfPasswordEditUser.setEditable(false);

		// Values to populate the screen with current user's information
		//String username = 	(String) tblAllUsers.getValueAt(row, 0);
		String username = 	(String) tblAllUsers.getValueAt(row, 0);
		String permission = "";											        		// Test code
		String password = 	"";												    		// Original code
		Boolean administrator 			= (Boolean) tblAllUsers.getValueAt(row, 1);
		Boolean createBillboards		= (Boolean) tblAllUsers.getValueAt(row, 2);
		Boolean editAllBillboards 		= (Boolean) tblAllUsers.getValueAt(row, 3);
		Boolean scheduleBillboards 		= (Boolean) tblAllUsers.getValueAt(row, 4);
		Boolean editUsers 				= (Boolean) tblAllUsers.getValueAt(row, 5);
		pfPasswordEditUser.setText(password);
		tfUsernameEditUser.setText(username);
		jcbPermissions.setSelectedItem(permission);
		cbAdministratorEditUser.setSelected(administrator);
		cbCreateBillboardEditUser.setSelected(createBillboards);
		cbEditAllBillboadsEditUser.setSelected(editAllBillboards);
		cbScheduleBillboardsEditUser.setSelected(scheduleBillboards);
		cbEditUsersEditUser.setSelected(editUsers);

		int update = JOptionPane.showConfirmDialog(this, pnlEditUser, "Edit User",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (update == 0) { 										// If OK.
			String pswrd = new String(pfPasswordEditUser.getPassword());
			pswrd = pswrd.trim();

			// If Administrator is selected then set all permission to true.
			System.out.println("Admin: " + cbAdministratorEditUser.isSelected());
			if(cbAdministratorEditUser.isSelected()){
				cbCreateBillboardEditUser.setSelected(true);
				cbEditAllBillboadsEditUser.setSelected(true);
				cbScheduleBillboardsEditUser.setSelected(true);
				cbEditUsersEditUser.setSelected(true);
			}

			boolean changePassword = cbChangePasswordEditUser.isSelected();
			if (changePassword && pswrd.isEmpty()) {
				GUI.displayError("Password cannot be empty!");
			}
			else if (changePassword && pswrd.length() < 4) {
				GUI.displayError("Username and Password must contain at least 4 characters!");
			}
			else {
				// If user select change password
				if(cbChangePassword.isSelected() == true){
					user = new User(username, pswrd, (String) jcbPermissions.getSelectedItem(),cbAdministratorEditUser.isSelected(),
							cbCreateBillboardEditUser.isSelected(), cbEditAllBillboadsEditUser.isSelected(),
							cbScheduleBillboardsEditUser.isSelected(),
							cbEditUsersEditUser.isSelected());
					user.setOldPassword(password);

				}// If user did not selected change password
				else {
					user = new User(username, "", (String) jcbPermissions.getSelectedItem(),cbAdministratorEditUser.isSelected(),
							cbCreateBillboardEditUser.isSelected(), cbEditAllBillboadsEditUser.isSelected(), cbScheduleBillboardsEditUser.isSelected(),
							cbEditUsersEditUser.isSelected());
					user.setOldPassword(password);
				}
			}
		}
		/*
		 * Reset components.
		 */
		disableButtons();
		tfUsernameEditUser.setText("");
		//pfPasswordEditUser.setText();
		jcbPermissions.setSelectedIndex(0);
		tfUsernameEditUser.setEditable(true);
		pfPasswordEditUser.setEditable(false);
		cbAdministratorEditUser.setSelected(false);
		cbCreateBillboardEditUser.setSelected(false);
		cbEditAllBillboadsEditUser.setSelected(false);
		cbScheduleBillboardsEditUser.setSelected(false);
		cbEditUsersEditUser.setSelected(false);
		cbChangePasswordEditUser.setSelected(false);

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

	/**
	 * Gets checkbox change Password.
	 * @return cbChangePassword checkBox.
	 */
	public  JCheckBox getCbChangePasswordEditUser(){
		return  cbChangePasswordEditUser;
	}

	/**
	 * Gets home button
	 * @return home button.
	 */
	public  JButton getBtnHome(){
		return  btnHome;
	}


}
