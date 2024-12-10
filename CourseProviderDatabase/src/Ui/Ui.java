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
            System.out.println("10. Take quiz from an assignment");
            System.out.println("11. View all courses taught by a instructor");
            System.out.println("12. Get the assigned instructor to a course");
            System.out.println("13. Logout");
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
                case 8 -> getUnderOcupiedCourses();
                case 9 -> previewCoursesThatEndBeforeADate();
                case 10 -> takeAssignmentQuiz();
                case 11 -> getCoursesByInstructor();
                case 12 -> getAssignedInstructor();
                case 13 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice. Please try again.");

            }
        }while (choice != 11);


        // Implement further functionality for students
    }

    public void instructorMenu(Instructor instructor) throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do{
            System.out.println("Instructor Menu:");
            System.out.println("1. View all courses");
            System.out.println("2. Get course info");
            System.out.println("3. View all students from a course");
            System.out.println("4. Get student info");
            //Assignment handling
            System.out.println("5. View all Assignments from a module");
            System.out.println("6. Add Assignment to Module");
            System.out.println("7. Remove Assignment from Module");
            //Quiz handling
            System.out.println("8. View All Quiz's from a Assignment");
            System.out.println("9. Add Quiz to Assignment");
            System.out.println("10. Remove Quiz from Assignment");
            //Personal info
            System.out.println("11. Get instructor info");
            System.out.println("12. Preview underocupied Courses");
            System.out.println("13. Preview courses that end before a given date");
            System.out.println("14. Get courses you teach(provide ID)");
            System.out.println("15. Get the assigned instructor to a course");
            System.out.println("16. Logout");
            choice = scanner.nextInt();
            switch (choice){
                case 1 -> viewAllCourses();
                case 2 -> getCourseInfo();
                case 3 -> getEnrolledStudents();
                case 4 -> getStudentInfo();
                case 5 -> viewAssignmentsFromModule();
                case 6 -> addAssignmentToModule();
                case 7 -> removeAssignmentFromModule();
                case 8 -> viewQuizFromAssignment();
                case 9 -> addQuizToAssignment();
                case 10 -> removeQuizFromAssignment();
                case 11 -> getInstructorInfo();
                case 12 -> getUnderOcupiedCourses();
                case 13 -> previewCoursesThatEndBeforeADate();
                case 14 -> getCoursesByInstructor();
                case 15 -> getAssignedInstructor();
                case 16 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice. Please try again.");

            }
        }while (choice != 14);

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
            System.out.println("12. Get all instructors sorted by number of courses");
            System.out.println("13. Get all instructors by number of students they teach");
            //Module handling
            System.out.println("14. View all Modules from a Course");
            System.out.println("15. Add Module to Course");
            System.out.println("16. Remove Module from Course");
            //Assignment handling
            System.out.println("17. View all Assignments from a module");
            System.out.println("18. Add Assignment to Module");
            System.out.println("19. Remove Assignment from Module");
            //Quiz handling
            System.out.println("20. View All Quiz's from a Assignment");
            System.out.println("21. Add Quiz to Assignment");
            System.out.println("22. Remove Quiz from Assignment");
            //Admin handling
            System.out.println("23. View all admins");
            System.out.println("24. Add Admin");
            System.out.println("25. Remove Admin");
            //Update operations
            System.out.println("26. Update Course");
            System.out.println("27. Update Instructor");
            System.out.println("28. Update Student");
            System.out.println("29. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

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
                case 12 -> sortInstructorsByNumberOfCourses();
                case 13 -> sortInstructorsByEnrollment();
                case 14 -> viewModulesFromCourse();
                case 15 -> addModuleToCourse();
                case 16 -> removeModuleFromCourse();
                case 17 -> viewAssignmentsFromModule();
                case 18 -> addAssignmentToModule();
                case 19 -> removeAssignmentFromModule();
                case 20 -> viewQuizFromAssignment();
                case 21 -> addQuizToAssignment();
                case 22 -> removeQuizFromAssignment();
                case 23 -> viewAllAdmins();
                case 24 -> addAdmin();
                case 25 -> removeAdmin();
                case 26 -> updateCourse();
                case 27 -> updateInstructor();
                case 28 -> updateStudent();
                case 29 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 21);
    }

    public void updateCourse() throws EntityNotFoundException {
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
        System.out.println(courseUserController.updateCourse(course));
    }

    public void updateInstructor() throws EntityNotFoundException {
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
        System.out.println(courseUserController.updateInstructor(instructor));
    }

    public void updateStudent() throws EntityNotFoundException {
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
        System.out.println(courseUserController.updateStudent(student));
    }

    public void viewAllAdmins() throws EntityNotFoundException{
        List<Admin> admins = courseUserController.getAllAdmins();
        for (Admin admin : admins) {
            System.out.println(admin);
        }
    }

    public void addAdmin() throws EntityNotFoundException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Admin ID: ");
        Integer adminId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Admin Name: ");
        String adminName = scanner.nextLine();
        System.out.println("Enter Admin password: ");
        String adminPassword = scanner.nextLine();
        System.out.println("Enter Admin Email: ");
        String adminEmail = scanner.nextLine();
        Admin admin = new Admin(adminId, adminName, adminPassword, adminEmail, "Admin");
        System.out.println(courseUserController.addAdmin(admin));
    }

    public void removeAdmin() throws EntityNotFoundException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Admin ID: ");
        Integer adminId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(courseUserController.removeAdmin(adminId));
    }

    public void getCourseInfo() throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter course ID: ");
        Integer courseID = scanner.nextInt();
        scanner.nextLine();
        System.out.println(courseUserController.getCourseInfo(courseID));
    }

    public void getStudentInfo() throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student ID: ");
        Integer studentID = scanner.nextInt();
        scanner.nextLine();
        System.out.println(courseUserController.getStudentInfo(studentID));
    }

    public void getEnrolledStudents() throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter course ID: ");
        Integer courseID = scanner.nextInt();
        scanner.nextLine();
        List<Student> students = courseUserController.getEnrolledStudents(courseID);
        for (Student student : students) {
            System.out.println(student);
        }
    }

    public void getInstructorInfo() throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter instructor ID: ");
        Integer instructorID = scanner.nextInt();
        scanner.nextLine();
        System.out.println(courseUserController.getInstructorInfo(instructorID));
    }

    public void getCoursesByInstructor() throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter instructor ID: ");
        Integer instructorID = scanner.nextInt();
        scanner.nextLine();
        List<Course> courses = courseUserController.getCoursesByInstructor(instructorID);
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    public void getAssignedInstructor() throws EntityNotFoundException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter course ID: ");
        Integer courseId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(courseUserController.getAssignedInstructor(courseId));
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

    public void removeModuleFromCourse() throws EntityNotFoundException {
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

    public void removeAssignmentFromModule() throws EntityNotFoundException {
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

    public void removeQuizFromAssignment() throws EntityNotFoundException {
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

    public void sortInstructorsByNumberOfCourses(){
        List<Instructor> instructors = courseUserController.sortAllInstructorsByNumberOfCourses();
        for (Instructor instructor : instructors) {
            System.out.println(instructor);
        }
    }

    public void sortInstructorsByEnrollment(){
        List<Instructor> instructors = courseUserController.getInstructorsByTotalEnrollment();
        for (Instructor instructor : instructors) {
            System.out.println(instructor);
        }
    }

    public void takeAssignmentQuiz(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Assignment ID: ");
        int assignmentId = scanner.nextInt();
        scanner.nextLine();
        assignmentController.takeAssignmentQuiz(assignmentId);
    }

    public void getUnderOcupiedCourses(){
        List<Course> courses = courseUserController.getAllUnderOccupiedCourses();
        for (Course course : courses) {
            System.out.println(course);
        }
    }


}
