import common.Message;

/**
 * The Interface Observable.
 * Contains only one method
 * to update observer object
 * once observable is changed.
 * In this project, observable
 * is {@link InputCommandHandler}, observer
 * is {@link Controller}.
 * Note, actually the updated object is {@link GUI},
 * but it's updated through the {@link Controller}, so
 * controller waits for the updates from observable
 * {@link InputCommandHandler} and updates GUI.
 */
public interface Observable {
	
	/**
	 * Updates observer ({@link Controller}
	 *
	 * @param msg the {@link Message} to identify needed update
	 */
	public void update(Message msg);
}
