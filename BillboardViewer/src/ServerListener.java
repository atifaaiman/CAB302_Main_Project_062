
import static common.Message.GET_BILLBOARD;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import common.Message;

/**
 * Listens for the incoming messages from server.
 */
public class ServerListener implements Runnable {

	private String billboardName = null;

	/** The server socket. */
	private Socket serverSocket;

	/** The gui reference to update. */
	private GUI gui;

	/** The output stream to write responses. */
	private ObjectOutputStream oos;

	/**
	 * Instantiates a new server listener.
	 *
	 * @param serverSocket the server socket
	 * @param oos          the output stream
	 * @param gui          the gui
	 */
	public ServerListener(Socket serverSocket, ObjectOutputStream oos, GUI gui) {
		this.serverSocket = serverSocket;
		this.oos = oos;
		this.gui = gui;
	}

	/**
	 * Runs listener.
	 */
	@Override
	public void run() {

		try (ObjectInputStream ois = new ObjectInputStream(serverSocket.getInputStream())) {
			Message msg = (Message) ois.readObject();

			// Update the billboard from the server
			processCommand(msg);

		} catch (IOException e) {

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
					if (serverSocket != null) {
						serverSocket.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Processes command.
	 * @param msg the message
	 */
	public void processCommand(Message msg) {
		switch (msg.command()) {
			case GET_BILLBOARD:
				gui.updateBillboard(msg.file(), msg.filename(),msg.billboard());
		}
	}

}