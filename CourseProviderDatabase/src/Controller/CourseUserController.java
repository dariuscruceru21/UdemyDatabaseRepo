package Controller;

import Exceptions.BusinessException;
import Exceptions.EntityNotFoundException;
import Exceptions.ValidationException;
import Models.Admin;
import Models.Course;
import Models.Instructor;
import Models.Student;
import Service.CoursesUserService;

import java.util.List;

public class CourseUserController {
    private final CoursesUserService service;

    public CourseUserController(CoursesUserService service) {
        this.service = service;
    }

    /**
     * Retrieves all students enrolled in a specified course.
     * @param courseId The ID of the course.
     * @return A list of students enrolled in the course.
     */
    public List<Student> getEnrolledStudents(Integer courseId) throws EntityNotFoundException {
        return service.getEnrolledStudents(courseId);
    }

    /**
     * Enrolls a student in a course if there are available spots.
     * @param studentId The ID of the student to enroll.
     * @param courseId The ID of the course.
     * @return A success message if enrollment is successful.
     */
    public String enrollStudentInCourse(Integer studentId, Integer courseId) {
        try {
            service.enroll(studentId, courseId);
            return "Student enrolled successfully.";
        } catch (Exception e) {
            return "Enrollment failed: " + e.getMessage();
        }
    }

    /**
     * Assigns an instructor to a specified course.
     * @param instructorId The ID of the instructor.
     * @param courseId The ID of the course.
     * @return A success message if the assignment is successful.
     */
    public String assignInstructorToCourse(Integer instructorId, Integer courseId) {
        try {
            service.assignInstructor(instructorId, courseId);
            return "Instructor assigned successfully.";
        } catch (Exception e) {
            return "Instructor assignment failed: " + e.getMessage();
        }
    }

    /**
     * Unassigns an instructor from a specific course.
     * @param instructorId The ID of the instructor.
     * @param courseId The ID of the course.
     * @return A success message if unassignment is successful.
     */
    public String unassignInstructorFromCourse(java.lang.Integer instructorId, java.lang.Integer courseId) {
        try {
            service.unAssignInstructor(instructorId, courseId);
            return "Instructor unassigned successfully.";
        } catch (Exception e) {
            return "Instructor unassignment failed: " + e.getMessage();
        }
    }

    /**
     * Adds a new course to the system.
     * @param course The course to add.
     * @return A success message after adding the course.
     */
    public String addCourse(Course course) throws ValidationException {
        service.addCourse(course);
        return "Course added successfully.";
    }

    /**
     * Adds a new student to the system.
     * @param student The student to add.
     * @return A success message after adding the student.
     */
    public String addStudent(Student student) {
        service.addStudent(student);
        return "Student added successfully.";
    }

    /**
     * Adds a new instructor to the system.
     * @param instructor The instructor to add.
     * @return A success message after adding the instructor.
     */
    public String addInstructor(Instructor instructor) {
        service.addInstructor(instructor);
        return "Instructor added successfully.";
    }

    /**
     * Adds a new admin to the system.
     * @param admin The instructor to add.
     * @return A success message after adding the instructor.
     */
    public String addAdmin(Admin admin) {
        service.addAdmin(admin);
        return "Admin added successfully.";
    }

    /**
     * Removes a course and unenrolls all students from it.
     * @param courseId The ID of the course to remove.
     * @return A success message after removing the course.
     */
    public String removeCourse(Integer courseId) throws EntityNotFoundException {
        service.removeCourse(courseId);
        return "Course removed successfully.";
    }

    /**
     * Removes a student and unenrolls them from all courses.
     * @param studentId The ID of the student to remove.
     * @return A success message after removing the student.
     */
    public String removeStudent(Integer studentId) throws EntityNotFoundException {
        service.removeStudent(studentId);
        return "Student removed successfully.";
    }

    /**
     * Removes an instructor and unassigns them from all courses.
     * @param instructorId The ID of the instructor to remove.
     * @return A success message after removing the instructor.
     */
    public String removeInstructor(Integer instructorId) throws EntityNotFoundException, BusinessException {
        if (service.getCourseInfo(instructorId) != null) {
            service.unAssignInstructor(instructorId, service.getCourseInfo(instructorId).getId());
        }
        service.removeInstructor(instructorId);
        return "Instructor removed successfully.";
    }

    /**
     * Removes an admin.
     * @param adminId The ID of the admin to remove.
     * @return A success message after removing the admin.
     */
    public String removeAdmin(Integer adminId) throws EntityNotFoundException {
        service.removeAdmin(adminId);
        return "Admin removed successfully.";
    }

    /**
     * Retrieves a list of all courses in the system.
     * @return A list of all courses.
     */
    public List<Course> getAllCourses() {
        return service.getAllCourses();
    }

    /**
     * Retrieves a list of all students in the system.
     * @return A list of all students.
     */
    public List<Student> getAllStudents() {
        return service.getAllStudents();
    }

