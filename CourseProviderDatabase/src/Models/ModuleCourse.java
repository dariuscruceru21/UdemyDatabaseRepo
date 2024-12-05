package Models;

public class ModuleCourse {
    private Integer moduleId;
    private Integer courseId;

    public ModuleCourse(Integer moduleId, Integer courseId) {
        this.moduleId = moduleId;
        this.courseId = courseId;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

}
