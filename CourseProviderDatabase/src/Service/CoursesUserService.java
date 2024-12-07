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


}
