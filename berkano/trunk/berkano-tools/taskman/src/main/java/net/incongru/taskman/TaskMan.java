package net.incongru.taskman;

import net.incongru.taskman.def.TaskDef;
import net.incongru.taskman.def.TaskDefParser;

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

    /**
     * Since a deployed TaskDef is by nature incomplete (doesn't have an id, versionId, nor deploymentDate),
     * we here pass a TaskDefParser instance, which will somehow load our TaskDef definition
     * (from an xml file for instance), and pass it on for actual deployment.
     * @param forceEvenIfSame if false, the TaskDef will not be deployed if it is same as the latest deployed TaskDef with that name
     * @see TaskDef#isSameAs(net.incongru.taskman.def.TaskDef)
     */
    TaskDef deployTaskDef(final TaskDefParser taskDefParser, boolean forceEvenIfSame);

    TaskInstance getTaskById(String taskId);

    /**
     * Retrieves the tasks assigned to this specific actor.
     */
    List<TaskInstance> getTasksByReporterId(String actorId);
    
    /**
     * Retrieves the pending tasks assigned to this specific actor.
     */
    List<TaskInstance> getPendingTasksByReporterId(String actorId);

    /**
     * Retrieves all the tasks assigned to an assignee (or a group/pool of actors TODO ?).
     */
    List<TaskInstance> getTasksByAssignee(Assignee assignee);

    /**
     * Retrieves the <strong>pending</strong> tasks assigned to an assignee
     * (or a group/pool of actors TODO ?), so that a specific actor can see
     * what tasks he could do if he's bored.
     */
    List<TaskInstance> getPendingTasksByAssignee(Assignee assignee);
    
    /**
     * Creates a new instance of a given task definition (the last deployed TaskDef with
     * the given name), reported by the given userid, assigns it a specific id, name and description.
     * Any of these last 3 parameters can be null, in which case they get a default value.
     * (a generated id, null and null, respectively)
     */
    TaskInstance newTaskInstance(String taskDefName, String reporterId, String taskId, String taskName, String taskDesc);

    void assign(TaskInstance task, Assignee assignee);

    void start(TaskInstance task);

    void cancel(TaskInstance task);

    void stop(TaskInstance task);

    /**
     * @see TaskInstance#getVariableNames()
     */
    void addVariable(TaskInstance task, String name, Object value);

    /**
     * For scheduler: find tasks which are due and not finished.
     * Should then probably keep track of the last reminder ?
     */
    List<TaskInstance> findRemainingTasks();
}
