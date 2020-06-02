package common;

import java.io.Serializable;

/**
 * The Class Billboard. Encapsulates billboard to be stored into database and
 * transferred through network between server and clients.
 */
public class Billboard implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The name. */
	private String name;

	/** The xml data. */
	private byte[] xmlData;

	/** The username. */
	private String username;

	/**
	 * Instantiates a new billboard.
	 */
	public Billboard() {

	}

	/**
	 * Instantiates a new billboard.
	 *
	 * @param name     the name
	 * @param xmlData  the xml data
	 * @param username the username
	 */
	public Billboard(String name, byte[] xmlData, String username) {
		this.name = name;
		this.xmlData = xmlData;
		this.username = username;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the xml data.
	 *
	 * @return the xml data
	 */
	public byte[] getXmlData() {
		return xmlData;
	}

	/**
	 * Sets the xml data.
	 *
	 * @param xmlData the new xml data
	 */
	public void setXmlData(byte[] xmlData) {
		this.xmlData = xmlData;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

}
