import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * The Class LoginPanel that encapsulates login window with all graphical
 * components. It does not contain any listeners since
 * the listeners are added in {@link Controller} through 
 * the getters.
 */
public class LoginPanel extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Components */
	private JTextField tfUsername = new JTextField(20);
	private JPasswordField pfPassword = new JPasswordField(20);
	private JButton btnLogin = new JButton("Login");
	private JButton btnClose = new JButton("Close");


	/**
	 * Instantiates a new login panel.
	 */
	public LoginPanel() {
		initGUIComponents();
	}

	/**
	 * author Fernando Barbosa Silva
	 * Inits the GUI components.
	 */
	private void initGUIComponents() {

		Box box = Box.createVerticalBox();
		JPanel panel_0 = new JPanel();
		JPanel panel_1 = new JPanel();
		JPanel panel_2 = new JPanel();
		JPanel panel_3 = new JPanel();
		JPanel panel_4 = new JPanel();
		JPanel panel_5 = new JPanel();
		JPanel panel_6 = new JPanel();
		BufferedImage image;
		try{
			image = ImageIO.read(new File("./login.png"));
			//System.out.println(image);
			panel_1.add(new JLabel(new ImageIcon(image)));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		panel_2.add(new JLabel("Username:", SwingConstants.CENTER));
		panel_3.add(tfUsername);
		tfUsername.setToolTipText("Enter your username");
		panel_4.add(new JLabel("Password:", SwingConstants.CENTER));
		pfPassword.setToolTipText("Enter your password");
		panel_5.add(pfPassword);
		panel_6.add(btnLogin);
		panel_6.add(btnClose);

		box.add(panel_0);
		box.add(Box.createVerticalStrut(30));
		box.add(panel_1);
		box.add(Box.createVerticalStrut(30));
		box.add(panel_2);
		box.add(panel_3);
		box.add(panel_4);
		box.add(panel_5);
		box.add(panel_6);

		this.add(box);

	}

	/**
	 * Gets the text field username.
	 * @return the text field username
	 */
	public JTextField getTfUsername() {
		return tfUsername;
	}

	/**
	 * Gets the password field password.
	 * @return the password field password
	 */
	public JPasswordField getPfPassword() {
		return pfPassword;
	}

	/**
	 * Gets the button login.
	 * @return the button login
	 */
	public JButton getBtnLogin() {
		return btnLogin;
	}

	/**
	 * Gets the button close.
	 * @return the button login
	 */
	public JButton getBtnClose() {
		return btnClose;
	}


}
