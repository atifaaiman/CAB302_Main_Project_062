import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;

import common.Billboard;
import common.Schedule;

/**
 * The Class SchedulesPanel. Represents panel to display schedules.
 */
public class SchedulesPanel extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/** Schedule list. */
	private List<Schedule> schedules;
	/** Main schedules panel components. */
	private DefaultTableModel tblMdlAllSchedules = new DefaultTableModel(
			new String[] { "Schedule id", "Billboard name","Start date-time", "Finish date-time", "Creator",
					"Create date"}, 0) {
		private static final long serialVersionUID = 1L;
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	private JTable tblAllSchedules = new JTable(tblMdlAllSchedules);
	private JButton btnAddSchedule = new JButton("Add ");
	private JButton btnLogout = new JButton("Logout");
	private JButton btnHome = new JButton("Home");
	private JButton btnDeleteSchedule = new JButton("Delete");
	private JButton btnShowSchedule = new JButton("Show all schedules");
	private JButton btnWeeksSchedules = new JButton("Week schedules");


	/** Components for Add Schedule Panel */
	private String[] hour = {
			"","00","01","02","03", "04","05","06","07","08", "09","10", "11",
			"12","13","14","15","16","17","18","19","20","21","22","23"
	};
	private String[] minutes = {
			"","00","01","02","03","04","05","06","07","08", "09",
			"10","11","12","13","14","15","16","17","18","19",
			"20","21","22","23","24","25","26","27", "28","29",
			"30","31","32","33","34", "35","36","37","38","39",
			"40","41","42","43","44","45","46","47","48","49",
			"50", "51","52","53","54","55","56","57","58","59"};
	private String[] recurEveryMinutes = {
			"02","03","04","05","06","07","08", "09",
			"10","11","12","13","14","15","16","17","18","19",
			"20","21","22","23","24","25","26","27", "28","29",
			"30","31","32","33","34", "35","36","37","38","39",
			"40","41","42","43","44","45","46","47","48","49",
			"50", "51","52","53","54","55","56","57","58","59"};
	private String[] recurDurationMinutes = {
			"01","02","03","04","05","06","07","08", "09",
			"10","11","12","13","14","15","16","17","18","19",
			"20","21","22","23","24","25","26","27", "28","29",
			"30","31","32","33","34", "35","36","37","38","39",
			"40","41","42","43","44","45","46","47","48","49",
			"50", "51","52","53","54","55","56","57","58","59"};

	private JComboBox<String> jcbBillboards 			= new JComboBox<>(); 											// Drop-down menu for the created billboards
	private JPanel pnlAddSchedule 						= new JPanel(); 												// Window create schedules
	private JTextField tfDateStar						= new JTextField(7);
	private JTextField tfDateFinish 					= new JTextField(7);
	private final JRadioButton jrbRecurNo 				= new JRadioButton("No Recur");
	private final JRadioButton jrbRecurDay 				= new JRadioButton("Every Day");
	private final JRadioButton jrbRecurHour 			= new JRadioButton("Every Hour");
	private final JRadioButton jrbRecurMinutes 			= new JRadioButton("Every Minutes");
	private JButton btnSelectStartDate 					= new JButton("Start date");
	private JButton btnSelectFinishDate 				= new JButton("Finish date");
	private JComboBox<String> jcbHourStart 				= new JComboBox<>(hour);
	private JComboBox<String> jcbHourFinish 			= new JComboBox<>(hour);
	private JComboBox<String> jcbMinuteStart 			= new JComboBox<>(minutes);
	private JComboBox<String> jcbMinuteFinish 			= new JComboBox<>(minutes);
	private JComboBox<String> jcbRecurDurationMinutes 	= new JComboBox<>(recurDurationMinutes);
	private JComboBox<String> jcbEveryMinutes 			= new JComboBox<>(recurEveryMinutes);

	//Use to align components on the screen
	private JCheckBox Hour_ChkBox 						= new JCheckBox("Hour");
	private JPanel pnlGap1 								= new JPanel();
	private JPanel pnlGap2 								= new JPanel();
	private JPanel pnlGap3 								= new JPanel();
	private JPanel pnlGap4 								= new JPanel();
	private JPanel pnlGap5								= new JPanel();
	private JPanel pnlGap6 								= new JPanel();
	private JPanel pnlGap7 								= new JPanel();
	private JPanel pnlGap8 								= new JPanel();
	private JPanel pnlGap9 								= new JPanel();
	private JPanel pnlGap10 							= new JPanel();
	private JPanel pnlGap11 							= new JPanel();
	private JPanel pnlGap12 							= new JPanel();
	private JPanel pnlGap13 							= new JPanel();
	private JPanel pnlGap14 							= new JPanel();
	private JPanel pnlGap15 							= new JPanel();

	/**
	 * Instantiates a new schedules panel.
	 */
	public SchedulesPanel() {
		//getWeekdays();
		initGUIComponents();
	}


	/**
	 * @author Fernando Barbosa Silva
	 * Inititiate the GUI components.
	 */
	private void initGUIComponents() {
		//--------------------------------------------------------------------------------------------------------------
		/*
		 * MAIN SCHEDULE PANEL
		 */
		//--------------------------------------------------------------------------------------------------------------
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		// NORTH
		JPanel pnlNorth = new JPanel(new GridLayout(1, 2));
		pnlNorth.add(new JLabel("Schedule list:", SwingConstants.LEFT));
		JPanel pnlLogout = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlLogout.add(btnHome);
		pnlLogout.add(btnLogout);
		pnlNorth.add(pnlLogout);
		add(pnlNorth, BorderLayout.NORTH);
		// CENTER
		add(new JScrollPane(tblAllSchedules), BorderLayout.CENTER);
		tblAllSchedules.getTableHeader().setReorderingAllowed(false);
		// SOUTH
		Box boxSouth = Box.createHorizontalBox();
		JPanel pnlLeftSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlLeftSouth.add(btnShowSchedule);
		pnlLeftSouth.add(btnWeeksSchedules);
		pnlLeftSouth.add(btnAddSchedule);
		pnlLeftSouth.add(btnDeleteSchedule);
		boxSouth.add(pnlLeftSouth);
		add(boxSouth, BorderLayout.SOUTH);

		//--------------------------------------------------------------------------------------------------------------
		/*
		 * Init add Schedule.
		 */
		//--------------------------------------------------------------------------------------------------------------
		Box mainPanel = Box.createVerticalBox();
		JPanel panel1 = new JPanel();
			panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
			JPanel panel11 = new JPanel();
			panel11.setLayout(new FlowLayout(FlowLayout.LEFT,10,20));
			panel11.add(new JLabel("Billboard Name:"));
			panel11.add(jcbBillboards);
			jcbBillboards.setMinimumSize(new Dimension(100,20));
		panel1.add(panel11);

		// Once panel
		Box onceBox = Box.createVerticalBox();
		onceBox.setBorder(BorderFactory.createDashedBorder(Color.darkGray));
		onceBox.setBorder(BorderFactory.createTitledBorder("Once: "));
		JPanel panel2 = new JPanel();
			panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
			panel2.add(pnlGap1);
		    panel2.add(btnSelectStartDate);
			panel2.add(tfDateStar);
			panel2.add(pnlGap2);
			panel2.add(btnSelectFinishDate);
			panel2.add(tfDateFinish);
		onceBox.add(panel2);
		JPanel panel3 = new JPanel();
			panel3.setLayout(new FlowLayout(FlowLayout.LEFT));
			panel3.add(pnlGap5);
			panel3.add(new JLabel("Time start:"));
			panel3.add(pnlGap6);
			panel3.add(jcbHourStart);
			panel3.add(new JLabel("hours"));
			panel3.add(pnlGap7);
			panel3.add(jcbMinuteStart);
			panel3.add(pnlGap4);
			panel3.add(new JLabel("minutes"));
		JPanel panel4 = new JPanel();
		panel4.setLayout(new FlowLayout(FlowLayout.LEFT));
			panel4.add(pnlGap8);
			panel4.add(new JLabel("Time finish:"));
			panel4.add(pnlGap3);
			panel4.add(jcbHourFinish);
			panel4.add(new JLabel("hours"));
			panel4.add(pnlGap9);
			panel4.add(jcbMinuteFinish);
			panel4.add(pnlGap12);
			panel4.add(new JLabel("minutes:"));
		onceBox.add(panel3);
		onceBox.add(panel4);

		// Recur panel
		Box recurBox = Box.createVerticalBox();
		recurBox.setBorder(BorderFactory.createDashedBorder(Color.darkGray));
		recurBox.setBorder(BorderFactory.createTitledBorder("Recur: "));
		JPanel panel5 = new JPanel();
			panel5.setLayout(new FlowLayout(FlowLayout.CENTER, 20,30));
			panel5.add(jrbRecurNo);
			panel5.add(jrbRecurDay);
			panel5.add(jrbRecurHour);
			panel5.add(jrbRecurMinutes);
		JPanel panel6 = new JPanel();
			panel6.setLayout(new FlowLayout(FlowLayout.LEFT));
			panel6.add(pnlGap10);
			JLabel jlbRecurFrequency =new JLabel("Recur every:");
			panel6.add(jlbRecurFrequency);
			panel6.add(pnlGap14);
			panel6.add(jcbEveryMinutes);
			JLabel jlbRecurFrequencyMinutes =new JLabel("minutes");
			panel6.add(jlbRecurFrequencyMinutes);
		JPanel panel7 = new JPanel();
			panel7.setLayout(new FlowLayout(FlowLayout.LEFT));
			panel7.add(pnlGap11);
			JLabel jlbRecurHour =new JLabel("Duration:");
			panel7.add(jlbRecurHour);
			panel7.add(pnlGap13);
			panel7.add(jcbRecurDurationMinutes);
			JLabel jlbRecurHourMinutes =new JLabel("minutes");
			panel7.add(jlbRecurHourMinutes);
		recurBox.add(panel5);
		recurBox.add(panel6);
		recurBox.add(panel7);
		// Big Panels
		mainPanel.add(panel1);
		mainPanel.add(onceBox);
		mainPanel.add(recurBox);
		// Main Panel
		pnlAddSchedule.add(mainPanel);

		//--------------------------------------------------------------------------------------------------------------
		// Components setup
		//--------------------------------------------------------------------------------------------------------------
		// Components alignment
		pnlGap1.setPreferredSize	(new Dimension (3,10));   // ok
		pnlGap2.setPreferredSize	(new Dimension (30,10));  // ok
		pnlGap3.setPreferredSize	(new Dimension (16,10));  // ok
		pnlGap4.setPreferredSize	(new Dimension (0,10));   // ok
		pnlGap5.setPreferredSize	(new Dimension (10,10));  // ok
		pnlGap6.setPreferredSize	(new Dimension (23,10));  // ok
		pnlGap7.setPreferredSize	(new Dimension (15,10));  // ok
		pnlGap8.setPreferredSize	(new Dimension (10,10));  // ok
		pnlGap9.setPreferredSize	(new Dimension (15,10));  // ok
		pnlGap10.setPreferredSize	(new Dimension (10,10));  // ok
		pnlGap11.setPreferredSize	(new Dimension (10,10));  // ok
		pnlGap12.setPreferredSize	(new Dimension (0,10));   // ok
		pnlGap13.setPreferredSize	(new Dimension (30,10));  // ok
		pnlGap14.setPreferredSize	(new Dimension (14,10));  // ok
		pnlGap15.setPreferredSize	(new Dimension (57,10));  // ok

		// Setup for the buttons
		btnDeleteSchedule.setEnabled(false);
		btnAddSchedule.setEnabled(false);
		btnWeeksSchedules.setEnabled(false);
		jcbRecurDurationMinutes.setEnabled(false);
		jcbEveryMinutes.setEnabled(false);

		// Recurrence buttons selection
		ButtonGroup bg = new ButtonGroup();
		bg.add(jrbRecurNo);
		bg.add(jrbRecurDay);
		bg.add(jrbRecurHour);
		bg.add(jrbRecurMinutes);

		// Event listeners dates
		btnSelectStartDate.addActionListener(e -> selectDateStart());
		btnSelectFinishDate.addActionListener(e -> selectDateFinish());
		btnWeeksSchedules.addActionListener(e -> weekSchedules());

		// If no recurrence
		jrbRecurNo.addActionListener(e-> {
			tfDateFinish.setText(tfDateStar.getText());
			jcbRecurDurationMinutes.setEnabled(false);
			jcbEveryMinutes.setEnabled(false);
		});
		// If recur every dar
		jrbRecurDay.addActionListener(e-> {
			jcbRecurDurationMinutes.setEnabled(false);
			jcbEveryMinutes.setEnabled(false);
		});
		// If recur every hour
		jrbRecurHour.addActionListener(e-> {
			jcbRecurDurationMinutes.setEnabled(true);
			jcbEveryMinutes.setEnabled(false);

		});
		// If recur every minutes
		jrbRecurMinutes.addActionListener(e-> {
			jcbRecurDurationMinutes.setEnabled(true);
			jcbEveryMinutes.setEnabled(true);
		});

	}
	/**
	 * @author Fernando Barbosa Silva
	 * Show the weel schedule panel
	 */
	private void weekSchedules(){
		System.out.println("Schedule list size: " + schedules.size());
		ScheduleWeekly sk = new ScheduleWeekly(this, schedules);
	}

	/**
	 * @author Fernando Barbosa Silva
	 * Set start date.
	 */
	public void selectDateStart(){
		DatePicker dp = new DatePicker(this);
		tfDateStar.setText(dp.getDate());
		if(jrbRecurNo.isSelected())tfDateFinish.setText(tfDateStar.getText());
		dp.displayDate();
	}

	/**
	 * @author Fernando Barbosa Silva
	 * Set finish date
	 */
	public void selectDateFinish(){
		DatePicker dp = new DatePicker(this);
		tfDateFinish.setText(dp.getDate());
	}

	/**
	 * @author Fernando Barbosa Silva
	 * Updates table.
	 * @param schedules the schedules
	 */
	public void updateTable(List<Schedule> schedules) {
		this.schedules = schedules;
		tblMdlAllSchedules.setRowCount(0);
		for (Schedule s : schedules) {
			String [] dateTimeStartSplit = s.getDateTimeStart().split(" ");
			String [] dateStartSplit = dateTimeStartSplit[0].split("-");
			String dateStart = dateStartSplit[2]+"/"+dateStartSplit[1]+"/"+dateStartSplit[0];
			String timeStart = dateTimeStartSplit[1];

			String [] dateTimeFinishSplit = s.getDateTimeFinish().split(" ");
			String [] dateFinishSplit = dateTimeFinishSplit[0].split("-");
			String dateFinish = dateFinishSplit[2]+"/"+dateFinishSplit[1]+"/"+dateFinishSplit[0];
			String timeFinish = dateTimeFinishSplit[1];


			tblMdlAllSchedules.addRow(new String[] {String.valueOf(s.getId()), s.getIdBillboard(),
					dateStart+" "+timeStart, dateFinish+" "+timeFinish, s.getScheduleCreatedBy(),
					s.getScheduleCreateDate()});
		}
	}

	/**
	 *  * @author Fernando Barbosa Silva
	 * Adds new schedule in the database
	 * @return the schedule
	 * @throws Exception the exception
	 */
	public List<Schedule> addSchedule() {
		Schedule schedule = null;
		List<Schedule> list = new ArrayList<>();
		// Current Date and time
		int dayInt 	 =   java.util.Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		int monthInt =   java.util.Calendar.getInstance().get(java.util.Calendar.MONTH)+1;
		int yearInt  =   java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
		int hourInt  =   java.util.Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		int minInt   =   java.util.Calendar.getInstance().get(Calendar.MINUTE);
		String day   = String.format("%02d",dayInt);
		String month = String.format("%02d",monthInt);
		String year  = String.format("%d",yearInt);
		String hour = String.format("%02d", hourInt);
		String minute = String.format("%02d", minInt);
		tfDateStar.setText(day+"/"+month+"/"+year);
		tfDateFinish.setText(day+"/"+month+"/"+year);

		jrbRecurNo.setSelected(true);
		tfDateStar.setEnabled(false);
		tfDateFinish.setEnabled(false);

		//jcbBillboards.setSelectedIndex(0);
		jcbHourStart.setSelectedItem(hour);
		jcbHourFinish.setSelectedItem(hour);
		jcbMinuteStart.setSelectedIndex(minInt);
		jcbMinuteFinish.setSelectedIndex(minInt);

		String startDate;
		String startTime;
		String finishDate;
		String finishTime;

		int add = JOptionPane.showConfirmDialog(this, pnlAddSchedule, "Add Schedule",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (add == 0) {
			try{
				// Validations
				if  (tfDateStar.getText().equals("")) {
					JOptionPane.showMessageDialog(this, "Please enter a start date", "Error",
							JOptionPane.ERROR_MESSAGE);
					return null;
				}else if(tfDateFinish.getText().equals("")){
					JOptionPane.showMessageDialog(this, "Please enter a finish date", "Error",
							JOptionPane.ERROR_MESSAGE);
					return null;
				}else if(jcbHourStart.getSelectedItem().equals("") || jcbHourFinish.getSelectedItem().equals("")){
					JOptionPane.showMessageDialog(this, "Please enter the start and finish hour.",
							"Error", JOptionPane.ERROR_MESSAGE);
					return null;
				}else if(jcbMinuteStart.getSelectedItem().equals("") || jcbMinuteFinish.getSelectedItem().equals("")){
					JOptionPane.showMessageDialog(this, "Please enter the start and finish minutes.",
							"Error", JOptionPane.ERROR_MESSAGE);
					return null;
				}else if(jcbBillboards.getSelectedItem().equals("")){
					JOptionPane.showMessageDialog(this, "Please select a billboard.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return null;
				}
				// Format start date
				String [] dateStartFormat = tfDateStar.getText().split("/");
				startDate = dateStartFormat[2]+"-"+dateStartFormat[1]+"-"+dateStartFormat[0];
				startTime = jcbHourStart.getSelectedItem()+":"+jcbMinuteStart.getSelectedItem()+":00";

				// Format finish date
				String [] dateFinishFormat = tfDateFinish.getText().split("/");
				finishDate = dateFinishFormat[2]+"-"+dateFinishFormat[1]+"-"+dateFinishFormat[0];
				finishTime = jcbHourFinish.getSelectedItem()+":"+ jcbMinuteFinish.getSelectedItem()+":00";

				// Check if date is valid
				SimpleDateFormat formatter = new SimpleDateFormat("dd/M/yyyy HH:mm:ss");
				String dateStart    = tfDateStar.getText()+" "+startTime ;
				String dateFinish   = tfDateFinish.getText()+" "+finishTime ;

				Date dateTimeStart  = formatter.parse(dateStart);
				Date dateTimeFinish = formatter.parse(dateFinish);
				Date currentDate = null;
				try {
					currentDate = formatter.parse(formatter.format(new Date()));
				} catch (ParseException e) {
					e.printStackTrace();
				}

				LocalDate start ;
				LocalDate finish ;

				// Start and Finish date validation
				boolean isDatesValid = (dateTimeStart.before(dateTimeFinish));
				boolean isDatesValid2 = (dateTimeStart.before(currentDate));

				//System.out.println("Is valid: " + isDatesValid);
				if(!isDatesValid){
					JOptionPane.showMessageDialog(this, "Date and time start must be greater " +
									"than date and time finish.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return null;
				}
				if(isDatesValid2){
					JOptionPane.showMessageDialog(this, "Start date and time must be grater than " +
									"the current date and time.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return null;
				}
				// If no recurrence
				if(jrbRecurNo.isSelected()){
					schedule = new Schedule();
					schedule.setDateTimeStart(startDate + " " + startTime.replace(":","-"));
					schedule.setDateTimeFinish(finishDate + " " + finishTime.replace(":", "-"));
					schedule.setIdBillboard((String) jcbBillboards.getSelectedItem());
					schedule.setScheduleCreatedBy(Controller.userName);
					list.add(schedule);
				}// If recurrence every day
				else if(jrbRecurDay.isSelected()){

					start = dateTimeStart.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					finish = dateTimeFinish.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();;
					for (LocalDate date = start; date.isBefore(finish) || date.isEqual(finish); date = date.plusDays(1)) {
						schedule = new Schedule();
						schedule.setDateTimeStart(date + " " + startTime.replace(":", "-"));
						schedule.setDateTimeFinish(date + " " + finishTime.replace(":", "-"));
						schedule.setIdBillboard((String) jcbBillboards.getSelectedItem());
						schedule.setScheduleCreatedBy(Controller.userName);
						list.add(schedule);
					}

				}// If recurrence every hour
				else if(jrbRecurHour.isSelected()){

					int duration = Integer.parseInt(String.valueOf(jcbRecurDurationMinutes.getSelectedItem()));
					Date newDateStart = dateTimeStart;
					Date newDateFinish = new Date(newDateStart.getTime()+TimeUnit.MINUTES.toMillis(duration));

					while( newDateFinish.before(dateTimeFinish)) {
						// Format dates
						String dStart = formatter.format(newDateStart);		// dss -date start split
						String [] dss = dStart.split(" ");  			// dss -date start split
						dss[1].replace(":", "-");			// dss -date start split
						String [] dss2 = dss[0].split("/");		    // dss - date start split
						String dateStartFormatted = dss2[2]+"-"+dss2[1]+"-"+dss2[0]+" "+dss[1];

						String dFinish = formatter.format(newDateFinish);	// dfs - date finish split
						String [] dfs1 = dFinish.split(" ");			// dfs - date finish split
						dfs1[1].replace(":","-");			// dfs - date finish split
						String [] dfs2 = dfs1[0].split("/");	        // dfs - date finish split
						String dateFinishFormatted = dfs2[2]+"-"+dfs2[1]+"-"+dfs2[0]+" "+dfs1[1];

						// Add schedules in the list of schedules
						schedule = new Schedule();
						schedule.setDateTimeStart(dateStartFormatted);
						schedule.setDateTimeFinish(dateFinishFormatted);
						schedule.setIdBillboard((String) jcbBillboards.getSelectedItem());
						schedule.setScheduleCreatedBy(Controller.userName);
						list.add(schedule);

						// Loop Increment
						newDateStart = new Date(newDateStart.getTime() + TimeUnit.HOURS.toMillis(1));
						newDateFinish = new Date(newDateStart.getTime() + TimeUnit.MINUTES.toMillis(duration));
					}

				}
				else if (jrbRecurMinutes.isSelected()){
					//System.out.println("Every  minutes is selected: " + jrbRecurMinutes.isSelected());
					int recurrence = Integer.parseInt(String.valueOf(jcbEveryMinutes.getSelectedItem()));
					int duration = Integer.parseInt(String.valueOf(jcbRecurDurationMinutes.getSelectedItem()));

					if  (recurrence<= duration) {
						JOptionPane.showMessageDialog(this, "Recurrence must by greater than the duration", "Error",
								JOptionPane.ERROR_MESSAGE);
						return null;
					}
					Date newDateStart = dateTimeStart;
					Date newDateFinish = new Date(newDateStart.getTime()+TimeUnit.MINUTES.toMillis(duration));

					while( newDateFinish.before(dateTimeFinish)) {
						// Format dates
						String dStart = formatter.format(newDateStart);		// dss -date start split
						String [] dss = dStart.split(" ");  			// dss -date start split
						dss[1].replace(":", "-");			// dss -date start split
						String [] dss2 = dss[0].split("/");		    // dss - date start split
						String dateStartFormatted = dss2[2]+"-"+dss2[1]+"-"+dss2[0]+" "+dss[1];

						String dFinish = formatter.format(newDateFinish);	// dfs - date finish split
						String [] dfs1 = dFinish.split(" ");			// dfs - date finish split
						dfs1[1].replace(":","-");			// dfs - date finish split
						String [] dfs2 = dfs1[0].split("/");	        // dfs - date finish split
						String dateFinishFormatted = dfs2[2]+"-"+dfs2[1]+"-"+dfs2[0]+" "+dfs1[1];

						// Add schedules in the list of schedules
						schedule = new Schedule();
						schedule.setDateTimeStart(dateStartFormatted);
						schedule.setDateTimeFinish(dateFinishFormatted);
						schedule.setIdBillboard((String) jcbBillboards.getSelectedItem());
						schedule.setScheduleCreatedBy(Controller.userName);
						list.add(schedule);

						// Loop Increment
						newDateStart = new Date(newDateStart.getTime() + TimeUnit.MINUTES.toMillis(recurrence));
						newDateFinish = new Date(newDateStart.getTime() + TimeUnit.MINUTES.toMillis(duration));
					}

				}

			} catch (Exception e1){
				System.out.println(e1);
			}
		}
		btnDeleteSchedule.setEnabled(false);
		return list;
	}

	/**
	 * author Fernando Barbosa Silva
	 * Updates billboards.
	 * @param billboards the billboards
	 */
	public void updateBillboards(List<Billboard> billboards) {
		jcbBillboards.removeAllItems();
		jcbBillboards.addItem("");
		for (Billboard b : billboards) {
			jcbBillboards.addItem(b.getName());
		}
		revalidate();
		updateUI();
	}

	/**
	 * Gets the button add schedule.
	 * @return the button add schedule
	 */
	public JButton getBtnAddSchedule() {
		return btnAddSchedule;
	}

	/**
	 * Gets the button logout.
	 * @return the button logout
	 */
	public JButton getBtnLogout() {
		return btnLogout;
	}

	/**
	 * Gets the button btnDeleteSchedule.
	 * @return the button logout
	 */
	public JButton getBtnDeleteSchedule(){
		return btnDeleteSchedule;
	}

	/**
	 * Gets the button  btnShowSchedule.
	 * @return the button logout
	 */
	public JButton getBtnShowSchedule(){
		return btnShowSchedule;
	}

	/**
	 * @author Fernando Barbosa Silva
	 * Gets the button btnEditSchedule.
	 * @return the button logout
	 */
	public JButton getBtnWeeksSchedules(){
		return btnWeeksSchedules;
	}

	/**
	 * Gets table AllSchedules.
	 * @return the table all schedules
	 */
	public JTable getTblAllSchedules (){
		return tblAllSchedules;
	}

	/**
	 * Gets home button
	 * @return home button.
	 */
	public  JButton getBtnHome(){
		return  btnHome;
	}

}
