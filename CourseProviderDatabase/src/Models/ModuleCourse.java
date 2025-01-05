package Models;

import java.io.Serializable;

public class ModuleCourse implements Identifiable, Serializable {
    private Integer moduleId;
    private Integer courseId;

    public ModuleCourse(Integer moduleId, Integer courseId) {
        this.moduleId = moduleId;
        this.courseId = courseId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    @Override
    public Integer getId() {
        return moduleId;
    }

    @Override
    public void setId(Integer id) {
        this.moduleId = id;
    }

    @Override
    public String toString() {
        return "ModuleCourse{" +
                "moduleId=" + moduleId +
                ", courseId=" + courseId +
                '}';
    }
}
