import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;

import common.Billboard;
import common.Schedule;

/**
 * The Class SchedulesPanel. Represents panel to display schedules.
 */
public class SchedulesPanel extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
//_______________________naif edited_________________________________
	/** The table model all schedules. */
	private DefaultTableModel tblMdlAllSchedules = new DefaultTableModel(
			new String[] { "ID", "Date Time", "Duration by min" }, 0) {
													//no need for this one
														//, "ID Billboard"
//____________________________________________________________________________
		private static final long serialVersionUID = 1L;

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	/** The table all schedules. */
	private JTable tblAllSchedules = new JTable(tblMdlAllSchedules);

	/** The button to add schedule. */
	private JButton btnAddSchedule = new JButton("Add Schedule");

	/** The button to logout. */
	private JButton btnLogout = new JButton("Logout");

	/** The combo box with billboards. */
	// This is the drop-down menu for the created billboards
	private JComboBox<String> jcbBillboards = new JComboBox<>();

	//_______________Naif added____________________________
	/** The panel to add schedule. */
	// This is the window that brings up the Calendar View
	private JPanel pnlAddSchedule = new JPanel();

	/** The text field day. */
	private JTextField tfDay = new JTextField(5);

	/** The text field hour. */
	private JTextField tfHour = new JTextField(5);

	/** The text field minute. */
	private JTextField tfMinute = new JTextField(5);

	/** The text field duration. */
	private JTextField tfDuration = new JTextField(5);

	/** The combo box with repeat values. */
	private JComboBox<String> jcbRepeat = new JComboBox<>(new String[] { "Hour", "Day", "Minutes" });
	//_____________________Naif added_________________________________________
	//I added JCheckBox for the new time for sasi design code
	private JCheckBox Hour_ChkBox = new JCheckBox("Hour");


	// Boolean variable to update combobox with billboards only if it is true.
	private boolean update;

	// I added JTextField to present the date that been choose by the user from the calender
	private JTextField jtfPickedDate = new JTextField(15);

	//added spinner for the time from sasi code
	private JSpinner jsPickedTime = new JSpinner(new SpinnerDateModel());

// add JRadioButton for duration day, hour, min
	private JRadioButton jrbDay = new JRadioButton("Day");

	private JRadioButton jrbHour = new JRadioButton("Hour");

	private JRadioButton jrbMinute = new JRadioButton("Minute", true);
	//_____________________________________________________________________________
	/**
	 * Instantiates a new schedules panel.
	 */
	public SchedulesPanel() {
		//______________Naif added_______________________________________________
		// Initially it should be initialized to true, combobox
		// it need to be updated.
		update = true;


		initGUIComponents();
	}

	/**
	 * Inits the GUI components.
	 */
	private void initGUIComponents() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JPanel pnlNorthSched = new JPanel(new GridLayout(1, 2));
		pnlNorthSched.add(new JLabel("Schedule Your Billboard Here !", SwingConstants.LEFT));
		JPanel pnlLogoutSched = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlLogoutSched.add(btnLogout);
		pnlNorthSched.add(pnlLogoutSched);
		add(pnlNorthSched, BorderLayout.NORTH);
		add(new JScrollPane(tblAllSchedules), BorderLayout.CENTER);
		JPanel pnlSouthSched = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSouthSched.add(btnAddSchedule);
		add(pnlSouthSched, BorderLayout.EAST);

		/*
		 * Init add Schedule.
		 */
		/*
		pnlAddSchedule.setLayout(new BoxLayout(pnlAddSchedule, BoxLayout.Y_AXIS)); // Determines the orientation of the window
		pnlAddSchedule.setBackground(Color.LIGHT_GRAY); // NEW CODE, //Sets the background colour of the add schedule window
		pnlAddSchedule.add(new JLabel(" Select billboard: ")); // Sets the tile of the select billboard combo box
		pnlAddSchedule.add(Box.createVerticalStrut(5)); // Whitespace between Select Billboard and ComboBox
		pnlAddSchedule.add(jcbBillboards);
		pnlAddSchedule.add(Box.createVerticalStrut(10));
		pnlAddSchedule.add(new JLabel("Schedule From: "));
		pnlAddSchedule.add(Box.createVerticalStrut(5));

		pnlAddSchedule.add(tfDay);
		pnlAddSchedule.add(Box.createVerticalStrut(20));
		pnlAddSchedule.add(new JLabel("Schedule Till: "));
		pnlAddSchedule.add(Box.createVerticalStrut(5));

		pnlAddSchedule.add(tfHour);
		pnlAddSchedule.add(Box.createVerticalStrut(15));
		pnlAddSchedule.add(Hour_ChkBox);
		pnlAddSchedule.add(Box.createVerticalStrut(15));
		*/

		//pnlAddSchedule.add(new JLabel("Enter minute:"));
		//pnlAddSchedule.add(Box.createVerticalStrut(5));

		//pnlAddSchedule.add(tfMinute);
		//pnlAddSchedule.add(Box.createVerticalStrut(15));
		//pnlAddSchedule.add(new JLabel("Enter duration:"));
		//pnlAddSchedule.add(Box.createVerticalStrut(5));

		//pnlAddSchedule.add(tfDuration);
		//pnlAddSchedule.add(Box.createVerticalStrut(15));
		//pnlAddSchedule.add(new JLabel("Select repeat:"));
		//pnlAddSchedule.add(Box.createVerticalStrut(5));

		//pnlAddSchedule.add(jcbRepeat);
		//pnlAddSchedule.add(Box.createVerticalStrut(15));

		//___________________OLD version___________________________________

//		pnlAddSchedule.setLayout(new BoxLayout(pnlAddSchedule, BoxLayout.Y_AXIS));
//		pnlAddSchedule.add(new JLabel("Select billboard:"));
//		pnlAddSchedule.add(Box.createVerticalStrut(5));
//
//		pnlAddSchedule.add(jcbBillboards);
//		pnlAddSchedule.add(Box.createVerticalStrut(15));
//		pnlAddSchedule.add(new JLabel("Enter day:"));
//		pnlAddSchedule.add(Box.createVerticalStrut(5));
//
//		pnlAddSchedule.add(tfDay);
//		pnlAddSchedule.add(Box.createVerticalStrut(15));
//		pnlAddSchedule.add(new JLabel("Enter hour:"));
//		pnlAddSchedule.add(Box.createVerticalStrut(5));
//
//		pnlAddSchedule.add(tfHour);
//		pnlAddSchedule.add(Box.createVerticalStrut(15));
//		pnlAddSchedule.add(new JLabel("Enter minute:"));
//		pnlAddSchedule.add(Box.createVerticalStrut(5));
//
//		pnlAddSchedule.add(tfMinute);
//		pnlAddSchedule.add(Box.createVerticalStrut(15));
//		pnlAddSchedule.add(new JLabel("Enter duration:"));
//		pnlAddSchedule.add(Box.createVerticalStrut(5));
//
//		pnlAddSchedule.add(tfDuration);
//		pnlAddSchedule.add(Box.createVerticalStrut(15));
//		pnlAddSchedule.add(new JLabel("Select repeat:"));
//		pnlAddSchedule.add(Box.createVerticalStrut(5));
//
//		pnlAddSchedule.add(jcbRepeat);
//		pnlAddSchedule.add(Box.createVerticalStrut(15));
//_________________________________________________________________________________________________________
//___________________________________Naif added_______________________________________________________
		ButtonGroup bg = new ButtonGroup();
		bg.add(jrbDay);
		bg.add(jrbHour);
		bg.add(jrbMinute);
//   I toke 3 layout from the old v enter date, Enter duration and Select billboard.
		pnlAddSchedule.setLayout(new BoxLayout(pnlAddSchedule, BoxLayout.Y_AXIS));
		pnlAddSchedule.add(new JLabel("Select billboard:"));
		pnlAddSchedule.add(Box.createVerticalStrut(5));

		pnlAddSchedule.add(jcbBillboards);
//		pnlAddSchedule.add(Box.createVerticalStrut(15));
//		pnlAddSchedule.add(new JLabel("Enter date:"));
		pnlAddSchedule.add(Box.createVerticalStrut(15));
		//added JButton for selected date for the calender
		JButton jbSelectDate = new JButton("Select date");
		//button wont move maybe we can fix it together Sasi used in his code
		jbSelectDate.setBounds(20,20,12,5);
		jbSelectDate.addActionListener(e -> {
			// add addActionListener for Picked date from Sasi code.
			// to use his calendar in PickedDate class
			jtfPickedDate.setText(new DatePicker(pnlAddSchedule).setPickedDate());
		});
		// jb for selectDate
		pnlAddSchedule.add(jbSelectDate);
		// the height for the box
		pnlAddSchedule.add(Box.createVerticalStrut(5));
		pnlAddSchedule.add(jtfPickedDate);
		pnlAddSchedule.add(Box.createVerticalStrut(15));
//		pnlAddSchedule.add(Box.createHorizontalStrut(-50));
		pnlAddSchedule.add(new JLabel("Select time:"));
		pnlAddSchedule.add(jsPickedTime);
		// to make it easy for user added Jlabel for Enter duration and Repeat:
		pnlAddSchedule.add(Box.createVerticalStrut(15));
		pnlAddSchedule.add(new JLabel("Enter duration:"));
		pnlAddSchedule.add(Box.createVerticalStrut(5));
		pnlAddSchedule.add(tfDuration);
		pnlAddSchedule.add(Box.createVerticalStrut(15));
		pnlAddSchedule.add(new JLabel("Repeat:"));
		pnlAddSchedule.add(Box.createVerticalStrut(5));
		// the 3 JRadioButton for the duration have to be added here as well
		pnlAddSchedule.add(jrbDay);
		pnlAddSchedule.add(Box.createVerticalStrut(5));
		pnlAddSchedule.add(jrbHour);
		pnlAddSchedule.add(Box.createVerticalStrut(5));
		pnlAddSchedule.add(jrbMinute);
//__________________Sasi code billboard Update program, ControlPanel class, line 767 to 770_____________________________
		JSpinner.DateEditor de_Clock_2 = new JSpinner.DateEditor(jsPickedTime, "HH:mm:ss");
//		jsPickedTime.setBounds(432, 43, 67, 20);
		DateFormatter formatter_1 = (DateFormatter) de_Clock_2.getTextField().getFormatter();
		formatter_1.setAllowsInvalid(false); // this makes what you want
		formatter_1.setOverwriteMode(true);

		jsPickedTime.setEditor(de_Clock_2);
//______________________________________________________________________________________________________________________
	}
