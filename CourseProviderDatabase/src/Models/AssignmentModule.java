package Models;

public class AssignmentModule {
    private Integer assignmentId;
    private Integer courseId;

    public AssignmentModule(Integer assignmentId, Integer courseId) {
        this.assignmentId = assignmentId;
        this.courseId = courseId;
    }

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
}
