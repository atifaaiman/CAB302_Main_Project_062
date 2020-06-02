import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * The Class TestServer.
 */
class TestServer {

	/** The Constant out. */
	private static final ByteArrayOutputStream out = new ByteArrayOutputStream();
	
	/** The Constant initOut. */
	private static final PrintStream initOut = System.out;

	/**
	 * Sets the up streams.
	 */
	@BeforeAll
	public static void setUpStreams() {
		System.setOut(new PrintStream(out));
	}

	/**
	 * Restore streams.
	 */
	@AfterAll
	public static void restoreStreams() {
		System.setOut(initOut);
	}

	/**
	 * Test start on port.
	 * Waits 3 seconds only.
	 */
	@Test
	final void testStartOnPort() {
		
		new Thread(() -> {
			Server server = new Server();
			server.start(3223);
		}).start();
		try {
			Thread.sleep(3000);
			assertEquals("Server is running", out.toString().trim());
		} catch (InterruptedException e) {
			fail(e.getMessage());
		}

	}

}
