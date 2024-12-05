
package Models;

/**
 * Represents an admin user in the system, extending the generic User class.
 * Admins have privileges to manage and oversee system functionalities.
 */
public class Admin extends User {

    /**
     * Constructs an Admin with the specified user ID, username, password, and email.
     *
     * @param userID    The unique ID of the admin.
     * @param userName  The admin's username.
     * @param password  The admin's password.
     * @param email     The admin's email address.
     */
    public Admin(int userID, String userName, String password, String email,String type) {
        super(userID, userName, password, email,type);
    }

}
