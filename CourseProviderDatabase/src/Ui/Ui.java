package Ui;

import Controller.AssignmentController;
import Controller.CourseUserController;
import Exceptions.BusinessException;
import Exceptions.EntityNotFoundException;
import Exceptions.ValidationException;
import Models.*;
import Models.Module;
import Service.AuthenticationService;

import java.util.List;
import java.util.Scanner;

/**
 * The Ui class is responsible for interacting with the user in the Education Management System.
 * It provides various methods for handling authentication, assignment management, and course user management.
 * This class delegates the functionality of assignment and course-user related operations to the respective controller classes.
 */
public class Ui {
    private  AuthenticationService authService;
    private  AssignmentController assignmentController;
    private  CourseUserController courseUserController;

//    /**
//     * Constructs a new Ui object, initializing the controllers for assignment and course-user management.
//     *
//     * @param assignmentController the controller responsible for managing assignments.
//     * @param courseUserController the controller responsible for managing users in courses.
//     */
//    public Ui(AssignmentController assignmentController, CourseUserController courseUserController) {
//        this.assignmentController = assignmentController;
//        this.courseUserController = courseUserController;
//    }

    /**
     * Starts the Education Management System by welcoming the user and initiating the login process.
     * After successful login, the user is directed to the appropriate menu based on their role (Student, Admin, or Instructor).
     * If login fails, a failure message is displayed.
     *
     * @throws EntityNotFoundException if a necessary entity cannot be found during menu processing.
     */
    public void start() throws EntityNotFoundException, BusinessException, ValidationException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Education Management System!");

        System.out.println("--- Select Data Storage Method ---");
        System.out.println("1. In-memory");
        System.out.println("2. File");
        System.out.println("3. Database");
        System.out.print("Choose an option: ");

        int storageOption;

        try {
            storageOption = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid option, defaulting to Database storage.");
            storageOption = 3;
            scanner.nextLine();
        }
        String storageMethod;

        switch (storageOption) {
            case 1:
                storageMethod = "inmemory";
                break;
            case 2:
                storageMethod = "file";
                break;
            case 3:
                storageMethod = "db";
                break;
            default:
                System.out.println("Invalid option, defaulting to Database storage.");
                storageMethod = "db";
                break;
        }

        this.assignmentController = new AssignmentController(storageMethod);
        this.courseUserController = new CourseUserController(storageMethod);
        this.authService = new AuthenticationService(storageMethod);

        if ("inmemory".equalsIgnoreCase(storageMethod)) {
            populateInMemoryRepo();
        }


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

    /**
     * Authenticates the user by prompting for their username and password.
     * If the credentials are invalid, the user is repeatedly prompted to try again until valid credentials are provided.
     *
     * @return a User object representing the authenticated user.
     */
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

