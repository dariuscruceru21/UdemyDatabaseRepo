import Controller.AssignmentController;
import Controller.CourseUserController;
import Exceptions.BusinessException;
import Exceptions.EntityNotFoundException;
import Exceptions.ValidationException;
import Models.*;
import Models.Module;
import Repository.DataBaseRepository;

import Repository.FileRepoistory;
import Repository.InMemoryRepo;
import Service.AssignmentService;
import Service.AuthenticationService;
import Service.CoursesUserService;
import Ui.Ui;
import Utils.Utils;




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



        CourseUserController courseUserController = new CourseUserController(coursesUserService);
        AssignmentController assignmentController = new AssignmentController(assignmentService);


          Course course = new Course(1000,"DariusCruceru","greu",100,"2024-12-21","2025-07-21",1);
//        InMemoryRepo<Course> courseInMemoryRepo = new InMemoryRepo<>();
//        courseInMemoryRepo.create(course);
//        System.out.println(courseInMemoryRepo.get(1));

        //System.out.println(coursesUserService.getInstructorsSortedByEnrollment());

       courseDataBaseRepository.create(course);



    }
}