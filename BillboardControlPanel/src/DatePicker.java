import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

class DatePicker {
    int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH)+1;
    int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);;
    JLabel l = new JLabel("", JLabel.CENTER);
    String day = "";
    int dayInt;
    JDialog d;
    JButton[] button = new JButton[49];
    String date  = null;

    public DatePicker(JPanel parent) {
        d = new JDialog();
        d.setModal(true);
        String[] header = { "Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat" };
        JPanel p1 = new JPanel(new GridLayout(7, 7));
        p1.setPreferredSize(new Dimension(430, 220));

        for (int x = 0; x <button.length ; x++) {
            final int selection = x;
            button[x] = new JButton();
            button[x].setFocusPainted(false);
            button[x].setBackground(Color.white);
            if (x > 6)
                button[x].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        day = button[selection].getActionCommand();
                        d.dispose();
                        dayInt = Integer.parseInt(day);
                        date = String.format("%02d",dayInt) +"/"+String.format("%02d",month)+"/"+year;
                    }
                });
            if (x < 7) {
                button[x].setText(header[x]);
                button[x].setForeground(Color.blue);
            }
            p1.add(button[x]);

        }
        JPanel p2 = new JPanel(new GridLayout(1, 3));
        JButton previous = new JButton("<< Previous");
        previous.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                month--;
                displayDate();
            }
        });
        p2.add(previous);
        p2.add(l);
        JButton next = new JButton("Next >>");
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                month++;
                displayDate();
            }
        });
        p2.add(next);
        d.add(p1, BorderLayout.CENTER);
        d.add(p2, BorderLayout.SOUTH);
        d.pack();
        d.setLocationRelativeTo(parent);
        displayDate();
        d.setVisible(true);

    }

    public void displayDate() {
        int compare = 0;
        for (int x = 7; x < button.length; x++){
            button[x].setText("");
            button[x].setEnabled(true);
        }
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMMM yyyy");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month, 1);
        int dayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK);
        int daysInMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        for (int x = 6 + dayOfWeek, day = 1; day <= daysInMonth; x++, day++){
            button[x].setText("" + day);
//            System.out.println(button[x].getText());
//            try{
//                int intDayNoFormatted = Integer.parseInt(button[x].getText());
//                String dayFormatted = String.format("%02d",intDayNoFormatted);
//                String monthFormatted = String.format("%02d", month);
//                String yearFormatted = String.format("%d", year);
//                String date = dayFormatted+"/"+monthFormatted+"/"+yearFormatted+" 23:59:59";
//                System.out.println("Day: " + date);
//                SimpleDateFormat formatter = new SimpleDateFormat("dd/M/yyyy HH:mm:ss");
//                Date dateCalender = formatter.parse(date);
//                System.out.println(dateCalender);
//                Date currentDate = new Date();
//                compare = (dateCalender.compareTo(currentDate));
//                System.out.println("Compare: " + compare);
//            }catch (Exception e){
//                System.out.println(e);
//            }
        }
        l.setText(sdf.format(cal.getTime()));
        d.setTitle("Select Date");
    }



    public String setPickedDate() {
        if (day.equals(""))
            return day;
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                "dd-MM-yyyy");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month, Integer.parseInt(day));
        return sdf.format(cal.getTime());
    }

    public String getDate(){
        return date;
    }


}

