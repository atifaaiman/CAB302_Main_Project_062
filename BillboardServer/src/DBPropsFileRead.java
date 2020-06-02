

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class DBPropsFileRead {
    private String url ="";
    private String schema = "";
    private String username = "";
    private String password = "";

    public DBPropsFileRead(){
        setVariables();
    }

    /**
     * @author Fernando Barbosa Silva
     * Read db.props file and put the values in variables that can be used by the applaication.
     * @throws FileNotFoundException if the file is not found.
     * @throws IOException if that is some read/write error.
     */
    private void setVariables () {
        Properties props = new Properties();
        FileInputStream in = null;

        try {
            in = new FileInputStream("./db.props");
            props.load(in);
            in.close();

            url = props.getProperty("jdbc.url");
            schema = props.getProperty("jdbc.schema");
            username = props.getProperty("jdbc.username");
            password = props.getProperty("jdbc.password");
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * @author Fernando Barbosa Silva
     * Read db.props file and return url used to connect to the database .
     * @return String "url" from db.props file
     */
    public String getURL(){
        return url;
    }

    /**
     * @author Fernando Barbosa Silva
     * Read db.props file and return schema name used to connect to the database .
     * @return String "schema" from db.props file
     */
    public String getSchema(){
        return schema;
    }


    /**
     * @author Fernando Barbosa Silva
     * Read db.props file and return the userName used to connect to the database .
     * @return String "username" from db.props file
     */
    public String getUsername(){
        return username;
    }

    /**
     * @author Fernando Barbosa Silva
     * Read db.props file and return the userName used to connect to the database .
     * @return String "password" from db.props file
     */
    public String getPassword(){
        return password;
    }

}
