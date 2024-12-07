package Tests;

import Models.*;
import Models.Module;
import Repository.DataBaseRepository;
import Utils.Utils;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class DatabaseTests {
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

    @Test
    @Order(1)
    void testCreateStudent() {
        // Create a new student
        Student student = new Student(4, "john_doe", "password123", "john@example.com", "student");
        studentDataBaseRepository.create(student);

        // Verify student was created
        Student fetchedStudent = studentDataBaseRepository.get(1);
        assertNotNull(fetchedStudent);
        assertEquals("john_doe", fetchedStudent.getUsername());
        assertEquals("john@example.com", fetchedStudent.getEmail());
    }

    @Test
    @Order(2)
    void testCreateInstructor() {
        // Create a new instructor
        Instructor instructor = new Instructor(4, "prof_smith", "password456", "prof_smith@example.com", "instructor");
        instructorDataBaseRepository.create(instructor);

        // Verify instructor was created
        Instructor fetchedInstructor = instructorDataBaseRepository.get(4);
        assertNotNull(fetchedInstructor);
        assertEquals("prof_smith", fetchedInstructor.getUsername());
        assertEquals("prof_smith@example.com", fetchedInstructor.getEmail());
    }

    @Test
    @Order(3)
    void testCreateAdmin() {
        // Create a new admin
        Admin admin = new Admin(3, "admin_user", "adminpass", "admin_user@example.com", "admin");
        adminDataBaseRepository.create(admin);

        // Verify admin was created
        Admin fetchedAdmin = adminDataBaseRepository.get(3);
        assertNotNull(fetchedAdmin);
        assertEquals("admin_user", fetchedAdmin.getUsername());
        assertEquals("admin_user@example.com", fetchedAdmin.getEmail());
    }

    @Test
    @Order(4)
    void testCreateCourse() {
        // Create a new course
        Course course = new Course(101, "Java Programming", "Introduction to Java", 30, "2024-01-15", "2024-05-15", 2);
        courseDataBaseRepository.create(course);

        // Verify course was created
        Course fetchedCourse = courseDataBaseRepository.get(101);
        assertNotNull(fetchedCourse);
        assertEquals("Java Programming", fetchedCourse.getCourseTitle());
        assertEquals("Introduction to Java", fetchedCourse.getDescription());
        assertEquals(30, fetchedCourse.getAvailableSpots());
    }

    @Test
    @Order(5)
    void testCreateModule() {
        // Create a new module
        Module module = new Module(101, "Introduction to Programming", "Basic concepts of programming using Java");
        moduleDataBaseRepository.create(module);

        // Verify module was created
        Module fetchedModule = moduleDataBaseRepository.get(101);
        assertNotNull(fetchedModule);
        assertEquals("Introduction to Programming", fetchedModule.getModuleTitle());
        assertEquals("Basic concepts of programming using Java", fetchedModule.getModuleContent());
    }

    @Test
    @Order(6)
    void testCreateQuiz() {
        // Create a new quiz
        Quiz quiz = new Quiz(201, "Quiz 1", "What is Java?", 21);
        quizDataBaseRepository.create(quiz);

        // Verify quiz was created
        Quiz fetchedQuiz = quizDataBaseRepository.get(201);
        assertNotNull(fetchedQuiz);
        assertEquals("Quiz 1", fetchedQuiz.getTitle());
        assertEquals("What is Java?", fetchedQuiz.getContents());
    }

    @Test
    @Order(7)
    void testCreateAssignment() {
        // Create a new assignment
        Assignment assignment = new Assignment(301, "Java Assignment", "2024-02-01", 100);
        assignmentDataBaseRepository.create(assignment);

        // Verify assignment was created
        Assignment fetchedAssignment = assignmentDataBaseRepository.get(301);
        assertNotNull(fetchedAssignment);
        assertEquals("Java Assignment", fetchedAssignment.getDescription());
        assertEquals("2024-02-01", fetchedAssignment.getDueDate());
        assertEquals(100, fetchedAssignment.getScore());
    }

    @Test
    @Order(8)
    void testCreateMessage() {
        // Create a new message
        Message message = new Message(401, "Hello, welcome to the course!", 1, 1);
        messageDataBaseRepository.create(message);

        // Verify message was created
        Message fetchedMessage = messageDataBaseRepository.get(401);
        assertNotNull(fetchedMessage);
        assertEquals("Hello, welcome to the course!", fetchedMessage.getMessage());
    }

    @Test
    @Order(9)
    void testCreateForum() {
        // Create a new forum
        Forum forum = new Forum(501, "Programming Discussions");
        forumDataBaseRepository.create(forum);

        // Verify forum was created
        Forum fetchedForum = forumDataBaseRepository.get(501);
        assertNotNull(fetchedForum);
        assertEquals("Programming Discussions", fetchedForum.getTopic());
    }
    

}
