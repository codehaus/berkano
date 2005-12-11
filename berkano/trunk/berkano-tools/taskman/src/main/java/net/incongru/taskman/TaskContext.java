package net.incongru.taskman;

/**
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public interface TaskContext {
    void assignTask(Assignee assignee);

    void stopTask();

    void cancelTask();

    Task getTask();

    TaskEvent getEvent();
}
