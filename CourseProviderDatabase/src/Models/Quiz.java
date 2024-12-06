
package Models;

/**
 * Represents a quiz within the system, which contains a title, content, and a correct answer.
 * A quiz can be associated with modules or assignments and is identifiable by a unique quiz ID.
 */
public class Quiz implements Identifiable {
    private Integer quizId;
    private String title;
    private String contents;
    private Instructor correctAnswer;

    /**
     * Constructs a new Quiz with the specified ID, title, content, and correct answer.
     *
     * @param quizId        The unique identifier for the quiz.
     * @param title         The title of the quiz.
     * @param contents      The content or questions within the quiz.
     * @param correctAnswer The correct answer identifier for the quiz.
     */
    public Quiz(Integer quizId, String title, String contents, Instructor correctAnswer) {
        this.quizId = quizId;
        this.title = title;
        this.contents = contents;
        this.correctAnswer = correctAnswer;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Instructor getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Instructor correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public Integer getId() {
        return this.quizId;
    }

    @Override
    public void setId(Integer id) {
        this.quizId = id;
    }
}
