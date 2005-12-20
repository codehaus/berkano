package net.incongru.taskman;

/**
 * A simple object passed to TaskAction which exposes the event
 * which triggered this TaskAction and the concerned TaskInstance;
 * it also exposes a couple of lifecycle methods for the current TaskInstance.
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public interface TaskContext {
    void assignTask(Assignee assignee);

    void startTask();

    void stopTask();

    void cancelTask();

    TaskInstance getTask();

    TaskEvent getEvent();
}
