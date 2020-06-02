
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Toolkit;


public class LoginClass extends JPanel{

	static String unametopass;
	
	JFrame frameLoginPage;
	private JPasswordField passwordField;
	private JTextField textFieldUName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginClass window = new LoginClass();
					window.frameLoginPage.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginClass() {
		initialize();
	}

	private void initialize() {
		//Login Frame
		frameLoginPage = new JFrame();
		frameLoginPage.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\sasit\\Desktop\\Billboard Control Panel\\src\\Login.jpg"));
		 ImageIcon image = new ImageIcon("C:\\Users\\sasit\\Desktop\\Billboard Control Panel\\src\\Login.jpg");
		JLabel background = new JLabel(image);
		frameLoginPage.setContentPane(background);
		
		frameLoginPage.setResizable(false);
		frameLoginPage.getContentPane().setBackground(Color.WHITE);
		frameLoginPage.setBackground(Color.WHITE);
		frameLoginPage.setTitle("Log In");
		frameLoginPage.setBounds(100/2, 100/2, 924/2, 634/2);
		frameLoginPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameLoginPage.getContentPane().setLayout(null);

		
		JLabel lblLogInTo = new JLabel("WELCOME TO CAB302");
		lblLogInTo.setForeground(new Color(248, 248, 255));
		lblLogInTo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogInTo.setFont(new Font("Raleway", Font.PLAIN, 18));
		lblLogInTo.setBounds(10/2, 30/2, 847/2, 67/2);
		frameLoginPage.getContentPane().add(lblLogInTo);
		
		JLabel lblLogInTo_ = new JLabel(" ADVERTISING SPACE");
		lblLogInTo_.setForeground(new Color(248, 248, 255));
		lblLogInTo_.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogInTo_.setFont(new Font("Raleway", Font.PLAIN, 18));
		lblLogInTo_.setBounds(93, 45, 243, 33);
		frameLoginPage.getContentPane().add(lblLogInTo_);
		
		JLabel lblUsername = new JLabel("Username :");
		lblUsername.setForeground(new Color(248, 248, 255));
		lblUsername.setFont(new Font("Dialog", Font.BOLD, 17));
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setBounds(48, 106, 130, 15);
		frameLoginPage.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setForeground(new Color(248, 248, 255));
		lblPassword.setBackground(new Color(128, 0, 128));
		lblPassword.setFont(new Font("Dialog", Font.BOLD, 17));
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setBounds(48, 155, 130, 19);
		frameLoginPage.getContentPane().add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setForeground(new Color(0, 0, 0));
		passwordField.setBackground(new Color(255, 255, 255));
		passwordField.setToolTipText("Enter Password ");
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setBounds(188, 156, 173, 19);
		frameLoginPage.getContentPane().add(passwordField);
		
		textFieldUName = new JTextField();
		textFieldUName.setForeground(new Color(0, 0, 0));
		textFieldUName.setBackground(new Color(255, 255, 255));
		textFieldUName.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldUName.setToolTipText("Enter Username");
		textFieldUName.setBounds(188, 105, 173, 19);
		frameLoginPage.getContentPane().add(textFieldUName);
		textFieldUName.setColumns(10);
		
		JButton btnLogIn = new JButton("Log In");
		btnLogIn.setForeground(new Color(248, 248, 255));
		btnLogIn.setFont(new Font("Raleway", Font.PLAIN, 13));
		btnLogIn.setBackground(new Color(0, 0, 0));
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String username=textFieldUName.getText().toString();
				String password=new String(passwordField.getPassword());		

				unametopass=username;

				
				if (username.equals("")) {
					JOptionPane.showMessageDialog(frameLoginPage, "Username cannot be blank ");
					
				}
				else if (username.contains(" ")) {
					JOptionPane.showMessageDialog(frameLoginPage, "Username cannot have blank spaces! ");
					
				}
				else if (password.equals("")) {
					JOptionPane.showMessageDialog(frameLoginPage, "Password cannot be blank");
					
				}
				
				else{
					
					//You will get here if he will enter user Name Password
					//Here you will check that the credentials are valid? Compare from DB
							//If valid then check permissions and Hide tabs from Control Panel which are not permitted 
				frameLoginPage.setVisible(false);
				ControlPanel cp = new ControlPanel(); //Control Panel Navigation
			   
			}
			}});
		btnLogIn.setBounds(176, 210, 117, 23);
		frameLoginPage.getContentPane().add(btnLogIn);
		
		
		
	
		
	

		
		JLabel label = new JLabel("");
	//	label.setIcon(new ImageIcon(LoginClass.class.getResource("/com/arav/minorproject/bg_gray.png")));
		label.setBackground(Color.LIGHT_GRAY);
		label.setBounds(0, 0, 918, 617);
		frameLoginPage.getContentPane().add(label);
	}
}