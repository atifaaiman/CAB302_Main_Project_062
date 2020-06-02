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
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import common.Billboard;
import common.Message;
import common.MessageBuilder;
import common.Schedule;
import org.w3c.dom.ls.LSOutput;

/**
 * The Class Client to connect to the server, retrieve schedules and run viewers
 * according to the schedule times.
 */
public class Client {

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
			}
			else if (sh.getRepeat().equals("Day")) {
				minutes = 60 * 24;
			}
			else if (sh.getRepeat().startsWith("Minutes")) {
				minutes = Integer.parseInt(sh.getRepeat().split(":")[1]);
			}
			long millis = minutes * 60 * 1000;
			LocalDateTime limit = sh.getDateTime().plusMinutes(sh.getDuration());
			Timer tmr = new Timer();
			tmr.schedule(new TimerTask() {
				GUI viewer = null;

				@Override
				public void run() {
					if (LocalDateTime.now().isBefore(limit) || LocalDateTime.now().isEqual(limit)) {
						SwingUtilities.invokeLater(() -> {
							if (viewer == null) viewer = new GUI("Billboard Viewer", sh.getIdBillboard());
							viewer.setVisible(true);
						});
					} else {

						// If there is no Billboard scheduled set name to "" GUI look at GUI CLASS			  				       // New code by Fernando
							GUI viewer = null;																				       // New code by Fernando
							viewer = new GUI("Billboard Viewer", "");									   			   // New code by Fernando
							viewer.setVisible(true);																		       // New code by Fernando

						//if (viewer != null)  viewer.dispose(); 			// Close viewer after limit.						   // Old code
						//tmr.cancel(); 									// Cancel timer.									   // Old code
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
