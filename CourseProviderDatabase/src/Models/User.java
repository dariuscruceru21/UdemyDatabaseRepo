package Models;

/**
 * Represents a generic user in the system, storing basic user information such as ID,
 * username, password, and email. This class is intended to be extended by specific user types.
 */
public abstract class User implements Identifiable {
    /** Unique identifier for the user */
    private Integer userID;
    /** Username for user login */
    private String userName;
    /** User password */
    private String password;
    /** User email address */
    private String email;

    private String type;

    public String getType() {
        return type;
    }

    /**
     * Constructs a User with the specified ID, username, password, and email.
     *
     * @param userID    The unique ID of the user.
     * @param userName  The user's username.
     * @param password  The user's password.
     * @param email     The user's email address.
     */
    User(Integer userID, String userName, String password, String email, String type) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.type = type;
    }

    /**
     * Gets the unique identifier for the user.
     *
     * @return The user's ID.
     */
    @Override
    public Integer getId() {
        return this.userID;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username.
     */
    public String getUsername() {
        return this.userName;
    }

    /**
     * Gets the user's password.
     *
     * @return The password.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Gets the user's email address.
     *
     * @return The email address.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the unique identifier for the user.
     *
     * @param userid The ID to set for the user.
     */
    public void setUserid(Integer userid) {
        this.userID = userid;
    }

    /**
     * Sets the username for the user.
     *
     * @param username The username to set for the user.
     */
    public void setUsername(String username) {
        this.userName = username;
    }

    /**
     * Sets the user's password.
     *
     * @param password The password to set for the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the user's email address.
     *
     * @param email The email address to set for the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets a new username for the user. (Alias for setUserName)
     *
     * @param newUserName The new username to set for the user.
     */
    public void setName(String newUserName) {
        this.userName = newUserName;
    }

    @Override
    public void setId(Integer id) {
        this.userID = id;
    }
}