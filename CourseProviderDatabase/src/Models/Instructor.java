
package Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Instructor in the system, extending the User class.
 * An instructor can have a list of courses they are teaching.
 */
public class Instructor extends User {
    private List<Integer> courses = new ArrayList<>();



    /**
     * Constructs an Instructor with the specified ID, username, password, and email.
     *
     * @param userID   The unique ID for the instructor.
     * @param userName The username of the instructor.
     * @param password The password for the instructor's account.
     * @param email    The email address of the instructor.
     */
    public Instructor(int userID, String userName, String password, String email, String type) {
        super(userID, userName, password, email,type);
    }

    public List<Integer> getCourses() {
        return courses;
    }

    public void setCourses(List<Integer> courses) {
        this.courses = courses;
    }
}
