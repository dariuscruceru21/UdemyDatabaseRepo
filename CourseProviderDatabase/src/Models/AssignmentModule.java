package Models;

public class AssignmentModule implements Identifiable {
    private Integer assignmentId;
    private Integer moduleId;

    public AssignmentModule(Integer assignmentId, Integer moduleId) {
        this.assignmentId = assignmentId;
        this.moduleId = moduleId;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleIdId(Integer moduleIdId) {
        this.moduleId = moduleIdId;
    }

    @Override
    public Integer getId() {
        return assignmentId;
    }

    @Override
    public void setId(Integer id) {
        this.assignmentId = id;
    }
}
