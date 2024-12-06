package Models;

public class AssignmentModule {
    private Integer assignmentId;
    private Integer moduleId;

    public AssignmentModule(Integer assignmentId, Integer moduleId) {
        this.assignmentId = assignmentId;
        this.moduleId = moduleId;
    }

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleIdId(Integer moduleIdId) {
        this.moduleId = moduleIdId;
    }
}
