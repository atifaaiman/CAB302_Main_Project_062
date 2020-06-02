import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

/**
 * The Class Server. Encapsulates server to respond on all requests from clients
 * (billboard panel and viewers).
 */
public class Server {

	/** The Constant NETWORK_PROPERTIES_FILENAME. */
	public final static String NETWORK_PROPERTIES_FILENAME = "network.props";

	/**
	 * Starts the server. Here it reads the network properties file and specifies
	 * the port to listen to.
	 */
	public void start() {

		// Read network.props file to obtain host and port to listen to.
		try (InputStream fileStream = new FileInputStream(NETWORK_PROPERTIES_FILENAME)) {

			Properties props = new Properties();

			props.load(fileStream);

			int port = Integer.parseInt(props.getProperty("port"));

			// Initialize database before start the server (it does not
			// make sense to run the server if database is not working).
			DB.init();

			start(port);

		} catch (FileNotFoundException e) {
			System.err.println(NETWORK_PROPERTIES_FILENAME + " file with host and port to listen to "
					+ "the server was not found.");
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	/**
	 * Starts the server on port.
	 *
	 * @param port the port
	 */
	public void start(int port) {

		try (ServerSocket serverSocket = new ServerSocket(port)) {

			System.out.println("Server is running");

			while (true) {

				Socket clientSocket = serverSocket.accept();

				System.out.println("Client connected");

				// Run client listener in a separate thread to
				// wait for new clients again. Now the connection
				// with this client is handled by the separate class.
				new Thread(new ClientListener(clientSocket)).start();

			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

}
