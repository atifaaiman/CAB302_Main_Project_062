package com.fbs.general;


import java.io.Serializable;

/**
 * @author Fernando Barbosa Silva
 * Create an UserPermission object with the attributes required by the application.
 * Constructor (user_name,administrator, create_billboards,edit_all_billboards, schedule_billboards, edit_users )
 */
public class UserPermissions implements Serializable {

    private String user_name;
    private String administrator;
    private String create_billboards;
    private String edit_all_billboards;
    private String schedule_billboards;
    private String edit_users;

    public UserPermissions(String user_name, String administrator, String create_billboards,
                           String edit_all_billboards, String schedule_billboards, String edit_users){

        this.user_name = user_name;
        this.administrator = administrator;
        this.create_billboards = create_billboards;
        this.edit_all_billboards = edit_all_billboards;
        this.schedule_billboards = schedule_billboards;
        this.edit_users = edit_users;
    }


    /**
     * @author Fernando Barbosa Silva
     * @return user_name string.
     */
    public String getUser_name(){
        return user_name;
    }

    /**
     * @author Fernando Barbosa Silva
     * @return administration permission in a string format "yes" or "no" .
     */
    public String getAdministrator(){
        return administrator;
    }

    /**
     * @author Fernando Barbosa Silva
     * @return create_billboards permission in a string format "yes" or "no" .
     */
    public String getCreate_billboards(){
        return create_billboards;
    }

    /**
     * @author Fernando Barbosa Silva
     * @return edit_all_billboards permission in a string format "yes" or "no" .
     */
    public String getEdit_all_billboards(){
        return edit_all_billboards;
    }

    /**
     * @author Fernando Barbosa Silva
     * @return schedule_billboards permission in a string format "yes" or "no" .
     */
    public String getSchedule_billboard(){
        return schedule_billboards;
    }
    /**
     * @author Fernando Barbosa Silva
     * @return edit_users permission in a string format "yes" or "no" .
     */
    public String getEdit_users(){
        return edit_users;
    }

    /**
     * @author Fernando Barbosa Silva
     * Override the toString method.
     * @return user information in the format "["+user_name+","+administrator+","+create_billboards+",
     * "+edit_all_billboards+","+schedule_billboards+","+edit_users+"]"
     */
    @Override
    public String toString() {
        return "["+user_name+","+administrator+","+create_billboards+","+edit_all_billboards+","+schedule_billboards+","+edit_users+"]";
    }
}
