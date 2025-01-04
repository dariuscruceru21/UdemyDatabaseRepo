package Service;

import Models.*;
import Models.Module;
import Repository.DataBaseRepository;
import Repository.FileRepository;
import Repository.IRepository;
import Repository.InMemoryRepo;
import Utils.Utils;

import java.util.List;

public class AuthenticationService {
    Utils utils = new Utils();
    private IRepository<Student> studentIRepository;
    private IRepository<Admin> adminIRepository;
    private IRepository<Instructor> instructorIRepository;




    public AuthenticationService(String storageMethode) {
        switch (storageMethode) {
            case "inmemory":
                studentIRepository = new InMemoryRepo<>();
                adminIRepository = new InMemoryRepo<>();
                instructorIRepository = new InMemoryRepo<>();
                break;
            case "file":
                studentIRepository = new FileRepository<>("student.csv");
                adminIRepository = new FileRepository<>("admin.csv");
                instructorIRepository = new FileRepository<>("instructor.csv");
                break;
            case "database":
                studentIRepository = new DataBaseRepository<>("student",Student.class,utils.getUsersParameters());
                adminIRepository = new DataBaseRepository<>("admin",Admin.class,utils.getUsersParameters());
                instructorIRepository = new DataBaseRepository<>("instructor",Instructor.class,utils.getUsersParameters());
                break;
            default:
                studentIRepository = new DataBaseRepository<>("student",Student.class,utils.getUsersParameters());
                adminIRepository = new DataBaseRepository<>("admin",Admin.class,utils.getUsersParameters());
                instructorIRepository = new DataBaseRepository<>("instructor",Instructor.class,utils.getUsersParameters());
                break;
        }
    }

    public User authenticate(String username, String password) {
        List<Student>students = studentIRepository.getAll();
        for(Student student : students) {
            if(student.getUsername().equals(username) && student.getPassword().equals(password)) {
                return student;
            }
        }

        List<Admin>admins = adminIRepository.getAll();
        for(Admin admin : admins) {
            if(admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                return admin;
            }
        }

        List<Instructor>instructors = instructorIRepository.getAll();
        for(Instructor instructor : instructors) {
            if(instructor.getUsername().equals(username) && instructor.getPassword().equals(password)) {
                return instructor;
            }
        }

        return null;
    }
}
