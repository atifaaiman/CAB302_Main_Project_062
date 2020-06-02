package com.fbs.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static com.fbs.db.DBConnection.*;


public class DBDisplayContent {
    // Queries to display information from the database;
    public static final String SELECT = "SELECT * FROM users";

    /**
     * @author Fernando Barbosa Silva
     * Material from lecture 7
     * Display content from the queries passes in the method
     * @param query is the SQL query in a string format. Query Example: 'SELECT * FROM users'.
     * @return in a table format all information retrieved from the database.
     * @throws SQLException if there is some problem with the query or connection.
     */
    public static void displayContents(String query)  {

        try{
            // get connection
            Connection connection = DBConnection.getInstance();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            // use metadata to get the number of columns
            int columnCount = rs.getMetaData().getColumnCount();

            // output the column names
            for (int i = 0; i < columnCount; i++) {
                System.out.printf("%-20s", rs.getMetaData().getColumnName(i + 1));
            }
            System.out.printf("%n");

            // output each row
            while (rs.next()) {
                for (int i = 0; i < columnCount; i++) {
                    System.out.printf("%-20s", rs.getString(i + 1));
                }
                System.out.printf("%n");
            }
            System.out.printf("%n");

            st.close();
            connection.close();
            setInstanceToNull();

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    static public void main (String [] Args){
        displayContents("Select * from billboards");
    }

}
