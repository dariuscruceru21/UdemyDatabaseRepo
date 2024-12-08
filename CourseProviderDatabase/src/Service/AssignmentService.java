package Service;

import Models.*;
import Models.Module;
import Repository.IRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AssignmentService {
    private final IRepository<Quiz> quizRepo;
    private final IRepository<Assignment> assignmentRepo;
    private final IRepository<Module> moduleRepo;
    private final IRepository<Course> courseRepo;
    private final IRepository<ModuleCourse> moduleCourseRepo;
    private final IRepository<AssignmentModule> assignmentModuleRepo;
    private final IRepository<QuizAssignment> assignmentQuizRepo;

    public AssignmentService(IRepository<Quiz> quizRepo, IRepository<Assignment> assignmentRepo, IRepository<Module> moduleRepo, IRepository<Course> courseRepo, IRepository<ModuleCourse> moduleCourseRepo, IRepository<AssignmentModule> assignmentModuleRepo, IRepository<QuizAssignment> assignmentQuizRepo) {
        this.quizRepo = quizRepo;
        this.assignmentRepo = assignmentRepo;
        this.moduleRepo = moduleRepo;
        this.courseRepo = courseRepo;
        this.moduleCourseRepo = moduleCourseRepo;
        this.assignmentModuleRepo = assignmentModuleRepo;
        this.assignmentQuizRepo = assignmentQuizRepo;
    }

    /**
     * Adds an module to a specific course.
     * @param courseId ID of the course.
     * @param moduleId The module to add.
     */
    public void addModuleToCourse(Integer moduleId,Integer courseId) {
        Course course = courseRepo.get(courseId);
        if(course == null)
            throw new IllegalArgumentException("Course with id " + courseId + " not found");
        Module module = moduleRepo.get(moduleId);
        if (module == null)
            throw new IllegalArgumentException("Module with id " + moduleId + " not found");

        //check if the module is already in the course
        List<ModuleCourse> moduleCourses = moduleCourseRepo.getAll();
        boolean alreadyExist = moduleCourses.stream().anyMatch(e -> e.getId().equals(moduleId) && e.getCourseId().equals(courseId));

        if(alreadyExist)
            throw new IllegalArgumentException("Module with id " + moduleId + " already exists");

        //create new moduleCourse entry
        ModuleCourse moduleCourse = new ModuleCourse(moduleId,courseId);
        moduleCourseRepo.create(moduleCourse);


        // Update the course's list of modules
        List<Integer> courseModules = course.getModules();
        courseModules.add(moduleId);
        course.setModules(courseModules);
        courseRepo.update(course);


    }



    /**
     * Adds an assignment to a specific module.
     * @param moduleId ID of the module.
     * @param assignmentId The id of the assignment to add.
     */
    public void addAssignmentToModule(Integer moduleId, Integer assignmentId){
        Module module = moduleRepo.get(moduleId);
        if(module == null)
            throw new IllegalArgumentException("Module with id " + moduleId + " not found");
        Assignment assignment = assignmentRepo.get(moduleId);
        if (assignment == null)
            throw new IllegalArgumentException("Assignment with id " + assignmentId + " not found");

        //check if the assignment is already in the module
        List<AssignmentModule> assignmentModules = assignmentModuleRepo.getAll();
        boolean alreadyExist = assignmentModules.stream().anyMatch(e -> e.getId().equals(assignmentId) && e.getModuleId().equals(moduleId));

        if(alreadyExist)
            throw new IllegalArgumentException("Assignment with id " + assignmentId + " already exists");

        //create new assignmentModule entry
        AssignmentModule assignmentModule = new AssignmentModule(assignmentId,moduleId);
        assignmentModuleRepo.create(assignmentModule);

        // Update the modules's list of assignments
        List<Integer> moduleAssignments = module.getAssignments();
        moduleAssignments.add(moduleId);
        module.setAssignments(moduleAssignments);
        moduleRepo.update(module);
    }

    /**
     * Adds a quiz to a specific assignment.
     * @param assignmentId ID of the assignment.
     * @param quizId The id of the quiz to add.
     */
    public void addQuizToAssignment(Integer assignmentId, Integer quizId){
        Assignment assignment = assignmentRepo.get(assignmentId);
        if(assignment == null)
            throw new IllegalArgumentException("Assignment with id " + assignmentId + " not found");
        Quiz quiz = quizRepo.get(quizId);
        if (quiz == null)
            throw new IllegalArgumentException("Quiz with id " + quizId + " not found");

        //check if the quiz is already in the assignment
        List<QuizAssignment> quizAssignments = assignmentQuizRepo.getAll();
        boolean alreadyExist = quizAssignments.stream().anyMatch(e -> e.getId().equals(quizId) && e.getAssignmentId().equals(assignmentId));

        if(alreadyExist)
            throw new IllegalArgumentException("Quiz with id " + quizId + " already exists");

        //create new assignmentQuiz entry
        QuizAssignment quizAssignment = new QuizAssignment(quizId,assignmentId);
        assignmentQuizRepo.create(quizAssignment);

        // Update the assignments's list of quizes
        List<Integer> assignmentQuizes = assignment.getQuizzes();
        assignmentQuizes.add(quizId);
        assignment.setQuizzes(assignmentQuizes);
        assignmentRepo.update(assignment);
    }

    /**
     * Removes a module from a specific course.
     * @param courseId ID of the course.
     * @param moduleId The id of the module to remove.
     */
    public void removeModuleFromCourse(Integer moduleId,Integer courseId) {

        Course course = courseRepo.get(moduleId);
        if (course == null)
            throw new IllegalArgumentException("Course with id " + courseId + " not found");
        Module module = moduleRepo.get(moduleId);
        if (module == null)
            throw new IllegalArgumentException("Module with id " + moduleId + " not found");


        moduleCourseRepo.delete(moduleId);

//        Course course = courseRepo.get(courseId);
//        if (course == null)
//            throw new IllegalArgumentException("Course with id " + courseId + " not found");
//
//        Module module = moduleRepo.get(moduleId);
//        if (module == null)
//            throw new IllegalArgumentException("Module with id " + moduleId + " not found");
//
//        // Remove the entry from the connection table
//        List<ModuleCourse> moduleCourses = moduleCourseRepo.getAll();
//        ModuleCourse moduleCourse = moduleCourses.stream()
//                .filter(mc -> mc.getId().equals(moduleId) && mc.getCourseId().equals(courseId))
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("Module is not part of the course"));
//        moduleCourseRepo.delete(moduleCourse.getId());
//
//        // Update the course's list of modules
//        List<Integer> courseModules = course.getModules();
//        courseModules.remove(moduleId);
//        course.setModules(courseModules);
//        courseRepo.update(course);
    }


    /**
     * Removes an assignment from a specific module.
     * @param moduleId ID of the module.
     * @param assignmentId The id of the assignment to remove.
     */
    public void removeAssignmentFromModule(Integer moduleId, Integer assignmentId) {
        Module module = moduleRepo.get(moduleId);
        if (module == null)
            throw new IllegalArgumentException("Module with id " + moduleId + " not found");

        Assignment assignment = assignmentRepo.get(assignmentId);
        if (assignment == null)
            throw new IllegalArgumentException("Assignment with id " + assignmentId + " not found");

        // Remove the entry from the connection table
        List<AssignmentModule> assignmentModules = assignmentModuleRepo.getAll();
        AssignmentModule assignmentModule = assignmentModules.stream()
                .filter(am -> am.getId().equals(assignmentId) && am.getModuleId().equals(moduleId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Assignment is not part of the module"));
        assignmentModuleRepo.delete(assignmentModule.getId());

        // Update the module's list of assignments
        List<Integer> moduleAssignments = module.getAssignments();
        moduleAssignments.remove(assignmentId);
        module.setAssignments(moduleAssignments);
        moduleRepo.update(module);
    }


    /**
     * Removes a quiz from a specific assignment.
     * @param assignmentId ID of the assignment.
     * @param quizId The id of the quiz to remove.
     */
    public void removeQuizFromAssignment(Integer assignmentId, Integer quizId) {
        Assignment assignment = assignmentRepo.get(assignmentId);
        if (assignment == null)
            throw new IllegalArgumentException("Assignment with id " + assignmentId + " not found");

        Quiz quiz = quizRepo.get(quizId);
        if (quiz == null)
            throw new IllegalArgumentException("Quiz with id " + quizId + " not found");

        // Remove the entry from the connection table
        List<QuizAssignment> quizAssignments = assignmentQuizRepo.getAll();
        QuizAssignment quizAssignment = quizAssignments.stream()
                .filter(qa -> qa.getId().equals(quizId) && qa.getAssignmentId().equals(assignmentId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Quiz is not part of the assignment"));
        assignmentQuizRepo.delete(quizAssignment.getId());

        // Update the assignment's list of quizzes
        List<Integer> assignmentQuizzes = assignment.getQuizzes();
        assignmentQuizzes.remove(quizId);
        assignment.setQuizzes(assignmentQuizzes);
        assignmentRepo.update(assignment);
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
