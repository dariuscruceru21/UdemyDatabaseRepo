import Controller.AssignmentController;
import Controller.CourseUserController;
import Exceptions.BusinessException;
import Exceptions.EntityNotFoundException;
import Exceptions.ValidationException;
import Models.*;
import Models.Module;
import Repository.DataBaseRepository;


import Repository.FileRepository;
import Repository.InMemoryRepo;
import Service.AssignmentService;
import Service.AuthenticationService;
import Service.CoursesUserService;
import Ui.Ui;
import Utils.Utils;

import java.io.File;
import java.util.List;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


        Utils utils = new Utils();



        DataBaseRepository<Message> messageDataBaseRepository = new DataBaseRepository<>("message",Message.class,utils.getMessageParamteres());
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



        AssignmentService assignmentService = new AssignmentService(quizDataBaseRepository,assignmentDataBaseRepository,moduleDataBaseRepository,courseDataBaseRepository,moduleCourseDataBaseRepository,assignmentModuleDataBaseRepository,quizAssignmentDataBaseRepository);





//          Instructor instructor = new Instructor(1,"darius","maiapass","maia@gmail.com","instructor");
//          Student student = new Student(1,"ma","mapassword","ma@h","student");
//          Course course = new Course(1,"DariusCruceru","greu",100,"2024-12-21","2025-07-21",1);
////        InMemoryRepo<Course> courseInMemoryRepo = new InMemoryRepo<>();
////        courseInMemoryRepo.create(course);
////        System.out.println(courseInMemoryRepo.get(1));
//
//        //System.out.println(coursesUserService.getInstructorsSortedByEnrollment());
//
       FileRepository<Instructor> instructorFileRepository = new FileRepository<>("instructor.csv");
//        FileRepository<Student> studentFileRepository = new FileRepository<>("student.csv");
//        List<Instructor>instructors = instructorFileRepository.getAll();


            FileRepository<Admin>adminFile = new FileRepository<>("admin.csv");
            Admin admin = new Admin(5, "adminUser", "adminPass", "admin@example.com", "admin");
            Course course = new Course(101, "Java Programming", "Learn Java basics", 30, "2025-01-15", "2025-05-15", 4);
            FileRepository<Course>courseFileRepository = new FileRepository<>("course.csv");
            //courseFileRepository.create(course);
        System.out.println(courseFileRepository.getAll());
//        Ui ui = new Ui();
//        try {
//            ui.start();
//        } catch (EntityNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (BusinessException e) {
//            throw new RuntimeException(e);
//        } catch (ValidationException e) {
//            throw new RuntimeException(e);
//        }


    }
}