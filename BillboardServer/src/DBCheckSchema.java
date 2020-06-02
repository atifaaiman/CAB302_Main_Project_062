

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCheckSchema {

    /**
     * @author Fernando Barbosa Silva
     * Check if the database exits using the information from the 'db.props', if it
     * exists the method return true, otherwise it returns false.
     * @return true if the database schema exists and false if it does not exist.
     * @throws SQLException if the connection to the database fails.
     */
    public static Boolean checkDatabase (String databaseName) {

        // Get information from the db.props file
        DBPropsFileRead DBProps = new DBPropsFileRead();
        String url = DBProps.getURL();
        String schema = DBProps.getSchema();
        String username = DBProps.getUsername();
        String password = DBProps.getPassword();

        // Messages to the user
        String messageNotFound = "Database 'cab302' not found. " +
                "\nPlease create the database 'cab302' and check the configuration of the 'db.props' file. ";
        String messageFound = "Database " + schema + " found.";

        try {
            Connection connection = DriverManager.getConnection(url + schema, username, password);
            ResultSet resultSet = null;
            resultSet = connection.getMetaData().getCatalogs();

            while (true) {

                if (!resultSet.next()) break;
                String database = null;
                database = resultSet.getString(1);
                //System.out.println(database);
                Boolean databaseExist = database.equals(databaseName);
                if (databaseExist) {
                    System.out.println(messageFound);
                    connection.close();
                    return true;
                }
            }
            System.out.println(messageNotFound);
            connection.close();
            return false;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("\n" + messageNotFound);
            return false;
        }
    }
}