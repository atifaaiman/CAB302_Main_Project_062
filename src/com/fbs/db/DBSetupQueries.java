package com.fbs.db;

/**
 * @author Fernando Barbosa Silva
 * Query to create table users
 */
public abstract class DBSetupQueries {

    /**
     * @author Fernando Barbosa Silva
     * Queries used to setup the database tables used by the application.
     */
    public static final String CREATE_TABLE_USERS = "CREATE TABLE  user (" +
            "   username                    VARCHAR (45)," +
            "   password                    VARCHAR (255) NOT NULL ," +
            "   permission                  VARCHAR (45) NOT NULL ," +
            "   administrator               ENUM('no', 'yes') DEFAULT 'no' ," +
            "   create_billboards           ENUM('no', 'yes') DEFAULT 'no' ," +
            "   edit_all_billboards         ENUM('no', 'yes') DEFAULT 'no' ," +
            "   schedule_billboards         ENUM('no', 'yes') DEFAULT 'no' ," +
            "   edit_users                  ENUM('no', 'yes') DEFAULT 'no' ," +
            "   PRIMARY KEY (username))";

    /**
     * @author Fernando Barbosa Silva
     * Query to create table billboards
     */
    public static final String CREATE_TABLE_BILLBOARDS = "CREATE TABLE billboard ( " +
            "   name_billboard              VARCHAR (45) NOT NULL," +
            "   xml_data                    LONGBLOB NOT NULL," +
            "   username                    VARCHAR (45),"+
            "   PRIMARY KEY (name_billboard ))";
    /**
     * @author Fernando Barbosa Silva
     * Query to create table schedule
     */
    public static final String CREATE_TABLE_SCHEDULES = "CREATE TABLE schedules (" +
            "   schedule_id                 INT NOT NULL AUTO_INCREMENT," +
            "   billboard_name              VARCHAR (255) NOT NULL," +
            "   date_time_start             DATETIME NOT NULL," +
            "   date_time_finish            DATETIME NOT NULL," +
            "   schedule_create_by          VARCHAR (255) NOT NULL," +
            "   schedule_create_date        DATETIME NOT NULL DEFAULT ( CURRENT_TIMESTAMP() ),"+
            "   PRIMARY KEY (schedule_id ))";

    /**
     * @author Fernando Barbosa Silva
     * Query to create the default administrator user and hes permission.
     */
    public static final String CREATE_DEFAULT_ADMINISTRATOR_USER = "INSERT INTO users " +
            "(user_name, password, administrator, create_billboards, edit_all_billboards, schedule_billboards, edit_users)" +
            "VALUES ('admin', 'admin2020', 'yes' ,'yes', 'yes', 'yes','yes' )";

    // Query to delete table users
    public static final String DELETE_TABLE_USERS = "DROP TABLE users";

    // Query to delete table billboards
    public static final String DELETE_TABLE_BILLBOARDS = "DROP TABLE billboards";

    // Query to delete table schedules
    public static final String DELETE_TABLE_SCHEDULES = "DROP TABLE schedules";

    // The the connections open with the database
    public  static final String STATUS = "show status where `variable_name` = 'Threads_connected'";

}