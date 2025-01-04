package Controller;
import java.util.List;

import Exceptions.EntityNotFoundException;
import Models.Assignment;
import Models.Module;
import Models.Quiz;
import Service.AssignmentService;

public class AssignmentController {
    private final AssignmentService assignmentService;

    public AssignmentController(String storageMethod) {
        this.assignmentService = new AssignmentService(storageMethod);
    }

    /**
     * Adds a module to a specific course.
     * @param courseId The ID of the course.
     * @param moduleId The id of the module to be added.
     * @return Success message if added, or error message if failed.
     */
    public String addModuleToCourse(Integer courseId, Integer moduleId) {
        try {
            assignmentService.addModuleToCourse(courseId, moduleId);
            return "Module added to course successfully.";
        } catch (IllegalArgumentException e) {
            return "Failed to add module: " + e.getMessage();
        }
    }

    /**
     * Adds an assignment to a specific module.
     * @param moduleId The ID of the module.
     * @param assignmentId The id of the assignment to add.
     * @return Success message if added, or error message if failed.
     */
    public String addAssignmentToModule(Integer moduleId, Integer assignmentId) {
        try {
            assignmentService.addAssignmentToModule(moduleId, assignmentId);
            return "Assignment added to module successfully.";
        } catch (IllegalArgumentException e) {
            return "Failed to add assignment: " + e.getMessage();
        }
    }

    /**
     * Adds a quiz to a specific assignment.
     * @param assignmentId The ID of the assignment.
     * @param quizId The id of the quiz to add.
     * @return Success message if added, or error message if failed.
     */
    public String addQuizToAssignment(Integer assignmentId, Integer quizId) {
        try {
            assignmentService.addQuizToAssignment(assignmentId, quizId);
            return "Quiz added to assignment successfully.";
        } catch (IllegalArgumentException e) {
            return "Failed to add quiz: " + e.getMessage();
        }
    }

    /**
     * Removes a module from a specific course.
     * @param courseId The ID of the course.
     * @param moduleId The ID of the module to remove.
     * @return Success message if removed, or error message if failed.
     */
    public String removeModuleFromCourse(Integer courseId, Integer moduleId) throws EntityNotFoundException {
        try {
            assignmentService.removeModuleFromCourse(courseId, moduleId);
            return "Module removed from course successfully.";
        } catch (IllegalArgumentException e) {
            return "Failed to remove module: " + e.getMessage();
        }
    }

    /**
     * Removes an assignment from a specific module.
     * @param moduleId The ID of the module.
     * @param assignmentId The ID of the assignment to remove.
     * @return Success message if removed, or error message if failed.
     */
    public String removeAssignmentFromModule(Integer moduleId, Integer assignmentId) throws EntityNotFoundException {
        try {
            assignmentService.removeAssignmentFromModule(moduleId, assignmentId);
            return "Assignment removed from module successfully.";
        } catch (IllegalArgumentException e) {
            return "Failed to remove assignment: " + e.getMessage();
        }
    }

    /**
     * Removes a quiz from a specific assignment.
     * @param assignmentId The ID of the assignment.
     * @param quizId The ID of the quiz to remove.
     * @return Success message if removed, or error message if failed.
     */
    public String removeQuizFromAssignment(Integer assignmentId, Integer quizId) throws EntityNotFoundException {
        try {
            assignmentService.removeQuizFromAssignment(assignmentId, quizId);
            return "Quiz removed from assignment successfully.";
        } catch (IllegalArgumentException e) {
            return "Failed to remove quiz: " + e.getMessage();
        }
    }

    /**
     * Allows a student to take a quiz in an assignment.
     * @param assignmentId The ID of the assignment.
     * @return The score or a message indicating if the operation failed.
     */
    public String takeAssignmentQuiz(Integer assignmentId) {
        try {
            assignmentService.takeAssignmentQuiz(assignmentId);
            return "Quiz completed. Check console for your score!";
        } catch (Exception e) {
            return "Failed to take quiz: " + e.getMessage();
        }
    }

    /**
     * Retrieves all modules in a specific course.
     * @param courseId The ID of the course.
     * @return A list of modules or an empty list if not found.
     */
    public List<Module> getModulesFromCourse(Integer courseId) {
        try {
            return assignmentService.getModulesFromCourse(courseId);
        } catch (Exception e) {
            System.out.println("Failed to retrieve modules: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Retrieves all assignments in a specific module.
     * @param moduleId The ID of the module.
     * @return A list of assignments or an empty list if not found.
     */
    public List<Assignment> getAssignmentsFromModule(Integer moduleId) {
        try {
            return assignmentService.getAssignmentsFromModule(moduleId);
        } catch (Exception e) {
            System.out.println("Failed to retrieve assignments: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Retrieves all quizzes in a specific assignment.
     * @param assignmentId The ID of the assignment.
     * @return A list of quizzes or an empty list if not found.
     */
    public List<Quiz> getQuizFromAssignment(Integer assignmentId) {
        try {
            return assignmentService.getQuizFromAssignment(assignmentId);
        } catch (Exception e) {
            System.out.println("Failed to retrieve quizzes: " + e.getMessage());
            return List.of();
        }
    }
}
