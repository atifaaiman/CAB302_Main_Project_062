package com.fbs.general;


/**
 * @author Fernando Barbosa Silva
 * @param xmlValues strings array, provide the information to be inserted in the xml.
 * xml constructor attributes => {"backgroundColour","messageColour", "message", "pictureType",
 *                  "pictureData", "informationColour", "information" };
 * @return returns xml string with all information used by the billboard applications.
 */
public class Xml {

    private String backgroundColour;
    String messageColour;
    String message;
    String pictureType;
    String pictureData;
    String informationColour;
    String information;

    public Xml( String backgroundColour, String messageColour, String message, String pictureType,
                String pictureData, String informationColour, String information){

        this.backgroundColour = backgroundColour;
        this.messageColour = messageColour;
        this.message = message.replace("'", "''");
        this.pictureType = pictureType;
        this.pictureData = pictureData;
        this.informationColour = informationColour;
        this.information = information;
    }

    public  String getXmlString() {

        String xml = "<?xml version=\"1.0\" encoding =\"UTF-8\"?>\n" +
                "<billboard background=\""+ backgroundColour + "\">\n" +
                "<message colour=\""+ messageColour + "\">" + message + "</message>\n" +
                "<picture \""+pictureType+"\"=\""+ pictureData +"\" />\n" +
                "<information colour=\""+ informationColour +"\">" + information + "</information>\n" +
                "</billboard>";
        return xml;
    }

    public String getBackgroundColour() {
        return backgroundColour;
    }

    public String getMessageColour() {
        return messageColour;
    }

    public String getMessage() {
        return message;
    }

    public String getPictureType() {
        return pictureType;
    }

    public String getPictureData() {
        return pictureData;
    }

    public String getInformationColour() {
        return informationColour;
    }

    public String getInformation() {
        return information;
    }




}