    /**
     * Retrieves a list of all instructors in the system.
     * @return A list of all instructors.
     */
    public List<Instructor> getAllInstructors() {
        return service.getAllInstructors();
    }

    /**
     * Retrieves a list of all admins in the system.
     * @return A list of all admins.
     */
    public List<Admin> getAllAdmins() {
        return service.getAllAdmins();
    }

    /**
     * Unenrolls a student from a specific course.
     * @param studentId The ID of the student to unenroll.
     * @param courseId The ID of the course.
     * @return A success message if unenrollment is successful.
     */
    public String unenrollStudentFromCourse(Integer studentId, Integer courseId) throws EntityNotFoundException, BusinessException {
        service.unenroll(studentId, courseId);
        return "Student unenrolled successfully.";
    }

    /**
     * Retrieves all courses a student is enrolled in.
     * @param studentId The ID of the student.
     * @return A list of courses the student is enrolled in.
     */
    public List<Course> getCoursesByStudent(Integer studentId) throws EntityNotFoundException {
        return service.getCoursesAStudentEnrolledIn(studentId);
    }

    /**
     * Retrieves all courses an instructor teaches.
     * @param instructorId The ID of the instructor.
     * @return A list of courses the instructor teaches.
     */
    public List<Course> getCoursesByInstructor(Integer instructorId) {
        return service.getCoursesAInstructorTeaches(instructorId);
    }

    /**
     * Retrieves detailed information about a specific course.
     * @param courseId The ID of the course.
     * @return The course object containing detailed information.
     */
    public Course getCourseInfo(Integer courseId) throws EntityNotFoundException {
        return service.getCourseInfo(courseId);
    }

    /**
     * Retrieves detailed information about a specific student.
     * @param studentId The ID of the student.
     * @return The student object containing detailed information.
     */
    public Student getStudentInfo(Integer studentId) throws EntityNotFoundException {
        return service.getStudentInfo(studentId);
    }

    /**
     * Retrieves detailed information about a specific instructor.
     * @param instructorId The ID of the instructor.
     * @return The instructor object containing detailed information.
     */
    public Instructor getInstructorInfo(Integer instructorId) throws EntityNotFoundException {
        return service.getInstructorInfo(instructorId);
    }

    /**
     * Updates information for an existing student.
     * @param student The student object with updated information.
     * @return A success message if the update is successful.
     */
    public String updateStudent(Student student) throws EntityNotFoundException {
        try {
            service.updateStudent(student);
            return "Student updated successfully.";
        } catch (IllegalArgumentException e) {
            return "Update failed: " + e.getMessage();
        }
    }


    public String updateCourse(Course course)throws EntityNotFoundException {
        try {
            service.updateCourse(course);
            return "Course updated successfully.";
        } catch (IllegalArgumentException e) {
            return "Update failed: " + e.getMessage();
        }
    }

    /**
     * Updates information for an existing instructor.
     * @param instructor The instructor object with updated information.
     * @return A success message if the update is successful.
     */
    public String updateInstructor(Instructor instructor) throws EntityNotFoundException {
        try {
            service.updateInstructor(instructor);
            return "Instructor updated successfully.";
        } catch (IllegalArgumentException e) {
            return "Update failed: " + e.getMessage();
        }
    }

    /**
     * Retrieves the instructor assigned to a specific course.
     * @param courseId The ID of the course.
     * @return The instructor assigned to the course.
     */
    public Instructor getAssignedInstructor(java.lang.Integer courseId) throws EntityNotFoundException {
        return service.getAssignedInstructor(courseId);
    }

    /**
     * Retrieves all under-occupied courses.
     * Under-occupied courses are those where the number of enrolled students is
     * less than or equal to 20% of the available spots.
     *
     * @return A list of under-occupied courses.
     */
    public List<Course> getAllUnderOccupiedCourses() {
        return service.getAllUnderOcupiedCourses();
    }


    /**
     * Retrieves all instructors sorted by the number of courses they teach in descending order.
     *
     * @return A list of instructors sorted by the number of courses.
     */
    public List<Instructor> sortAllInstructorsByNumberOfCourses() {
        return service.sortAllInstructorsByNumberOfTeachingCourses();
    }

    /**
     * Retrieves all courses that end before a specified date.
     *
     * @param date The cutoff date in the format "yyyy-MM-dd".
     * @return A list of courses that end before the specified date.
     */
    public List<Course> getAllCoursesThatEndBeforeADate(String date) {
        return service.getAllCoursesThatEndBeforeADate(date);
    }

    /**
     * Retrieves all instructors sorted by their total number of enrolled students across all courses.
     *
     * @return A list of instructors sorted by total enrollment.
     */
    public List<Instructor> getInstructorsByTotalEnrollment() {
        return service.getInstructorsSortedByEnrollment();
    }


}
