import Controller.AssignmentController;
import Controller.CourseUserController;
import Exceptions.BusinessException;
import Exceptions.EntityNotFoundException;
import Exceptions.ValidationException;
import Models.*;
import Models.Module;
import Repository.DataBaseRepository;
import Service.AssignmentService;
import Service.AuthenticationService;
import Service.CoursesUserService;
import Ui.Ui;
import Utils.Utils;


import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

//        // Manually add the ID as part of the object creation
         Instructor student = new Instructor(9, "Paul", "paul123", "paul@gmail.com", "instructor");
         Course course = new Course(2,"ALgebra","usor",10,"2024-07-12","2024-10-11",2);
         Module module = new Module(1,"Linked Lists","hard content");
         Assignment assignment = new Assignment(1,"optional assignment", "2024-12-07",100);
//
//        // Pass column names including 'userid'
         List<String> columnNames = Arrays.asList("courseID", "courseTitle", "description", "availableSpots", "startDate", "endDate","instructorId");
         List<String> columnNames2 = Arrays.asList("userID","userName","password","email","type");
         List<String> columnName3 = Arrays.asList("moduleID", "moduleTitle", "moduleContent");

         List<String> columnName4 = Arrays.asList("assignmentID", "description","dueDate","score");
         DataBaseRepository<Course> dataBaseRepository = new DataBaseRepository<>("course", Course.class, columnNames);
         DataBaseRepository<Module> dataBaseRepository1 = new DataBaseRepository<>("module",Module.class,columnName3);
         DataBaseRepository<Assignment> dataBaseRepository2 = new DataBaseRepository<>("assignment",Assignment.class,columnName4);


        //System.out.println("courseId".toLowerCase());

        Utils parameters = new Utils();
            //trebuie sa schimbam clasa user si sa facem separat tabele cu variabile pentru student admin instructor
        Instructor instructor = new Instructor(2, "Marius", "paul123", "paul@gmail.com", "instructor");
        List<String> columnNames5 = Arrays.asList("userID", "userName", "password", "email", "type");
        DataBaseRepository<Instructor> instructorRepository = new DataBaseRepository<>("instructor", Instructor.class, columnNames5);
        //instructorRepository.create(instructor);
        Utils utils = new Utils();
//
//        // Create the student in the database
//        dataBaseRepository.create(student);
//        System.out.println("Print");
//        System.out.println(System.getenv("DB_PASSWORD"));

        //System.out.println(instructorRepository.getAll());

        Message message = new Message(1,"Salut sunt darius",1,1);
        DataBaseRepository<Message> messageDataBaseRepository = new DataBaseRepository<>("message",Message.class,utils.getMessageParamteres());
        //DataBaseRepository
//        messageDataBaseRepository.create(message);
        DataBaseRepository<Course>courseDataBaseRepository = new DataBaseRepository<>("course",Course.class,utils.getCourseParameters());
        DataBaseRepository<Student>studentDataBaseRepository = new DataBaseRepository<>("student",Student.class,utils.getUsersParameters());
        DataBaseRepository<Instructor>instructorDataBaseRepository = new DataBaseRepository<>("instructor",Instructor.class,utils.getUsersParameters());
        DataBaseRepository<Admin>adminDataBaseRepository = new DataBaseRepository<>("admin", Admin.class,utils.getUsersParameters());
        DataBaseRepository<Enrolled>enrolledDataBaseRepository = new DataBaseRepository<>("studentcourse",Enrolled.class,utils.getEnrolledParameters());
        CoursesUserService coursesUserService = new CoursesUserService(courseDataBaseRepository,studentDataBaseRepository,instructorDataBaseRepository,adminDataBaseRepository,enrolledDataBaseRepository);
        DataBaseRepository<Enrolled> studentCourseDataBaseRepository = new DataBaseRepository<>("studentcourse",Enrolled.class,utils.getEnrolledParameters());
        DataBaseRepository<Quiz> quizDataBaseRepository = new DataBaseRepository<>("quiz",Quiz.class,utils.getQuizParameters());
        DataBaseRepository<Assignment> assignmentDataBaseRepository = new DataBaseRepository<>("assignment",Assignment.class,utils.getAssignmentParameteres());
        DataBaseRepository<Module> moduleDataBaseRepository = new DataBaseRepository<>("module",Module.class,utils.getModuleParameters());
        DataBaseRepository<ModuleCourse>moduleCourseDataBaseRepository = new DataBaseRepository<>("coursemodule",ModuleCourse.class,utils.getCourseModuleParameters());
        DataBaseRepository<AssignmentModule> assignmentModuleDataBaseRepository = new DataBaseRepository<>("moduleassignment",AssignmentModule.class,utils.getModuleAssignmentParameteres());
        DataBaseRepository<QuizAssignment>quizAssignmentDataBaseRepository = new DataBaseRepository<>("assignmentquiz",QuizAssignment.class,utils.getQuizAssignmentParameteres());
        //AssignmentService assignmentService = new AssignmentService(quizDataBaseRepository,assignmentDataBaseRepository,moduleDataBaseRepository,courseDataBaseRepository,moduleCourseDataBaseRepository,assignmentModuleDataBaseRepository,quizAssignmentDataBaseRepository);

        //studentCourseDataBaseRepository.delete(2);
        //studentDataBaseRepository.delete(2);


        //coursesUserService.removeStudent(4);

        //coursesUserService.removeInstructor(2);

        //coursesUserService.removeCourse(1);

        //coursesUserService.assignInstructor(3,101);


        //coursesUserService.removeAssignedInstructor(3,101);

        //coursesUserService.assignInstructor(3,101);

