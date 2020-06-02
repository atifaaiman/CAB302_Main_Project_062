import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import common.Billboard;
import common.Schedule;

/**
 * The Class SchedulesPanel. Represents panel to display schedules.
 */
public class SchedulesPanel extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The table model all schedules. */
	private DefaultTableModel tblMdlAllSchedules = new DefaultTableModel(
			new String[] { "ID", "Date Time", "Duration", "Repeat", "ID Billboard" }, 0) {

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
	private JComboBox<String> jcbBillboards = new JComboBox<>();

	/** The panel to add schedule. */
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
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JPanel pnlNorthSched = new JPanel(new GridLayout(1, 2));
		pnlNorthSched.add(new JLabel("Schedules Calendar", SwingConstants.LEFT));
		JPanel pnlLogoutSched = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlLogoutSched.add(btnLogout);
		pnlNorthSched.add(pnlLogoutSched);
		add(pnlNorthSched, BorderLayout.NORTH);
		add(new JScrollPane(tblAllSchedules), BorderLayout.CENTER);
		JPanel pnlSouthSched = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSouthSched.add(btnAddSchedule);
		add(pnlSouthSched, BorderLayout.SOUTH);

		/*
		 * Init add Schedule.
		 */
		pnlAddSchedule.setLayout(new BoxLayout(pnlAddSchedule, BoxLayout.Y_AXIS));
		pnlAddSchedule.add(new JLabel("Select billboard:"));
		pnlAddSchedule.add(Box.createVerticalStrut(5));

		pnlAddSchedule.add(jcbBillboards);
		pnlAddSchedule.add(Box.createVerticalStrut(15));
		pnlAddSchedule.add(new JLabel("Enter day:"));
		pnlAddSchedule.add(Box.createVerticalStrut(5));

		pnlAddSchedule.add(tfDay);
		pnlAddSchedule.add(Box.createVerticalStrut(15));
		pnlAddSchedule.add(new JLabel("Enter hour:"));
		pnlAddSchedule.add(Box.createVerticalStrut(5));

		pnlAddSchedule.add(tfHour);
		pnlAddSchedule.add(Box.createVerticalStrut(15));
		pnlAddSchedule.add(new JLabel("Enter minute:"));
		pnlAddSchedule.add(Box.createVerticalStrut(5));

		pnlAddSchedule.add(tfMinute);
		pnlAddSchedule.add(Box.createVerticalStrut(15));
		pnlAddSchedule.add(new JLabel("Enter duration:"));
		pnlAddSchedule.add(Box.createVerticalStrut(5));

		pnlAddSchedule.add(tfDuration);
		pnlAddSchedule.add(Box.createVerticalStrut(15));
		pnlAddSchedule.add(new JLabel("Select repeat:"));
		pnlAddSchedule.add(Box.createVerticalStrut(5));

		pnlAddSchedule.add(jcbRepeat);
		pnlAddSchedule.add(Box.createVerticalStrut(15));
	}

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
		Schedule schedule = null;
		int add = JOptionPane.showConfirmDialog(this, pnlAddSchedule, "Add Schedule", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (add == 0) {

			LocalDateTime dateTime = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(),
					Integer.parseInt(tfDay.getText().trim()), Integer.parseInt(tfHour.getText().trim()),
					Integer.parseInt(tfMinute.getText().trim()));
			int duration = Integer.parseInt(tfDuration.getText().trim());
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

		}
		tfDay.setText("");
		tfMinute.setText("");
		tfDuration.setText("");
		tfHour.setText("");
		jcbRepeat.setSelectedIndex(0);
		return schedule;
	}

	/**
	 * Updates billboards.
	 *
	 * @param billboards the billboards
	 */
	public void updateBillboards(List<Billboard> billboards) {
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
