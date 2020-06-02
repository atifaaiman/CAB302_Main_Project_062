import org.w3c.dom.Document;

import java.io.*;
import java.net.Socket;
import java.nio.file.Paths;

public class Main {
    /**
     * Main method to run the client program. Creates instance of {@link GUI}, makes
     * it visible and runs it on the AWT event dispatching thread.
     * @param args the command line arguments, not used in this program
     */
    public static void main(String[] args) {

         ReadPropsFile rpf = new ReadPropsFile();
         String host = rpf.getHost();
         int port    = rpf.getPort();

        // Create Connection with the server
        try {
            Socket serverSocket = new Socket(host, port);
            // If server is connected start GUI :                                                                       //Added by Fernando - OK
            GUI viewer = null;
            viewer = new GUI("Billboard Viewer");
            viewer.setVisible(true);

        }
        catch (IOException e) {
            // If server is not connected show Billboard Error Message :                                                 //Added by Fernando - OK
            GUI viewer = null;
            viewer = new GUI("Billboard Viewer");
            try {
                Document doc = viewer.parseXML(Paths.get("./billboard03.xml"));
                viewer.updateBillboard(doc);
            } catch (Exception e1){
                System.out.println(e1.getMessage());
            }
            viewer.setVisible(true);
        }
        catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }
}
