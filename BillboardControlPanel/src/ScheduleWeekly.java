import common.Schedule;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ScheduleWeekly {

    JLabel jLabel = new JLabel("", JLabel.CENTER);
    JDialog jDialog;
    JTextField[] jTextField = new JTextField[7];
    JTextArea[] jTextArea = new JTextArea[98];
    List<Schedule> listSchedule;
    String [] weekDay = getWeekdays();
    String [] header = { weekDay[0], weekDay[1], weekDay[2], weekDay[3], weekDay[4], weekDay[5],  weekDay[6] };

    public ScheduleWeekly(JPanel parent, List<Schedule> schedules) {

        this.listSchedule = schedules;

        jDialog = new JDialog();
        jDialog.setModal(true);
        JPanel p1 = new JPanel(new GridLayout(0, 7));
        p1.setPreferredSize(new Dimension(1400, 900));

        for (int x = 0; x < jTextArea.length ; x++) {

            final int selection = x;
            int count = 0;
            if (x < 7) {
                jTextField[x] = new JTextField();
                jTextField[x].setEditable(false);
                jTextField[x].setText(header[x]);
                jTextField[x].setForeground(Color.white);
                jTextField[x].setBackground(Color.blue);
                jTextField[x].setHorizontalAlignment(JTextField.CENTER);
                p1.add(jTextField[x]);
            }
            else
            {
                jTextArea[x] = new JTextArea();
                jTextArea[x].setEditable(false);
                jTextArea[x].setBackground(Color.white);
                jTextArea[x].setBorder(new EmptyBorder(5,5,5,5));
                jTextArea[x].setWrapStyleWord(true);
                jTextArea[x].setEditable(false);
                jTextArea[x].setLineWrap(true);
                jTextArea[x].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
                p1.add(jTextArea[x]);
            }

        }
        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        //JButton jbnClose = new JButton("Close");

        jDialog.add(p1, BorderLayout.CENTER);
        jDialog.add(p2, BorderLayout.SOUTH);
        jDialog.pack();
        jDialog.setLocationRelativeTo(parent);
        displayDate();
        jDialog.setTitle("Calendar of billboards scheduled");
        jDialog.setVisible(true);

    }

    /**
     * @author Fernando Barbosa Silva
     * Validate the date of each schedule in order to present them in the schedule calendar.
     * @return a list of schedules.
     */
    private List<Schedule> filterSchedules(List<Schedule> schedules){
        List<Schedule> list = schedules;
        List<Schedule> validSchedules = new ArrayList<>();

        for(int i = 0 ; i< list.size(); i++){

            Schedule schedule = list.get(i);

            // Format time start
            String dateTimeStart = schedule.getDateTimeStart();
            String [] dateTimeStartSplit = dateTimeStart.split(" ");
            //Date start
            String [] dateStartSplit = dateTimeStartSplit[0].split("-");
            String dateStart = dateStartSplit[2]+"/"+dateStartSplit[1]+"/"+dateStartSplit[0];

            // Check if date is valid
            SimpleDateFormat formatter = new SimpleDateFormat("dd/M/yyyy");
            Date currentDate = null;
            Date scheduleStartDate = null;
            Date maxDate = null;

            try {
                currentDate = formatter.parse(formatter.format(new Date()));
                scheduleStartDate  = formatter.parse(dateStart);
                maxDate = new Date(currentDate.getTime()+TimeUnit.DAYS.toMillis(6));
//                System.out.println("Current date:             "+ currentDate);
//                System.out.println("Date start date schedule: "+ scheduleStartDate);
//                System.out.println("Maximum date:             "+ maxDate);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(scheduleStartDate.before(currentDate)) {
//                System.out.println("Schedule not valid - Before");
            }
            else if (scheduleStartDate.after(maxDate)) {
                System.out.println("Schedule not valid - After");
            }else{
//                System.out.println("Schedule valid!");
                validSchedules.add(schedule);
            }
            System.out.println("ID :" + schedule.getId());
            System.out.println("");
        }
//        System.out.println("List size: " + validSchedules.size());
        return validSchedules;
    }

    /**
     * @author Fernando Barbosa Silva
     * Display the calendar with the billboard scheduled in the week
     */
    public void displayDate() {


        // Set the calendar
        List<Schedule>totalList = listSchedule;
        List<Schedule>list = filterSchedules(totalList);

        // Set all jTextFilds to ""
        for (int x = 7; x < jTextArea.length; x++){
            jTextArea[x].setText("");
            jTextArea[x].setEnabled(true);
        }

        // Update the calendar
        for (int x = 7; x < jTextArea.length; x++){

            if(!list.isEmpty()){

                for (int i = 0; i < list.size() ; x++){
                    Schedule schedule = list.get(i);
                    String billboardName = schedule.getIdBillboard();
                    String scheduleId = String.valueOf(schedule.getId());

                    // Format time start
                    String dateTimeStart = schedule.getDateTimeStart();
                    String [] dateTimeStartSplit = dateTimeStart.split(" ");
                    //Date start
                    String [] dateStartSplit = dateTimeStartSplit[0].split("-");
                    String dateStart = dateStartSplit[2]+"/"+dateStartSplit[1]+"/"+dateStartSplit[0];
                    // Time start
                    String timeStart = dateTimeStartSplit[1];
                    String [] timeStartSplit = timeStart.split(":");
                    String timeStart1 = timeStartSplit[0]+":"+timeStartSplit[1];

                    // Format time finish
                    String dateTimeFinish = schedule.getDateTimeFinish();
                    String [] dateTimeFinishSplit = dateTimeFinish.split(" ");
                    //Date start
                    String [] dateFinishSplit = dateTimeStartSplit[0].split("-");
                    String dateFinish = dateFinishSplit[2]+"/"+dateFinishSplit[1]+"/"+dateFinishSplit[0];
                    // Time Finish
                    String timeFinish = dateTimeFinishSplit[1];
                    String [] timeFinishSplit = timeFinish.split(":");
                    String timeFinish1 = timeFinishSplit[0]+":"+timeFinishSplit[1];
                    // Set the format of the information presented in the billboard
                    String info = billboardName+"\n ("+timeStart1+"-"+timeFinish1+")";

                    if (getWeekDay(dateStart).equals(header[x%7])) {
                        jTextArea[x].setText(info);
                        list.remove(i);
                        break;
                    }
                }
                System.out.println("Index" + x);
            }
            else break;
        }

    }


    /**
     * @author Fernando Barbosa Silva
     * Get day of the week.
     * @return a string array list with the days od the week.
     */
    public static String[] getWeekdays() {
        String [] weekDays = new String[7];
        Date currentDate = new Date();
        DateFormat formatter = new SimpleDateFormat("EEEE");
        Date newDate = currentDate;
        for (int i = 0 ; i < 7 ; i++){
            weekDays[i] = formatter.format(newDate);
            //System.out.println("Week day: "+ weekDays[i]);
            newDate = new Date(newDate.getTime() + TimeUnit.DAYS.toMillis(1));
        }
        return weekDays;
    }


    /**
     * @author Fernando Barbosa Silva
     * Get day of the week.
     * @return a string with the days od the week.
     */
    public static String getWeekDay(String date){
        String input_date = date ;
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat format2= new SimpleDateFormat("EEEE");
        Date dt1= null;
        Date dateCurrent = new Date();
        try {
            dt1 = format1.parse(input_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String finalDay=format2.format(dt1);
        return finalDay;
    }


}







