package com.fbs.general;


import java.io.Serializable;

/**
 * @author Fernando Barbosa Silva
 * Create an Billboard object with the attributes required by the application.
 * Constructor (String billboard_name, String background_colour, String message_colour,
 *                      String message, String picture_type, String picture_data,
 *                      String information_colour, String information, String created_by)
 */
public class Billboard implements Serializable {

    private String billboard_name;
    private String background_colour;
    private String message_colour;
    private String message ;
    private String picture_type ;
    private String picture_data;
    private String information_colour;
    private String information;
    private String created_by;


    public Billboard(String billboard_name, String background_colour, String message_colour,
                     String message, String picture_type, String picture_data, String information_colour,
                     String information, String created_by){

        this.billboard_name = billboard_name;
        this.background_colour = background_colour;
        this.message_colour = message_colour;
        this.message = message;
        this.picture_type = picture_type;
        this.picture_data = picture_data;
        this.information_colour = information_colour;
        this.information = information;
        this.created_by = created_by;
    }

    /**
     * @author Fernando Barbosa Silva
     * @return billboard_name string
     */
    public String getBillboard_name() {
        return billboard_name;
    }

    /**
     * @author Fernando Barbosa Silva
     * @return the color of the background
     */
    public String getBackground_colour() {
        return background_colour;
    }

    /**
     * @author Fernando Barbosa Silva
     * @return the colour of the message
     */
    public String getMessage_colour() {
        return message_colour;
    }

    /**
     * @author Fernando Barbosa Silva
     * @return message string
     */
    public String getMessage() { return message; }

    /**
     * @author Fernando Barbosa Silva
     * @return the type of the image "url" or "data"
     */
    public String getPicture_type() { return picture_type; }

    /**
     * @author Fernando Barbosa Silva
     * @return url or image in Base64-encoding  string
     */
    public String getPicture_data() { return picture_data; }

    /**
     * @author Fernando Barbosa Silva
     * @return colour of the information message
     */
    public String getInformation_colour() { return information_colour; }

    /**
     * @author Fernando Barbosa Silva
     * @return information message string
     */
    public String getInformation() { return information; }

    /**
     * @author Fernando Barbosa Silva
     * @return user_name of the person who has created the billboard.
     */
    public String getCreated_by() {
        return created_by;
    }

}
