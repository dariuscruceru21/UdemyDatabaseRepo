package Models;

public class QuizAssignment {
    private Integer quizId;
    private Integer assignmentId;

    public QuizAssignment(Integer quizId, Integer assignmentId) {
        this.quizId = quizId;
        this.assignmentId = assignmentId;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
    }

}
