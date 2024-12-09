package Ui;

import Models.*;
import Service.AuthenticationService;

import java.util.Scanner;

public class Ui {
    private final AuthenticationService authService = new AuthenticationService();

    public void start() {
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

    private User login() {
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

    private void studentMenu(Student student) {
        System.out.println("Student Menu:");
        System.out.println("1. View Enrolled Courses");
        System.out.println("2. View Assignments");
        System.out.println("3. Logout");

        // Implement further functionality for students
    }

    private void adminMenu(Admin admin) {
        System.out.println("Admin Menu:");
        System.out.println("1. Manage Users");
        System.out.println("2. Manage Courses");
        System.out.println("3. View Reports");
        System.out.println("4. Logout");

        // Implement further functionality for admins
    }

    private void instructorMenu(Instructor instructor) {
        System.out.println("Instructor Menu:");
        System.out.println("1. Manage Modules");
        System.out.println("2. Assign Grades");
        System.out.println("3. View Assignments");
        System.out.println("4. Logout");

        // Implement further functionality for instructors
    }
}
