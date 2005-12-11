package net.incongru.taskman;

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
    void deployTaskDef(TaskDef taskDef);

    Task getTaskById(String taskId);

    /**
     * Retrieve the tasks assigned to this specific actor.
     */
    List<Task> getTasksByActorId(String actorId);

    /**
     * Retrieve the tasks assigned to an assignee (or a group/pool of actors TODO ?), so that
     * a specific actor can see what tasks he could do if he's bored.
     */
    List<Task> getTasksByAssignee(Assignee assignee);

    /**
     * Creates a new instance of a given task definition, assigns it a specific
     * id, name and description. Any of these 3 parameters can be null, in which case
     * they get a default value. (a generated id, the task def name and a null value,
     * respectively)
     */
    Task newTask(String taskDefId, String taskId, String taskName, String taskDesc);

    void assign(Task task, Assignee assignee);

    void start(Task task);

    void cancel(Task task);

    void end(Task task);

    void addVariable(String name, Object value); // ??

    /**
     * For scheduler: find tasks which are due and not finished.
     * Should then probably keep track of the last reminder ?
     */
    List<Task> findRemainingTasks();
}
