import Models.Course;
import Models.Student;
import Models.User;
import Repository.DataBaseRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        // Manually add the ID as part of the object creation
        Student student = new Student(8, "Alex", "alex123", "alexandru@gmail.com", "student");

        // Pass column names including 'userid'
        List<String> columnNames = Arrays.asList("userid", "username", "password", "email", "type");
        DataBaseRepository<Student> dataBaseRepository = new DataBaseRepository<>("\"User\"", Student.class, columnNames);

        // Create the student in the database
        dataBaseRepository.create(student);

    }
}