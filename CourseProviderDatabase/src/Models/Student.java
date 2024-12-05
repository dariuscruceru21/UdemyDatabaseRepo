
package Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a student user within the system.
 * A student has additional attributes and methods specific to their role,
 * such as course enrollment and assignment submission.
 */
public class Student extends User {
    /** List of courses that the student is enrolled in */
    private  List<Integer> courses = new ArrayList<>();

    /**
     * Constructs a Student object with specified attributes.
     *
     * @param userID         The unique ID of the student.
     * @param userName       The username of the student.
     * @param password       The password for the student's account.
     * @param email          The student's email address.

     */
    public Student(int userID, String userName, String password, String email,String type) {
        super(userID, userName, password, email,type);
    }


    public List<Integer> getCourses() {
        return courses;
    }

    public void setCourses(List<Integer> courses) {
        this.courses = courses;
    }
}
