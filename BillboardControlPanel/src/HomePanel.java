import common.Permission;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class HomePanel extends JPanel{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** Components. */
    private JButton btnSchedules            = new JButton("SCHEDULES");
    private JButton btnBillboards           = new JButton("BILLBOARDS");
    private JButton btnUsers 			    = new JButton("USERS");
    private JButton btnLogout 				= new JButton("Logout");
    private JPanel pnlGap1 					= new JPanel();
    private JPanel pnlGap2 					= new JPanel();
    private JPanel pnlGap3					= new JPanel();
    private JPanel pnlGap4 					= new JPanel();

    /**
     * Instantiates a new users panel.
     */
    public HomePanel() {
        initGUIComponents();
    }

    /**
     * Initiates the GUI components.
     */
    private void initGUIComponents() {

        //--------------------------------------------------------------------------------------------------------------
        // MAIN PANEL
        //--------------------------------------------------------------------------------------------------------------
        setLayout(new BorderLayout());

        Box homeBox = Box.createVerticalBox();

        JPanel pnl1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnl1.add(btnLogout);
        homeBox.add(pnl1);

        JPanel pnl2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        BufferedImage image;
        try{
            image = ImageIO.read(new File("./welcome.png"));
            //System.out.println(image);
            pnl2.add(new JLabel(new ImageIcon(image)));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        homeBox.add(pnl2);

        JPanel pnl3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnl3.add(btnSchedules);
        pnl3.add(pnlGap1);
        pnl3.add(btnBillboards);
        pnl3.add(pnlGap2);
        pnl3.add(btnUsers);
        homeBox.add(pnl3);

        add(homeBox);

        // Set up
        btnSchedules.setPreferredSize(new Dimension(150,50));
        btnBillboards.setPreferredSize(new Dimension(150,50));
        btnUsers.setPreferredSize(new Dimension(150,50));
        btnUsers.setEnabled(false);
        btnBillboards.setEnabled(false);
        btnSchedules.setEnabled(false);
        pnlGap1.setPreferredSize	(new Dimension (27,10));
        pnlGap2.setPreferredSize	(new Dimension (27,10));

   }



    /**
     * Gets the button add user.
     * @return the button add user
     */
    public JButton getBtnBillboards() {
        return btnBillboards;
    }

    /**
     * Gets the button logout.
     * @return the button logout
     */
    public JButton getBtnLogout() {
        return btnLogout;
    }

    /**
     * Gets the button edit users.
     * @return the table all users
     */
    public JButton getBtnUsers(){
        return  btnUsers;
    }


    /**
     * Gets all users from the database.
     * @return the table all users
     */
    public JButton getBtnSchedules(){
        return btnSchedules;
    }

}





