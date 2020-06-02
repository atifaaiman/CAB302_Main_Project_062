package com.fbs.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.fbs.db.DBSetupQueries.*;
import static com.fbs.db.DBExecuteQuery.*;
import static com.fbs.db.DBConnection.*;

public class DBCheckTables {

    private static  String [] tablesName = {"users", "billboards", "schedules"};

    /**
     * @author Fernando Barbosa Silva
     * Check if the tables used by the application exits, if it
     * exists the method just print the message "Tables exist", otherwise it prints
     * the message "Tables does not exist" and creates the tables that does not exist.
     * @return true if the database schema exists and false if it does not exist.
     * @throws SQLException if the connection to the database fails.
     */
    public static void checkTables() {

        try{
            for ( int i = 0 ; i < tablesName.length ; i++){
                Connection db = DBConnection.getInstance();
                DatabaseMetaData dbm = db.getMetaData();

                ResultSet rs = dbm.getTables(null, null, tablesName[i], null);
                if (rs.next()) {
                    System.out.println("Table " + tablesName[i] + " found");
                }
                else {
                    System.out.println("Table "  + tablesName[i] + " not found");
                    if(tablesName[i].equals("users")) {
                        execute(CREATE_TABLE_USERS);
                        executeUpdate(CREATE_DEFAULT_ADMINISTRATOR_USER);
                    }
                    if(tablesName[i].equals("billboards")) execute(CREATE_TABLE_BILLBOARDS);
                    if(tablesName[i].equals("schedules")) execute(CREATE_TABLE_SCHEDULES);
                    System.out.println("Table " + tablesName[i] + " created");
                }
                db.close();
                setInstanceToNull();
            }
        }
        catch (Exception e){
            System.out.println("DBCheckTables.java");
            System.out.println(e);
        }
    }

}


