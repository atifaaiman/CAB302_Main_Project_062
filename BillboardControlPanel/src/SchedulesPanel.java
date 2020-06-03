import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import common.Billboard;
import common.Schedule;

/**
 * The Class SchedulesPanel. Represents panel to display schedules.
 */
public class SchedulesPanel extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Main schedules panel components. */
	private DefaultTableModel tblMdlAllSchedules = new DefaultTableModel(
			new String[] { "Schedule Date", "Schedule Time","Duration",
					"Billboard Name", "User Name", "Creation Date"  }, 0) {
		private static final long serialVersionUID = 1L;
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	private JTable tblAllSchedules = new JTable(tblMdlAllSchedules);
	private JButton btnAddSchedule = new JButton("Add ");
	private JButton btnLogout = new JButton("Logout");
	private JButton btnDeleteSchedule = new JButton("Delete");
	private JButton btnShowSchedule = new JButton("Show Schedules");
	private JButton btnEditSchedule = new JButton("Edit");


	/** Components for Add Schedule Panel */
	private String[] hour = {
			"00","01","02","03", "04","05","06","07","08", "09","10", "11",
			"12","13","14","15","16","17","18","19","20","21","22","23"
	};
	private String[] minutes = {
			"00","01","02","03","04","05","06","07","08", "09",
			"10","11","12","13","14","15","16","17","18","19",
			"20","21","22","23","24","25","26","27", "28","29",
			"30","31","32","33","34", "35","36","37","38","39",
			"40","41","42","43","44","45","46","47","48","49",
			"50", "51","52","53","54","55","56","57","58","59"};
	private JComboBox<String> jcbBillboards 			= new JComboBox<>(); 											// Drop-down menu for the created billboards
	private JPanel pnlAddSchedule 						= new JPanel(); 												// Window create schedules
	private JTextField tfDay 							= new JTextField(5);
	private JTextField tfHour 							= new JTextField(5);
	private JTextField tfMinute							= new JTextField(5);
	private JTextField tfDurationOnce 					= new JTextField(5);
	private JTextField tfDurationRecur 					= new JTextField(5);
	private JTextField tfDateStartOnce 					= new JTextField(7);
	private JTextField tfDateStartRecur 				= new JTextField(7);
	private JTextField tfDateFinishOnce 				= new JTextField(7);
	private JTextField tfDateFinishRecur 				= new JTextField(7);
	private JTextField tfMinutesRecur  				    = new JTextField(5);
	private final JRadioButton jrbOnce 					= new JRadioButton("Once", true);
	private final JRadioButton jrbRecur 				= new JRadioButton("Recur");
	private JComboBox<String> jcbRepeat 				= new JComboBox<>(new String[] { "Day","Hour","Min" });
	private JComboBox<String> jcbHourOnce 				= new JComboBox<>(hour);
	private JComboBox<String> jcbHourRecur 				= new JComboBox<>(hour);
	private JComboBox<String> jcbMinuteOnce 			= new JComboBox<>(minutes);
	private JComboBox<String> jcbMinuteRecur 			= new JComboBox<>(minutes);
	private String [] minutesRecurrence ={"0","1"};
	private JComboBox<String> jcbMinutesRecurrence 		= new JComboBox<>(minutesRecurrence);

	//NEW CODE
	private JCheckBox Hour_ChkBox 						= new JCheckBox("Hour");
	private JPanel pnlGap1 = new JPanel();
	private JPanel pnlGap2 = new JPanel();
	private JPanel pnlGap3 = new JPanel();
	private JPanel pnlGap4 = new JPanel();
	private JPanel pnlGap5 = new JPanel();
	private JPanel pnlGap6 = new JPanel();



	/**
	 * Instantiates a new schedules panel.
	 */
	public SchedulesPanel() {
		initGUIComponents();
	}

	/**
	 * Inits the GUI components.
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
		pnlLeftSouth.add(btnAddSchedule);
		pnlLeftSouth.add(btnEditSchedule);
		pnlLeftSouth.add(btnDeleteSchedule);
		boxSouth.add(pnlLeftSouth);
		add(boxSouth, BorderLayout.SOUTH);

		//Date picker
		//DatePicker dp = new DatePicker(this);

		//--------------------------------------------------------------------------------------------------------------
		/*
		 * Init add Schedule.
		 */
		//--------------------------------------------------------------------------------------------------------------
		Box mainPanel = Box.createVerticalBox();

		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
		JPanel panel11 = new JPanel();
		panel11.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
		panel11.add(new JLabel("Billboard Name:"));
		panel11.add(jcbBillboards);
		jcbBillboards.setMinimumSize(new Dimension(100,20));
		JPanel panel12 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		//panel12.add(tfBlbdName);
		//panel12.add(pnlBackground);
		JPanel panel13= new JPanel(new FlowLayout(FlowLayout.RIGHT));
		//panel13.add(btnBackground);
		panel1.add(panel11);
		panel1.add(panel12);
		panel1.add(panel13);

		JPanel panel2 = new JPanel();
		JPanel panel21 = new JPanel();
		panel21.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 10));
		//panel21.add(new JLabel("Message:"));
		JPanel panel22 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		//panel22.add(tfMsgText);
		//panel22.add(pnlMsgColour);
		JPanel panel23= new JPanel(new FlowLayout(FlowLayout.RIGHT));
		//panel23.add(btnMsgColour);
		panel2.add(panel21);
		panel2.add(panel22);
		panel2.add(panel23);


		JPanel panel3 = new JPanel();
		JPanel panel31 = new JPanel();
		panel31.setLayout(new FlowLayout(FlowLayout.LEFT, 40, 10));
		//panel31.add(new JLabel("Information:"));
		JPanel panel32 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		//panel32.add(tfInfoText);
		//panel32.add(pnlInfoColour);
		JPanel panel33= new JPanel(new FlowLayout(FlowLayout.RIGHT));
		//panel33.add(btnInfoColour);
		panel3.add(panel31);
		panel3.add(panel32);
		panel3.add(panel33);

		// Once schedule
		Box onceBox = Box.createVerticalBox();
		onceBox.setBorder(BorderFactory.createDashedBorder(Color.darkGray));
		onceBox.setBorder(BorderFactory.createTitledBorder("Once:"));
		JPanel panel4 = new JPanel();
			panel4.setLayout(new FlowLayout(FlowLayout.LEFT));
			panel4.add(jrbOnce);
			panel4.add(pnlGap1);
			panel4.add(new JLabel("Date:"));
			panel4.add(pnlGap3);
			panel4.add(tfDateStartOnce);
			JLabel dateFinishOnce = new JLabel("Date Finish:");
			panel4.add(dateFinishOnce);
			panel4.add(tfDateFinishOnce);
			panel4.add(new JLabel("Hour:"));
			panel4.add(jcbHourOnce);
			panel4.add(new JLabel("Minute:"));
			panel4.add(jcbMinuteOnce);
			panel4.add(new JLabel("Duration in minutes:"));
			panel4.add(tfDurationOnce);
		//panel4.add(jrbBase64);
		onceBox.add(panel4);
		//panel4.add(pnlGap);
		//panel4.add(lblSelectImage);
		//panel4.add(pnlPicture);
		//panel4.add(btnAddImage);
		//panel4.add(btnDeleteImage);

		// Recur schedule
		Box recurBox = Box.createVerticalBox();
		recurBox.setBorder(BorderFactory.createDashedBorder(Color.darkGray));
		recurBox.setBorder(BorderFactory.createTitledBorder("Recur:"));
		JPanel panel5 = new JPanel();
			panel5.setLayout(new FlowLayout(FlowLayout.LEFT));
			panel5.add(jrbRecur);
			panel5.add(pnlGap2);
			panel5.add(new JLabel("Date Start:"));
			panel5.add(tfDateStartRecur);
			JLabel dateFinishRecur = new JLabel("Date Finish:");
			panel5.add(dateFinishRecur);
			panel5.add(tfDateFinishRecur);
			panel5.add(new JLabel("Hour:"));
			panel5.add(jcbHourRecur);
			panel5.add(new JLabel("Minute:"));
			panel5.add(jcbMinuteRecur);
			panel5.add(new JLabel("Duration in minutes:"));
			panel5.add(tfDurationRecur);
		recurBox.add(panel5);

		JPanel panel6 = new JPanel();
		panel6.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel6.add(pnlGap4);
		JLabel recurLabel = new JLabel("Recurrence:");
		panel6.add(recurLabel);
		panel6.add(jcbRepeat);
		JLabel minutesRecur = new JLabel("Minutes to recur:");
		panel6.add(minutesRecur);
		panel6.add(jcbMinutesRecurrence);
		recurBox.add(panel6);


		JPanel panel7 = new JPanel();
		panel7.setLayout(new FlowLayout(FlowLayout.RIGHT));
		//panel6.add(btnPreview);

		//setDefaultColors();
		mainPanel.add(panel1);
		mainPanel.add(panel2);
		mainPanel.add(panel3);
		mainPanel.add(onceBox);
		mainPanel.add(recurBox);
		mainPanel.add(panel7);
		pnlAddSchedule.add(mainPanel);

		//--------------------------------------------------------------------------------------------------------------
		// Components setup
		//--------------------------------------------------------------------------------------------------------------
		pnlGap1.setPreferredSize	(new Dimension (8,10));
		pnlGap3.setPreferredSize	(new Dimension (28,10));
		pnlGap2.setPreferredSize	(new Dimension (5,10));
		pnlGap4.setPreferredSize	(new Dimension (77,10));
		tfDateFinishOnce.setEnabled(false);
		dateFinishOnce.setForeground(Color.LIGHT_GRAY);
		tfDateFinishOnce.setBackground(Color.LIGHT_GRAY);
		//pnlInfoColour.setPreferredSize	(new Dimension (40,18));
		//pnlMsgColour.setPreferredSize	(new Dimension (40,18));
		//pnlBackground.setPreferredSize	(new Dimension (40,18));
		ButtonGroup bg = new ButtonGroup();
		bg.add(jrbOnce);
		bg.add(jrbRecur);
		//setDefaultImage();
		//btnDeleteImage.addActionListener(e -> setDefaultImage());
		//disableButtons();



		//--------------------------------------------------------------------------------------------------------------
		//pnlAddSchedule.setLayout(new BoxLayout(pnlAddSchedule, BoxLayout.Y_AXIS)); // Determines the orientation of the window
