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
        coursesUserService.enroll(1,101);
        assertNotNull(coursesUserService.getEnrolledStudents(101));
        coursesUserService.unenroll(1,101);
        List<Student> enrolledStudents = new ArrayList<>();
        assertEquals(coursesUserService.getEnrolledStudents(101),enrolledStudents);
    }

    @Test
    @Order(4)
    void testAssignIntructorUnassign()throws EntityNotFoundException{
        Instructor instructor = new Instructor(2,"john","johm@","john@gmail.com","instructor");
        Course course = new Course(3,"MAP","greu",11,"2024-07-09","2024-08-12",4);
        coursesUserService.addInstructor(instructor);
        coursesUserService.addCourse(course);
        coursesUserService.assignInstructor(2,3);
        assertEquals(courseDataBaseRepository.get(3).getInstructorId(),2);
        coursesUserService.unAssignInstructor(2,3);
        assertEquals(courseDataBaseRepository.get(3).getInstructorId(),null);
        coursesUserService.removeInstructor(2);
        coursesUserService.removeCourse(3);
    }

    @Test
    @Order(5)
    void testAddRemoveCourse()throws EntityNotFoundException{
        Course course = new Course(3,"MAP","greu",11,"2024-07-09","2024-08-12",4);
        coursesUserService.addCourse(course);
        assertNotNull(courseDataBaseRepository.get(3));
        coursesUserService.removeCourse(3);
        assertNull(courseDataBaseRepository.get(3));
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
        Instructor instructor = new Instructor(4,"robert","password123","robert@gmail.com","instructor");
        coursesUserService.addInstructor(instructor);
        assertNotNull(instructorDataBaseRepository.get(4));
        coursesUserService.removeInstructor(4);
        assertNull(instructorDataBaseRepository.get(4));
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
    void testRemoveAssignedInstructor() {
        Instructor instructor = new Instructor(4,"robert","password123","robert@gmail.com","instructor");
        Course course = new Course(3,"MAP","greu",11,"2024-07-09","2024-08-12",4);

        instructorDataBaseRepository.create(instructor);
        courseDataBaseRepository.create(course);

        try {
            coursesUserService.removeAssignedInstructor(4, 3);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println(course.getInstructorId());
        assertNull(courseDataBaseRepository.get(3).getInstructorId());
        assertFalse(instructor.getCourses().contains(3));
    }

    @Test
    @Order(12)
    void testGetCoursesAStudentEnrolledIn(){
        List<Course>courses = null;
        try {
            courses = coursesUserService.getCoursesAStudentEnrolledIn(2);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertEquals(courses.size(),1);
    }

    @Test
    @Order(13)
    void testGetCoursesInstructorTeaches(){
        List<Course>courses = coursesUserService.getCoursesAInstructorTeaches(1);
        assertEquals(courses.size(),1);
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
        Instructor instructor = new Instructor(2,"maia","password21","maia@gmail.com","instructor");
        coursesUserService.addInstructor(instructor);
        assertEquals(instructorDataBaseRepository.get(2).getPassword(),instructor.getPassword());
        Instructor instructor1 = new Instructor(2,"maia","maiak","maia@gmail.com","instructor");
        coursesUserService.updateInstructor(instructor1);
        assertEquals(instructorDataBaseRepository.get(2).getPassword(),instructor1.getPassword());
        coursesUserService.removeInstructor(2);
    }

    @Test
    @Order(20)
    void testGetAllUnderOcupiedCourses(){
        List<Course>courses = coursesUserService.getAllUnderOcupiedCourses();
        assertEquals(courses.size(),3);
    }

    @Test
    @Order(21)
    void testSortAllInstructorsByNumberOfTeachingCourses(){
        List<Instructor>instructors = coursesUserService.sortAllInstructorsByNumberOfTeachingCourses();
        assertEquals(instructorDataBaseRepository.get(1).getUsername(),instructors.get(0).getUsername());
        assertEquals(instructorDataBaseRepository.get(3).getUsername(),instructors.get(1).getUsername());

    }

    @Test
    @Order(22)
    void testGetAllCOursesThatEndBeforeADate(){
        List<Course>courses = coursesUserService.getAllCoursesThatEndBeforeADate("2024-06-12");
        assertEquals(courses.size(),1);
    }

    @Test
    @Order(23)
    void testgetInstructorsSortedByEnrollment(){
        List<Instructor>instructors = coursesUserService.getInstructorsSortedByEnrollment();
        assertEquals(instructorDataBaseRepository.get(1).getUsername(),instructors.get(0).getUsername());
        assertEquals(instructorDataBaseRepository.get(3).getUsername(),instructors.get(1).getUsername());
    }




}
