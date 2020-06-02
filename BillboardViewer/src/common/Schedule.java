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

	/**
	 * Instantiates a new schedule.
	 *
	 * @param id          the id
	 * @param dateTime    the date time
	 * @param duration    the duration
	 * @param repeat      the repeat
	 * @param idBillboard the id billboard
	 */
	public Schedule(int id, LocalDateTime dateTime, int duration, String repeat, String idBillboard) {
		this.id = id;
		this.dateTime = dateTime;
		this.duration = duration;
		this.repeat = repeat;
		this.idBillboard = idBillboard;
	}

	/**
	 * Instantiates a new schedule.
	 */
	public Schedule() {

	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the date time.
	 *
	 * @return the date time
	 */
	public LocalDateTime getDateTime() {
		return dateTime;
	}

	/**
	 * Sets the date time.
	 *
	 * @param dateTime the new date time
	 */
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	/**
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Sets the duration.
	 *
	 * @param duration the new duration
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * Gets the repeat.
	 *
	 * @return the repeat
	 */
	public String getRepeat() {
		return repeat;
	}

	/**
	 * Sets the repeat.
	 *
	 * @param repeat the new repeat
	 */
	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

	/**
	 * Gets the id billboard.
	 *
	 * @return the id billboard
	 */
	public String getIdBillboard() {
		return idBillboard;
	}

	/**
	 * Sets the id billboard.
	 *
	 * @param idBillboard the new id billboard
	 */
	public void setIdBillboard(String idBillboard) {
		this.idBillboard = idBillboard;
	}


}
