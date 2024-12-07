package Service;

import Models.*;
import Repository.IRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CoursesUserService {
    private final IRepository<Course> courseIRepository;
    private final IRepository<Student> studentIRepository;
    private final IRepository<Instructor> instructorIRepository;
    private final IRepository<Admin> adminIRepository;
    private final IRepository<Enrolled> enrolledIRepository;


    /**
     * Constructs a new {@code CoursesUserService} with the specified repositories.
     * This service manages the interactions between courses, students, instructors, and admins.
     * The constructor allows dependency injection for the repositories that handle course, student, instructor, and admin data.
     *
     * @param courseIRepository     The repository interface for course data, used to fetch and manage course-related information.
     * @param studentIRepository    The repository interface for student data, used to fetch and manage student-related information.
     * @param instructorIRepository The repository interface for instructor data, used to fetch and manage instructor-related information.
     * @param adminIRepository      The repository interface for admin data, used to fetch and manage admin-related information.
     */
    public CoursesUserService(IRepository<Course> courseIRepository, IRepository<Student> studentIRepository, IRepository<Instructor> instructorIRepository, IRepository<Admin> adminIRepository, IRepository<Enrolled> enrolledIRepository) {
        this.courseIRepository = courseIRepository;
        this.studentIRepository = studentIRepository;
        this.instructorIRepository = instructorIRepository;
        this.adminIRepository = adminIRepository;
        this.enrolledIRepository = enrolledIRepository;
    }

    /**
     * Retrieves a list of all students enrolled in a specific course.
     *
     * @param courseId The ID of the course.
     * @return A list of students enrolled in the specified course.
     */
    public List<Student> getEnrolledStudents(Integer courseId) {
        List<Enrolled> enrollments = enrolledIRepository.getAll();

        //extract student ids
        List<Integer> enrolledStudentIds = enrollments.stream()
                .map(Enrolled::getId)
                .distinct()
                .collect(Collectors.toList());

        //Fetch student objects for each student
        List<Student> enrolledStudents = new ArrayList<>();
        for(Integer studentId : enrolledStudentIds){
            Student student = studentIRepository.get(studentId);
            if (student != null)
                enrolledStudents.add(student);
        }

        return enrolledStudents;

    }

    /**
     * Retrieves the instructor that teaches a specific course.
     *
     * @param courseId The ID of the course.
     * @return A list of students enrolled in the specified course.
     */
    public Instructor getAssignedInstructor(Integer courseId){
        Course course = courseIRepository.get(courseId);
        Instructor instructor;
        if(course == null)
            throw new Error("Course with id : " + course.getId() + " does not exist");
        instructor = instructorIRepository.get(course.getInstructorId());
        return instructor;
    }

    /**
     * Enrolls a student in a course if there are available spots.
     *
     * @param studId   The ID of the student to enroll.
     * @param courseId The ID of the course.
     */
    public void enroll(Integer studId, Integer courseId){
        Student student = studentIRepository.get(studId);
        if(student == null)
            throw new IllegalArgumentException("Student with id " + studId + " does not exist");

        Course course = courseIRepository.get(courseId);
        if(course == null)
            throw new IllegalArgumentException("Course with id " + courseId + " does not exist");

        if (course.getAvailableSpots() <= getEnrolledStudents(courseId).size())
            throw new IllegalArgumentException("Course is already at full capacity");

        //check if the student is already enrolled
        List<Enrolled> enrollments = enrolledIRepository.getAll();
        boolean alreadyEnrolled = enrollments.stream().anyMatch(e -> e.getId().equals(studId) && e.getCourseId().equals(courseId));

        if (alreadyEnrolled)
            throw new IllegalArgumentException("Student is already enrolled");

        //create new enrollment
        Enrolled newEnrollment = new Enrolled(studId,courseId);
        enrolledIRepository.create(newEnrollment);

        //update course availability
        course.setAvailableSpots(course.getAvailableSpots() - 1);
        courseIRepository.update(course);

        //update the Students list of courses
        List<Integer> studentCourses = student.getCourses();
        studentCourses.add(courseId);
        student.setCourses(studentCourses);

        //update the Courses list of students
        List<Integer> coursesStudents = course.getEnrolledStudents();
        coursesStudents.add(studId);
        course.setEnrolledStudents(coursesStudents);

    }

    /**
     * Assigns a instructor to teach a course, the current instructor of the course will be overwritten.
     *
     * @param instructorId The ID of the student to enroll.
     * @param courseId     The ID of the course.
     */
    public void assignInstructor(Integer instructorId, Integer courseId) {
        // Fetch the instructor from the repository
        Instructor instructor = instructorIRepository.get(instructorId);
        if (instructor == null)
            throw new IllegalArgumentException("Instructor with id " + instructorId + " does not exist");

        // Fetch the course from the repository
        Course course = courseIRepository.get(courseId);
        if (course == null)
            throw new IllegalArgumentException("Course with id " + courseId + " does not exist");

        // Check if the instructor is already assigned to the course
        if (course.getInstructorId() != null && course.getInstructorId().equals(instructorId)) {
            throw new IllegalArgumentException("Instructor with id " + instructorId + " is already teaching this course");
        }

        //Add the course to the list of courses an instructor teaches
        List<Integer> assignedCourses = instructor.getCourses();
        assignedCourses.add(courseId);
        instructor.setCourses(assignedCourses);

        // Assign the instructor to the course (overwriting any existing assignment)
        course.setInstructorId(instructorId);

        // Update the instructor and course in the repository
        instructorIRepository.update(instructor);
        courseIRepository.update(course);

        System.out.println("Instructor with id " + instructorId + " has been assigned to course with id " + courseId);
    }

    /**
     * Adds a new course to the repository.
     *
     * @param course The course to add.
     */
    public void addCourse(Course course) {
        courseIRepository.create(course);
    }

    /**
     * Adds a new student to the repository.
     *
     * @param student The student to add.
     */
    public void addStudent(Student student) {
        studentIRepository.create(student);
    }

    /**
     * Adds a new instructor to the repository.
     *
     * @param instructor The instructor to add.
     */
    public void addInstructor(Instructor instructor) {
        instructorIRepository.create(instructor);
    }

    /**
     * Removes a course from the system.
     *
     * Before removing the course, the following steps are performed:
     * - Unassign the instructor from the course (if any).
     * - Unenroll all students from the course.
     *
     * @param courseId The ID of the course to be removed.
     * @throws IllegalArgumentException if the course with the given ID does not exist.
     */
    public void removeCourse(Integer courseId) {
        // Check if course exists
        Course course = courseIRepository.get(courseId);
        if (course == null) {
            throw new IllegalArgumentException("Course with id " + courseId + " does not exist");
        }

        // Before deleting, ensure that there are no students or instructors assigned to this course.
        // Example: You may choose to unenroll students or unassign the instructor.

        // Unassign the instructor from the course (if any)
        if (course.getInstructorId() != null) {
            Instructor instructor = instructorIRepository.get(course.getInstructorId());
            if (instructor != null) {
                // Unassign instructor from course
                List<Integer> assignedCourses = instructor.getCourses();
                assignedCourses.remove(courseId);
                instructor.setCourses(assignedCourses);
                instructorIRepository.update(instructor);
            }
        }

        // Unenroll students from the course
        List<Enrolled> enrollments = enrolledIRepository.getAll();
        for (Enrolled enrollment : enrollments) {
            if (enrollment.getCourseId().equals(courseId)) {
                // Unenroll the student
                Student student = studentIRepository.get(enrollment.getId());
                if (student != null) {
                    // Remove the course from the student's list of enrolled courses
                    List<Integer> studentCourses = student.getCourses();
                    studentCourses.remove(courseId);
                    student.setCourses(studentCourses);
                    studentIRepository.update(student);

                    // Remove the student from the course's enrolled students
                    List<Integer> courseStudents = course.getEnrolledStudents();
                    courseStudents.remove(enrollment.getId());
                    course.setEnrolledStudents(courseStudents);
                }

                // Delete the enrollment record
                enrolledIRepository.delete(enrollment.getId());
            }
        }

        // Now delete the course itself
        courseIRepository.delete(courseId);
        System.out.println("Course with id " + courseId + " has been successfully removed");
    }

    /**
     * Removes an instructor from the system.
     *
     * Before removing the instructor, the following steps are performed:
     * - Unassign the instructor from any courses they are teaching.
     * - Remove the instructor from any courses that they are teaching.
     *
     * @param instructorId The ID of the instructor to be removed.
     * @throws IllegalArgumentException if the instructor with the given ID does not exist.
     */
    public void removeInstructor(Integer instructorId) {
        // Retrieve the instructor to ensure they exist
        Instructor instructor = instructorIRepository.get(instructorId);
        if (instructor == null) {
            throw new IllegalArgumentException("Instructor with id " + instructorId + " does not exist");
        }

        // Retrieve the course the instructor is teaching
        Course course = courseIRepository.get(instructor.getId());
        if (course != null) {
            // Unassign the instructor from the course
            course.setInstructorId(null);
            courseIRepository.update(course);
        }

        // Now, remove the instructor from the system
        instructorIRepository.delete(instructorId);
        System.out.println("Instructor with id " + instructorId + " has been successfully removed");
    }

    /**
     * Removes a student from the system.
     *
     * Before removing the student, the following steps are performed:
     * - Unenroll the student from all courses they are enrolled in.
     * - Remove the student from any course's list of enrolled students.
     *
     * @param studentId The ID of the student to be removed.
     * @throws IllegalArgumentException if the student with the given ID does not exist.
     */
    public void removeStudent(Integer studentId) {
        // Retrieve the student to ensure they exist
        Student student = studentIRepository.get(studentId);
        if (student == null) {
            throw new IllegalArgumentException("Student with id " + studentId + " does not exist");
        }

        // Retrieve all enrollments for the student
        List<Enrolled> enrollments = enrolledIRepository.getAll();
        for (Enrolled enrollment : enrollments) {
            if (enrollment.getId().equals(studentId)) {
                // Retrieve the course the student is enrolled in
                Course course = courseIRepository.get(enrollment.getCourseId());

                if (course != null) {
                    // Remove the student from the course's list of enrolled students
                    List<Integer> courseStudents = course.getEnrolledStudents();
                    courseStudents.remove(studentId);
                    course.setEnrolledStudents(courseStudents);
                    courseIRepository.update(course);
                }

                // Remove the student from the student's list of enrolled courses
                List<Integer> studentCourses = student.getCourses();
                studentCourses.remove(enrollment.getCourseId());
                student.setCourses(studentCourses);
                studentIRepository.update(student);

                // Delete the enrollment record
                enrolledIRepository.delete(enrollment.getId());
            }
        }

        // Now, delete the student
        studentIRepository.delete(studentId);
        System.out.println("Student with id " + studentId + " has been successfully removed");
    }


}
