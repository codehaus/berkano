package net.incongru.taskman.hibernate;

import net.incongru.taskman.AbstractTaskManTestCase;
import net.incongru.taskman.action.TaskAction;
import net.incongru.taskman.action.TaskActionManager;
import net.incongru.taskman.action.TaskContext;
import net.incongru.taskman.TaskEvent;
import net.incongru.taskman.TaskInstance;
import net.incongru.taskman.TaskInstanceImpl;
import net.incongru.taskman.TaskLog;
import net.incongru.taskman.TaskMan;
import net.incongru.taskman.def.TaskDef;
import net.incongru.taskman.def.TaskDefImpl;
import net.incongru.taskman.id.IdGenerator;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.criterion.Order;
import org.jmock.Mock;

/**
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public class HibernatedTaskManTest extends AbstractTaskManTestCase {
    public void testGetTaskInstancebyIdJustWorks() {
        Mock session = mock(Session.class);
        session.expects(once()).method("load").with(eq(TaskInstanceImpl.class), eq("foo")).will(returnValue(getDummyTaskInstance(getDummyTaskDef())));

        final TaskMan taskMan = new HibernatedTaskMan((Session) session.proxy(), null);
        taskMan.getTaskById("foo");
    }

    public void testEventDispatchingDoesnotMoveWhenNoActionForSpecificEvent() {
        Mock session = mock(Session.class);
        Mock taskDef = mock(TaskDef.class);
        Mock actionMan = mock(TaskActionManager.class);

        final TaskInstance task = getDummyTaskInstance((TaskDef) taskDef.proxy());
        session.expects(once()).method("save").with(eq(task));
        actionMan.expects(once()).method("getTaskAction").with(eq(task), eq(TaskEvent.cancelled)).will(returnValue(null));

        assertEquals(0, task.getLog().size());
        final TaskMan taskMan = new HibernatedTaskMan((Session) session.proxy(), (TaskActionManager) actionMan.proxy());

        taskMan.cancel(task);

        assertEquals(1, task.getLog().size());
        final TaskLog taskLog = task.getLog().get(0);
        //assertEquals(taskLog.getDateTime());
        assertEquals(TaskEvent.cancelled, taskLog.getTaskEvent());
        assertEquals(null, taskLog.getOldValue());
        assertEquals(null, taskLog.getNewValue());
    }

    public void testEventDispatchingExecutesAction() {
        Mock session = mock(Session.class);
        Mock taskDef = mock(TaskDef.class);
        Mock actionMan = mock(TaskActionManager.class);
        Mock action = mock(TaskAction.class);

        final TaskInstance task = getDummyTaskInstance((TaskDef) taskDef.proxy());
        session.expects(once()).method("save").with(eq(task));
        actionMan.expects(once()).method("getTaskAction").with(eq(task), eq(TaskEvent.started)).will(returnValue(action.proxy()));
        action.expects(once()).method("execute").with(isA(TaskContext.class));
        assertEquals(0, task.getLog().size());
        final TaskMan taskMan = new HibernatedTaskMan((Session) session.proxy(), (TaskActionManager) actionMan.proxy());
        taskMan.start(task);

        assertEquals(1, task.getLog().size());
        final TaskLog taskLog = task.getLog().get(0);
        //assertEquals(taskLog.getDateTime());
        assertEquals(TaskEvent.started, taskLog.getTaskEvent());
        assertEquals(null, taskLog.getOldValue());
        assertEquals(null, taskLog.getNewValue());
    }

    public void testNewTaskInstanceUsesTheGivenTaskId() {
        Mock session = mock(Session.class);
        Mock actionMan = mock(TaskActionManager.class);
        Mock idGen = mock(IdGenerator.class);

        TaskInstanceImpl task = new TaskInstanceImpl();
        task.setId("myId");
        task.setName("some specific task name");
        task.setDescription("some specific desc");
        task.setTaskDef(getDummyTaskDef());

        expectCriteriaForLatestTaskDef(session, getDummyTaskDef());
        idGen.expects(never());
        // can't expect eq(task) as a parameter, because creationDate will differ
        session.expects(once()).method("save").with(isA(TaskInstance.class));
        // can't expect eq(task) as a parameter, because creationDate will differ
        actionMan.expects(once()).method("getTaskAction").with(isA(TaskInstance.class), eq(TaskEvent.instanciated)).will(returnValue(null));

        final TaskMan taskMan = new HibernatedTaskMan((Session) session.proxy(), (TaskActionManager) actionMan.proxy(), (IdGenerator) idGen.proxy());
        final TaskInstance taskInstance = taskMan.newTaskInstance("myTaskDef", "myId", "some specific task name", "some specific desc");
        assertEquals("myId", taskInstance.getId());
        assertEquals("some specific task name", taskInstance.getName());
        assertEquals("some specific desc", taskInstance.getDescription());
        assertEquals(getDummyTaskDef(), taskInstance.getTaskDef());
    }


    public void testNewTaskInstanceGeneratesAnIdIfNonePassed() {
        Mock session = mock(Session.class);
        Mock actionMan = mock(TaskActionManager.class);
        Mock idGen = mock(IdGenerator.class);

        TaskInstanceImpl task = new TaskInstanceImpl();
        task.setId("myId");
        task.setName("some specific task name");
        task.setDescription("some specific desc");
        task.setTaskDef(getDummyTaskDef());

        expectCriteriaForLatestTaskDef(session, getDummyTaskDef());
        idGen.expects(once()).method("generate").withNoArguments().will(returnValue("generatedID"));
        // can't expect eq(task) as a parameter, because creationDate will differ
        session.expects(once()).method("save").with(isA(TaskInstance.class));
        // can't expect eq(task) as a parameter, because creationDate will differ
        actionMan.expects(once()).method("getTaskAction").with(isA(TaskInstance.class), eq(TaskEvent.instanciated)).will(returnValue(null));

        final TaskMan taskMan = new HibernatedTaskMan((Session) session.proxy(), (TaskActionManager) actionMan.proxy(), (IdGenerator) idGen.proxy());
        final TaskInstance taskInstance = taskMan.newTaskInstance("myTaskDef", null, "some specific task name", "some specific desc");
        assertEquals("generatedID", taskInstance.getId());
        assertEquals("some specific task name", taskInstance.getName());
        assertEquals("some specific desc", taskInstance.getDescription());
        assertEquals(getDummyTaskDef(), taskInstance.getTaskDef());
    }

    public void testNewTaskInstanceWithUnknownTaskDefThrowsIllegalStateException() {
        Mock session = mock(Session.class);
        Mock actionMan = mock(TaskActionManager.class);
        Mock idGen = mock(IdGenerator.class);

        expectCriteriaForLatestTaskDef(session, null);
        idGen.expects(never());
        actionMan.expects(never());

        final TaskMan taskMan = new HibernatedTaskMan((Session) session.proxy(), (TaskActionManager) actionMan.proxy(), (IdGenerator) idGen.proxy());

        try {
            taskMan.newTaskInstance("myTaskDef", null, "some specific task name", "some specific desc");
            fail("Should have thrown an IllegalStateException");
        } catch (IllegalStateException e) {
            assertEquals("TaskDef with name myTaskDef does not exist.", e.getMessage());
        }
    }

    public void testFindRemainingTasksIsNotImplementedYet() {
        Mock session = mock(Session.class);
        Mock crit = mock(Criteria.class);
        session.expects(once()).method("createCriteria").with(eq(TaskInstance.class)).will(returnValue(crit.proxy()));

        final TaskMan taskMan = new HibernatedTaskMan((Session) session.proxy(), null);

        try {
            taskMan.findRemainingTasks();
            fail("darn, i thought this wasn't implemented yet?");
        } catch (IllegalStateException e) {
            assertEquals("darn, i thought this wasn't implemented yet?", "not implemented yet", e.getMessage());
        }

        // TODO : more to test ?
    }

    private void expectCriteriaForLatestTaskDef(final Mock session, final TaskDef taskDefToReturn) {
        Mock criteria = mock(Criteria.class);
        session.expects(once()).method("createCriteria").with(eq(TaskDefImpl.class)).will(returnValue(criteria.proxy()));
        criteria.expects(once()).method("add").with(isA(SimpleExpression.class)).will(returnValue(criteria.proxy()));
        criteria.expects(once()).method("addOrder").with(isA(Order.class)).will(returnValue(criteria.proxy()));
        criteria.expects(once()).method("setMaxResults").with(eq(1)).will(returnValue(criteria.proxy()));
        criteria.expects(once()).method("uniqueResult").will(returnValue(taskDefToReturn));
    }
}
