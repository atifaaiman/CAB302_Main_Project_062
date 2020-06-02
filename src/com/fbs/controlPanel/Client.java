import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.swing.SwingUtilities;

/**
 * The Class Client.
 */
public class Client {

	/** The Constant NETWORK_PROPERTIES_FILENAME. */
	public final static String NETWORK_PROPERTIES_FILENAME = "network.props";

	/**
	 * Connects to server.
	 *
	 * @return the server listener
	 */
	public ServerListener connectToServer() {

		// Read network.props file to obtain host and port to connect to.
		try (InputStream fileStream = new FileInputStream(NETWORK_PROPERTIES_FILENAME)) {

			Properties props = new Properties();

			props.load(fileStream);

			String host = props.getProperty("host");

			if (host == null) {
				throw new UnknownHostException();
			}

			int port = Integer.parseInt(props.getProperty("port"));

			try {

				Socket serverSocket = new Socket(host, port);

				ServerListener serverListener = new ServerListener(serverSocket);

				return serverListener;

			} catch (UnknownHostException e) {
				GUI.displayError("Host found in " + NETWORK_PROPERTIES_FILENAME + " (" + host + ") is not valid.");
			} catch (IOException e) {
				GUI.displayError(e.getMessage());
			} catch (NumberFormatException e) {
				GUI.displayError("Port found in " + NETWORK_PROPERTIES_FILENAME + " (" + props.getProperty("port")
						+ ") is not valid.");
			}
		} catch (FileNotFoundException e1) {
			GUI.displayError(NETWORK_PROPERTIES_FILENAME + " file with host and port to connect to "
					+ "the server was not found.");
		} catch (IOException e1) {
			GUI.displayError(e1.getMessage());
		}
		return null;
	}

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
