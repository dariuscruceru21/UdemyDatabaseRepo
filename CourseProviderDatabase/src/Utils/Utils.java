package Utils;

import Models.Instructor;
import Models.User;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Utils {
    private final List<String> usersParameters = Arrays.asList("userID","userName","password","email","type");
    private final List<String> courseParameters = Arrays.asList("courseID", "courseTitle", "description", "availableSpots", "startDate", "endDate","instructorId");
    private final List<String> moduleParameters = Arrays.asList("moduleID", "moduleTitle", "moduleContent");
    private final List<String> assignmentParameteres = Arrays.asList("assignmentID", "description", "dueDate", "score");
    private final List<String> quizParameters = Arrays.asList("quizId", "title", "contents", "correctAnswer");
    private final List<String> forumParameters = Arrays.asList("forumID","topic");
    private final List<String> messageParamteres = Arrays.asList("messageID","messagecontent","senderid","receiverid");
    private final List<String> enrolledParameters = Arrays.asList("studentId","courseId");
    private final List<String> quizAssignmentParameteres = Arrays.asList("assignmentId","quizId");
    private final List<String> moduleAssignmentParameteres = Arrays.asList("moduleId","assignmentId");
    private final List<String> courseModuleParameters = Arrays.asList("moduleId","courseId");
    private final List<String> messageForumParameters = Arrays.asList("messageId","forumId");

    public Utils(){};

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

    public List<String> getForumParameters() {
        return forumParameters;
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

    public List<String> getMessageForumParameters() {return messageForumParameters;}
}
