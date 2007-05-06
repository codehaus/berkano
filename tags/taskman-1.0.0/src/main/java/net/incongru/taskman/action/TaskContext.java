package net.incongru.taskman.action;

import net.incongru.taskman.Assignee;
import net.incongru.taskman.TaskEvent;
import net.incongru.taskman.TaskInstance;

/**
 * A simple object passed to TaskAction which exposes the event
 * which triggered this TaskAction and the concerned TaskInstance;
 * it also exposes a couple of lifecycle methods for the current TaskInstance.
 *
 * TODO : the lifecycle methods can't probably work as of now, since the session
 * in HibernatedTaskMan would be closed at the time of execution.
 * TODO : actually the statement is probably false, since actions are currently
 * executed synchronously.
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public interface TaskContext {
    void assignTask(Assignee assignee);

    void addTaskVariable(String name, Object value);

    void startTask();

    void stopTask();

    void cancelTask();

    TaskInstance getTask();

    TaskEvent getEvent();
}
