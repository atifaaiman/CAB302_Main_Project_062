package com.fbs.general;

import java.time.format.DateTimeFormatter;
import java.util.Date;


/**
 * @author Fernando Barbosa Silva
 * Create an Billboard object with the attributes required by the application.
 * Constructor (int schedule_id, String billboard_name,Date date_time_start,Date date_time_finish,
 *                     String schedule_create_by, Date schedule_create_date)
 */
public class Schedule {

    private int schedule_id;
    private String billboard_name;
    private String date_time_start;
    private String date_time_finish;
    private String schedule_create_by;
    private String schedule_create_date;

    // Contructor use
    public Schedule(int schedule_id ,String billboard_name,String date_time_start,String date_time_finish,
                    String schedule_create_by, String schedule_create_date){
        this.schedule_id            = schedule_id;
        this.billboard_name         = billboard_name;
        this.date_time_start        = date_time_start;
        this.date_time_finish       = date_time_finish;
        this.schedule_create_by     = schedule_create_by;
        this.schedule_create_date   = schedule_create_date;
    }

    public Schedule(String billboard_name,String date_time_start,String date_time_finish,
                    String schedule_create_by){
        this.schedule_id            = 0;
        this.billboard_name         = billboard_name;
        this.date_time_start        = date_time_start;
        this.date_time_finish       = date_time_finish;
        this.schedule_create_by     = schedule_create_by;
        this.schedule_create_date   = "";
    }

    /**
     * @author Fernando Barbosa Silva
     * @return billboard_id integer.
     */
    public int getSchedule_id() { return schedule_id;}

    /**
     * @author Fernando Barbosa Silva
     * @return billboard_name string
     */
    public String getBillboard_name() { return billboard_name; }

    /**
     * @author Fernando Barbosa Silva
     * @return Date type variable with the date and time which the billboard must
     * start been presented.
     */
    public String getDate_time_start() {
        return date_time_start;
    }

    /**
     * @author Fernando Barbosa Silva
     * @return Date type variable with the date and time which the billboard must
     * finish been presented.
     */
    public String getDate_time_finish() {
        return date_time_finish;
    }

    /**
     * @author Fernando Barbosa Silva
     * @return the user_name of the person who created the schedule in a string format.
     */
    public String getSchedule_create_by() {
        return schedule_create_by;
    }

    /**
     * @author Fernando Barbosa Silva
     * @return Date type variable with the date and time which the billboard was created.
     */
    public String getSchedule_create_date() {
        return schedule_create_date;
    }
}
