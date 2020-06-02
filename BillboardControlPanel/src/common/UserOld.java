package common;

import java.io.Serializable;

/**
 * The Class User. Encapsulates user object to be transferred over
 * network and to be stored into database.
 */
public class UserOld implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The username. */
    private String username;

    /** The password. */
    private String password;

    /** The permission. */
    private String permission;

    /** The old password. */
    private String oldPassword;

    /**
     * Instantiates a new user.
     *
     * @param username the username
     * @param password the password
     * @param permission the permission
     */
    public UserOld(String username, String password, String permission) {
        this.username = username;
        this.password = password;
        this.permission = permission;
    }

    /**
     * Instantiates a new user.
     */
    public UserOld() {

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

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the permission.
     *
     * @return the permission
     */
    public String getPermission() {
        return permission;
    }

    /**
     * Sets the permission.
     *
     * @param permission the new permission
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     * Gets the serialversionuid.
     *
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * Gets the old password.
     *
     * @return the old password
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * Sets the old password.
     *
     * @param oldPassword the new old password
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

}
