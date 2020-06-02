import javax.swing.*;

public class Main {
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {

        // First, connect to server.
       Client client = new Client();
       ServerListener serverListener = client.connectToServer();

        // If not connected to server, exit because
        // Billboard Control Panel makes no sense without the server.
        if (serverListener == null) {
            System.exit(1);
        }

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
            gui.setVisible(true);
        });
    }
}
