
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.jupiter.api.Test;

import common.Message;
import common.MessageBuilder;

/**
 * The Class TestClientListener.
 */
class TestClientListener {

	/**
	 * Test run client listener.
	 * Tests TEST_COMMAND.
	 */
	@Test
	final void testRun() {
		new Thread(() -> {
			try (ServerSocket ss = new ServerSocket(3345)) {
			try (Socket client = ss.accept()) {
				try (ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream())) {					
					try (ObjectInputStream ois = new ObjectInputStream(client.getInputStream())) {
						oos.writeObject(MessageBuilder.build(Message.TEST_COMMAND));
						String response = (String)ois.readObject();
						assertEquals("Test success", response);
					}
				}
			}
		} catch (Exception exc) {}
		}).start();

			try (Socket s = new Socket("localhost", 3345)) {
				new Thread(new ClientListener(s)).start();					
				Thread.sleep(3000);
			} catch (Exception e) {}

	}

}
