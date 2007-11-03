package net.incongru.taskman.action;

import net.incongru.taskman.TaskEvent;
import net.incongru.taskman.TaskInstance;

/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public interface TaskActionManager {
    TaskAction getTaskAction(TaskInstance task, TaskEvent event);
}
