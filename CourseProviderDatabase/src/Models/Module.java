
package Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a course module within the system, which contains a title, content,
 * and a list of assignments. A module can be associated with a course and contains
 * methods for managing assignments and enrolling students.
 */
public class Module implements Identifiable {
    private int moduleID;
    private String moduleTitle;
    private String moduleContent;
    private List<Integer> assignments = new ArrayList<>();

    /**
     * Constructs a new Module with the specified ID, title, and content.
     *
     * @param moduleID      The unique identifier for the module.
     * @param moduleTitle   The title of the module.
     * @param moduleContent The content or description of the module.
     */
    public Module(int moduleID, String moduleTitle, String moduleContent) {
        this.moduleID = moduleID;
        this.moduleTitle = moduleTitle;
        this.moduleContent = moduleContent;
    }

    public int getModuleID() {
        return moduleID;
    }

    public void setModuleID(int moduleID) {
        this.moduleID = moduleID;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }

    public void setModuleTitle(String moduleTitle) {
        this.moduleTitle = moduleTitle;
    }

    public String getModuleContent() {
        return moduleContent;
    }

    public void setModuleContent(String moduleContent) {
        this.moduleContent = moduleContent;
    }

    public List<Integer> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Integer> assignments) {
        this.assignments = assignments;
    }


    @Override
    public Integer getId() {
        return this.moduleID;
    }

    @Override
    public void setId(Integer id) {
        this.moduleID = id;
    }


}
