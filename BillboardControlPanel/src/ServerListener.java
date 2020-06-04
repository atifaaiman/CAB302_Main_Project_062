import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import common.Message;

/**
 * Listens for the responses from server.
 * Creates two handlers: {@link InputCommandHandler} to
 * handle incoming messages, {@link OutputCommandHandler} to
 * handle outgoing messages.
 */
public class ServerListener implements Runnable {

	/** The server socket. */
	private Socket serverSocket;
	/** The input command handler. */
	private InputCommandHandler inputCommandHandler;
	/** The output command handler. */
	private OutputCommandHandler outputCommandHandler;

	/**
	 * Instantiates a new server listener.
	 *
	 * @param serverSocket the server socket
	 */
	public ServerListener(Socket serverSocket) {
		this.serverSocket = serverSocket;
	}

	/**
	 * Runs the listener.
	 */
	@Override
	public void run() {

		try (ObjectOutputStream oos = new ObjectOutputStream(serverSocket.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(serverSocket.getInputStream())) {

			// Pass output stream to handler for callback the server.
			outputCommandHandler.setOos(oos);

			while (true) {
				Message msg = (Message) ois.readObject();
				inputCommandHandler.processCommand(msg);
				System.out.println(msg.command()); 																		// Added by Fernando
			}

			// Case when server disconnects.
		} catch (IOException e) {
			System.out.println("No server");

		} catch (ClassNotFoundException e) {
			GUI.displayError("SERVER IS NOT AVAILABLE.\nPlease contact the system administrator.");
		} finally {
			try {
				if (serverSocket != null) {
					serverSocket.close();
				}
			} catch (IOException e) {
				GUI.displayError("SERVER IS NOT AVAILABLE.\nPlease contact the system administrator.");
			}
		}
	}

	/**
	 * Sets the output command handler.
	 * @param outputCommandHandler the new output command handler
	 */
	public void setOutputCommandHandler(OutputCommandHandler outputCommandHandler) {
		this.outputCommandHandler = outputCommandHandler;
	}

	/**
	 * Sets the input command handler.
	 * @param inputCommandHandler the new input command handler
	 */
	public void setInputCommandHandler(InputCommandHandler inputCommandHandler) {
		this.inputCommandHandler = inputCommandHandler;
	}

}
