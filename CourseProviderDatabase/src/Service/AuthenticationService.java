package Service;

import Models.*;
import Models.Module;
import Repository.DataBaseRepository;
import Utils.Utils;

import java.util.List;

public class AuthenticationService {
    Utils utils = new Utils();
    private DataBaseRepository<Student> studentDataBaseRepository= new DataBaseRepository<>("student",Student.class,utils.getUsersParameters());
    private DataBaseRepository<Admin> adminDataBaseRepository = new DataBaseRepository<>("admin",Admin.class,utils.getUsersParameters());
    private DataBaseRepository<Instructor> instructorDataBaseRepository = new DataBaseRepository<>("instructor",Instructor.class,utils.getUsersParameters());





    public AuthenticationService() {}

    public User authenticate(String username, String password) {
        List<Student>students = studentDataBaseRepository.getAll();
        for(Student student : students) {
            if(student.getUsername().equals(username) && student.getPassword().equals(password)) {
                return student;
            }
        }

        List<Admin>admins = adminDataBaseRepository.getAll();
        for(Admin admin : admins) {
            if(admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                return admin;
            }
        }

        List<Instructor>instructors = instructorDataBaseRepository.getAll();
        for(Instructor instructor : instructors) {
            if(instructor.getUsername().equals(username) && instructor.getPassword().equals(password)) {
                return instructor;
            }
        }

        return null;
    }
}
