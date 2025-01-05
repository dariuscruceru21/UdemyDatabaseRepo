package Models;

import java.io.Serializable;

public class AssignmentModule implements Identifiable, Serializable {
    private Integer moduleId;
    private Integer assignmentId;


    public AssignmentModule(Integer moduleId,Integer assignmentId) {
        this.moduleId = moduleId;
        this.assignmentId = assignmentId;

    }

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer moduleIdId) {
        this.assignmentId = assignmentId;
    }

    @Override
    public Integer getId() {
        return moduleId;
    }

    @Override
    public void setId(Integer id) {
        this.moduleId = id;
    }
}
