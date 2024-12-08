package Service;

import Models.Assignment;
import Models.Course;
import Models.Module;
import Models.Quiz;
import Repository.IRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AssignmentService {
    private final IRepository<Quiz> quizRepo;
    private final IRepository<Assignment> assignmentRepo;
    private final IRepository<Module> moduleRepo;
    private final IRepository<Course> courseRepo;

    public AssignmentService(IRepository<Quiz> quizRepo, IRepository<Assignment> assignmentRepo, IRepository<Module> moduleRepo, IRepository<Course> courseRepo) {
        this.quizRepo = quizRepo;
        this.assignmentRepo = assignmentRepo;
        this.moduleRepo = moduleRepo;
        this.courseRepo = courseRepo;
    }

    /**
     * Adds an module to a specific course.
     * @param courseId ID of the course.
     * @param module The module to add.
     */
    public void addModuleToCourse(Integer courseId, Module module){
        Course course = courseRepo.get(courseId);
        if(course != null){

            if(moduleRepo.get(module.getId()) == null)
                moduleRepo.create(module);
            if (course.getModules().contains(module.getId()))
                throw new IllegalArgumentException("Module with id " + module.getId() + " is already in the course.");
            else {
                course.getModules().add(module.getId());
                courseRepo.update(course);
            }
        }else
            throw new IllegalArgumentException("Course with id " + courseId + " does not exist");
    }

    /**
     * Adds an assignment to a specific module.
     * @param moduleId ID of the module.
     * @param assignment The assignment to add.
     */
    public void addAssignmentToModule(Integer moduleId, Assignment assignment){
        Module module = moduleRepo.get(moduleId);
        if(module != null){

            if(assignmentRepo.get(assignment.getId()) == null)
                assignmentRepo.create(assignment);
            if (module.getAssignments().contains(assignment.getId()))
                throw new IllegalArgumentException("Assignment with id " + assignment.getId() + " is already in the module.");
            else {
                module.getAssignments().add(assignment.getId());
                moduleRepo.update(module);
            }
        }else {
            throw new IllegalArgumentException("Module with id " + moduleId + " does not exist");
        }
    }

    /**
     * Adds a quiz to a specific assignment.
     * @param assignmentId ID of the assignment.
     * @param quiz The quiz to add.
     */
    public void addQuizToAssignment(Integer assignmentId, Quiz quiz){
        Assignment assignment = assignmentRepo.get(assignmentId);
        if(assignment != null){

            if(quizRepo.get(quiz.getId()) == null)
                quizRepo.create(quiz);
            if (assignment.getQuizzes().contains(quiz.getId()))
                throw new IllegalArgumentException("Quiz with id " + quiz.getId() + " is already in the assignment.");
            else {
                assignment.getQuizzes().add(quiz.getId());
                assignmentRepo.update(assignment);
            }
        }else
            throw new IllegalArgumentException("Assignment with id " + assignmentId + " does not exist");
    }

    /**
     * Removes a module from a specific course.
     * @param courseId ID of the course.
     * @param moduleId The id of the module to remove.
     */
    public void removeModuleFromCourse(Integer courseId, Integer moduleId){
        Course course =courseRepo.get(courseId);
        Module moduleToRemove = moduleRepo.get(moduleId);
        if(course != null && moduleToRemove != null){
            course.getModules().remove(moduleToRemove.getId());
            courseRepo.update(course);
        }else if (course == null && moduleToRemove != null) {
            throw new IllegalArgumentException("Course with id " + courseId + " does not exist");
        } else
            throw new IllegalArgumentException("Module with id " + moduleId + " does not exist");
    }

    /**
     * Removes a assignment from a specific module.
     * @param moduleId ID of the module.
     * @param assignmentId The id of the assignment to remove.
     */
    public void removeAssignmentFromModule(Integer moduleId, Integer assignmentId){
        Module module =moduleRepo.get(moduleId);
        Assignment assignmentToRemove = assignmentRepo.get(assignmentId);
        if(module != null && assignmentToRemove != null){
            module.getAssignments().remove(assignmentToRemove.getId());
            moduleRepo.update(module);
        }else if (module == null &&  assignmentToRemove != null) {
            throw new IllegalArgumentException("Module with id " + moduleId + " does not exist");
        } else
            throw new IllegalArgumentException("Assignment with id " + assignmentId + " does not exist");
    }

    /**
     * Removes a quiz from a specific assignment.
     * @param assignmentId ID of the assignment.
     * @param quizId The id of the quiz to remove.
     */
    public void removeQuizFromAssignment(Integer assignmentId, Integer quizId){
        Assignment assignment = assignmentRepo.get(assignmentId);
        Quiz quizToRemove = quizRepo.get(quizId);
        if(assignment != null && quizToRemove != null){
            assignment.getQuizzes().remove(quizToRemove.getId());
            assignmentRepo.update(assignment);
        }else if (assignment == null &&  quizToRemove != null) {
            throw new IllegalArgumentException("Assignment with id " + assignmentId + " does not exist");
        } else
            throw new IllegalArgumentException("Quiz with id " + quizId + " does not exist");
    }

    /**
     * This method allows a student to take a quiz associated with an assignment.
     * The user is prompted with each quiz question, and they are asked to provide an answer.
     * The method compares the user's input to the correct answer and updates the score accordingly.
     *
     * @param assignmentId The ID of the assignment that the student is taking the quiz for.
     *                     The assignment should contain quizzes that the student will answer.
     */
    public void takeAssignmentQuiz(Integer assignmentId){
        // Fetch the assignment object from the repository using its ID
        Assignment assignment = assignmentRepo.get(assignmentId);

        // Create a Scanner object to capture user input from the console
        Scanner scanner = new Scanner(System.in);

        // Iterate over each quiz in the assignment
        for(Integer quizId : assignment.getQuizzes()){

            // Display the quiz question (contents) to the user
            Quiz quiz = quizRepo.get(quizId);
            System.out.println(quiz.getContents());

            // Prompt the user for their answer to the quiz
            System.out.println("Your answer:");
            int answer = scanner.nextInt();  // Capture the user's answer

            // Check if the user's answer is correct
            if(answer == quiz.getCorrectAnswer()){
                // If correct, inform the user and increment the score
                System.out.println("Correct!\n");
                assignment.setScore(assignment.getScore() + 1);  // Update score
                return;  // Exit after one correct answer (can be changed based on behavior)
            }else{
                // If wrong, inform the user and reveal the correct answer
                System.out.println("Wrong answer! The answer was " + quiz.getCorrectAnswer() + "\n");
                return;  // Exit after one wrong answer (can be changed based on behavior)
            }

        }

        // Print the user's total score at the end of the quiz
        System.out.println("You scored " + assignment.getScore());

        // Close the scanner object after use (good practice)
        scanner.close();
    }

    /**
     * Retrieves all modules associated with a specific course.
     * This is useful for accessing and displaying the list of modules for a given course.
     *
     * @param courseId The ID of the course whose modules are to be fetched.
     * @return A list of modules associated with the specified course.
     */
    public List<Module> getModulesFromCourse(Integer courseId){
        // Get the course object by its ID
        Course course = courseRepo.get(courseId);

        // Return the list of modules associated with the course
        List<Integer> moduleIds = course.getModules();
        List<Module> modules = new ArrayList<>();
        for (Integer moduleId : moduleIds) {
            Module module = moduleRepo.get(moduleId);
            modules.add(module);
        }
        return modules;
    }

    /**
     * Retrieves all assignments associated with a specific module.
     * This method is useful for displaying assignments to users within a particular module.
     *
     * @param moduleId The ID of the module whose assignments are to be fetched.
     * @return A list of assignments associated with the specified module.
     */
    public List<Assignment> getAssignmentsFromModule(Integer moduleId){
        // Get the module object by its ID
        Module module = moduleRepo.get(moduleId);

        // Return the list of assignments associated with the module
        List<Integer> assignmentIds = module.getAssignments();
        List<Assignment> assignments = new ArrayList<>();
        for (Integer assignmentId : assignmentIds) {
            Assignment assignment = assignmentRepo.get(assignmentId);
            assignments.add(assignment);
        }
        return assignments;
    }

    /**
     * Retrieves all quizzes associated with a specific assignment.
     * This method provides access to all quizzes linked to a particular assignment.
     *
     * @param assignmentId The ID of the assignment whose quizzes are to be fetched.
     * @return A list of quizzes associated with the specified assignment.
     */
    public List<Quiz> getQuizFromAssignment(Integer assignmentId){
        // Get the assignment object by its ID
        Assignment assignment = assignmentRepo.get(assignmentId);

        // Return the list of quizzes associated with the assignment
        List<Integer> quizIds = assignment.getQuizzes();
        List<Quiz> quizzes = new ArrayList<>();
        for (Integer quizId : quizIds) {
            Quiz quiz = quizRepo.get(quizId);
            quizzes.add(quiz);
        }
        return quizzes;
    }

}
