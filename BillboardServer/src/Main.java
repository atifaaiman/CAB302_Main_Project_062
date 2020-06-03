import common.User;

public class Main {
    /**
     * The main method to run the server
     *
     * @param args the arguments
     */
    public static void main(String[] args) {

        DBPropsFileRead dbprops = new DBPropsFileRead();
        if(DBCheckSchema.checkDatabase(dbprops.getSchema())){
            Server server = new Server();
            server.start();
        }
        else{
            System.out.println("Server Terminated");
        }


    }
}
