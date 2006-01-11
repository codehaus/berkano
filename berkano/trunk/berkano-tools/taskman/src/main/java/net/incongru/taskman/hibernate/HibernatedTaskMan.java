package net.incongru.taskman.hibernate;

import net.incongru.taskman.Assignee;
import net.incongru.taskman.TaskEvent;
import net.incongru.taskman.TaskInstance;
import net.incongru.taskman.TaskInstanceImpl;
import net.incongru.taskman.TaskLogImpl;
import net.incongru.taskman.TaskMan;
import net.incongru.taskman.action.TaskAction;
import net.incongru.taskman.action.TaskActionManager;
import net.incongru.taskman.action.TaskContext;
import net.incongru.taskman.def.TaskDef;
import net.incongru.taskman.def.TaskDefImpl;
import net.incongru.taskman.def.TaskDefParser;
import net.incongru.taskman.id.IdGenerator;
import net.incongru.taskman.id.NullIdGenerator;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public class HibernatedTaskMan implements TaskMan {
    private static final Criterion PENDING_TASK_STATUS = Expression.not(Expression.in("status", Arrays.asList(TaskEvent.cancelled, TaskEvent.stopped)));
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


    public List<TaskInstance> getPendingTasksByReporterId(String actorId) {
        final Criteria criteria = session.createCriteria(TaskInstanceImpl.class);
        criteria.add(Expression.eq("reporter", actorId));
        criteria.add(PENDING_TASK_STATUS);
        //criteria.addOrder(Order.asc(""))// TODO
        return criteria.list();
    }

    public List<TaskInstance> getPendingTasksByAssignee(Assignee assignee) {
        final Criteria criteria = session.createCriteria(TaskInstanceImpl.class);
        criteria.add(Expression.eq("assignee", assignee));
        criteria.add(PENDING_TASK_STATUS);
        //criteria.addOrder(Order.asc(""))// TODO
        return criteria.list();

//        Query q = session.createQuery("select t from TaskInstanceImpl t where maxElement(t.log.taskEvent) = :event");
//        q.setParameter("event", TaskEvent.started);
//        return q.list();

    }

    public TaskInstance newTaskInstance(final String taskDefName, final String reporterId, final String taskId, final String taskName, final String taskDesc) {
        final TaskDef taskDef = findLatestTaskDef(taskDefName);
        if (taskDef == null) {
            throw new IllegalStateException("TaskDef with name " + taskDefName + " does not exist.");
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
        task.setReporter(reporterId);

        logAndDispatchSimpleEvent(task, TaskEvent.instanciated, null, null);
        session.save(task);
        return task;
    }

    public void assign(TaskInstance task, Assignee newAssignee) {
        assert task instanceof TaskInstanceImpl;
        final Assignee previousAssignee = task.getAssignee();
        ((TaskInstanceImpl) task).setAssignee(newAssignee);
        logAndDispatchSimpleEvent(task, TaskEvent.assigned, toString(previousAssignee), toString(task.getAssignee()));
        ((TaskInstanceImpl) task).setStatus(TaskEvent.assigned);
        session.save(task);
    }

    public void start(TaskInstance task) {
        logAndDispatchSimpleEvent(task, TaskEvent.started, null, null);
        ((TaskInstanceImpl) task).setStatus(TaskEvent.started);
        session.save(task);
    }

    public void cancel(TaskInstance task) {
        logAndDispatchSimpleEvent(task, TaskEvent.cancelled, null, null);
        ((TaskInstanceImpl) task).setStatus(TaskEvent.cancelled);
        session.save(task);
    }

    // TODO : the dispatch should probably somehow need to know at least the variable name, and possibly its previous value
    // TODO : the var. name could also be logged, thus avoiding values in the "oldvalue" column like "foo=null" when there was no value before
    public void stop(TaskInstance task) {
        logAndDispatchSimpleEvent(task, TaskEvent.stopped, null, null);
        ((TaskInstanceImpl) task).setStatus(TaskEvent.stopped);
        session.save(task);
    }

    public void addVariable(TaskInstance task, String name, Object value) {
        assert task instanceof TaskInstanceImpl;

        TaskInstanceImpl taskImpl = ((TaskInstanceImpl) task);
        final Object oldValue = taskImpl.getVariables().put(name, value);
        logAndDispatchSimpleEvent(task, TaskEvent.variableAdded, name + "=" + toString(oldValue), name + "=" + toString(value));
        session.save(task);
    }

    // TODO
    public List<TaskInstance> findRemainingTasks() {
        final Criteria criteria = session.createCriteria(TaskInstance.class);

        throw new IllegalStateException("not implemented yet");
    }

    private void logAndDispatchSimpleEvent(final TaskInstance task, final TaskEvent event, final String oldValue, final String newValue) {
        final TaskLogImpl taskLog = new TaskLogImpl();
        taskLog.setDateTime(new DateTime());
        taskLog.setTaskEvent(event);
        taskLog.setOldValue(oldValue);
        taskLog.setNewValue(newValue);
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

        public void addTaskVariable(String name, Object value) {
            addVariable(this.task, name, value);
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
