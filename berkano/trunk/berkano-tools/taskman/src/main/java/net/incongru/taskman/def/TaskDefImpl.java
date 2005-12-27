package net.incongru.taskman.def;

import net.incongru.taskman.TaskAction;
import net.incongru.taskman.TaskEvent;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.Map;

/**
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public class TaskDefImpl implements TaskDef {
    private Long id;
    private Long versionId;
    private String name;
    private String description;
    private DateTime deploymentDateTime;
    private Period duePeriod;
    private Period reminderPeriod;
    private Period dueDateTimeout;
    private Map<TaskEvent, Class<? extends TaskAction>> eventActionMap;

    public Class<? extends TaskAction> getEventActionClass(TaskEvent event) {
        if (eventActionMap == null) {
            return null;
        }
        return eventActionMap.get(event);
    }

    public boolean isSameAs(TaskDef other) {
        if (!(other instanceof TaskDefImpl)) {
            return false;
        }
        return isSameAs((TaskDefImpl) other);
    }

    private boolean isSameAs(TaskDefImpl other) {
        if (description != null ? !description.equals(other.getDescription()) : other.getDescription() != null)
            return false;
        if (dueDateTimeout != null ? !dueDateTimeout.equals(other.getDueDateTimeout()) : other.getDueDateTimeout() != null)
            return false;
        if (duePeriod != null ? !duePeriod.equals(other.getDuePeriod()) : other.getDuePeriod() != null)
            return false;
        if (eventActionMap != null ? !eventActionMap.equals(other.getEventActionMap()) : other.getEventActionMap() != null)
            return false;
        if (name != null ? !name.equals(other.getName()) : other.getName() != null)
            return false;
        if (reminderPeriod != null ? !reminderPeriod.equals(other.getReminderPeriod()) : other.getReminderPeriod() != null)
            return false;

        return true;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final TaskDefImpl taskDef = (TaskDefImpl) o;

        if (!isSameAs(taskDef)) {
            return false;
        }
        if (deploymentDateTime != null ? !deploymentDateTime.equals(taskDef.deploymentDateTime) : taskDef.deploymentDateTime != null)
            return false;
        if (id != null ? !id.equals(taskDef.id) : taskDef.id != null)
            return false;
        if (versionId != null ? !versionId.equals(taskDef.versionId) : taskDef.versionId != null)
            return false;

        return true;
    }

    // generated getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
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

    public DateTime getDeploymentDateTime() {
        return deploymentDateTime;
    }

    public void setDeploymentDateTime(DateTime deploymentDateTime) {
        this.deploymentDateTime = deploymentDateTime;
    }

    public Period getDuePeriod() {
        return duePeriod;
    }

    public void setDuePeriod(Period duePeriod) {
        this.duePeriod = duePeriod;
    }

    public Period getReminderPeriod() {
        return reminderPeriod;
    }

    public void setReminderPeriod(Period reminderPeriod) {
        this.reminderPeriod = reminderPeriod;
    }

    public Period getDueDateTimeout() {
        return dueDateTimeout;
    }

    public void setDueDateTimeout(Period dueDateTimeout) {
        this.dueDateTimeout = dueDateTimeout;
    }

    public Map<TaskEvent, Class<? extends TaskAction>> getEventActionMap() {
        return eventActionMap;
    }

    public void setEventActionMap(Map<TaskEvent, Class<? extends TaskAction>> eventActionMap) {
        this.eventActionMap = eventActionMap;
    }

    // generated hashCode
    public int hashCode() {
        int result;
        result = (id != null ? id.hashCode() : 0);
        result = 29 * result + (versionId != null ? versionId.hashCode() : 0);
        result = 29 * result + (name != null ? name.hashCode() : 0);
        result = 29 * result + (description != null ? description.hashCode() : 0);
        result = 29 * result + (deploymentDateTime != null ? deploymentDateTime.hashCode() : 0);
        result = 29 * result + (duePeriod != null ? duePeriod.hashCode() : 0);
        result = 29 * result + (reminderPeriod != null ? reminderPeriod.hashCode() : 0);
        result = 29 * result + (dueDateTimeout != null ? dueDateTimeout.hashCode() : 0);
        result = 29 * result + (eventActionMap != null ? eventActionMap.hashCode() : 0);
        return result;
    }
}
