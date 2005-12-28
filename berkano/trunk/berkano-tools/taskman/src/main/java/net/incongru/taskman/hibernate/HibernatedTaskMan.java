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
import net.incongru.taskman.def.TaskDefImpl;
import net.incongru.taskman.def.TaskDefParser;
import net.incongru.taskman.id.IdGenerator;
import net.incongru.taskman.id.NullIdGenerator;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.joda.time.DateTime;

import java.util.List;

/**
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public class HibernatedTaskMan implements TaskMan {
    private static final Long DEFAULT_VERSION_ID = new Long(1L);

    private final Session session;
    private final TaskActionManager actionManager;
    private final IdGenerator idGenerator;

    public HibernatedTaskMan(Session session, TaskActionManager actionManager) {
        this(session, actionManager, new NullIdGenerator());
    }

    public HibernatedTaskMan(Session session, TaskActionManager actionManager, IdGenerator idGenerator) {
        this.session = session;
        this.actionManager = actionManager;
        this.idGenerator = idGenerator;
    }

    public TaskDef deployTaskDef(final TaskDefParser taskDefParser, boolean forceEvenIfEquals) {
        final TaskDef taskDef = taskDefParser.loadTaskDesk();
        assert taskDef instanceof TaskDefImpl;
        assert taskDef.getId() == null;
        assert taskDef.getVersionId() == null;
        assert taskDef.getDeploymentDateTime() == null;

        final TaskDef latestTaskDef = findLatestTaskDef(taskDef.getName());
        if (!forceEvenIfEquals && taskDef.isSameAs(latestTaskDef)) {
            return latestTaskDef;
        }

        Long newVersion = DEFAULT_VERSION_ID;
        if (latestTaskDef != null) {
            newVersion = Long.valueOf(latestTaskDef.getVersionId().longValue() + 1);
        }
        taskDef.setVersionId(newVersion);
        taskDef.setDeploymentDateTime(new DateTime());
        session.save(taskDef);
        return taskDef;
    }

    private TaskDef findLatestTaskDef(String taskDefName) {
        final Criteria criteria = session.createCriteria(TaskDefImpl.class);
        criteria.add(Expression.eq("name", taskDefName));
        criteria.addOrder(Order.desc("versionId"));
        criteria.setMaxResults(1); // TODO : see how this gets translated : doesn't seem to appear in derby queries + maybe it is incompatible with oracle ?
        return (TaskDef) criteria.uniqueResult();
    }

    public TaskInstance getTaskById(String taskId) {
        return (TaskInstance) session.load(TaskInstanceImpl.class, taskId);
    }

    // TODO
    public List<TaskInstance> getTasksByActorId(String actorId) {
        throw new IllegalStateException("not implemented yet");
    }

    // TODO
    public List<TaskInstance> getTasksByAssignee(Assignee assignee) {
        throw new IllegalStateException("not implemented yet");
    }

    public TaskInstance newTaskInstance(final Long taskDefId, final String taskId, final String taskName, final String taskDesc) {
        final TaskDef taskDef = (TaskDef) session.get(TaskDefImpl.class, taskDefId);
        if (taskDef == null) {
            throw new IllegalStateException("TaskDef with id " + taskDefId + " does not exist.");
        }
        final TaskInstanceImpl task = new TaskInstanceImpl();
        if (taskId == null) {
            task.setId(idGenerator.generate());
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
        assert task instanceof TaskInstanceImpl;
        final Assignee previousAssignee = task.getAssignee();
        ((TaskInstanceImpl)task).setAssignee(newAssignee);
        logAndDispatchSimpleEvent(task, TaskEvent.assigned, previousAssignee, task.getAssignee());
        session.save(task);
    }

    public void start(TaskInstance task) {
        logAndDispatchSimpleEvent(task, TaskEvent.started, null, null);
        session.save(task);
    }

    public void cancel(TaskInstance task) {
        logAndDispatchSimpleEvent(task, TaskEvent.cancelled, null, null);
        session.save(task);
    }

    public void stop(TaskInstance task) {
        logAndDispatchSimpleEvent(task, TaskEvent.stopped, null, null);
        session.save(task);
    }

    // TODO
    public void addTaskVariable(TaskInstance task, String name, Object value) {
        // TODO : either user should modify the variables Map and call some save() method,
        // or the TaskInstance should be passed here AND ideally the getVariables should return an immutable map instance
        throw new IllegalStateException("not implemented yet");
    }

    // TODO
    public List<TaskInstance> findRemainingTasks() {
        final Criteria criteria = session.createCriteria(TaskInstance.class);

        throw new IllegalStateException("not implemented yet");
        //return null;
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

        // generated toString
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("TaskContextImpl");
            sb.append("{event=").append(event);
            sb.append(",task=").append(task);
            sb.append('}');
            return sb.toString();
        }
    }
}
