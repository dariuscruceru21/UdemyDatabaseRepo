import Models.*;
import Models.Module;
import Repository.DataBaseRepository;
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
         Course course = new Course(2,"ALgebra","usor",10,"2024-07-12","2024-10-11",9);
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
        messageDataBaseRepository.create(message);






    }
}