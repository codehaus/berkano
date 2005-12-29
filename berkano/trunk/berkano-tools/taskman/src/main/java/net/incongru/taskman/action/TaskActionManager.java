package net.incongru.taskman.action;

import net.incongru.taskman.action.TaskAction;
import net.incongru.taskman.TaskInstance;
import net.incongru.taskman.TaskEvent;

/**
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public interface TaskActionManager {
    TaskAction getTaskAction(TaskInstance task, TaskEvent event);
}
