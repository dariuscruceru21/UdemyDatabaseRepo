
package Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an assignment that may contain a list of quizzes and has an associated score.
 * Each assignment has a unique ID, description, due date, and score.
 */
public class Assignment implements Identifiable, Serializable {
    private Integer assignmentID;
    private String description;
    private String dueDate;
    private List<Integer> quizzes = new ArrayList<>();
    private Integer score;

    /**
     * Constructs an Assignment with the specified ID, description, due date, and score.
     *
     * @param assignmentID The unique ID of the assignment.
     * @param description  A brief description of the assignment.
     * @param dueDate      The due date for the assignment as a string.
     * @param score        The score or weight of the assignment.
     */
    public Assignment(Integer assignmentID, String description, String dueDate, Integer score) {
        this.assignmentID = assignmentID;
        this.description = description;
        this.dueDate = dueDate;
        this.score = score;
    }

    public Integer getAssignmentID() {
        return assignmentID;
    }

    public void setAssignmentID(Integer assignmentID) {
        this.assignmentID = assignmentID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public List<Integer> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Integer> quizzes) {
        this.quizzes = quizzes;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }



    @Override
    public Integer getId() {
        return this.assignmentID;
    }

    @Override
    public void setId(Integer id) {
        this.assignmentID = id;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "assignmentID=" + assignmentID +
                ", description='" + description + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", quizzes=" + quizzes +
                ", score=" + score +
                '}';
    }
}
