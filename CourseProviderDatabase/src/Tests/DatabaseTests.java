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
    private DataBaseRepository<Student> studentDataBaseRepository= new DataBaseRepository("student",Student.class,utils.getUsersParameters());
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
    void testCreate() {
        // Create a new student
        Student student = new Student(1, "john_doe", "password123", "john@example.com", "student");
        studentDataBaseRepository.create(student);

        // Verify student was created
        Student fetchedStudent = studentDataBaseRepository.get(1);
        assertNotNull(fetchedStudent);
        assertEquals("john_doe", fetchedStudent.getUsername());
        assertEquals("john@example.com", fetchedStudent.getEmail());
    }
    

}