//______________________________________________________________________________________________________________________
	/**
	 * Updates table.
	 *
	 * @param schedules the schedules
	 */
	public void updateTable(List<Schedule> schedules) {
		tblMdlAllSchedules.setRowCount(0);
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
//__________________Naif added____________________________________________
		// here we set 'update' to false to avoid
		// updating combobox when the user tries to add new Schedule.
		update = false;
//___________________________________________________________________
		Schedule schedule = null;
		int add = JOptionPane.showConfirmDialog(this, pnlAddSchedule, "Add Schedule", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (add == 0) {
//______________________________Naif delete the old dateTime Object to get the text________________
//			LocalDateTime dateTime = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(),
//					Integer.parseInt(tfDay.getText().trim()), Integer.parseInt(tfHour.getText().trim()),
//					Integer.parseInt(tfMinute.getText().trim()));
//____________________________added Sasi PickedDate to get the text___________________________________
			String dateStr = jtfPickedDate.getText();

			// added the time format to be accepted
			String timeStr = new SimpleDateFormat("HH-mm-ss").format(jsPickedTime.getValue());

			LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			LocalTime time = LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH-mm-ss"));
			LocalDateTime dateTime = LocalDateTime.of(date, time);
			// this one did not work
//			try {
//				dateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"));
//			} catch (Exception exc) {
//				exc.printStackTrace();
//			}

			int duration = Integer.parseInt(tfDuration.getText().trim());
			if (duration < 1) {
				throw new Exception();
			}
			//_________________Naif edited_______________________________________________________
			String billboardId = (String) jcbBillboards.getSelectedItem();
			//added here Object for selecting the repeat
			String repeat = jrbDay.isSelected() ? "Day" : jrbMinute.isSelected() ? "Minutes" : "Hour";
			int minutes = -1;
			if (repeat.equals("Minutes")) {
//__________________________________________________________________________________________________
				// Found JOptionPane dialog(window) to specify minutes.
//				String minutesStr = JOptionPane.showInputDialog("Specify minutes:");
//
//				minutes = Integer.parseInt(minutesStr);
//				if (minutes < 1) {
//					throw new Exception();
//				}
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

			// New Code  Sasitheran ***********************************
			schedule.setDateTimeStart(dateTime.toString());
			schedule.setDateTimeFinish(dateTime.toString());
			schedule.setScheduleCreatedBy("All");
			//schedule.setScheduleCreateDate("");

		}
		tfDay.setText("");
		tfMinute.setText("");
		tfDuration.setText("");
		tfHour.setText("");
		jcbRepeat.setSelectedIndex(0);
//______________________Naif added_______________________________________________
		// When 'Add new schedule' operation finished, set 'update' back to true.
		update = true;
//________________________________________________________________________________
		return schedule;
	}

	/**
	 * Updates billboards.
	 *
	 * @param billboards the billboards
	 */
	public void updateBillboards(List<Billboard> billboards) {
//_______________________Naif added____________________________________
		// check 'update' variable and it is false, just return
		// to avoid updating combobox.
		if (!update) {
			return;
		}
//_____________________________________________________________________
		jcbBillboards.removeAllItems();
		for (Billboard b : billboards) {
			jcbBillboards.addItem(b.getName());
		}
		revalidate();
		updateUI();
	}

	/**
	 * Gets the button add schedule.
	 *
	 * @return the button add schedule
	 */
	public JButton getBtnAddSchedule() {
		return btnAddSchedule;
	}

	/**
	 * Gets the button logout.
	 *
	 * @return the button logout
	 */
	public JButton getBtnLogout() {
		return btnLogout;
	}

}
