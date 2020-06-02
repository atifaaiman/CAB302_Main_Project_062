package com.fbs.db;

import com.fbs.general.User;
import com.fbs.general.UserPermissions;

import java.util.ArrayList;
import java.util.List;

import static com.fbs.db.DBExecuteQuery.*;


public class DBUsersTableQueries {

    /**
     * @author Fernando Barbosa Silva
     * Add new user in the database
     * @param user object with attributes ( user_name, password,administrator,create_billboards,
     *             edit_all_billboards, schedule_billboards, edit_users)
     * @return returns 1 if user was added successfully, returns -1 if user already exists,
     * returns 0 if user was not added because of some error.
     */
    public static int addUser(User user){

        // Query to add user in the database
        String ADD_USER_QUERY = "INSERT INTO " +
                "users  (       user_name              , password     ,   administrator  , create_billboards  , edit_all_billboards, schedule_billboards, edit_users)" +
                "VALUES ('"+user.getUser_name()+"','"+user.getPassword()+"','"+user.getAdministrator()+"','"+ user.getCreate_billboards() +"','"+
                            user.getEdit_all_billboards()+ "','" +  user.getSchedule_billboard()  + "','"+user.getEdit_users()+"')";

        int result = 0;
        if (executeUserExists(user.getUser_name())) return -1;
        try{
            result =  executeUpdate(ADD_USER_QUERY);
            return result;
        }catch (Exception e){
            return result;
        }
    }

    /**
     * @author Fernando Barbosa Silva
     * Add new user in the database
     * @param user_name  strings, provide the user_name that need to be updated
     * @param columnName  strings, provide the name of the columns which need to be updated
     * valid columns's names (password,administrator,create_billboards,edit_all_billboards,schedule_billboards,edit_users).
     * @param newValue  strings, provide the new value for the column that need to be updated.
     * values for user permission,administrator,create_billboards,edit_all_billboards,schedule_billboards,edit_users,
     * must be 'no' or 'yes', no other value is valid in the user table
     * @return returns 1 if user was updated successfully, returns -1 if user does not exist,
     * returns 0 if user was not updated because of some error.
     */
    public static int updateUser(String user_name, String columnName, String newValue){

        // Query to update user's information
        String update = "UPDATE users SET " + columnName.toLowerCase() + "='" + newValue + "'WHERE user_name = '"+
                user_name.toLowerCase() +"'";

        int result = 0;
        if (!executeUserExists(user_name)) return -1;
        result = executeUpdate(update);
        return result;
    }

    /**
     * @author Fernando Barbosa Silva
     * Delete user from the database
     * @param user_name  strings, provide the user_name that need to be deleted from the database
     * @return returns 1 if user was deleted successfully, returns -1 if user does not exist in the database,
     * returns 0 if user was not deleted because of some error.
     */
    public static int deleteUser(String user_name){

        // Query used to delete user from the database
        String deleteQuery = "DELETE FROM users WHERE user_name ='"+ user_name.toLowerCase() + "'";

        int result = 0;
        if (!executeUserExists(user_name)) return -1;
        result = executeUpdate(deleteQuery);
        return  result;
    }

    /**
     * @author Fernando Barbosa Silva
     * Get list of  all user's permissions from the database.
     * @return returns a List object with the permission of each user.
     */
    public static List getUserPermissionsList(){

        // Query used for retrieving all users from the database
        String query = "SELECT * FROM users";
        List<UserPermissions> userList = new ArrayList();

        List<UserPermissions> result = new ArrayList<>();
        result = executeGetUserPermissions(query);
        return result;
    }

    /**
     * @author Fernando Barbosa Silva
     * Get user's permissions from the database.
     * @param user_name string.
     * @return returns an object with the permission of  the user selected, if user does
     * not exist it return an empty object.
     */
    public static List getUserPermissions(String user_name){

        // Query used for retrieving all users from the database
        String query = "SELECT * FROM users where user_name ='"+user_name+"'";

        List<UserPermissions> result = new ArrayList<>();
        result = executeGetUserPermissions(query);
        return result;
    }


    // Main class to validate the code
    public static void main(String[] Args){

        User fernando = new User("fernandobs03", "1234", "yes","no", "no", "yes", "yes");

        System.out.println("Add user return status: " + addUser(fernando));
        /*
        List<User> list = getUserPermissionsList();
        System.out.println(list.toString());
        System.out.println(list.get(1));
        List<UserPermissions> permissions = getUserPermissions("fernandobs");
        System.out.println(permissions);
        if(!permissions.isEmpty() ){
            System.out.println(permissions.get(0).getUser_name());
        }

         */





    }
}
