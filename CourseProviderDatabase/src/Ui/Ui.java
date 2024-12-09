package Ui;

import Controller.AssignmentController;
import Controller.CourseUserController;
import Exceptions.EntityNotFoundException;
import Models.*;
import Models.Module;
import Service.AuthenticationService;

import java.util.List;
import java.util.Scanner;

public class Ui {
    private final AuthenticationService authService = new AuthenticationService();
    private final AssignmentController assignmentController;
    private final CourseUserController courseUserController;

    public Ui(AssignmentController assignmentController, CourseUserController courseUserController) {
        this.assignmentController = assignmentController;
        this.courseUserController = courseUserController;
    }

    public void start() throws EntityNotFoundException {
        System.out.println("Welcome to the Education Management System!");

        // Login Process
        User loggedInUser = login();

        // Differentiate user roles and redirect to respective functionalities
        switch (loggedInUser) {
            case Student student -> {
                System.out.println("Welcome, Student: " + loggedInUser.getUsername());
                studentMenu(student);
            }
            case Admin admin -> {
                System.out.println("Welcome, Admin: " + loggedInUser.getUsername());
                adminMenu(admin);
            }
            case Instructor instructor -> {
                System.out.println("Welcome, Instructor: " + loggedInUser.getUsername());
                instructorMenu(instructor);
            }
            case null, default -> System.out.println("Login failed. Please try again.");
        }
    }

    public User login() {
        Scanner scanner = new Scanner(System.in);
        User user = null;

        while (user == null) {
            System.out.println("Please log in:");
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            user = authService.authenticate(username, password);

            if (user == null) {
                System.out.println("Invalid credentials. Please try again.");
            }
        }
        return user;
    }

    public void studentMenu(Student student) throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do{
            System.out.println("\n--- Student Menu ---");
            System.out.println("1. View All Courses");
            System.out.println("2. View all Instructors");
            System.out.println("3. Enroll Into a Course");
            System.out.println("4. Unenroll from a Course");
            System.out.println("5. View Courses I am enrolled in");
            System.out.println("6. Get Info about a Course");
            System.out.println("7. Get Info about an Instructor");
            System.out.println("8. Preview under ocupied Courses");
            System.out.println("9. Preview courses that end before a given date");
            System.out.println("10. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            switch (choice){
                case 1 -> viewAllCourses();
                case 2 -> viewAllInstructors();
                case 3 -> enroll();
                case 4 -> unenroll();
                case 5 -> viewCoursesAStudentIsEnrolledIn();
                case 6 -> viewCourseInfo();
                case 7 -> viewInstructorInfo();
                case 8 -> System.out.println(courseUserController.getAllUnderOccupiedCourses());
                case 9 -> previewCoursesThatEndBeforeADate();
                case 10 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice. Please try again.");

            }
        }while (choice != 10);


