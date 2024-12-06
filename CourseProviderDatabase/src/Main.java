import Models.*;
import Models.Module;
import Repository.DataBaseRepository;


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
         Instructor instructor = new Instructor(9, "Paul", "paul123", "paul@gmail.com", "instructor");
         Course course = new Course(2,"Analiza","hard",10,"2024-07-12","2024-10-11",9);
        Quiz quiz = new Quiz(3, "logaritm", "fain", 1);
        Assignment assignment = new Assignment(3, "rezolva", "01-02-2020", 30);
        Module module = new Module(3, "modul", "continut");

        // Pass column names including 'userid'
         List<String> columnNames = Arrays.asList("courseID", "courseTitle", "description", "availableSpots", "startDate", "endDate","instructorId");
         List<String> columnNames2 = Arrays.asList("userId","userName","password","email","type");
         List<String> columnNames3 = Arrays.asList("quizId","title","contents","correctAnswer");
        List<String> columnNames4 = Arrays.asList("assignmentID","description","dueDate","correctAnswer", "module");
        List<String> columnNames5 = Arrays.asList("moduleID","moduleTitle","moduleContent");


        DataBaseRepository<Instructor> dataBaseRepository = new DataBaseRepository<>("\"User\"", Instructor.class, columnNames2);
        DataBaseRepository<Quiz> dataBaseQuizRepository = new DataBaseRepository<>("quiz", Quiz.class, columnNames3);
        DataBaseRepository<Assignment> dataBaseAssignmentRepository = new DataBaseRepository<>("assignment", Assignment.class, columnNames4);
        DataBaseRepository<Module> dataBaseModuleRepository = new DataBaseRepository<>("module", Module.class, columnNames5);




//
//        // Create the student in the database
        //dataBaseRepository.create(instructor);
        //dataBaseQuizRepository.create(quiz);
        //dataBaseAssignmentRepository.create(assignment);
        //dataBaseModuleRepository.create(module);
        dataBaseModuleRepository.create(module);


         //System.out.println(dataBaseRepository.get(1));

    }
}