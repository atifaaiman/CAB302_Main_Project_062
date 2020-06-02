import static common.Message.SCHEDULES;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimerTask;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import common.Message;
import common.MessageBuilder;
import common.Schedule;

/**
 * The Class Client to connect to the server, retrieve schedules and run viewers
 * according to the schedule times.
 */
public class Client {

	/**
	 * Main method to run the client program. Creates instance of {@link GUI}, makes
	 * it visible and runs it on the AWT event dispatching thread.
	 * 
	 * @param args the command line arguments, not used in this program
	 */
	public static void main(String[] args) {

		// Read network.props file to obtain host and port to connect to.
		try (InputStream fileStream = new FileInputStream(GUI.NETWORK_PROPERTIES_FILENAME)) {

			Properties props = new Properties();

			props.load(fileStream);

			String host = props.getProperty("host");

			if (host == null) {
				throw new UnknownHostException();
			}

			int port = Integer.parseInt(props.getProperty("port"));

			try {

				Socket serverSocket = new Socket(host, port);

				try (ObjectOutputStream oos = new ObjectOutputStream(serverSocket.getOutputStream())) {

					// Special token "viewer" allows to get all schedules.
					oos.writeObject(MessageBuilder.build(null, null, SCHEDULES, null, null, "viewer", null, null, null,
							null, null, null, null));

					try (ObjectInputStream ois = new ObjectInputStream(serverSocket.getInputStream())) {
						Message msg = (Message) ois.readObject();

						List<Schedule> schedules = msg.schedules();
						for (Schedule sh : schedules) {
							runViewer(sh);
						}
					} catch (ClassNotFoundException e) {
						displayError(e.getMessage());
					}
				} finally {
					if (serverSocket != null) {
						serverSocket.close();
					}
				}
			} catch (UnknownHostException e) {
				displayError("Host found in " + GUI.NETWORK_PROPERTIES_FILENAME + " (" + host + ") is not valid.");
			} catch (IOException e) {
				displayError(e.getMessage());
			} catch (NumberFormatException e) {
				displayError("Port found in " + GUI.NETWORK_PROPERTIES_FILENAME + " (" + props.getProperty("port")
						+ ") is not valid.");
			}

		} catch (FileNotFoundException e1) {
			displayError(GUI.NETWORK_PROPERTIES_FILENAME + " file with host and port to connect to "
					+ "the server was not found.");
		} catch (IOException e1) {
			displayError(e1.getMessage());
		}
	}

	/**
	 * Runs viewer.
	 *
	 * @param sched the schedule
	 */
	public static void runViewer(Schedule sched) {

		new Thread(() -> {
			final Schedule sh = sched;
			int minutes = 0;
			if (sh.getRepeat().equals("Hour")) {
				minutes = 60;
			} else if (sh.getRepeat().equals("Day")) {
				minutes = 60 * 24;
			} else if (sh.getRepeat().startsWith("Minutes")) {
				minutes = Integer.parseInt(sh.getRepeat().split(":")[1]);
			}
			long millis = minutes * 60 * 1000;
			LocalDateTime limit = sh.getDateTime().plusMinutes(sh.getDuration());
			java.util.Timer tmr = new java.util.Timer();
			tmr.schedule(new TimerTask() {
				GUI viewer = null;

				@Override
				public void run() {

					if (LocalDateTime.now().isBefore(limit) || LocalDateTime.now().isEqual(limit)) {

						SwingUtilities.invokeLater(() -> {
							if (viewer == null)
							viewer = new GUI("Billboard Viewer", sh.getIdBillboard());
							viewer.setVisible(true);
						});
					} else {
						if (viewer != null)
							viewer.dispose(); // Close viewer after limit.
						tmr.cancel(); // Cancel timer.
					}
				}

			}, Date.from(sched.getDateTime().atZone(ZoneId.systemDefault()).toInstant()), millis);
		}).start();
	}

	/**
	 * Displays error.
	 *
	 * @param error the error
	 */
	public static void displayError(String error) {
		JOptionPane.showMessageDialog(null, error, "Failed", JOptionPane.ERROR_MESSAGE);
	}

}