    /**
     * Populates the in-memory repository with initial data for testing and runtime operations.
     * This method initializes and adds sample data for Users, Students, Instructors, Admins,
     * Courses, Modules, Assignments, Quizzes, Messages, and Forums into the repository.
     */
    public void populateInMemoryRepo() throws ValidationException {
        // Adding Students
        courseUserController.addStudent(new Student(3, "mikeSmith", "pass789", "mike.smith@example.com", "student"));

        // Adding Instructors
        courseUserController.addInstructor(new Instructor(4, "profMiller", "teach123", "miller@example.com", "instructor"));

        // Adding Admins
        courseUserController.addAdmin(new Admin(5, "adminUser", "adminPass", "admin@example.com", "admin"));

        // Adding Courses
        try {
            courseUserController.addCourse(new Course(101, "Java Programming", "Learn Java basics", 30, "2025-01-15", "2025-05-15", 4));
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
        try {
            courseUserController.addCourse(new Course(102, "Database Systems", "Introduction to databases", 25, "2025-02-01", "2025-06-01", 4));
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }

        // Adding Modules
        assignmentController.addModule(new Module(201, "Introduction to Java", "Java basics and setup"));
        assignmentController.addModule(new Module(202, "Advanced Java", "Multithreading and streams"));

        // Adding Assignments
        assignmentController.addAssignment(new Assignment(301, "Complete Java Project", "2025-03-01", 100));
        assignmentController.addAssignment(new Assignment(302, "Database Query Exercise", "2025-04-01", 50));

        // Adding Quizzes
        assignmentController.addQuiz(new Quiz(401, "Java Basics Quiz", "Multiple choice questions on Java basics", 3));
        assignmentController.addQuiz(new Quiz(402, "Database Fundamentals", "SQL queries and relational design questions", 2));


    }


    /**
     * Displays the menu of options available to a Student, allowing them to view courses, enroll or unenroll,
     * view instructor details, and more.
     * The menu continues to show until the student chooses to log out.
     *
     * @param student the student object whose menu options are being displayed.
     * @throws EntityNotFoundException if a necessary entity cannot be found during menu processing.
     */
    public void studentMenu(Student student) throws EntityNotFoundException, BusinessException {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
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
            switch (choice) {
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
        } while (choice != 13);
    }

    /**
     * Displays the menu of options available to an Instructor, allowing them to view courses, assignments, quizzes,
     * student info, and more. The menu continues to show until the instructor chooses to log out.
     *
     * @param instructor the instructor object whose menu options are being displayed.
     * @throws EntityNotFoundException if a necessary entity cannot be found during menu processing.
     */
    public void instructorMenu(Instructor instructor) throws EntityNotFoundException, ValidationException {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Instructor Menu:");
            System.out.println("1. View all courses");
            System.out.println("2. Get course info");
            System.out.println("3. View all students from a course");
            System.out.println("4. Get student info");
            //Assignment handling
            System.out.println("5. View all Assignments from a module");
            System.out.println("6. Add an assignment");
            System.out.println("7. Remove an assignment");
            System.out.println("8. Add Assignment to Module");
            System.out.println("9. Remove Assignment from Module");
            //Quiz handling
            System.out.println("10. View All Quiz's from a Assignment");
            System.out.println("11. Add a quiz");
            System.out.println("12. Remove a quiz");
            System.out.println("13. Add Quiz to Assignment");
            System.out.println("14. Remove Quiz from Assignment");
            //Personal info
            System.out.println("15. Get instructor info");
            System.out.println("16. Preview underocupied Courses");
            System.out.println("17. Preview courses that end before a given date");
            System.out.println("18. Get courses you teach(provide ID)");
            System.out.println("19. Get the assigned instructor to a course");
            System.out.println("20. Logout");
            choice = scanner.nextInt();
            switch (choice) {
                case 1 -> viewAllCourses();
                case 2 -> getCourseInfo();
                case 3 -> getEnrolledStudents();
                case 4 -> getStudentInfo();
                case 5 -> viewAssignmentsFromModule();
                case 6 -> addAssignment();
                case 7 -> removeAssignment();
                case 8 -> addAssignmentToModule();
                case 9 -> removeAssignmentFromModule();
                case 10 -> addQuiz();
                case 11 -> removeQuiz();
                case 12 -> viewQuizFromAssignment();
                case 13 -> addQuizToAssignment();
                case 14 -> removeQuizFromAssignment();
                case 15 -> getInstructorInfo();
                case 16 -> getUnderOcupiedCourses();
                case 17 -> previewCoursesThatEndBeforeADate();
                case 18 -> getCoursesByInstructor();
                case 19 -> getAssignedInstructor();
                case 20 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 16);
    }

    /**
     * Displays the menu of options available to an Admin, allowing them to view and manage courses, students, instructors,
     * assignments, quizzes, and more. The menu continues to show until the admin chooses to log out.
     *
     * @param admin the admin object whose menu options are being displayed.
     * @throws EntityNotFoundException if a necessary entity cannot be found during menu processing.
     */
    public void adminMenu(Admin admin) throws EntityNotFoundException, BusinessException, ValidationException {
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
            System.out.println("15. Add module");
            System.out.println("16. Remove Module");
            System.out.println("17. Add Module to Course");
            System.out.println("18. Remove Module from Course");
            //Assignment handling
            System.out.println("19. View all Assignments from a module");
            System.out.println("20. Add assignment");
            System.out.println("21. Remove assignment");
            System.out.println("22. Add Assignment to Module");
            System.out.println("23. Remove Assignment from Module");
            //Quiz handling
            System.out.println("24. View All Quiz's from a Assignment");
            System.out.println("25. Add Quiz");
            System.out.println("26. Remove Quiz");
            System.out.println("27. Add Quiz to Assignment");
            System.out.println("28. Remove Quiz from Assignment");
            //Admin handling
            System.out.println("29. View all admins");
            System.out.println("30. Add Admin");
            System.out.println("31. Remove Admin");
            //Update operations
            System.out.println("32. Update Course");
            System.out.println("33. Update Instructor");
            System.out.println("34. Update Student");
            System.out.println("35. Logout");
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
                case 15 -> addModule();
                case 16 -> removeModule();
                case 17 -> addModuleToCourse();
                case 18 -> removeModuleFromCourse();
                case 19 -> viewAssignmentsFromModule();
                case 20 -> addAssignment();
                case 21 -> removeAssignment();
                case 22 -> addAssignmentToModule();
                case 23 -> removeAssignmentFromModule();
                case 24 -> viewQuizFromAssignment();
                case 25 -> addQuiz();
                case 26 -> removeQuiz();
                case 27 -> addQuizToAssignment();
                case 28 -> removeQuizFromAssignment();
                case 29 -> viewAllAdmins();
                case 30 -> addAdmin();
                case 31 -> removeAdmin();
                case 32 -> updateCourse();
                case 33 -> updateInstructor();
                case 34 -> updateStudent();
                case 35 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 29);
    }


