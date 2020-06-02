package com.fbs.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {

    // Get information from db.props file
    DBPropsFileRead DBProps = new DBPropsFileRead();
    String url = DBProps.getURL();
    String schema = DBProps.getSchema();
    String username = DBProps.getUsername();
    String password = DBProps.getPassword();

    // Create an instance of the database connection.
    private static Connection instance = null;

    /**
     * @author Fernando Barbosa Silva
     * Material from lecture 7 Database
     * Create a connection to the database using the parameters specified in the
     * 'db.props' file.
     * @throws SQLException if the connection to the database fails.
     */
    private DBConnection() {
        try {
            // get a connection
            instance = DriverManager.getConnection(url + "/" + schema,
                    username, password);
        } catch (SQLException e) {
            System.out.println("Connection Error. Please check the 'db.props' file configuration " +
                    "and the database connection configuration.");
            System.out.println(e.getMessage());
        }
    }

    /**
     * @author Fernando Barbosa Silva
     * Material from lecture 7
     * Provides global access to the singleton instance of the UrlSet.
     * @return a handle to the singleton instance of the UrlSet.
     */
    public static Connection getInstance() {
        if (instance == null) {
            new DBConnection();
        }
        return instance;
    }

    /**
     * @author Fernando Barbosa Silva
     * Set instance to Null in order to show that the connection is closed.
     */
    public static void setInstanceToNull(){
        instance = null;
    }

    /**
     * @author Fernando Barbosa Silva
     * Get the connection status, open (1) or close (0).
     * @return int 0 if the connection is closed, return int 1 if the connection is open.
     */
    public static int getConnectionStatus(){
        if (instance == null) return 0;
        else return 1;
    }

}