//        System.out.println(coursesUserService.getInstructorsSortedByEnrollment());
//        assignmentService.addModuleToCourse(101,101);
//        System.out.println(courseDataBaseRepository.get(101).getModules());

//        QuizAssignment quizAssignment = new QuizAssignment(201,301);
//        DataBaseRepository<QuizAssignment> quizAssignmentDataBaseRepository = new DataBaseRepository<>("assignmentquiz",QuizAssignment.class,utils.getQuizAssignmentParameteres());
//
//
//        //de ce nu salveaza in liste
//
//        //quizAssignmentDataBaseRepository.create(quizAssignment);
//        //assignmentService.takeAssignmentQuiz(301);
//        System.out.println(course.getEnrolledStudents());
          //coursesUserService.enroll(2,2);
        //coursesUserService.unenroll(2,2);
        //System.out.println(courseDataBaseRepository.get(2).getEnrolledStudents());
        //System.out.println(coursesUserService.getEnrolledStudents(2));

        AssignmentService assignmentService = new AssignmentService(quizDataBaseRepository,assignmentDataBaseRepository,moduleDataBaseRepository,courseDataBaseRepository,moduleCourseDataBaseRepository,assignmentModuleDataBaseRepository,quizAssignmentDataBaseRepository);

        //System.out.println(coursesUserService.getEnrolledStudents(2));

        //assignmentService.addModuleToCourse(1,101);

        //aici nu stiu de ce dar sunt inversate !!!!!!!!!
        //assignmentService.addModuleToCourse(1,101);
        //assignmentService.removeModuleFromCourse(1,101);
        //coursesUserService.removeStudent(3);
        //coursesUserService.removeAssignedInstructor(4,3);
        //coursesUserService.removeCourse(3);
        //coursesUserService.removeInstructor(4);
        //coursesUserService.removeStudent(3);
        //coursesUserService.assignInstructor(4,101);
        //coursesUserService.assignInstructor(1,101);
        //coursesUserService.assignInstructor(1,1);
        //coursesUserService.removeInstructor(2);
        //System.out.println(coursesUserService.sortAllInstructorsByNumberOfTeachingCourses());
        //AuthenticationService authenticationService = new AuthenticationService();
        //User user = authenticationService.authenticate("admin_user","adminpass");
        //System.out.println(user);

//        Ui ui = new Ui();
//        ui.start();

        CourseUserController courseUserController = new CourseUserController(coursesUserService);
        AssignmentController assignmentController = new AssignmentController(assignmentService);

        Ui ui = new Ui(assignmentController,courseUserController);
        try {
            ui.start();
        } catch (EntityNotFoundException | BusinessException | ValidationException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(coursesUserService.getCoursesAStudentEnrolledIn(2));
        //coursesUserService.unenroll(2,101);


        //assignmentService.addModuleToCourse(101,3);
//        try {
//            assignmentService.removeQuizFromAssignment(1,201);
//        } catch (EntityNotFoundException e) {
//            throw new RuntimeException(e);
//        }
        //assignmentDataBaseRepository.delete(201);
        //assignmentModuleDataBaseRepository.delete(202);
        //moduleDataBaseRepository.delete(202);

        //moduleCourseDataBaseRepository.delete(201);
        //moduleDataBaseRepository.delete(206);
        //assignmentModuleDataBaseRepository.delete(207);
        //moduleDataBaseRepository.delete(207);
        //assignmentDataBaseRepository.delete(305);
        //assignmentDataBaseRepository.delete(306);





    }
}