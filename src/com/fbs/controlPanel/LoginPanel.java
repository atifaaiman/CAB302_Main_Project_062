import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * The Class LoginPanel that encapsulates login window with all graphical
 * components. It does not contain any listeners since
 * the listeners are added in {@link Controller} through 
 * the getters.
 */
public class LoginPanel extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The text field username. */
	private JTextField tfUsername = new JTextField(15);

	/** The password field password. */
	private JPasswordField pfPassword = new JPasswordField(15);

	/** The button login. */
	private JButton btnLogin = new JButton("Login");

	/**
	 * Instantiates a new login panel.
	 */
	public LoginPanel() {
		initGUIComponents();
	}

	/**
	 * Inits the GUI components.
	 */
	private void initGUIComponents() {

		setLayout(new GridLayout(3, 2, 10, 10));
		setBorder(BorderFactory.createEmptyBorder(200, 70, 200, 70));
		add(new JLabel("Enter username:", SwingConstants.RIGHT));
		add(tfUsername);
		add(new JLabel("Enter password:", SwingConstants.RIGHT));
		add(pfPassword);
		add(new JLabel());
		add(btnLogin);
	}

	/**
	 * Gets the text field username.
	 *
	 * @return the text field username
	 */
	public JTextField getTfUsername() {
		return tfUsername;
	}

	/**
	 * Gets the password field password.
	 *
	 * @return the password field password
	 */
	public JPasswordField getPfPassword() {
		return pfPassword;
	}

	/**
	 * Gets the button login.
	 *
	 * @return the button login
	 */
	public JButton getBtnLogin() {
		return btnLogin;
	}


}
