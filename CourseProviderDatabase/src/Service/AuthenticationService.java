package Service;

import Exceptions.ValidationException;
import Models.*;
import Repository.DataBaseRepository;
import Repository.FileRepository;
import Repository.IRepository;
import Repository.InMemoryRepo;
import Utils.Utils;
import jdk.jshell.execution.Util;

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
                populate();

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

    public void populate(){
        studentIRepository.create(new Student(1, "maia", "maiapass", "maia@gmail.com", "student"));
        studentIRepository.create(new Student(2, "johnDoe", "jd123pass", "john.doe@yahoo.com", "student"));
        studentIRepository.create(new Student(3, "mikeSmith", "pass789", "mike.smith@example.com", "student"));
        studentIRepository.create(new Student(4, "sarahJones", "sarahJ2023", "sarah.jones@outlook.com", "student"));
        studentIRepository.create(new Student(5, "alexWong", "wongAlex22", "alex.wong@gmail.com", "student"));
        studentIRepository.create(new Student(6, "emilyBrown", "brownEmily1", "emily.brown@hotmail.com", "student"));
        studentIRepository.create(new Student(7, "davidLee", "lee123David", "david.lee@example.com", "student"));
        studentIRepository.create(new Student(8, "lisaChen", "chenLisa99", "lisa.chen@yahoo.com", "student"));
        studentIRepository.create(new Student(9, "ryanTaylor", "taylorRyan21", "ryan.taylor@outlook.com", "student"));
        studentIRepository.create(new Student(10, "oliviaGarcia", "garcia2023", "olivia.garcia@gmail.com", "student"));


        // Adding Instructors
        instructorIRepository.create(new Instructor(1, "profMiller", "teach123", "miller@example.com", "instructor"));
        instructorIRepository.create(new Instructor(2, "drJohnson", "jPhD2023", "johnson@university.edu", "instructor"));
        instructorIRepository.create(new Instructor(3, "profWilliams", "willTeach22", "williams@college.edu", "instructor"));
        instructorIRepository.create(new Instructor(4, "drBrown", "brownLectures", "brown@institute.edu", "instructor"));
        instructorIRepository.create(new Instructor(5, "profDavis", "davisClass101", "davis@academy.com", "instructor"));
        instructorIRepository.create(new Instructor(6, "drWilson", "wilsonLab2023", "wilson@school.edu", "instructor"));
        instructorIRepository.create(new Instructor(7, "profTaylor", "taylorTeach", "taylor@university.com", "instructor"));
        instructorIRepository.create(new Instructor(8, "drAnderson", "andersonLectures", "anderson@college.edu", "instructor"));
        instructorIRepository.create(new Instructor(9, "profMoore", "mooreMath2023", "moore@institute.com", "instructor"));
        instructorIRepository.create(new Instructor(10, "drLee", "leeScience101", "lee@academy.edu", "instructor"));


        adminIRepository.create(new Admin(1, "adminUser", "adminPass", "admin@example.com", "admin"));
        adminIRepository.create(new Admin(2, "sysAdmin", "sysPass123", "sysadmin@university.edu", "admin"));
        adminIRepository.create(new Admin(3, "techSupport", "techPass456", "techsupport@college.edu", "admin"));
        adminIRepository.create(new Admin(4, "itManager", "itPass789", "itmanager@institute.edu", "admin"));
        adminIRepository.create(new Admin(5, "securityAdmin", "securePass", "security@academy.com", "admin"));
        adminIRepository.create(new Admin(6, "dataAdmin", "dataPass321", "dataadmin@school.edu", "admin"));
        adminIRepository.create(new Admin(7, "networkAdmin", "netPass654", "network@university.com", "admin"));
        adminIRepository.create(new Admin(8, "supportAdmin", "suppPass987", "support@college.edu", "admin"));
        adminIRepository.create(new Admin(9, "maintenanceAdmin", "maintPass", "maintenance@institute.com", "admin"));
        adminIRepository.create(new Admin(10, "systemManager", "sysManPass", "sysman@academy.edu", "admin"));


    }
}
