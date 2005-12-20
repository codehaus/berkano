package net.incongru.taskman.hibernate;

import net.incongru.taskman.Assignee;
import net.incongru.taskman.TaskAction;
import net.incongru.taskman.TaskActionManager;
import net.incongru.taskman.TaskContext;
import net.incongru.taskman.TaskEvent;
import net.incongru.taskman.TaskInstance;
import net.incongru.taskman.TaskInstanceImpl;
import net.incongru.taskman.TaskLogImpl;
import net.incongru.taskman.TaskMan;
import net.incongru.taskman.def.TaskDef;
import org.hibernate.Session;
import org.hibernate.Criteria;
import org.joda.time.DateTime;

import java.util.List;

/**
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public class HibernatedTaskMan implements TaskMan {
    private final Session session;
    private final TaskActionManager actionManager;

    public HibernatedTaskMan(Session session, TaskActionManager actionManager) {
        this.session = session;
        this.actionManager = actionManager;
    }

    public void deployTaskDef(TaskDef taskDef) {
    }

    public TaskInstance getTaskById(String taskId) {
        return (TaskInstance) session.load(TaskInstance.class, taskId);
    }

    public List<TaskInstance> getTasksByActorId(String actorId) {
        throw new IllegalStateException("not implemented yet");
    }

    public List<TaskInstance> getTasksByAssignee(Assignee assignee) {
        throw new IllegalStateException("not implemented yet");
    }

    public TaskInstance newTaskInstance(final String taskDefId, final String taskId, final String taskName, final String taskDesc) {
        final TaskDef taskDef = (TaskDef) session.load(TaskDef.class, taskDefId);
        final TaskInstanceImpl task = new TaskInstanceImpl();
        if (taskId == null) {
            task.setId(generateTaskId());
        } else {
            task.setId(taskId);
        }
        task.setName(taskName);
        task.setDescription(taskDesc);
        task.setTaskDef(taskDef);

        logAndDispatchSimpleEvent(task, TaskEvent.instanciated, null, null);
        session.save(task);
        return task;
    }

    public void assign(TaskInstance task, Assignee newAssignee) {
        logAndDispatchSimpleEvent(task, TaskEvent.assigned, task.getAssignee(), newAssignee);
        session.merge(task);
    }

    public void start(TaskInstance task) {
        logAndDispatchSimpleEvent(task, TaskEvent.started, null, null);
        session.merge(task);
    }

    public void cancel(TaskInstance task) {
        logAndDispatchSimpleEvent(task, TaskEvent.cancelled, null, null);
        session.merge(task);
    }

    public void stop(TaskInstance task) {
        logAndDispatchSimpleEvent(task, TaskEvent.stopped, null, null);
        session.merge(task);
    }

    public void addVariable(String name, Object value) {
        // TODO : either user should modify the variables Map and call some save() method,
        // or the TaskInstance should be passed here AND ideally the getVariables should return an immutable map instance
        throw new IllegalStateException("not implemented yet");
    }

    public List<TaskInstance> findRemainingTasks() {
        final Criteria criteria = session.createCriteria(TaskInstance.class);

        throw new IllegalStateException("not implemented yet");

    }

    private String generateTaskId() {
        throw new IllegalStateException("not implemented yet");
    }

    private void logAndDispatchSimpleEvent(final TaskInstance task, final TaskEvent event, final Object oldValue, final Object newValue) {
        final TaskLogImpl taskLog = new TaskLogImpl();
        taskLog.setDateTime(new DateTime());
        taskLog.setTaskEvent(event);
        taskLog.setOldValue(toString(oldValue));
        taskLog.setNewValue(toString(newValue));
        task.getLog().add(taskLog);

        final TaskAction taskAction = actionManager.getTaskAction(task, event);
        if (taskAction != null) {
            TaskContext ctx = new TaskContextImpl(task, event);
            taskAction.execute(ctx);
        }
    }

    private String toString(Object value) {
        return value != null ? value.toString() : null;
    }

    protected final class TaskContextImpl implements TaskContext {
        private TaskInstance task;
        private TaskEvent event;

        private TaskContextImpl(TaskInstance task, TaskEvent event) {
            this.task = task;
            this.event = event;
        }

        public void assignTask(Assignee assignee) {
            assign(this.task, assignee);
        }

        public void startTask() {
            start(this.task);
        }

        public void stopTask() {
            stop(this.task);
        }

        public void cancelTask() {
            cancel(this.task);
        }

        public TaskInstance getTask() {
            return task;
        }

        public TaskEvent getEvent() {
            return event;
        }
    }
}
