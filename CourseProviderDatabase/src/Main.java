import Models.Course;
import Models.Instructor;
import Models.Student;
import Models.User;
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
         Instructor student = new Instructor(9, "Paul", "paul123", "paul@gmail.com", "instructor");
         Course course = new Course(2,"Analiza","hard",10,"2024-07-12","2024-10-11",9);
//
//        // Pass column names including 'userid'
         List<String> columnNames = Arrays.asList("courseID", "courseTitle", "description", "availableSpots", "startDate", "endDate","instructorId");
        //List<String> columnNames2 = Arrays.asList("userid","username","password","email","type");
         DataBaseRepository<Course> dataBaseRepository = new DataBaseRepository<>("course", Course.class, columnNames);


        //System.out.println("courseId".toLowerCase());


//
//        // Create the student in the database
//        dataBaseRepository.create(student);
//        System.out.println("Print");
//        System.out.println(System.getenv("DB_PASSWORD"));

         System.out.println(dataBaseRepository.get(1));

    }
}