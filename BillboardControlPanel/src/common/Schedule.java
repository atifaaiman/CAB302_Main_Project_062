package common;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The Class Schedule. Encapsulates schedule object to be transferred over
 * network and to be stored into database.
 */
public class Schedule implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private int id;

	/** The date time. */
	private LocalDateTime dateTime;

	/** The duration. */
	private int duration;

	/** The repeat. */
	private String repeat;

	/** The id billboard. */
	private String idBillboard;

	// Fernando Changes start -----------------------------------

	/** The Date and time to start */
	private String dateTimeStart;

	/** The Date and time to finish */
	private String dateTimeFinish;

	/** The username of the scheduler */
	private String scheduleCreatedBy;

	/** The Date when schedule was created */
	private String scheduleCreateDate;

	// Fernando Changes finish -----------------------------------

	/**
	 * Instantiates a new schedule.
	 *
	 * @param id          		 the id
	 * @param dateTime    		 the date time
	 * @param duration    		 the duration
	 * @param repeat      		 the repeat
	 * @param idBillboard 		 the id billboard
	 * @param dateTimeStart 	 the date and time to start presenting the billboard     (Added by Fernando)
	 * @param dateTimeFinish	 the date and time to finish presenting the billboard    (Added by Fernando)
	 * @param scheduleCreatedBy  the user who create the schedule  					   	 (Added by Fernando)
	 * @param scheduleCreateDate the date when the schedule was created					 (Added by Fernando)
	 */
	public Schedule(int id, LocalDateTime dateTime, int duration, String repeat, String idBillboard,
					String dateTimeStart, String dateTimeFinish, String scheduleCreatedBy, String scheduleCreateDate) {
		this.id = id;
		this.dateTime = dateTime;
		this.duration = duration;
		this.repeat = repeat;
		this.idBillboard = idBillboard;

		// Fernando Changes start -----------------------------------
		this.dateTimeStart = dateTimeStart;
		this.dateTimeFinish = dateTimeFinish;
		this.scheduleCreatedBy = scheduleCreatedBy;
		this.scheduleCreateDate = scheduleCreateDate;
		// Fernando Changes finish -----------------------------------
	}

	/**
	 * Instantiates a new schedule.
	 */
	public Schedule() {

	}

	/**
	 * Gets the id.
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the date time.
	 * @return the date time
	 */
	public LocalDateTime getDateTime() {
		return dateTime;
	}

	/**
	 * Sets the date time.
	 * @param dateTime the new date time
	 */
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	/**
	 * Gets the duration.
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Sets the duration.
	 * @param duration the new duration
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * Gets the repeat.
	 * @return the repeat
	 */
	public String getRepeat() {
		return repeat;
	}

	/**
	 * Sets the repeat.
	 * @param repeat the new repeat
	 */
	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

	/**
	 * Gets the id billboard.
	 * @return the id billboard
	 */
	public String getIdBillboard() {
		return idBillboard;
	}

	/**
	 * Sets the id billboard.
	 * @param idBillboard the new id billboard
	 */
	public void setIdBillboard(String idBillboard) {
		this.idBillboard = idBillboard;
	}

	// Fernando Changes start -----------------------------------

	/**
	 * Gets the date and time to start presenting the billboard.
	 * @return the date and time in a string format "YYYY-MM-dd HH-mm-ss"
	 */
	public String getDateTimeStart() {
		return dateTimeStart;
	}
	/**
	 * Sets the date and time to start presenting the billboard.
	 * @param dateTimeStart in a string format "YYYY-MM-dd HH-mm-ss"
	 */
	public void setDateTimeStart(String dateTimeStart) {
		this.dateTimeStart = dateTimeStart;
	}

	/**
	 * Gets the date and time to finish presenting the billboard.
	 * @return the date and time in a string format "YYYY-MM-dd HH-mm-ss".
	 */
	public String getDateTimeFinish() {
		return dateTimeFinish;
	}
	/**
	 * Sets the date and time to finish presenting the billboard.
	 * @param dateTimeFinish in a string format "YYYY-MM-dd HH-mm-ss"
	 */
	public void setDateTimeFinish(String dateTimeFinish) {
		this.dateTimeFinish = dateTimeFinish;
	}

	/**
	 * Gets the username of the schedule creator.
	 * @return the username in a string.
	 */
	public String getScheduleCreatedBy() {
		return scheduleCreatedBy;
	}
	/**
	 * Sets the username of the schedule creator.
	 * @param scheduleCreatedBy the username in a string.
	 */
	public void setScheduleCreatedBy(String scheduleCreatedBy) {
		this.scheduleCreatedBy = scheduleCreatedBy;
	}

	/**
	 * Gets the date and time when the schedule was created.
	 * @return the date and time in a string format "YYYY-MM-dd HH-mm-ss"
	 */
	public String getScheduleCreateDate() {
		return scheduleCreateDate;
	}
	/**
	 * Sets the date and time when the schedule was created.
	 * @param  scheduleCreateDate date and time in a string format "YYYY-MM-dd HH-mm-ss"
	 */
	public void setScheduleCreateDate(String scheduleCreateDate) {
		this.scheduleCreateDate = scheduleCreateDate;
	}


	// Fernando Changes finish -----------------------------------
}
