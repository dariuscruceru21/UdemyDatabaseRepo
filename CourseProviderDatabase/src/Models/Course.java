package Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a course in the system, which includes information about the course
 * title, description, schedule, instructor, enrolled students, and associated modules.
 */
public class Course implements Identifiable {
    /** Unique identifier for the course */
    private Integer courseID;
    /** Title of the course */
    private String courseTitle;
    /** Description of the course */
    private String description;
    /** Number of available spots for enrollment */
    private Integer availableSpots;
    /** Start date of the course */
    private String startDate;
    /** End date of the course */
    private String endDate;
    /** List of students enrolled in the course */
    private List<Integer> enrolledStudents = new ArrayList<>();
    /** List of modules included in the course */
    private List<Integer> modules = new ArrayList<>();
    /** The instructor teaching this course */
    private Integer instructorId;

    /**
     * Constructs a Course object with specified attributes.
     *
     * @param courseID       The unique ID of the course.
     * @param courseTitle    The title of the course.
     * @param description    A description of the course content.
     * @param availableSpots The number of spots available for students to enroll.
     * @param startDate      The start date of the course.
     * @param endDate        The end date of the course.
     * @param instructor     The instructor teaching the course.
     */
    public Course(Integer courseID, String courseTitle, String description, Integer availableSpots, String startDate, String endDate, Integer instructor) {
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        this.description = description;
        this.availableSpots = availableSpots;
        this.startDate = startDate;
        this.endDate = endDate;
        this.instructorId = instructor;
    }

    public Integer getCourseID() {
        return courseID;
    }

    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAvailableSpots() {
        return availableSpots;
    }

    public void setAvailableSpots(Integer availableSpots) {
        this.availableSpots = availableSpots;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<Integer> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(List<Integer> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public List<Integer> getModules() {
        return modules;
    }

    public void setModules(List<Integer> modules) {
        this.modules = modules;
    }

    public Integer getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Integer instructorId) {
        this.instructorId = instructorId;
    }

    @Override
    public Integer getId() {
        return this.courseID;
    }

    @Override
    public void setId(Integer id) {
        this.courseID = id;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseID=" + courseID +
                ", courseTitle='" + courseTitle + '\'' +
                ", description='" + description + '\'' +
                ", availableSpots=" + availableSpots +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", instructorId=" + instructorId +
                '}';
    }
}