package net.incongru.taskman;

import org.joda.time.DateTime;

/**
 * .. some thing to keep a trace of events, assignments etc ?
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public class TaskLogImpl implements TaskLog {
    private long id;
    private DateTime dateTime;
    private TaskEvent taskEvent;
    private String oldValue;
    private String newValue;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // generated getters and setters
    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public TaskEvent getTaskEvent() {
        return taskEvent;
    }

    public void setTaskEvent(TaskEvent taskEvent) {
        this.taskEvent = taskEvent;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    // generated equals
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final TaskLogImpl taskLog = (TaskLogImpl) o;

        if (dateTime != null ? !dateTime.equals(taskLog.dateTime) : taskLog.dateTime != null) return false;
        if (newValue != null ? !newValue.equals(taskLog.newValue) : taskLog.newValue != null) return false;
        if (oldValue != null ? !oldValue.equals(taskLog.oldValue) : taskLog.oldValue != null) return false;
        if (taskEvent != taskLog.taskEvent) return false;

        return true;
    }

    // generated hashCode
    public int hashCode() {
        int result;
        result = (dateTime != null ? dateTime.hashCode() : 0);
        result = 29 * result + (taskEvent != null ? taskEvent.hashCode() : 0);
        result = 29 * result + (oldValue != null ? oldValue.hashCode() : 0);
        result = 29 * result + (newValue != null ? newValue.hashCode() : 0);
        return result;
    }
}
