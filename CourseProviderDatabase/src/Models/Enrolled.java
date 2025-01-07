package Models;

import java.io.Serializable;

public class Enrolled implements Identifiable, Serializable {
    private Integer studentId;
    private Integer courseId;

    public Enrolled(Integer studentId, Integer courseId) {
        this.studentId = studentId;
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
        return this.studentId;
    }

    @Override
    public void setId(Integer id) {
        this.studentId = id;
    }

    @Override
    public String toString() {
        return "Enrolled{" +
                "studentId=" + studentId +
                ", courseId=" + courseId +
                '}';
    }
}