//		pnlAddSchedule.setBackground(Color.LIGHT_GRAY); // NEW CODE, //Sets the background colour of the add schedule window
//		pnlAddSchedule.add(new JLabel(" Billboard: ")); // Sets the tile of the select billboard combo box
//		pnlAddSchedule.add(Box.createVerticalStrut(5)); // Whitespace between Select Billboard and ComboBox
//		pnlAddSchedule.add(jcbBillboards);
//		pnlAddSchedule.add(Box.createVerticalStrut(10));
//		pnlAddSchedule.add(new JLabel("Schedule From: "));
//		pnlAddSchedule.add(Box.createVerticalStrut(5));
//		pnlAddSchedule.add(tfDay);
//		pnlAddSchedule.add(Box.createVerticalStrut(20));
//		pnlAddSchedule.add(new JLabel("Schedule Till: "));
//		pnlAddSchedule.add(Box.createVerticalStrut(5));
//		pnlAddSchedule.add(tfHour);
//		pnlAddSchedule.add(Box.createVerticalStrut(15));
//		pnlAddSchedule.add(Hour_ChkBox);
//		pnlAddSchedule.add(Box.createVerticalStrut(15));
//		pnlAddSchedule.add(new JLabel("Enter minute:"));
//		pnlAddSchedule.add(Box.createVerticalStrut(5));
//		pnlAddSchedule.add(tfMinute);
//		pnlAddSchedule.add(Box.createVerticalStrut(15));
//		pnlAddSchedule.add(new JLabel("Enter duration:"));
//		pnlAddSchedule.add(Box.createVerticalStrut(5));
//		pnlAddSchedule.add(tfDuration);
//		pnlAddSchedule.add(Box.createVerticalStrut(15));
//		pnlAddSchedule.add(new JLabel("Select repeat:"));
//		pnlAddSchedule.add(Box.createVerticalStrut(5));
//		pnlAddSchedule.add(jcbRepeat);
//		pnlAddSchedule.add(Box.createVerticalStrut(15));
		//*******************************************************

	}

	public void setMinutesRecurrence(int minutes){
		String[] minutesList = new String[minutes];
		//if (tfDurationRecur > 1)
		int count = 1;
		for(int i = 0; i < minutes; i++){
			minutesList[i] = Integer.toString(count);
			count++;
		}
		minutesRecurrence = minutesList;
	}

	/**
	 * Updates table.
	 * @param schedules the schedules
	 */
	public void updateTable(List<Schedule> schedules) {
		tblMdlAllSchedules.setRowCount(0);
		System.out.println("Schedule list size: " + schedules.size());
		for (Schedule s : schedules) {
			tblMdlAllSchedules.addRow(new String[] { s.getId() + "",
					DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(s.getDateTime()).toString(),
					s.getDuration() + "", s.getRepeat(), s.getIdBillboard() + "" });
		}
	}

	/**
	 * Adds the schedule.
	 *
	 * @return the schedule
	 * @throws Exception the exception
	 */
	public Schedule addSchedule() throws Exception {
		Schedule schedule = null;
		int add = JOptionPane.showConfirmDialog(this, pnlAddSchedule, "Add Schedule", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		if (add == 0) {

			LocalDateTime dateTime = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(),
					Integer.parseInt(tfDay.getText().trim()), Integer.parseInt(tfHour.getText().trim()),
					Integer.parseInt(tfMinute.getText().trim()));
			int duration = Integer.parseInt(tfDurationOnce.getText().trim());
			if (duration < 1) {
				throw new Exception();
			}
			String billboardId = (String) jcbBillboards.getSelectedItem();
			String repeat = (String) jcbRepeat.getSelectedItem();
			int minutes = -1;
			if (repeat.equals("Minutes")) {
				String minutesStr = JOptionPane.showInputDialog("Specify minutes:");
				minutes = Integer.parseInt(minutesStr);
				if (minutes < 1) {
					throw new Exception();
				}
			}
			/*
			 * When scheduling a billboard, the user may specify that the showing is to
			 * recur every day, every hour or every minutes (with a limitation that a
			 * billboard may not be scheduled more frequently than the duration of the
			 * billboard.)
			 */
			if (repeat.equals("Hour")) {
				if (duration < 60) {
					throw new Exception("Duration is less than Hour!");
				}
			} else if (repeat.equals("Day")) {
				if (duration < 60 * 24) {
					throw new Exception("Duration is less than Day!");
				}
			}
			if (minutes > 0) {
				if (duration < minutes) {
					throw new Exception("Duration is less than " + minutes + "!");
				}
				repeat += ":" + minutes;
			}
			schedule = new Schedule();
			schedule.setDateTime(dateTime);
			schedule.setDuration(duration);
			schedule.setIdBillboard(billboardId);
			schedule.setRepeat(repeat);

			// New Code  Sasitheran
			schedule.setDateTimeStart("");
			schedule.setDateTimeFinish("");
			schedule.setScheduleCreatedBy("");
			//schedule.setScheduleCreateDate("");

		}
		tfDay.setText("");
		tfMinute.setText("");
		tfDurationOnce.setText("");
		tfHour.setText("");
		jcbRepeat.setSelectedIndex(0);
		return schedule;
	}

	/**
	 * Updates billboards.
	 * @param billboards the billboards
	 */
	public void updateBillboards(List<Billboard> billboards) {
		System.out.println("Billboard list size: " + billboards.size());
		jcbBillboards.removeAllItems();
		System.out.println("Is empty: " + billboards);
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
	 * Gets the button btnEditSchedule.
	 * @return the button logout
	 */
	public JButton getBtnEditSchedule (){
		return btnEditSchedule;
	}

}
