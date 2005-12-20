package net.incongru.taskman;

import net.incongru.taskman.def.TaskDef;

import java.util.List;

/**
 * The TaskMan implementations are responsible for storing and retrieving tasks.
 * They're basically DAOs.
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public interface TaskMan {
    // TODO : maybe avoid the user manipulation of the incomplete TaskDef: pass a parser instead ?
    TaskDef deployTaskDef(TaskDef taskDef);

    TaskInstance getTaskById(String taskId);

    /**
     * Retrieve the tasks assigned to this specific actor.
     */
    List<TaskInstance> getTasksByActorId(String actorId);

    /**
     * Retrieve the tasks assigned to an assignee (or a group/pool of actors TODO ?), so that
     * a specific actor can see what tasks he could do if he's bored.
     */
    List<TaskInstance> getTasksByAssignee(Assignee assignee);

    /**
     * Creates a new instance of a given task definition, assigns it a specific
     * id, name and description. Any of these 3 parameters can be null, in which case
     * they get a default value. (a generated id, null and null, respectively)
     */
    TaskInstance newTaskInstance(Long taskDefId, String taskId, String taskName, String taskDesc);

    void assign(TaskInstance task, Assignee assignee);

    void start(TaskInstance task);

    void cancel(TaskInstance task);

    void stop(TaskInstance task);

    void addVariable(String name, Object value); // ??

    /**
     * For scheduler: find tasks which are due and not finished.
     * Should then probably keep track of the last reminder ?
     */
    List<TaskInstance> findRemainingTasks();
}
