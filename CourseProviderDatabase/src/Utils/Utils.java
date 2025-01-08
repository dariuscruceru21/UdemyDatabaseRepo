package Utils;

import Controller.AssignmentController;
import Controller.CourseUserController;
import Exceptions.ValidationException;
import Models.*;
import Models.Module;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Utilities class to get all of the columns of the table.
 */
public class Utils {
    private final List<String> usersParameters = Arrays.asList("userID", "userName", "password", "email", "type");
    private final List<String> courseParameters = Arrays.asList("courseID", "courseTitle", "description", "availableSpots", "startDate", "endDate", "instructorId");
    private final List<String> moduleParameters = Arrays.asList("moduleID", "moduleTitle", "moduleContent");
    private final List<String> assignmentParameteres = Arrays.asList("assignmentID", "description", "dueDate", "score");
    private final List<String> quizParameters = Arrays.asList("quizId", "title", "contents", "correctAnswer");
    private final List<String> messageParamteres = Arrays.asList("messageID", "messagecontent", "senderid", "receiverid");
    private final List<String> enrolledParameters = Arrays.asList("studentId", "courseId");
    private final List<String> quizAssignmentParameteres = Arrays.asList("assignmentId", "quizId");
    private final List<String> moduleAssignmentParameteres = Arrays.asList("moduleId", "assignmentId");
    private final List<String> courseModuleParameters = Arrays.asList("moduleId", "courseId");
    private CourseUserController courseUserController;
    private AssignmentController assignmentController;



    public Utils() {
    }

    ;

    public List<String> getUsersParameters() {
        return usersParameters;
    }

    public List<String> getCourseParameters() {
        return courseParameters;
    }

    public List<String> getModuleParameters() {
        return moduleParameters;
    }

    public List<String> getAssignmentParameteres() {
        return assignmentParameteres;
    }

    public List<String> getQuizParameters() {
        return quizParameters;
    }

    public List<String> getMessageParamteres() {
        return messageParamteres;
    }

    public List<String> getEnrolledParameters() {
        return enrolledParameters;
    }

    public List<String> getQuizAssignmentParameteres() {
        return quizAssignmentParameteres;
    }

    public List<String> getModuleAssignmentParameteres() {
        return moduleAssignmentParameteres;
    }

    public List<String> getCourseModuleParameters() {return courseModuleParameters;}



}
