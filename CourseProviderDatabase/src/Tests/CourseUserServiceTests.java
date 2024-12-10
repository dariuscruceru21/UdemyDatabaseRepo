package Tests;

import Exceptions.EntityNotFoundException;
import Models.*;
import Models.Module;
import Repository.DataBaseRepository;
import Service.CoursesUserService;
import Utils.Utils;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CourseUserServiceTests {
    Utils utils = new Utils();
    private DataBaseRepository<Student> studentDataBaseRepository= new DataBaseRepository<>("student",Student.class,utils.getUsersParameters());
    private DataBaseRepository<Admin> adminDataBaseRepository = new DataBaseRepository<>("admin",Admin.class,utils.getUsersParameters());
    private DataBaseRepository<Instructor> instructorDataBaseRepository = new DataBaseRepository<>("instructor",Instructor.class,utils.getUsersParameters());
    private DataBaseRepository<Course> courseDataBaseRepository = new DataBaseRepository<>("course",Course.class,utils.getCourseParameters());
    private DataBaseRepository<Module> moduleDataBaseRepository = new DataBaseRepository<>("module",Module.class,utils.getModuleParameters());
    private DataBaseRepository<Assignment> assignmentDataBaseRepository = new DataBaseRepository<>("assignment",Assignment.class,utils.getAssignmentParameteres());
    private DataBaseRepository<Quiz> quizDataBaseRepository = new DataBaseRepository<>("quiz",Quiz.class,utils.getQuizParameters());
    private DataBaseRepository<Forum> forumDataBaseRepository = new DataBaseRepository<>("forum", Forum.class,utils.getForumParameters());
    private DataBaseRepository<Message> messageDataBaseRepository = new DataBaseRepository<>("message",Message.class,utils.getMessageParamteres());
    private DataBaseRepository<Enrolled> studentCourseDataBaseRepository = new DataBaseRepository<>("studentcourse",Enrolled.class,utils.getEnrolledParameters());
    private DataBaseRepository<QuizAssignment> quizAssignmentDataBaseRepository = new DataBaseRepository<>("assignmentquiz",QuizAssignment.class,utils.getQuizAssignmentParameteres());
    private DataBaseRepository<AssignmentModule> assignmentModuleDataBaseRepository = new DataBaseRepository<>("moduleassignment", AssignmentModule.class, utils.getModuleAssignmentParameteres());
    private DataBaseRepository<ModuleCourse> moduleCourseDataBaseRepository = new DataBaseRepository<ModuleCourse>("coursemodule", ModuleCourse.class, utils.getCourseModuleParameters());
    private DataBaseRepository<MessageForum> messageForumDataBaseRepository = new DataBaseRepository<MessageForum>("messageforum", MessageForum.class, utils.getMessageForumParameters());
    private CoursesUserService coursesUserService = new CoursesUserService(courseDataBaseRepository,studentDataBaseRepository,instructorDataBaseRepository,adminDataBaseRepository,studentCourseDataBaseRepository);



    @Test
    @Order(1)
    void testGetEnrolledStudent(){
        List<Student> enrolledStudents = null;
        try {
            enrolledStudents = coursesUserService.getEnrolledStudents(2);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(enrolledStudents);
    }

    @Test
    @Order(2)
    void testGetAssignedInstructor(){
        Instructor instructor = null;
        try {
            instructor = coursesUserService.getAssignedInstructor(2);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(instructor);
        assertEquals(instructor.getId(),courseDataBaseRepository.get(2).getInstructorId());
    }

    @Test
    @Order(3)
    void testEnrollStudentUnenrollStudent()throws EntityNotFoundException{
        coursesUserService.enroll(1,1);
        assertNotNull(coursesUserService.getEnrolledStudents(1));
        coursesUserService.unenroll(1,1);
        List<Student> enrolledStudents = new ArrayList<>();
        assertEquals(coursesUserService.getEnrolledStudents(1),enrolledStudents);
    }

    @Test
    @Order(4)
    void testAssignIntructorUnassign()throws EntityNotFoundException{
        Instructor instructor = new Instructor(101,"john","johm@","john@gmail.com","instructor");
        Course course = new Course(101,"MAP","greu",11,"2024-07-09","2024-08-12",4);
        coursesUserService.addInstructor(instructor);
        coursesUserService.addCourse(course);
        coursesUserService.assignInstructor(101,101);
        assertEquals(courseDataBaseRepository.get(101).getInstructorId(),101);
        coursesUserService.unAssignInstructor(101,101);
        assertEquals(courseDataBaseRepository.get(101).getInstructorId(),null);
        coursesUserService.removeInstructor(101);
        coursesUserService.removeCourse(101);
    }

    @Test
    @Order(5)
    void testAddRemoveCourse()throws EntityNotFoundException{
        Course course = new Course(101,"MAP","greu",11,"2024-07-09","2024-08-12",4);
        coursesUserService.addCourse(course);
        assertNotNull(courseDataBaseRepository.get(101));
        coursesUserService.removeCourse(101);
        assertNull(courseDataBaseRepository.get(101));
    }

    @Test
    @Order(6)
    void testAddRemoveStudent()throws EntityNotFoundException{
        Student student = new Student(3,"stefan","password1234","stefan@gmail.com","student");
        coursesUserService.addStudent(student);
        assertNotNull(studentDataBaseRepository.get(3));
        coursesUserService.removeStudent(3);
        assertNull(studentDataBaseRepository.get(3));
    }

    @Test
    @Order(7)
    void testAddRemoveInstructor()throws EntityNotFoundException{
        Instructor instructor = new Instructor(101,"robert","password123","robert@gmail.com","instructor");
        coursesUserService.addInstructor(instructor);
        assertNotNull(instructorDataBaseRepository.get(101));
        coursesUserService.removeInstructor(101);
        assertNull(instructorDataBaseRepository.get(101));
    }

    @Test
    @Order(8)
    void testGetAllCourses(){
        List<Course> courses = coursesUserService.getAllCourses();
        assertNotNull(courses);
    }

    @Test
    @Order(9)
    void testGetAllStudents(){
        List<Student> students = coursesUserService.getAllStudents();
        assertNotNull(students);
    }

    @Test
    @Order(10)
    void testGetAllInstructors(){
        List<Instructor> instructors = coursesUserService.getAllInstructors();
        assertNotNull(instructors);
    }

    @Test
    @Order(11)
    void testRemoveAssignedInstructor() throws EntityNotFoundException {
        Instructor instructor = new Instructor(101,"robert","password123","robert@gmail.com","instructor");
        Course course = new Course(101,"MAP","greu",11,"2024-07-09","2024-08-12",4);

       coursesUserService.addInstructor(instructor);
       coursesUserService.addCourse(course);
       coursesUserService.assignInstructor(101,101);

        try {
            coursesUserService.removeAssignedInstructor(101, 101);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println(course.getInstructorId());
        assertNull(courseDataBaseRepository.get(101).getInstructorId());
        assertFalse(instructor.getCourses().contains(101));
        coursesUserService.removeInstructor(101);
        coursesUserService.removeCourse(101);
    }

    @Test
    @Order(12)
    void testGetCoursesAStudentEnrolledIn() throws EntityNotFoundException {

       Student mockStudent = new Student(101,"robert","password123","robert@gmail.com","student");
       Course mockCourse = new Course(101,"MAP","greu",11,"2024-07-09","2024-08-12",4);
       coursesUserService.addStudent(mockStudent);
       coursesUserService.addCourse(mockCourse);
       coursesUserService.enroll(mockCourse.getId(), mockCourse.getId());
       List<Course> courses = coursesUserService.getCoursesAStudentEnrolledIn(mockStudent.getId());
       assertEquals(mockCourse.getId(), courses.get(0).getId(), "Enrolled course ID should match.");
       coursesUserService.unenroll(mockStudent.getId(), mockCourse.getId());
       courses = coursesUserService.getCoursesAStudentEnrolledIn(mockStudent.getId());
       assertEquals(0, courses.size(), "Student should be unenrolled from all courses.");
       coursesUserService.removeStudent(mockStudent.getId());
       coursesUserService.removeCourse(mockCourse.getId());

    }

    @Test
    @Order(13)
    void testGetCoursesInstructorTeaches() throws EntityNotFoundException {
        // Mock data for Instructor and Course
        Instructor mockInstructor = new Instructor(101, "Dr. John", "password123", "john@example.com", "instructor");
        Course mockCourse = new Course(101, "MAP", "Course description", 11, "2024-07-09", "2024-08-12", 4);

        // Add the instructor and course to the system
        coursesUserService.addInstructor(mockInstructor);
        coursesUserService.addCourse(mockCourse);

        // Assign the instructor to the course
        coursesUserService.assignInstructor(mockInstructor.getId(), mockCourse.getId());

        // Fetch the list of courses the instructor teaches
        List<Course> courses = coursesUserService.getCoursesAInstructorTeaches(mockInstructor.getId());

        // Assert the course is assigned to the instructor
        assertEquals(1, courses.size(), "Instructor should teach exactly one course.");
        assertEquals(mockCourse.getId(), courses.get(0).getId(), "Instructor should be teaching the correct course.");

        // Clean up: Remove instructor and course
        coursesUserService.removeInstructor(mockInstructor.getId());
        coursesUserService.removeCourse(mockCourse.getId());
    }


    @Test
    @Order(14)
    void testGetCourseInfo(){
        Course course = null;
        try {
            course = coursesUserService.getCourseInfo(1);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertEquals(course.getCourseTitle(),"Analiza");
    }

    @Test
    @Order(15)
    void testGetStudentInfo(){
        Student student = null;
        try {
            student = coursesUserService.getStudentInfo(1);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertEquals(student.getUsername(),"john_doe");
    }

    @Test
    @Order(16)
    void testGetInstructorInfo(){
        Instructor instructor = null;
        try {
            instructor = coursesUserService.getInstructorInfo(1);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertEquals(instructor.getUsername(),"Paul");
    }

    @Test
    @Order(17)
    void testUpdateCourse()throws EntityNotFoundException{
        Course course = new Course(102,"Java","Introduction to java",30,"2024-01-15","2024-05-15",1);
        coursesUserService.addCourse(course);
        assertEquals(courseDataBaseRepository.get(102).getCourseTitle(),course.getCourseTitle());
        Course course1 = new Course(102,"Java Training","Introduction to java",30,"2024-01-15","2024-05-15",1);
        coursesUserService.updateCourse(course1);
        assertEquals(courseDataBaseRepository.get(102).getCourseTitle(),course1.getCourseTitle());
        coursesUserService.removeCourse(102);
    }

    @Test
    @Order(18)
    void testUpdateStudent()throws EntityNotFoundException{
        Student student = new Student(3,"maia","password21","maia@gmail.com","student");
        coursesUserService.addStudent(student);
        assertEquals(studentDataBaseRepository.get(3).getPassword(),student.getPassword());
        Student student1 = new Student(3,"maia","maiak","maia@gmail.com","student");
        coursesUserService.updateStudent(student1);
        assertEquals(studentDataBaseRepository.get(3).getPassword(),student1.getPassword());
        coursesUserService.removeStudent(3);
    }

    @Test
    @Order(19)
    void testUpdateInstructor()throws EntityNotFoundException{
        Instructor instructor = new Instructor(101,"maia","password21","maia@gmail.com","instructor");
        coursesUserService.addInstructor(instructor);
        assertEquals(instructorDataBaseRepository.get(101).getPassword(),instructor.getPassword());
        Instructor instructor1 = new Instructor(101,"maia","maiak","maia@gmail.com","instructor");
        coursesUserService.updateInstructor(instructor1);
        assertEquals(instructorDataBaseRepository.get(101).getPassword(),instructor1.getPassword());
        coursesUserService.removeInstructor(101);
    }

    @Test
    @Order(20)
    void testGetAllUnderOcupiedCourses(){
        List<Course>courses = coursesUserService.getAllUnderOcupiedCourses();
        assertNotNull(courses);
    }



    @Test
    @Order(21)
    void testGetAllCOursesThatEndBeforeADate() throws EntityNotFoundException {

        Course mockCourse = new Course(101,"MAP","greu",11,"2024-07-09","2024-08-12",4);
        Course mockCourse2 = new Course(102,"LP","usor",10,"2024-01-09","2024-05-09",4);
        coursesUserService.addCourse(mockCourse);
        coursesUserService.addCourse(mockCourse2);
        List<Course>courses = coursesUserService.getAllCoursesThatEndBeforeADate("2024-06-12");
        coursesUserService.removeCourse(mockCourse.getId());
        coursesUserService.removeCourse(mockCourse2.getId());

        assertEquals(courses.size(),1);
    }






}
