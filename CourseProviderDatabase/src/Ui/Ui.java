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
        courseUserController.addStudent(new Student(1, "maia", "maiapass", "maia@gmail.com", "student"));
        courseUserController.addStudent(new Student(2, "johnDoe", "jd123pass", "john.doe@yahoo.com", "student"));
        courseUserController.addStudent(new Student(3, "mikeSmith", "pass789", "mike.smith@example.com", "student"));
        courseUserController.addStudent(new Student(4, "sarahJones", "sarahJ2023", "sarah.jones@outlook.com", "student"));
        courseUserController.addStudent(new Student(5, "alexWong", "wongAlex22", "alex.wong@gmail.com", "student"));
        courseUserController.addStudent(new Student(6, "emilyBrown", "brownEmily1", "emily.brown@hotmail.com", "student"));
        courseUserController.addStudent(new Student(7, "davidLee", "lee123David", "david.lee@example.com", "student"));
        courseUserController.addStudent(new Student(8, "lisaChen", "chenLisa99", "lisa.chen@yahoo.com", "student"));
        courseUserController.addStudent(new Student(9, "ryanTaylor", "taylorRyan21", "ryan.taylor@outlook.com", "student"));
        courseUserController.addStudent(new Student(10, "oliviaGarcia", "garcia2023", "olivia.garcia@gmail.com", "student"));


        // Adding Instructors
        courseUserController.addInstructor(new Instructor(1, "profMiller", "teach123", "miller@example.com", "instructor"));
        courseUserController.addInstructor(new Instructor(2, "drJohnson", "jPhD2023", "johnson@university.edu", "instructor"));
        courseUserController.addInstructor(new Instructor(3, "profWilliams", "willTeach22", "williams@college.edu", "instructor"));
        courseUserController.addInstructor(new Instructor(4, "drBrown", "brownLectures", "brown@institute.edu", "instructor"));
        courseUserController.addInstructor(new Instructor(5, "profDavis", "davisClass101", "davis@academy.com", "instructor"));
        courseUserController.addInstructor(new Instructor(6, "drWilson", "wilsonLab2023", "wilson@school.edu", "instructor"));
        courseUserController.addInstructor(new Instructor(7, "profTaylor", "taylorTeach", "taylor@university.com", "instructor"));
        courseUserController.addInstructor(new Instructor(8, "drAnderson", "andersonLectures", "anderson@college.edu", "instructor"));
        courseUserController.addInstructor(new Instructor(9, "profMoore", "mooreMath2023", "moore@institute.com", "instructor"));
        courseUserController.addInstructor(new Instructor(10, "drLee", "leeScience101", "lee@academy.edu", "instructor"));

        // Adding Admins

        courseUserController.addAdmin(new Admin(1, "adminUser", "adminPass", "admin@example.com", "admin"));
        courseUserController.addAdmin(new Admin(2, "sysAdmin", "sysPass123", "sysadmin@university.edu", "admin"));
        courseUserController.addAdmin(new Admin(3, "techSupport", "techPass456", "techsupport@college.edu", "admin"));
        courseUserController.addAdmin(new Admin(4, "itManager", "itPass789", "itmanager@institute.edu", "admin"));
        courseUserController.addAdmin(new Admin(5, "securityAdmin", "securePass", "security@academy.com", "admin"));
        courseUserController.addAdmin(new Admin(6, "dataAdmin", "dataPass321", "dataadmin@school.edu", "admin"));
        courseUserController.addAdmin(new Admin(7, "networkAdmin", "netPass654", "network@university.com", "admin"));
        courseUserController.addAdmin(new Admin(8, "supportAdmin", "suppPass987", "support@college.edu", "admin"));
        courseUserController.addAdmin(new Admin(9, "maintenanceAdmin", "maintPass", "maintenance@institute.com", "admin"));
        courseUserController.addAdmin(new Admin(10, "systemManager", "sysManPass", "sysman@academy.edu", "admin"));


        // Adding Courses

        courseUserController.addCourse(new Course(101, "Java Programming", "Learn Java basics and object-oriented programming", 5, "2025-01-15", "2025-05-15", 1));
        courseUserController.addCourse(new Course(102, "Web Development", "Introduction to HTML, CSS, and JavaScript", 25, "2025-02-01", "2025-06-01", 1));
        courseUserController.addCourse(new Course(103, "Database Management", "SQL and relational database design", 20, "2025-01-20", "2025-05-20", 1));
        courseUserController.addCourse(new Course(104, "Data Structures", "Fundamental data structures and algorithms", 35, "2025-03-01", "2025-07-01", 1));
        courseUserController.addCourse(new Course(105, "Mobile App Development", "Building apps for iOS and Android", 28, "2025-02-15", "2025-06-15", 2));
        courseUserController.addCourse(new Course(106, "Artificial Intelligence", "Introduction to AI concepts and applications", 22, "2025-03-15", "2025-07-15", 3));
        courseUserController.addCourse(new Course(107, "Network Security", "Fundamentals of cybersecurity and network protection", 18, "2025-04-01", "2025-08-01", 3));
        courseUserController.addCourse(new Course(108, "Cloud Computing", "Introduction to cloud services and architectures", 26, "2025-02-10", "2025-06-10", 5));
        courseUserController.addCourse(new Course(109, "Software Engineering", "Software development lifecycle and best practices", 32, "2025-01-25", "2025-05-25", 6));
        courseUserController.addCourse(new Course(110, "Machine Learning", "Fundamentals of ML algorithms and applications", 24, "2025-03-10", "2025-07-10", 7));
        courseUserController.addCourse(new Course(111, "Computer Graphics", "2D and 3D graphics programming", 20, "2025-04-15", "2025-08-15", 8));
        courseUserController.addCourse(new Course(112, "Operating Systems", "OS concepts and system programming", 28, "2025-02-20", "2025-06-20", 9));
        courseUserController.addCourse(new Course(113, "Blockchain Technology", "Introduction to blockchain and cryptocurrencies", 22, "2025-05-01", "2025-09-01", 10));
        courseUserController.addCourse(new Course(114, "Big Data Analytics", "Processing and analyzing large datasets", 25, "2025-03-20", "2025-07-20", 5));
        courseUserController.addCourse(new Course(115, "Internet of Things", "Connecting and programming IoT devices", 30, "2025-04-10", "2025-08-10", 8));


        //enroll students
        courseUserController.enrollStudentInCourse(1,101);
        courseUserController.enrollStudentInCourse(2,101);
        courseUserController.enrollStudentInCourse(3,101);
        courseUserController.enrollStudentInCourse(4,101);
        courseUserController.enrollStudentInCourse(5,101);
        courseUserController.enrollStudentInCourse(6,102);
        courseUserController.enrollStudentInCourse(7,102);
        courseUserController.enrollStudentInCourse(8,102);
        courseUserController.enrollStudentInCourse(9,102);
        courseUserController.enrollStudentInCourse(10,102);
        courseUserController.enrollStudentInCourse(1,103);
        courseUserController.enrollStudentInCourse(2,103);
        courseUserController.enrollStudentInCourse(3,103);
        courseUserController.enrollStudentInCourse(4,104);
        courseUserController.enrollStudentInCourse(5,104);
        courseUserController.enrollStudentInCourse(6,104);
        courseUserController.enrollStudentInCourse(7,104);
        courseUserController.enrollStudentInCourse(8,104);
        courseUserController.enrollStudentInCourse(9,105);
        courseUserController.enrollStudentInCourse(10,105);
        courseUserController.enrollStudentInCourse(1,106);
        courseUserController.enrollStudentInCourse(6,106);
        courseUserController.enrollStudentInCourse(7,107);
        courseUserController.enrollStudentInCourse(8,107);
        courseUserController.enrollStudentInCourse(9,107);
        courseUserController.enrollStudentInCourse(10,107);
        courseUserController.enrollStudentInCourse(5,108);
        courseUserController.enrollStudentInCourse(6,108);
        courseUserController.enrollStudentInCourse(7,109);
        courseUserController.enrollStudentInCourse(8,109);
        courseUserController.enrollStudentInCourse(9,109);
        courseUserController.enrollStudentInCourse(10,109);
        courseUserController.enrollStudentInCourse(5,110);
        courseUserController.enrollStudentInCourse(3,110);



        // Adding Modules
        assignmentController.addModule(new Module(201, "Introduction to Programming", "Fundamental concepts and basic syntax"));
        assignmentController.addModule(new Module(202, "Object-Oriented Programming", "Classes, objects, inheritance, and polymorphism"));
        assignmentController.addModule(new Module(203, "Data Structures and Algorithms", "Common data structures and algorithm design"));
        assignmentController.addModule(new Module(204, "Web Development Fundamentals", "HTML, CSS, and JavaScript basics"));
        assignmentController.addModule(new Module(205, "Database Management Systems", "Relational databases, SQL, and data modeling"));
        assignmentController.addModule(new Module(206, "Network Fundamentals", "OSI model, TCP/IP, and basic networking concepts"));
        assignmentController.addModule(new Module(207, "Operating Systems", "Process management, memory allocation, and file systems"));
        assignmentController.addModule(new Module(208, "Software Engineering Principles", "SDLC, Agile methodologies, and version control"));
        assignmentController.addModule(new Module(209, "Artificial Intelligence and Machine Learning", "Basic AI concepts and ML algorithms"));
        assignmentController.addModule(new Module(210, "Cybersecurity Fundamentals", "Security principles, cryptography, and ethical hacking"));

        //Adding modules to courses
        assignmentController.addModuleToCourse(101, 201);
        assignmentController.addModuleToCourse(101, 202);
        assignmentController.addModuleToCourse(102, 204);
        assignmentController.addModuleToCourse(103, 205);
        assignmentController.addModuleToCourse(104, 203);
        assignmentController.addModuleToCourse(105, 201);
        assignmentController.addModuleToCourse(106, 209);
        assignmentController.addModuleToCourse(107, 206);
        assignmentController.addModuleToCourse(108, 206);
        assignmentController.addModuleToCourse(110, 209);
        assignmentController.addModuleToCourse(111, 202);
        assignmentController.addModuleToCourse(112, 207);
        assignmentController.addModuleToCourse(113, 210);
        assignmentController.addModuleToCourse(114, 205);
        assignmentController.addModuleToCourse(105, 206);

        // Adding Assignments
        assignmentController.addAssignment(new Assignment(301, "Basic Programming Concepts Quiz", "2025-02-15", 0));
        assignmentController.addAssignment(new Assignment(302, "Object-Oriented Design Project", "2025-03-01", 0));
        assignmentController.addAssignment(new Assignment(303, "Data Structures Implementation", "2025-03-15", 0));
        assignmentController.addAssignment(new Assignment(304, "Personal Portfolio Website", "2025-04-01", 0));
        assignmentController.addAssignment(new Assignment(305, "Database Design and SQL Queries", "2025-04-15", 0));
        assignmentController.addAssignment(new Assignment(306, "Network Protocol Analysis", "2025-05-01", 0));
        assignmentController.addAssignment(new Assignment(307, "Operating System Simulation", "2025-05-15", 0));
        assignmentController.addAssignment(new Assignment(308, "Agile Development Group Project", "2025-06-01", 0));
        assignmentController.addAssignment(new Assignment(309, "Machine Learning Algorithm Implementation", "2025-06-15", 00));
        assignmentController.addAssignment(new Assignment(310, "Cybersecurity Vulnerability Assessment", "2025-07-01", 0));


        //Adding assignments to modules
        assignmentController.addAssignmentToModule(201, 301);
        assignmentController.addAssignmentToModule(202, 302);
        assignmentController.addAssignmentToModule(203, 303);
        assignmentController.addAssignmentToModule(204, 304);
        assignmentController.addAssignmentToModule(205, 305);
        assignmentController.addAssignmentToModule(206, 306);
        assignmentController.addAssignmentToModule(207, 307);
        assignmentController.addAssignmentToModule(208, 308);
        assignmentController.addAssignmentToModule(209, 309);
        assignmentController.addAssignmentToModule(210, 310);

        //Questions assignment 1
        String q1 = "What is the correct syntax for the main method in Java?\n1. public static void main(String[] args)\n2. public void main(String[] args)\n3. static void main(String args[])\n4. public static void main()";
        String q2 = "What is the size of an int in Java?\n1. 4 bytes\n2. 8 bytes\n3. 2 bytes\n4. 16 bytes";
        String q3 = "Which keyword is used to inherit a class in Java?\n1. implements\n2. extends\n3. inherits\n4. override";
        String q4 = "Which of these is a valid for-loop in Java?\n1. for(int i = 0; i < 5; i++)\n2. for(i=0, i<5; i+)\n3. foreach(int i : 5)\n4. loop(i=0 to 5)";
        String q5 = "Which of these is part of the Java Collections Framework?\n1. Array\n2. HashMap\n3. Enumeration\n4. Vector";

        //questions assignment 2
        String q6 = "What is encapsulation?\n1. Hiding implementation details\n2. Creating multiple instances of a class\n3. Inheriting from a superclass\n4. Overriding methods";
        String q7 = "Which keyword is used to prevent a class from being inherited?\n1. static\n2. final\n3. private\n4. abstract";
        String q8 = "What is polymorphism?\n1. Having multiple constructors\n2. Ability of an object to take many forms\n3. Hiding data within a class\n4. Creating multiple instances of a class";
        String q9 = "Which of these is not a pillar of OOP?\n1. Encapsulation\n2. Inheritance\n3. Polymorphism\n4. Compilation";
        String q10 = "What is the purpose of an interface?\n1. To create objects\n2. To define a contract for classes to implement\n3. To provide a default implementation\n4. To store data";

        //questions for assignment 3
        String q11 = "Which data structure uses LIFO (Last In First Out)?\n1. Queue\n2. Stack\n3. Linked List\n4. Tree";
        String q12 = "What is the time complexity of searching in a balanced binary search tree?\n1. O(1)\n2. O(n)\n3. O(log n)\n4. O(n^2)";
        String q13 = "Which of these is not a linear data structure?\n1. Array\n2. Linked List\n3. Stack\n4. Tree";
        String q14 = "What is the main advantage of a hash table?\n1. Ordered elements\n2. Fast average-case access time\n3. Memory efficiency\n4. Easy to implement";
        String q15 = "Which sorting algorithm has the best average-case time complexity?\n1. Bubble Sort\n2. Insertion Sort\n3. Quick Sort\n4. Selection Sort";

        //questions for assignment 4
        String q16 = "Which tag is used to define the main content of an HTML document?\n1. <main>\n2. <body>\n3. <content>\n4. <div>";
        String q17 = "What does CSS stand for?\n1. Computer Style Sheets\n2. Creative Style Sheets\n3. Cascading Style Sheets\n4. Colorful Style Sheets";
        String q18 = "Which JavaScript method is used to select an HTML element by its id?\n1. getElementById()\n2. querySelector()\n3. getElementByName()\n4. selectElement()";
        String q19 = "What is the purpose of the 'viewport' meta tag?\n1. To set the page background\n2. To define the character encoding\n3. To control layout on mobile browsers\n4. To import external stylesheets";
        String q20 = "Which of these is not a valid way to declare a variable in JavaScript?\n1. var\n2. let\n3. const\n4. variable";

        //questions for assignment 5
        String q21 = "What does SQL stand for?\n1. Structured Query Language\n2. Simple Question Language\n3. Structured Question Language\n4. Simple Query Language";
        String q22 = "Which SQL command is used to retrieve data from a database?\n1. GET\n2. EXTRACT\n3. SELECT\n4. FETCH";
        String q23 = "What is a primary key?\n1. The first column in a table\n2. A unique identifier for a record in a table\n3. A foreign key in another table\n4. The most important data in a table";
        String q24 = "Which of these is not a type of SQL join?\n1. INNER JOIN\n2. LEFT JOIN\n3. RIGHT JOIN\n4. MIDDLE JOIN";
        String q25 = "What is the purpose of the GROUP BY clause?\n1. To sort the result set\n2. To filter rows before grouping\n3. To group rows that have the same values\n4. To join multiple tables";

        //questions for assignment 6
        String q26 = "Which layer of the OSI model is responsible for routing?\n1. Physical Layer\n2. Data Link Layer\n3. Network Layer\n4. Transport Layer";
        String q27 = "What protocol is used for secure communication over a computer network?\n1. HTTP\n2. FTP\n3. HTTPS\n4. SMTP";
        String q28 = "Which of these is not a valid IP address?\n1. 192.168.0.1\n2. 256.0.0.1\n3. 10.0.0.1\n4. 172.16.0.1";
        String q29 = "What is the purpose of DNS?\n1. To assign IP addresses\n2. To encrypt data\n3. To translate domain names to IP addresses\n4. To route network traffic";
        String q30 = "Which protocol is connectionless?\n1. TCP\n2. UDP\n3. HTTP\n4. FTP";

        //questions for assignment 7
        String q31 = "What is a process in operating systems?\n1. A program in execution\n2. A type of file system\n3. A network protocol\n4. A hardware component";
        String q32 = "Which scheduling algorithm gives the shortest job the highest priority?\n1. First-Come, First-Served\n2. Round Robin\n3. Shortest Job First\n4. Priority Scheduling";
        String q33 = "What is virtual memory?\n1. A type of cache memory\n2. An illusion of more memory than physically available\n3. A faster type of RAM\n4. A backup of the hard drive";
        String q34 = "Which of these is not a common page replacement algorithm?\n1. FIFO\n2. LRU\n3. Optimal\n4. LIFO";
        String q35 = "What is the purpose of a semaphore in operating systems?\n1. To encrypt files\n2. To manage shared resources\n3. To allocate CPU time\n4. To defragment memory";

        //questions for assignment 8
        String q36 = "What is a sprint in Agile methodology?\n1. A bug in the code\n2. A fixed time-boxed iteration\n3. A type of meeting\n4. A coding competition";
        String q37 = "Which of these is not an Agile framework?\n1. Scrum\n2. Kanban\n3. Waterfall\n4. Extreme Programming (XP)";
        String q38 = "What is the purpose of a daily stand-up meeting?\n1. To plan the entire project\n2. To review completed work\n3. To synchronize activities and create a plan for the next 24 hours\n4. To assign tasks to team members";
        String q39 = "What does MVP stand for in Agile?\n1. Most Valuable Player\n2. Minimum Viable Product\n3. Maximum Value Proposition\n4. Multiple Version Program";
        String q40 = "Which role in Scrum is responsible for removing impediments?\n1. Product Owner\n2. Scrum Master\n3. Team Member\n4. Stakeholder";

        //questions for assignment 9
        String q41 = "Which of these is not a type of machine learning?\n1. Supervised Learning\n2. Unsupervised Learning\n3. Reinforcement Learning\n4. Prescriptive Learning";
        String q42 = "What is the purpose of the training set in machine learning?\n1. To evaluate the model's performance\n2. To make predictions on new data\n3. To teach the model patterns in the data\n4. To optimize the learning rate";
        String q43 = "Which algorithm is commonly used for classification problems?\n1. Linear Regression\n2. K-Means Clustering\n3. Decision Trees\n4. Principal Component Analysis";
        String q44 = "What does overfitting mean in machine learning?\n1. The model performs well on training data but poorly on new data\n2. The model is too simple to capture the underlying patterns\n3. The model takes too long to train\n4. The model requires too much memory";
        String q45 = "Which of these is not a common activation function in neural networks?\n1. ReLU\n2. Sigmoid\n3. Tanh\n4. Cosine";

        //questions for assignment 10
        String q46 = "What is a SQL injection attack?\n1. Inserting malicious SQL code into application queries\n2. Overloading a database with queries\n3. Encrypting database contents\n4. Deleting SQL databases";
        String q47 = "Which of these is not a common type of malware?\n1. Virus\n2. Trojan\n3. Worm\n4. Firewall";
        String q48 = "What does HTTPS provide?\n1. Faster internet connection\n2. Ad blocking\n3. Encrypted communication\n4. Anonymous browsing";
        String q49 = "What is the purpose of a firewall?\n1. To speed up internet connection\n2. To filter network traffic based on security rules\n3. To store sensitive data\n4. To generate encryption keys";
        String q50 = "Which of these is a best practice for password security?\n1. Using the same password for multiple accounts\n2. Sharing passwords with trusted colleagues\n3. Using multi-factor authentication\n4. Changing passwords every day";




        //adding quizes
        assignmentController.addQuiz(new Quiz(401, "Basic Programming Concepts Quiz 1", q1, 1));
        assignmentController.addQuiz(new Quiz(402, "Basic Programming Concepts Quiz 2", q2, 1));
        assignmentController.addQuiz(new Quiz(403, "Basic Programming Concepts Quiz 3", q3, 2));
        assignmentController.addQuiz(new Quiz(404, "Basic Programming Concepts Quiz 4", q4, 1));
        assignmentController.addQuiz(new Quiz(405, "Basic Programming Concepts Quiz 5", q5, 2));


        assignmentController.addQuiz(new Quiz(406, "Object-Oriented Design Quiz 1", q6, 1));
        assignmentController.addQuiz(new Quiz(407, "Object-Oriented Design Quiz 2", q7, 2));
        assignmentController.addQuiz(new Quiz(408, "Object-Oriented Design Quiz 3", q8, 2));
        assignmentController.addQuiz(new Quiz(409, "Object-Oriented Design Quiz 4", q9, 4));
        assignmentController.addQuiz(new Quiz(410, "Object-Oriented Design Quiz 5", q10, 2));


        assignmentController.addQuiz(new Quiz(411, "Data Structures Quiz 1", q11, 2));
        assignmentController.addQuiz(new Quiz(412, "Data Structures Quiz 2", q12, 3));
        assignmentController.addQuiz(new Quiz(413, "Data Structures Quiz 3", q13, 4));
        assignmentController.addQuiz(new Quiz(414, "Data Structures Quiz 4", q14, 2));
        assignmentController.addQuiz(new Quiz(415, "Data Structures Quiz 5", q15, 3));


        assignmentController.addQuiz(new Quiz(416, "Web Development Quiz 1", q16, 2));
        assignmentController.addQuiz(new Quiz(417, "Web Development Quiz 2", q17, 3));
        assignmentController.addQuiz(new Quiz(418, "Web Development Quiz 3", q18, 1));
        assignmentController.addQuiz(new Quiz(419, "Web Development Quiz 4", q19, 3));
        assignmentController.addQuiz(new Quiz(420, "Web Development Quiz 5", q20, 4));

        assignmentController.addQuiz(new Quiz(421, "Database and SQL Quiz 1", q21, 1));
        assignmentController.addQuiz(new Quiz(422, "Database and SQL Quiz 2", q22, 3));
        assignmentController.addQuiz(new Quiz(423, "Database and SQL Quiz 3", q23, 2));
        assignmentController.addQuiz(new Quiz(424, "Database and SQL Quiz 4", q24, 4));
        assignmentController.addQuiz(new Quiz(425, "Database and SQL Quiz 5", q25, 3));


        assignmentController.addQuiz(new Quiz(426, "Network Protocol Quiz 1", q26, 3));
        assignmentController.addQuiz(new Quiz(427, "Network Protocol Quiz 2", q27, 3));
        assignmentController.addQuiz(new Quiz(428, "Network Protocol Quiz 3", q28, 2));
        assignmentController.addQuiz(new Quiz(429, "Network Protocol Quiz 4", q29, 3));
        assignmentController.addQuiz(new Quiz(430, "Network Protocol Quiz 5", q30, 2));


        assignmentController.addQuiz(new Quiz(431, "Operating Systems Quiz 1", q31, 1));
        assignmentController.addQuiz(new Quiz(432, "Operating Systems Quiz 2", q32, 3));
        assignmentController.addQuiz(new Quiz(433, "Operating Systems Quiz 3", q33, 2));
        assignmentController.addQuiz(new Quiz(434, "Operating Systems Quiz 4", q34, 4));
        assignmentController.addQuiz(new Quiz(435, "Operating Systems Quiz 5", q35, 2));


        assignmentController.addQuiz(new Quiz(436, "Agile Development Quiz 1", q36, 2));
        assignmentController.addQuiz(new Quiz(437, "Agile Development Quiz 2", q37, 3));
        assignmentController.addQuiz(new Quiz(438, "Agile Development Quiz 3", q38, 3));
        assignmentController.addQuiz(new Quiz(439, "Agile Development Quiz 4", q39, 2));
        assignmentController.addQuiz(new Quiz(440, "Agile Development Quiz 5", q40, 2));


        assignmentController.addQuiz(new Quiz(441, "Machine Learning Quiz 1", q41, 4));
        assignmentController.addQuiz(new Quiz(442, "Machine Learning Quiz 2", q42, 3));
        assignmentController.addQuiz(new Quiz(443, "Machine Learning Quiz 3", q43, 3));
        assignmentController.addQuiz(new Quiz(444, "Machine Learning Quiz 4", q44, 1));
        assignmentController.addQuiz(new Quiz(445, "Machine Learning Quiz 5", q45, 4));


        assignmentController.addQuiz(new Quiz(446, "Cybersecurity Quiz 1", q46, 1));
        assignmentController.addQuiz(new Quiz(447, "Cybersecurity Quiz 2", q47, 4));
        assignmentController.addQuiz(new Quiz(448, "Cybersecurity Quiz 3", q48, 3));
        assignmentController.addQuiz(new Quiz(449, "Cybersecurity Quiz 4", q49, 2));
        assignmentController.addQuiz(new Quiz(450, "Cybersecurity Quiz 5", q50, 3));


        //Adding Quizzes to an Assignment
        assignmentController.addQuizToAssignment(301,401);
        assignmentController.addQuizToAssignment(301,402);
        assignmentController.addQuizToAssignment(301,403);
        assignmentController.addQuizToAssignment(301,404);
        assignmentController.addQuizToAssignment(301,405);

        assignmentController.addQuizToAssignment(302,406);
        assignmentController.addQuizToAssignment(302,407);
        assignmentController.addQuizToAssignment(302,408);
        assignmentController.addQuizToAssignment(302,409);
        assignmentController.addQuizToAssignment(302,410);

        assignmentController.addQuizToAssignment(303,411);
        assignmentController.addQuizToAssignment(303,412);
        assignmentController.addQuizToAssignment(303,413);
        assignmentController.addQuizToAssignment(303,414);
        assignmentController.addQuizToAssignment(303,415);

        assignmentController.addQuizToAssignment(304,416);
        assignmentController.addQuizToAssignment(304,417);
        assignmentController.addQuizToAssignment(304,418);
        assignmentController.addQuizToAssignment(304,419);
        assignmentController.addQuizToAssignment(304,420);

        assignmentController.addQuizToAssignment(305,421);
        assignmentController.addQuizToAssignment(305,422);
        assignmentController.addQuizToAssignment(305,423);
        assignmentController.addQuizToAssignment(305,424);
        assignmentController.addQuizToAssignment(305,425);

        assignmentController.addQuizToAssignment(306,426);
        assignmentController.addQuizToAssignment(306,427);
        assignmentController.addQuizToAssignment(306,428);
        assignmentController.addQuizToAssignment(306,429);
        assignmentController.addQuizToAssignment(306,430);

        assignmentController.addQuizToAssignment(307,431);
        assignmentController.addQuizToAssignment(307,432);
        assignmentController.addQuizToAssignment(307,433);
        assignmentController.addQuizToAssignment(307,434);
        assignmentController.addQuizToAssignment(307,435);

        assignmentController.addQuizToAssignment(308,436);
        assignmentController.addQuizToAssignment(308,437);
        assignmentController.addQuizToAssignment(308,438);
        assignmentController.addQuizToAssignment(308,439);
        assignmentController.addQuizToAssignment(308,440);

        assignmentController.addQuizToAssignment(309,441);
        assignmentController.addQuizToAssignment(309,442);
        assignmentController.addQuizToAssignment(309,443);
        assignmentController.addQuizToAssignment(309,444);
        assignmentController.addQuizToAssignment(309,445);

        assignmentController.addQuizToAssignment(310,446);
        assignmentController.addQuizToAssignment(310,447);
        assignmentController.addQuizToAssignment(310,448);
        assignmentController.addQuizToAssignment(310,449);
        assignmentController.addQuizToAssignment(310,450);

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
            System.out.println("13.View my messages");
            System.out.println("14. Logout");
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
                case 13 -> viewMyMessages();
                case 14 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice. Please try again.");

            }
        } while (choice != 14);
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
            System.out.println("20. Give feedback for an assignment");
            System.out.println("21. Logout");
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
                case 20 -> giveAssignmentFeedback();
                case 21 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 21);
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
            System.out.println("35. Get all Under Ocupied Courses");
            System.out.println("36. Get all courses that end before a date(format:yyyy-MM-dd)");
            System.out.println("37. Logout");
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
                case 35 -> getUnderOcupiedCourses();
                case 36 -> previewCoursesThatEndBeforeADate();
                case 37 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 37);
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

    public void viewMyMessages() throws EntityNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter youre id");
        int userId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(courseUserController.viewMessages(userId));
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
        System.out.println("Enter student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter instructor ID: ");
        int instructorId = scanner.nextInt();
        scanner.nextLine();
        assignmentController.takeAssignmentQuiz(assignmentId, studentId, instructorId);
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

    /**
     *This method is a way for instructors to give feedback on assignments
     */
    public void giveAssignmentFeedback(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Assignment, you want to give feedback for, ID: ");
        int assignmentId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("ID of the student that completed this assignment: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Your instructor ID: ");
        int instructorId = scanner.nextInt();
        scanner.nextLine();
        courseUserController.giveAssignmentFeedback(assignmentId);
        System.out.println("Please provide your feedback: ");
        String feedback = scanner.nextLine();
        Message message = new Message(assignmentId, feedback, instructorId, studentId);
        assignmentController.createMessage(message);
    }


}
