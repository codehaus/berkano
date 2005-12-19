package net.incongru.taskman;

/**
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public interface TaskActionManager {
    TaskAction getTaskAction(TaskInstance task, TaskEvent event);
}