        // Implement further functionality for students
    }

    public void instructorMenu(Instructor instructor) {
        System.out.println("Instructor Menu:");
        System.out.println("1. Courses");
        System.out.println("2. Assign Grades");
        System.out.println("3. View Assignments");
        System.out.println("4. Logout");

        // Implement further functionality for instructors
    }

    public void adminMenu(Admin admin) throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Admin Menu ---");
            //Course handling
            System.out.println("1. View All Courses");
            System.out.println("2. Add a Course");
            System.out.println("3. Remove a Course");
            //Assign/Unassign
            System.out.println("4. Assign Instructor to Course");
            System.out.println("5. Unassign Instructor from Course");
            //Student handling
            System.out.println("6. View all Students");
            System.out.println("7. Add Student");
            System.out.println("8. Remove Student");
            //Instructor handling
            System.out.println("9. View all Instructors");
            System.out.println("10. Add Instructor");
            System.out.println("11. Remove Instructor");
            //Module handling
            System.out.println("12. View all Modules from a Course");
            System.out.println("13. Add Module to Course");
            System.out.println("14. Remove Module from Course");
            //Assignment handling
            System.out.println("15. View all Assignments from a module");
            System.out.println("16. Add Assignment to Module");
            System.out.println("17. Remove Assignment from Module");
            //Quiz handling
            System.out.println("18. View All Quiz's from a Assignment");
            System.out.println("19. Add Quiz to Assignment");
            System.out.println("20. Remove Quiz from Assignment");
            System.out.println("21. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> viewAllCourses();
                case 2 -> addCourse();
                case 3 -> removeCourse();
                case 4 -> assignInstructorToCourse();
                case 5 -> unassignInstructorFromCourse();
                case 6 -> viewAllStudents();
                case 7 -> addStudent();
                case 8 -> removeStudent();
                case 9 -> viewAllInstructors();
                case 10 -> addInstructor();
                case 11 -> removeInstructor();
                case 12 -> viewModulesFromCourse();
                case 13 -> addModuleToCourse();
                case 14 -> removeModuleFromCourse();
                case 15 -> viewAssignmentsFromModule();
                case 16 -> addAssignmentToModule();
                case 17 -> removeAssignmentFromModule();
                case 18 -> viewQuizFromAssignment();
                case 19 -> addQuizToAssignment();
                case 20 -> removeQuizFromAssignment();
                case 21 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 21);
    }





    public void viewAllCourses() {
        List<Course> courses = courseUserController.getAllCourses();
        System.out.println("Available Courses:");
        courses.forEach(course -> System.out.println(course.getId() + ": " + course.getCourseTitle()));
    }

    public void addCourse() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Course ID: ");
        Integer courseID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Course Title: ");
        String courseName = scanner.nextLine();
        System.out.println("Enter Course Description: ");
        String courseDescription = scanner.nextLine();
        System.out.println("Enter available spots: ");
        Integer availableSpots = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter start date: ");
        String startDate = scanner.nextLine();
        System.out.println("Enter end date: ");
        String endDate = scanner.nextLine();
        System.out.println("Enter the id of the instructor: ");
        Integer id = scanner.nextInt();
        scanner.nextLine();
        Course course = new Course(courseID, courseName, courseDescription, availableSpots, startDate, endDate, id);
        System.out.println(courseUserController.addCourse(course));
    }

    public void removeCourse() throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Course ID to remove: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(courseUserController.removeCourse(courseId));
    }

    public void assignInstructorToCourse() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Instructor ID: ");
        int instructorId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Course ID: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(courseUserController.assignInstructorToCourse(instructorId, courseId));
    }

    public void unassignInstructorFromCourse() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Instructor ID: ");
        int instructorId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Course ID: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(courseUserController.unassignInstructorFromCourse(instructorId, courseId));
    }

    public void viewAllStudents() {
        List<Student> students = courseUserController.getAllStudents();
        System.out.println("Students:");
        students.forEach(student -> System.out.println(student.getId() + ": " + student.getUsername()));
    }

    public void addStudent() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Student ID: ");
        Integer studentID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Student Name: ");
        String studentName = scanner.nextLine();
        System.out.println("Enter Student password: ");
        String studentPassword = scanner.nextLine();
        System.out.println("Enter Student Email: ");
        String studentEmail = scanner.nextLine();
        Student student = new Student(studentID, studentName, studentPassword, studentEmail, "Student");
        System.out.println(courseUserController.addStudent(student));
    }

    public void removeStudent() throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Student ID to remove: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(courseUserController.removeStudent(studentId));
    }

    public void viewAllInstructors() {
        List<Instructor> instructors = courseUserController.getAllInstructors();
        System.out.println("Instructors:");
        instructors.forEach(instructor -> System.out.println(instructor.getId() + ": " + instructor.getUsername()));
    }

    public void addInstructor() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Instructor ID: ");
        Integer instructorID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Instructor Name: ");
        String instructorName = scanner.nextLine();
        System.out.println("Enter Instructor password: ");
        String instructorPassword = scanner.nextLine();
        System.out.print("Enter Instructor Email: ");
        String instructorEmail = scanner.nextLine();
        Instructor instructor = new Instructor(instructorID, instructorName, instructorPassword, instructorEmail, "Instructor");
        System.out.println(courseUserController.addInstructor(instructor));
    }

    public void removeInstructor() throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Instructor ID to remove: ");
        int instructorId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(courseUserController.removeInstructor(instructorId));
    }

    public void viewModulesFromCourse() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Course ID: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();
        List<Module> modules = assignmentController.getModulesFromCourse(courseId);
        System.out.println("Modules from course with ID " + courseId + ":" );
        modules.forEach(module -> System.out.println(module.getId() + ": " + module.getModuleTitle()));
    }

    public void addModuleToCourse() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Course ID: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Module ID: ");
        int moduleId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(assignmentController.addModuleToCourse(courseId, moduleId));
    }

    public void removeModuleFromCourse() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Course ID: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Module ID to remove: ");
        int moduleId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(assignmentController.removeModuleFromCourse(courseId, moduleId));
    }

    public void viewAssignmentsFromModule() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Module ID: ");
        int moduleId = scanner.nextInt();
        scanner.nextLine();
        List<Assignment> assignments = assignmentController.getAssignmentsFromModule(moduleId);
        System.out.println("Assignments from module with ID " + moduleId + ":");
        assignments.forEach(assignment -> System.out.println(assignment.getId() + ": " + assignment.getDescription()));
    }

    public void addAssignmentToModule() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Module ID: ");
        int moduleId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Assignment ID: ");
        int assignmentId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(assignmentController.addAssignmentToModule(moduleId, assignmentId));
    }

    public void removeAssignmentFromModule() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Module ID: ");
        int moduleId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Assignment ID to remove: ");
        int assignmentId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(assignmentController.removeAssignmentFromModule(moduleId, assignmentId));
    }

    public void viewQuizFromAssignment() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Assignment ID: ");
        int assignmentId = scanner.nextInt();
        scanner.nextLine();
        List<Quiz> quizzes = assignmentController.getQuizFromAssignment(assignmentId);
        System.out.println("Quizzes from assignment with ID " + assignmentId + ":");
        quizzes.forEach(quiz -> System.out.println(quiz.getId() + ": " + quiz.getTitle()));
    }

    public void addQuizToAssignment() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Assignment ID: ");
        int assignmentId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Quiz ID: ");
        int quizId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(assignmentController.addQuizToAssignment(assignmentId, quizId));
    }

    public void removeQuizFromAssignment() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Assignment ID: ");
        int assignmentId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Quiz ID to remove: ");
        int quizId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(assignmentController.removeQuizFromAssignment(assignmentId, quizId));
    }

    public void enroll(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter you're ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the course id you want to enroll in: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();
        courseUserController.enrollStudentInCourse(userId, courseId);
    }


    public void unenroll() throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter you're ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the course id you want to unenroll from: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();
        courseUserController.unenrollStudentFromCourse(userId, courseId);

    }

    public void viewCoursesAStudentIsEnrolledIn() throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter you're ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(courseUserController.getCoursesByStudent(userId));
    }

    public void viewCourseInfo() throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the course id you want to view information about: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(courseUserController.getCourseInfo(courseId));
    }

    public void viewInstructorInfo() throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the instructor id you want to view information about: ");
        int instructorId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(courseUserController.getInstructorInfo(instructorId));
    }

    public void previewCoursesThatEndBeforeADate(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the date to see the courses that end before the given date(YYYY-MM-DD: ");
        String date = scanner.nextLine();
        scanner.nextLine();
        System.out.println(courseUserController.getAllCoursesThatEndBeforeADate(date));
    }


}
