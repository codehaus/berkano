package net.incongru.taskman;

/**
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public class TaskContextImpl implements TaskContext {
    private TaskInstance task;
    private TaskEvent event;

    public TaskContextImpl(TaskInstance task, TaskEvent event) {
        this.task = task;
        this.event = event;
    }

    public void assignTask(Assignee assignee) {
        throw new IllegalStateException("not implemented yet");
    }

    public void stopTask() {
        throw new IllegalStateException("not implemented yet");
    }

    public void cancelTask() {
        throw new IllegalStateException("not implemented yet");
    }

    public TaskInstance getTask() {
        return task;
    }

    public TaskEvent getEvent() {
        return event;
    }
}
