package com.fbs.db;

import com.fbs.general.Billboard;
import com.fbs.general.Schedule;
import com.fbs.general.UserPermissions;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.fbs.db.DBConnection.*;

public class DBExecuteQuery {

    // Method to execute queries in the database
    /**
     * @author Fernando Barbosa Silva
     * Execute a query in the database
     * @param query is the SQL query in a string format.This method can be used for all types
     * of SQL statements
     * @throws SQLException exception if there is some problem in the query or database connection.
     * @return a boolean value. TRUE indicates that statement has returned a ResultSet object and FALSE
     * indicates that statement has returned an int value or returned nothing.
     */
    public static boolean execute(String query)  {
        boolean result = false;
        try {
            Connection connection = DBConnection.getInstance();
            Statement statement = connection.createStatement();
            result = statement.execute(query);
            statement.close();
            connection.close();
            setInstanceToNull();
            return result;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     * @author Fernando Barbosa Silva
     * Execute a query in the database
     * @param query is the SQL query in a string format. The method accepts only non-select statements.
     * @throws SQLException exception if there is some problem in the query or database connection.
     * @return int value representing number of records affected; Returns 0 if the query returns nothing.
     */
    public static int executeUpdate(String query)  {
        int rowsAffected = 0;
        try {
            Connection connection = DBConnection.getInstance();
            Statement statement = connection.createStatement();
            rowsAffected = statement.executeUpdate(query);
            statement.close();
            connection.close();
            setInstanceToNull();
            return rowsAffected;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected;
    }

    /**
     * @author Fernando Barbosa Silva
     * Execute a query in the database
     * @param query is the SQL query in a string format. The method accepts only non-select statements.
     * @throws SQLException exception if there is some problem in the query or database connection.
     * @return a resultSet object which contains the result returned by the query.
     */
    public static void executeQuery(String query)  {
        try {
            Connection connection = DBConnection.getInstance();
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
            statement.close();
            connection.close();
            setInstanceToNull();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @author Fernando Barbosa Silva
     * Get user's permissions from the database.
     * @return returns an object with the permission of  the users, returns an empty object if
     * no users were found in the database.
     * @throws SQLException if there is an error in the query or database connection.
     */
    public static List executeGetUserPermissions(String query){
        List<UserPermissions> userList = new ArrayList<>();

        try{
            // get connection
            Connection connection = DBConnection.getInstance();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Check each row of the table
            while (resultSet.next()) {
                String userName = resultSet.getString(2);
                String administrator= resultSet.getString(4);
                String createBillboards =  resultSet.getString(5);
                String editAllBillboards = resultSet.getString(6);
                String scheduleBillboards= resultSet.getString(7);
                String editUsers = resultSet.getString(8);
                UserPermissions userPermissions = new UserPermissions(userName,administrator,createBillboards,
                        editAllBillboards, scheduleBillboards, editUsers);
                userList.add(userPermissions);
            }
            statement.close();
            connection.close();
            setInstanceToNull();
            return userList;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return userList;
    }

    /**
     * @author Fernando Barbosa Silva
     * Get billboards from the database.
     * @return an object with the billboards, returns an empty object if
     * no users were found in the database.
     * @throws SQLException if there is an error in the query or database connection.
     */
    public static List executeGetBillboard(String query){
        List<Billboard> billboardList = new ArrayList<>();

        try{
            // get connection
            Connection connection = DBConnection.getInstance();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Check each row of the table
            while (resultSet.next()) {
                String billboardName = resultSet.getString(2);
                String backgroundColour= resultSet.getString(3);
                String messageColour =  resultSet.getString(4);
                String message =  resultSet.getString(5);
                String pictureType =  resultSet.getString(6);
                String pictureData =  resultSet.getString(7);
                String informationColour =  resultSet.getString(8);
                String information =  resultSet.getString(9);
                String createdBy =  resultSet.getString(10);
                Billboard billboard = new Billboard(billboardName,backgroundColour,messageColour,message,
                        pictureType,pictureData,informationColour, information ,createdBy);
                billboardList.add(billboard);
            }
            statement.close();
            connection.close();
            setInstanceToNull();
            return billboardList;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return billboardList;
    }

    /**
     * @author Fernando Barbosa Silva
     * Get schedules from the database.
     * @return an object with the schedules, returns an empty object if
     * no users were found in the database.
     * @throws SQLException if there is an error in the query or database connection.
     */
    public static List<Schedule> executeGetSchedule(String query) {
        List<Schedule> scheduleList = new ArrayList<>();

        try{
            // get connection
            Connection connection = DBConnection.getInstance();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Check each row of the table
            while (resultSet.next()) {
                int scheduleId = resultSet.getInt(1);
                String billboardName= resultSet.getString(2);
                String dateTimeStart = String.valueOf(resultSet.getDate(3));
                String dateTimeFinish = String.valueOf(resultSet.getDate(4));
                String scheduleCreatedBy =  resultSet.getString(5);
                String scheduleCreateDate = String.valueOf(resultSet.getDate(6));
                Schedule schedule = new Schedule(scheduleId,billboardName,dateTimeStart,dateTimeFinish,scheduleCreatedBy,scheduleCreateDate);
                scheduleList.add(schedule);
            }
            statement.close();
            connection.close();
            setInstanceToNull();
            return scheduleList;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return scheduleList;
    }

    /**
     * @author Fernando Barbosa Silva
     * Check if a billboard is in the database using the unique billboard_name.
     * @param billboard_name  strings, provide the user_name that need to be checked.
     * @return returns true if billboard_name exist in the database, returns false if it does not exist.
     * @throws SQLException if there is an error in the query or database connection.
     */
    public static boolean executeBillboardExists(String billboard_name){

        // Query used for retrieving all billboards from the database
        String query = "SELECT billboard_name FROM billboards";

        try{
            // get connection
            Connection connection = getInstance();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            // Check each row
            while (rs.next()) {
                String billboard = rs.getString(1);
                if (billboard.equals(billboard_name.toLowerCase())) {
                    st.close();
                    connection.close();
                    setInstanceToNull();
                    return true;
                }
            }
            st.close();
            connection.close();
            setInstanceToNull();
            return false;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        //return false;
    }

    /**
     * @author Fernando Barbosa Silva
     * Check if a user is in the database.
     * @param user_name  strings, provide the user_name that need to be checked.
     * @return returns true if user exist in the database, returns false if user does not exist.
     * @throws SQLException if there is an error in the query or database connection.
     */
    public static boolean executeUserExists(String user_name){
        // Query used for retrieving all users from the database
        String query = "SELECT user_name FROM users";

        try{
            // get connection
            //Connection connection = getInstance();
            Connection connection = DBConnection.getInstance();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            // Check each row
            boolean status = false;
            while (rs.next()) {
                String user = rs.getString(1);
                //System.out.println( user);
                if (user.equals(user_name.toLowerCase())) {
                    st.close();
                    connection.close();
                    setInstanceToNull();
                    return true;
                }
            }
            st.close();
            connection.close();
            setInstanceToNull();
            return false;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}
