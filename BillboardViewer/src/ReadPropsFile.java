import java.io.*;

import java.util.Properties;



public class ReadPropsFile {

    private String host = "";
    private int port = 0;

    public ReadPropsFile(){
        readeProps();
    }

    /**
     * @author Fernando Barbosa Silva
     *  Method reads the network props file and set the variables host and port
     */
    private  void readeProps() {

        // Read network.props file to obtain host and port to connect to.
        try (InputStream fileStream = new FileInputStream(GUI.NETWORK_PROPERTIES_FILENAME)) {

            Properties props = new Properties();
            props.load(fileStream);
            this.host = props.getProperty("host");
            this.port = Integer.parseInt(props.getProperty("port"));

        } catch (FileNotFoundException e1) {
            System.out.println(e1.getMessage());
        } catch (IOException e2) {
            System.out.println(e2.getMessage());
        }
    }

    /**
     * @author Fernando
     * Get the host address from the props file.
     * @return  a string with the address.
     */
    public String getHost() {
        return host;
    }

    /**
     * @author Fernando
     * Get the prot number from the props file.
     * @return  port number in a integer type.
     */
    public int getPort() {
        return port;
    }


}






