package com.fbs.db;

import com.fbs.general.Billboard;
import com.fbs.general.UserPermissions;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fbs.db.DBExecuteQuery.*;

public class DBBillboardTableQueries {

    /**
     * @author Fernando Barbosa Silva
     * Add new user in the database
     * @param billboard object with the attributes (billboard_name, xml, create_by)
     * @return returns 1 if billboard was added successfully, return -1 if billboard_name
     * already exist in the database, returns 0 if billboard was not added because of some error.
     */
    public static int addBillboard(Billboard billboard){
        // Query to add billboard
        String ADD_BILLBOARD_QUERY = "INSERT INTO " +
                "billboards  (billboard_name,background_colour,message_colour,message,picture_type,picture_data,information_colour, information ,created_by)" +
                "VALUES ('"+billboard.getBillboard_name().toLowerCase()+ "','"+ billboard.getBackground_colour()+"','"+billboard.getMessage_colour()+ "'," +
                "'"+billboard.getMessage().replace("'", "''")+"','"+billboard.getPicture_type()+
                "','"+billboard.getPicture_data()+ "','"+billboard.getInformation_colour()+
                "','"+billboard.getInformation().replace("'", "''")+ "','"+billboard.getCreated_by()+"')";

        int result = 0;
        if( DBExecuteQuery.executeBillboardExists(billboard.getBillboard_name())) return -1;
        result =  executeUpdate(ADD_BILLBOARD_QUERY);
        return result;
    }

    /**
     * @author Fernando Barbosa Silva
     * Add new user in the database
     * @param billboard_name  strings, provide the name of the billboard that need to be updated
     * @param columnName  strings, provide the name of the columns which need to be updated
     * valid columns's names (billboard_name, xml).
     * @param newValue  strings, provide the new value for the column that need to be updated.
     * @return returns 1 if user was deleted successfully, returns  0 if user was not updated
     * because of some error, returns -1 if billboard does not exist in the database.
     */
    public static int updateBillboard(String billboard_name, String columnName, String newValue){

        // Query to update billboards's information
        String update = "UPDATE billboards SET " + columnName.toLowerCase() + "='" + newValue + "'WHERE billboard_name = '"+
                billboard_name.toLowerCase() +"'";

        int result = 0;
        if (!DBExecuteQuery.executeBillboardExists(billboard_name)) return -1;
        result = executeUpdate(update);
        return result;
    }

    /**
     * @author Fernando Barbosa Silva
     * Delete billboard from the database
     * @param billboard_name  strings, provide the billboard_name that need to be deleted
     * @return returns 1 if user was deleted successfully, returns  0 if user was not deleted
     * or found because of some error, or return -1 if billboard does no exist in the database.
     */
    public static int deleteBillboard(String billboard_name){

        // Query used to delete billboard from the database
        String deleteQuery = "DELETE FROM billboards WHERE billboard_name ='"+ billboard_name.toLowerCase() + "'";

        int result = 0;
        if (!DBExecuteQuery.executeBillboardExists(billboard_name)) return -1;
        result = executeUpdate(deleteQuery);
        return  result;
    }


    /**
     * @author Fernando Barbosa Silva
     * Get list of  all billboards from the database.
     * @return returns a List object with all billboards.
     */
    public static List getBillboardList(){

        // Query used for retrieving all users from the database
        String query = "SELECT * FROM billboards";
        List<UserPermissions> billboardList = new ArrayList();

        List<UserPermissions> result = new ArrayList<>();
        result = executeGetBillboard(query);
        return result;
    }

    /**
     * @author Fernando Barbosa Silva
     * Get a billboard from the database.
     * @param billboard_name string.
     * @return returns an object of the billboard selected, if billboard does
     * not exist it return an empty object.
     */
    public static List getBillboard(String billboard_name){

        // Query used for retrieving all users from the database
        String query = "SELECT * FROM billboard where billbooard_name='"+billboard_name+"'";

        List<UserPermissions> result = new ArrayList<>();
        result = executeGetBillboard(query);
        return result;
    }

    // Main class to validate the code
    public static void main(String[] Args){

        boolean test = DBExecuteQuery.executeBillboardExists("TesT_01");
        System.out.println("Billboard exist: " + test);

        Billboard billboard = new Billboard("Test_02","#0000FF","#FFFF00",
                "Welcome to the _____ Corporation's Annual Fundraiser!", "url",
                "https://example.com/fundraiser_image.jpg", "#00FFF",
                "Be sure to check out https://example.com/ for more information.","fernandobs");
        System.out.println("Add billboard status: " + addBillboard(billboard));


        //String newXML = Xml.createXmlString(xmlFormat_2);

        //System.out.println( "Update billboard status: "+ updateBillboard("billboard_05","xml",newXML));

       // System.out.println("Delete billboard status: " + deleteBillboard("billboard_01"));

       //Billboard billboard = new Billboard("billboard_02", newXML, "fernando");
       //System.out.println("Add billboard Status: " + addBillboard(billboard));

    List<Billboard> list = getBillboardList();
    for (Billboard item : list){
        System.out.println(item.getBillboard_name());
    }
    
    }


}
