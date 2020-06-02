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
	 * @ Fernando Barbosa Silva
	 * Connects to server.
	 * @return the server listener, if some error occur return null.
	 */
	public ServerListener connectToServer() {

		// Reads network.props file
		ReadPropsFile rpf = new ReadPropsFile();
		String host = rpf.getHost();
		int port = rpf.getPort();

			try {
				Socket serverSocket = new Socket(host, port);
				ServerListener serverListener = new ServerListener(serverSocket);
				return serverListener;

			}
			catch (IOException e) {
				System.out.println("Exception: 2 - SERVER IS NOT AVAILABLE.\nPlease check the network.props file.");
				GUI.displayError("SERVER NOT AVAILABLE!\nPlease contact the system administrator.");
				//GUI.displayError(e.getMessage());
			}
		return null;
	}

}
