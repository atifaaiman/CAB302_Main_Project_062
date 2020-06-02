import javax.swing.*;
import java.awt.*;

public class Login {

    //// Attributes
    // Define frame's size
    private int frameWidth = 500;
    private int frameHeight = 300;
    // Define component's sizes
    private int textBoxWidtht = 250;
    private int textBoxHight = 25;
    private GUI gui = new GUI();


    //// Constructors
    public Login() {
        String title = "LOGIN";
        createLoginScreen(title);
    }
    public Login(String screenTitle) {
        createLoginScreen(screenTitle);
    }

    //// Methods
    public void createLoginScreen(String title){

        //// Create Frame
        JFrame frame = new JFrame();
        frame.setSize(frameWidth,frameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setTitle("System authentication");
        //frame.setLayout(null);

        //// Create Panel
        JPanel panel_1 = new JPanel();
        panel_1.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel panel_2 = new JPanel();
        panel_2.setLayout(null);

        JPanel panel_3 = new JPanel();
        panel_3.setLayout(null);

        JPanel panel_4 = new JPanel();
        panel_4.setLayout(new FlowLayout(FlowLayout.CENTER));

        Box verticalBox = Box.createVerticalBox();

        //// Create components in different lines (rows)
        // Line 1  - Login title:
        JLabel message = new JLabel(title);
        Font font = new Font(null,Font.PLAIN, 20) ;
        message.setFont(font);
        // Line 2 - User label and text box:
        JLabel userLabel = new JLabel("User");
        userLabel.setBounds(75,0,60,25);
        JTextField userTextBox = new JTextField();
        userTextBox.setBounds(150,0,textBoxWidtht,textBoxHight);
        userTextBox.setToolTipText("Enter your user ID.");

        // Line 3 - Password label and text box:
        JLabel  passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(75,0,60,25);
        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(150,0,textBoxWidtht,textBoxHight);
        passwordText.setToolTipText("Enter your password.");

        // Line 4 -  Login and Cancel button
        JButton loginButton = new JButton("Login ");
        JButton cancelButton = new JButton("Cancel");
        // Buttons listeners
        loginButton.addActionListener(e->{
            String user = userTextBox.getText();
            char[] password = passwordText.getPassword();
            String pass = String.valueOf(password);
            System.out.println("User: " + user + "\nPassword: " + pass);
            //gui.
        });

        cancelButton.addActionListener(e->{
            closeLogin();
        });



        // Add componets in the panel_1
        panel_1.add(message);
        // Add components in the panel_2
        panel_2.add(userLabel);
        panel_2.add(userTextBox);
        // Add components in the panel_3
        panel_3.add(passwordLabel);
        panel_3.add(passwordText);
        // Add component in the panel_4
        panel_4.add(loginButton);
        panel_4.add(cancelButton);
        // Add components in the box
        Box theBox = Box.createVerticalBox();
        theBox.add(Box.createVerticalStrut(20));
        theBox.add(panel_1);
        theBox.add(panel_2);
        theBox.add(panel_3);
        theBox.add(Box.createVerticalStrut(0));
        theBox.add(panel_4);
        // Add Box in the frame
        frame.add(theBox);
        // Show frame
        frame.setVisible(true);
    }

    public void closeLogin(){
        System.exit(0);
    }


    //------------------------------------------------------------ MAIN ------------------------------------------------
    public static void main(String[] args) {

        // First, connect to server.
        Client client = new Client();
        ServerListener serverListener = client.connectToServer();
        InputCommandHandler inputCommandHandler = new InputCommandHandler();
        OutputCommandHandler outputCommandHandler = new OutputCommandHandler();
        serverListener.setInputCommandHandler(inputCommandHandler);
        serverListener.setOutputCommandHandler(outputCommandHandler);

        // Start server listener in a separate thread because
        // it could be delay before a response.
        new Thread(serverListener).start();

        // Next, run GUI.
        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI("Login");
            Controller controller = new Controller(inputCommandHandler, outputCommandHandler, gui);
            controller.init();
            Login login = new Login();

        });
    }

}
