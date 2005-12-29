package net.incongru.taskman;

import net.incongru.taskman.def.TaskDef;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public class TaskInstanceImpl implements TaskInstance {
    private TaskDef taskDef;
    private String id;
    private String name;
    private String description;
    private Assignee assignee;
    private DateTime dueDate;
    private DateTime lastReminder;
    private Map<String, Object> variables = new HashMap<String, Object>();
    private List<TaskLog> log = new ArrayList<TaskLog>();

    public Set<String> getVariableNames() {
        return Collections.unmodifiableSet(getVariables().keySet());
    }

    public Object getVariable(String name) {
        return getVariables().get(name);
    }

    // generated getters and setters
    public TaskDef getTaskDef() {
        return taskDef;
    }

    public void setTaskDef(TaskDef taskDef) {
        this.taskDef = taskDef;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Assignee getAssignee() {
        return assignee;
    }

    public void setAssignee(Assignee assignee) {
        this.assignee = assignee;
    }

    public DateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(DateTime dueDate) {
        this.dueDate = dueDate;
    }

    public DateTime getLastReminder() {
        return lastReminder;
    }

    public void setLastReminder(DateTime lastReminder) {
        this.lastReminder = lastReminder;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public List<TaskLog> getLog() {
        return log;
    }

    public void setLog(List<TaskLog> log) {
        this.log = log;
    }

    // generated equals
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final TaskInstanceImpl that = (TaskInstanceImpl) o;

        if (assignee != null ? !assignee.equals(that.assignee) : that.assignee != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (dueDate != null ? !dueDate.equals(that.dueDate) : that.dueDate != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (lastReminder != null ? !lastReminder.equals(that.lastReminder) : that.lastReminder != null) return false;
        if (log != null ? !log.equals(that.log) : that.log != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (taskDef != null ? !taskDef.equals(that.taskDef) : that.taskDef != null) return false;
        if (variables != null ? !variables.equals(that.variables) : that.variables != null) return false;

        return true;
    }

    // generated hashCode
    public int hashCode() {
        int result;
        result = (taskDef != null ? taskDef.hashCode() : 0);
        result = 29 * result + (id != null ? id.hashCode() : 0);
        result = 29 * result + (name != null ? name.hashCode() : 0);
        result = 29 * result + (description != null ? description.hashCode() : 0);
        result = 29 * result + (assignee != null ? assignee.hashCode() : 0);
        result = 29 * result + (dueDate != null ? dueDate.hashCode() : 0);
        result = 29 * result + (lastReminder != null ? lastReminder.hashCode() : 0);
        result = 29 * result + (variables != null ? variables.hashCode() : 0);
        result = 29 * result + (log != null ? log.hashCode() : 0);
        return result;
    }

    // generated toString
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("TaskInstanceImpl");
        sb.append("{taskDef=").append(taskDef);
        sb.append(",id=").append(id);
        sb.append(",name=").append(name);
        sb.append(",description=").append(description);
        sb.append(",assignee=").append(assignee);
        sb.append(",dueDate=").append(dueDate);
        sb.append(",lastReminder=").append(lastReminder);
        sb.append(",variables=").append(variables);
        sb.append(",log=").append(log);
        sb.append('}');
        return sb.toString();
    }
}
