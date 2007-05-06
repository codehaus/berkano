package net.incongru.taskman.action;

import net.incongru.taskman.TaskEvent;
import net.incongru.taskman.TaskInstance;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public class TaskActionManagerImpl implements TaskActionManager {
    /* Maps Class objects to an instance of their type */
    final Map<Class<? extends TaskAction>, TaskAction> actionsMap;

    public TaskActionManagerImpl(TaskAction[] actions) {
        this.actionsMap = new HashMap<Class<? extends TaskAction>, TaskAction>();
        for (TaskAction action : actions) {
            actionsMap.put(action.getClass(), action);
        }
    }

    public TaskAction getTaskAction(TaskInstance task, TaskEvent event) {
        final Class<? extends TaskAction> eventActionClass = task.getTaskDef().getEventActionClass(event);
        // TODO : check for null? Allowing for null events in the taskdef could allow for a "generic" action to be executed ?
        return actionsMap.get(eventActionClass);
    }
}