    /**
     * Updates an existing course in the system.
     * Prompts the user to enter course details such as ID, title, description, available spots,
     * start date, end date, and instructor ID.
     *
     * @throws EntityNotFoundException if the course to be updated does not exist.
     */
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
        System.out.println("Enter the ID of the instructor: ");
        Integer id = scanner.nextInt();
        scanner.nextLine();
        Course course = new Course(courseID, courseName, courseDescription, availableSpots, startDate, endDate, id);
        System.out.println(courseUserController.updateCourse(course));
    }


    /**
     * Updates an existing instructor in the system.
     * Prompts the user to enter the instructor's ID, name, password, and email.
     *
     * @throws EntityNotFoundException if the instructor to be updated does not exist.
     */
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

    /**
     * Updates an existing student in the system.
     * Prompts the user to enter the student's ID, name, password, and email.
     *
     * @throws EntityNotFoundException if the student to be updated does not exist.
     */
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

    /**
     * Displays a list of all administrators in the system.
     *
     * @throws EntityNotFoundException if no administrators are found.
     */
    public void viewAllAdmins() throws EntityNotFoundException {
        List<Admin> admins = courseUserController.getAllAdmins();
        for (Admin admin : admins) {
            System.out.println(admin);
        }
    }

    /**
     * Adds a new administrator to the system.
     * Prompts the user to enter the administrator's details such as ID, name, password, and email.
     *
     * @throws EntityNotFoundException if there is an issue adding the administrator.
     */
    public void addAdmin() throws EntityNotFoundException {
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

    /**
     * Removes an administrator from the system.
     * Prompts the user to enter the administrator's ID.
     *
     * @throws EntityNotFoundException if the administrator to be removed does not exist.
     */
    public void removeAdmin() throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Admin ID: ");
        Integer adminId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(courseUserController.removeAdmin(adminId));
    }

    /**
     * Removes a module from the system.
     * Prompts the user to enter the module's ID.
     *
     * @throws EntityNotFoundException if the module to be removed does not exist.
     */
    public void removeModule() throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Module ID to remove: ");
        Integer moduleId = scanner.nextInt();
        scanner.nextLine(); // Clear the buffer
        System.out.println(assignmentController.removeModule(moduleId));
    }

    /**
     * Removes an assignment from the system.
     * Prompts the user to enter the assignment's ID.
     *
     * @throws EntityNotFoundException if the assignment to be removed does not exist.
     */
    public void removeAssignment() throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Assignment ID to remove: ");
        Integer assignmentId = scanner.nextInt();
        scanner.nextLine(); // Clear the buffer
        System.out.println(assignmentController.removeAssignment(assignmentId));
    }

    /**
     * Removes a quiz from the system.
     * Prompts the user to enter the quiz's ID.
     *
     * @throws EntityNotFoundException if the quiz to be removed does not exist.
     */
    public void removeQuiz() throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Quiz ID to remove: ");
        Integer quizId = scanner.nextInt();
        scanner.nextLine(); // Clear the buffer
        System.out.println(assignmentController.removeQuiz(quizId));
    }


    /**
     * Adds a new module to the system.
     * Prompts the user to enter the modules's details.
     *
     * @throws ValidationException if there is an issue adding the module.
     */
    public void addModule() throws ValidationException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Module ID: ");
        int moduleID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Module Title: ");
        String moduleTitle = scanner.nextLine();
        System.out.print("Enter Module Content: ");
        String moduleContent = scanner.nextLine();
        Module module = new Module(moduleID, moduleTitle, moduleContent);
        assignmentController.addModule(module);
    }

    /**
     * Adds a new assignment to the system.
     * Prompts the user to enter the assignments's details.
     *
     * @throws ValidationException if there is an issue adding the assignment.
     */
    public void addAssignment() throws ValidationException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Assignment ID: ");
        Integer assignmentID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Assignment Description: ");
        String assignmentDescription = scanner.nextLine();
        System.out.print("Enter Assignment due date: ");
        String assignmentDueDate = scanner.nextLine();
        System.out.print("Enter Assignment score: ");
        Integer assignmentScore = scanner.nextInt();
        scanner.nextLine();
        Assignment assignment = new Assignment(assignmentID, assignmentDescription, assignmentDueDate, assignmentScore);
        assignmentController.addAssignment(assignment);
    }

    /**
     * Adds a new quiz to the system.
     * Prompts the user to enter the quiz's details.
     *
     * @throws ValidationException if there is an issue adding the quiz.
     */
    public void addQuiz() throws ValidationException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Quiz ID: ");
        Integer quizID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Quiz Title: ");
        String quizTitle = scanner.nextLine();
        System.out.print("Enter Quiz Contents: ");
        String quizContents = scanner.nextLine();
        System.out.print("Enter Quiz correct Answer: ");
        int correctAnswer = scanner.nextInt();
        scanner.nextLine();
        Quiz quiz = new Quiz(quizID, quizTitle, quizContents, correctAnswer);
        assignmentController.addQuiz(quiz);
    }


    /**
     * Retrieves and displays information about a specific course.
     * Prompts the user to enter the course ID.
     *
     * @throws EntityNotFoundException if the course is not found.
     */
    public void getCourseInfo() throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter course ID: ");
        Integer courseID = scanner.nextInt();
        scanner.nextLine();
        System.out.println(courseUserController.getCourseInfo(courseID));
    }

    /**
     * Retrieves and displays information about a specific student.
     * Prompts the user to enter the student ID.
     *
     * @throws EntityNotFoundException if the student is not found.
     */
    public void getStudentInfo() throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student ID: ");
        Integer studentID = scanner.nextInt();
        scanner.nextLine();
        System.out.println(courseUserController.getStudentInfo(studentID));
    }

    /**
     * Retrieves and displays a list of students enrolled in a specific course.
     * Prompts the user to enter the course ID.
     *
     * @throws EntityNotFoundException if the course or students are not found.
     */
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

    /**
     * Retrieves and displays information about a specific instructor.
     * Prompts the user to enter the instructor ID.
     *
     * @throws EntityNotFoundException if the instructor is not found.
     */
    public void getInstructorInfo() throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter instructor ID: ");
        Integer instructorID = scanner.nextInt();
        scanner.nextLine();
        System.out.println(courseUserController.getInstructorInfo(instructorID));
    }

    /**
     * Retrieves and displays information about a course that is thaught by a specific instructor.
     * Prompts the user to enter the instructor ID.
     *
     * @throws EntityNotFoundException if the instructor is not found.
     */
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

    /**
     * Retrieves and displays information about a instructor assigned to a specific course.
     * Prompts the user to enter the course ID.
     *
     * @throws EntityNotFoundException if the course is not found.
     */
    public void getAssignedInstructor() throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter course ID: ");
        Integer courseId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(courseUserController.getAssignedInstructor(courseId));
    }


    /**
     * Displays all available courses by retrieving them from the course controller.
     * The courses are printed with their ID and title.
     */
    public void viewAllCourses() {
        List<Course> courses = courseUserController.getAllCourses();
        System.out.println("Available Courses:");
        courses.forEach(course -> System.out.println(course.getId() + ": " + course.getCourseTitle()));
    }

    /**
     * Prompts the user to input the details of a new course and adds the course to the system.
     * It collects the following details: course ID, title, description, available spots,
     * start date, end date, and instructor ID. After creating the course, it adds it to the system
     * through the course controller.
     */
    public void addCourse() throws ValidationException {
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

    /**
     * Prompts the user to input the ID of a course to remove. It then calls the course controller
     * to remove the course from the system.
     *
     * @throws EntityNotFoundException if the course with the given ID does not exist.
     */
    public void removeCourse() throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Course ID to remove: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(courseUserController.removeCourse(courseId));
    }

    /**
     * Prompts the user to input an instructor's ID and a course ID, and assigns the instructor to the course.
     * It calls the course controller to perform the assignment.
     */
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

    /**
     * Prompts the user to input an instructor's ID and a course ID, and unassigns the instructor from the course.
     * It calls the course controller to perform the unassignment.
     */
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


    /**
     * Displays all students by retrieving them from the course controller.
     * The students are printed with their ID and username.
     */
    public void viewAllStudents() {
        List<Student> students = courseUserController.getAllStudents();
        System.out.println("Students:");
        students.forEach(student -> System.out.println(student.getId() + ": " + student.getUsername()));
    }

    /**
     * Prompts the user to input the details of a new student and adds the student to the system.
     * It collects the following details: student ID, name, password, email, and role.
     * After creating the student, it adds them to the system through the course controller.
     */
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

    /**
     * Prompts the user to input the ID of a student to remove. It then calls the course controller
     * to remove the student from the system.
     *
     * @throws EntityNotFoundException if the student with the given ID does not exist.
     */
    public void removeStudent() throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Student ID to remove: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(courseUserController.removeStudent(studentId));
    }

    /**
     * Displays all instructors by retrieving them from the course controller.
     * The instructors are printed with their ID and username.
     */
    public void viewAllInstructors() {
        List<Instructor> instructors = courseUserController.getAllInstructors();
        System.out.println("Instructors:");
        instructors.forEach(instructor -> System.out.println(instructor.getId() + ": " + instructor.getUsername()));
    }

    /**
     * Prompts the user to input the details of a new instructor and adds the instructor to the system.
     * It collects the following details: instructor ID, name, password, email, and role.
     * After creating the instructor, it adds them to the system through the course controller.
     */
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

    /**
     * Prompts the user to input the ID of an instructor to remove. It then calls the course controller
     * to remove the instructor from the system.
     *
     * @throws EntityNotFoundException if the instructor with the given ID does not exist.
     */
    public void removeInstructor() throws EntityNotFoundException, BusinessException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Instructor ID to remove: ");
        int instructorId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(courseUserController.removeInstructor(instructorId));
    }


    /**
     * Displays all modules for a given course by retrieving them from the assignment controller.
     * The modules are printed with their ID and title.
     */
    public void viewModulesFromCourse() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Course ID: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();
        List<Module> modules = assignmentController.getModulesFromCourse(courseId);
        System.out.println("Modules from course with ID " + courseId + ":");
        modules.forEach(module -> System.out.println(module.getId() + ": " + module.getModuleTitle()));
    }

    /**
     * Prompts the user to input the IDs of a course and module, and adds the module to the specified course.
     * It calls the assignment controller to add the module to the course.
     */
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


    /**
     * Prompts the user to input the IDs of a course and module, and removes the module from the specified course.
     * It calls the assignment controller to remove the module from the course.
     */
    public void removeModuleFromCourse() throws EntityNotFoundException{
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Course ID: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Module ID to remove: ");
        int moduleId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(assignmentController.removeModuleFromCourse(courseId, moduleId));
    }

    /**
     * Displays all assignments for a given module by retrieving them from the assignment controller.
     * The assignments are printed with their ID and description.
     */
    public void viewAssignmentsFromModule() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Module ID: ");
        int moduleId = scanner.nextInt();
        scanner.nextLine();
        List<Assignment> assignments = assignmentController.getAssignmentsFromModule(moduleId);
        System.out.println("Assignments from module with ID " + moduleId + ":");
        assignments.forEach(assignment -> System.out.println(assignment.getId() + ": " + assignment.getDescription()));
    }

    /**
     * Prompts the user to input the IDs of a module and assignment, and adds the assignment to the specified module.
     * It calls the assignment controller to add the assignment to the module.
     */
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


    /**
     * Prompts the user to input the IDs of a module and assignment, and removes the assignment from the specified module.
     * It calls the assignment controller to remove the assignment from the module.
     */
    public void removeAssignmentFromModule() throws EntityNotFoundException{

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Module ID: ");
        int moduleId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Assignment ID to remove: ");
        int assignmentId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(assignmentController.removeAssignmentFromModule(moduleId, assignmentId));
    }

    /**
     * Displays all quizzes for a given assignment by retrieving them from the assignment controller.
     * The quizzes are printed with their ID and title.
     */
    public void viewQuizFromAssignment() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Assignment ID: ");
        int assignmentId = scanner.nextInt();
        scanner.nextLine();
        List<Quiz> quizzes = assignmentController.getQuizFromAssignment(assignmentId);
        System.out.println("Quizzes from assignment with ID " + assignmentId + ":");
        quizzes.forEach(quiz -> System.out.println(quiz.getId() + ": " + quiz.getTitle()));
    }

    /**
     * Prompts the user to input the IDs of an assignment and quiz, and adds the quiz to the specified assignment.
     * It calls the assignment controller to add the quiz to the assignment.
     */
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



    /**
     * Prompts the user to input the IDs of an assignment and quiz, and removes the quiz from the specified assignment.
     * It calls the assignment controller to remove the quiz from the assignment.
     */
    public void removeQuizFromAssignment() throws EntityNotFoundException{

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Assignment ID: ");
        int assignmentId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Quiz ID to remove: ");
        int quizId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(assignmentController.removeQuizFromAssignment(assignmentId, quizId));
    }

    /**
     * Prompts the user to input their ID and the course ID they wish to enroll in.
     * It then calls the course controller to enroll the student in the specified course.
     */
    public void enroll() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter you're ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the course id you want to enroll in: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();
        courseUserController.enrollStudentInCourse(userId, courseId);
    }

    /**
     * Prompts the user to input their ID and the course ID they wish to unenroll from.
     * It then calls the course controller to unenroll the student from the specified course.
     *
     * @throws EntityNotFoundException if the student or course does not exist.
     */
    public void unenroll() throws EntityNotFoundException, BusinessException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter you're ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the course id you want to unenroll from: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();
        courseUserController.unenrollStudentFromCourse(userId, courseId);
    }

    /**
     * Prompts the user to input their ID and retrieves all courses the student is currently enrolled in.
     * It then displays the list of courses the student is enrolled in.
     *
     * @throws EntityNotFoundException if the student does not exist.
     */
    public void viewCoursesAStudentIsEnrolledIn() throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter you're ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(courseUserController.getCoursesByStudent(userId));
    }

    /**
     * Prompts the user to input a course ID and retrieves the course's information.
     * It then displays the course details.
     *
     * @throws EntityNotFoundException if the course with the given ID does not exist.
     */
    public void viewCourseInfo() throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the course id you want to view information about: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(courseUserController.getCourseInfo(courseId));
    }

    /**
     * Prompts the user to input an instructor ID and retrieves the instructor's information.
     * It then displays the instructor details.
     *
     * @throws EntityNotFoundException if the instructor with the given ID does not exist.
     */
    public void viewInstructorInfo() throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the instructor id you want to view information about: ");
        int instructorId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(courseUserController.getInstructorInfo(instructorId));
    }

    /**
     * Prompts the user to input a date and retrieves all courses that end before the given date.
     * The courses are displayed with their details.
     */
    public void previewCoursesThatEndBeforeADate() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the date to see the courses that end before the given date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.println(courseUserController.getAllCoursesThatEndBeforeADate(date));
    }

    /**
     * Retrieves and displays a list of instructors sorted by the number of courses they teach.
     * The instructors are printed with their details.
     */
    public void sortInstructorsByNumberOfCourses() {
        List<Instructor> instructors = courseUserController.sortAllInstructorsByNumberOfCourses();
        for (Instructor instructor : instructors) {
            System.out.println(instructor);
        }
    }

    /**
     * Retrieves and displays a list of instructors sorted by total enrollment in their courses.
     * The instructors are printed with their details.
     */
    public void sortInstructorsByEnrollment() {
        List<Instructor> instructors = courseUserController.getInstructorsByTotalEnrollment();
        for (Instructor instructor : instructors) {
            System.out.println(instructor);
        }
    }

    /**
     * Prompts the user to input an assignment ID and allows them to take the quiz associated with the assignment.
     * It calls the assignment controller to handle the quiz-taking process.
     */
    public void takeAssignmentQuiz() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Assignment ID: ");
        int assignmentId = scanner.nextInt();
        scanner.nextLine();
        assignmentController.takeAssignmentQuiz(assignmentId);
    }

    /**
     * Retrieves and displays a list of under-occupied courses.
     * These are courses that have fewer students than a certain threshold.
     */
    public void getUnderOcupiedCourses() {
        List<Course> courses = courseUserController.getAllUnderOccupiedCourses();
        for (Course course : courses) {
            System.out.println(course);
        }
    }


}
