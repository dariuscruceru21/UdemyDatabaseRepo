package Models;

public class QuizAssignment implements Identifiable {
    private Integer assignmentId;
    private Integer quizId;


    public QuizAssignment( Integer assignmentId, Integer quizId) {
        this.assignmentId = assignmentId;
        this.quizId = quizId;

    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId= quizId;
    }

    @Override
    public Integer getId() {
        return assignmentId;
    }

    @Override
    public void setId(Integer id) {
        this.assignmentId = id;
    }

    @Override
    public String toString() {
        return "QuizAssignment{" +
                "quizId=" + quizId +
                ", assignmentId=" + assignmentId +
                '}';
    }
}
