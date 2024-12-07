package Models;

public class QuizAssignment implements Identifiable {
    private Integer quizId;
    private Integer assignmentId;

    public QuizAssignment(Integer quizId, Integer assignmentId) {
        this.quizId = quizId;
        this.assignmentId = assignmentId;
    }

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
    }

    @Override
    public Integer getId() {
        return quizId;
    }

    @Override
    public void setId(Integer id) {
        this.quizId = id;
    }
}
