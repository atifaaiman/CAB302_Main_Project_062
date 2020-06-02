import common.Billboard;
import common.Message;
import common.MessageBuilder;
import common.Schedule;
import org.w3c.dom.Document;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import static common.Message.SCHEDULES;

public class Main {
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
            System.out.println("Port is ok: Line 50.");  																// Debug Fernando

            // Create Connection with the server
            try {
                Socket serverSocket = new Socket(host, port);

                try (ObjectOutputStream oos = new ObjectOutputStream(serverSocket.getOutputStream())) {

                    // Special token "viewer" allows to get all schedules.
                    oos.writeObject(MessageBuilder.build(null, null, SCHEDULES, null, null,
                            "viewer", null, null, null,
                            null, null, null, null));

                    // Get response from the server
                    try (ObjectInputStream ois = new ObjectInputStream(serverSocket.getInputStream())) {
                        Message msg = (Message) ois.readObject();
                        List<Schedule> schedules = msg.schedules();

                        for (Schedule sh : schedules) {
                            Client.runViewer(sh);
                        }
                    } catch (ClassNotFoundException e) {
                        Client.displayError(e.getMessage());
                    }
                } finally {
                    if (serverSocket != null) {
                        serverSocket.close();
                    }
                }
            } catch (UnknownHostException e) {
                Client.displayError("Host found in " + GUI.NETWORK_PROPERTIES_FILENAME + " (" + host + ") is not valid.");
            } catch (IOException e) {

                // If server is not connected show Message :                                                            Added by Fernando - OK
                GUI viewer = null;
                viewer = new GUI("Billboard Viewer", "");
                try {
                    Document doc = viewer.parseXML(Paths.get("./billboard03.xml"));
                    viewer.updateBillboard(doc);
                }catch (Exception e1){
                    System.out.println(e1.getMessage());
                }
                viewer.setVisible(true);

                //displayError(e.getMessage());				                                                            // Original codeComment by Fernando

            } catch (NumberFormatException e) {
                Client.displayError("Port found in " + GUI.NETWORK_PROPERTIES_FILENAME + " (" + props.getProperty("port")
                        + ") is not valid.");
            }
        } catch (FileNotFoundException e1) {
            Client.displayError(GUI.NETWORK_PROPERTIES_FILENAME + " file with host and port to connect to "
                    + "the server was not found.");
        } catch (IOException e1) {
            Client.displayError(e1.getMessage());
        } catch (Exception e){						     // Create by Fernando
            System.out.println("Exception e: Line 92");  // Debug Fernando
        }
    }
}
