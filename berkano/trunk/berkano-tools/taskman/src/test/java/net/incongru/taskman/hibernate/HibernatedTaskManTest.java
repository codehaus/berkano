package net.incongru.taskman.hibernate;

import net.incongru.taskman.AbstractTaskManTestCase;
import net.incongru.taskman.TaskAction;
import net.incongru.taskman.TaskActionManager;
import net.incongru.taskman.TaskContext;
import net.incongru.taskman.TaskEvent;
import net.incongru.taskman.TaskInstance;
import net.incongru.taskman.TaskInstanceImpl;
import net.incongru.taskman.TaskLog;
import net.incongru.taskman.def.TaskDef;
import org.hibernate.Criteria;
import org.hibernate.Session;
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
        session.expects(once()).method("load").with(eq(TaskInstance.class), eq("foo")).will(returnValue(getDummyTaskInstance(getDummyTaskDef())));

        final HibernatedTaskMan taskMan = new HibernatedTaskMan((Session) session.proxy(), null);
        taskMan.getTaskById("foo");
    }

    public void testEventDispatchingDoesnotMoveWhenNoActionForSpecificEvent() {
        Mock session = mock(Session.class);
        Mock taskDef = mock(TaskDef.class);
        Mock actionMan = mock(TaskActionManager.class);

        final TaskInstance task = getDummyTaskInstance((TaskDef) taskDef.proxy());
        session.expects(once()).method("merge").with(eq(task));
        actionMan.expects(once()).method("getTaskAction").with(eq(task), eq(TaskEvent.cancelled)).will(returnValue(null));

        assertEquals(0, task.getLog().size());
        final HibernatedTaskMan taskMan = new HibernatedTaskMan((Session) session.proxy(), (TaskActionManager) actionMan.proxy());

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
        session.expects(once()).method("merge").with(eq(task));
        actionMan.expects(once()).method("getTaskAction").with(eq(task), eq(TaskEvent.started)).will(returnValue(action.proxy()));
        action.expects(once()).method("execute").with(isA(TaskContext.class));
        assertEquals(0, task.getLog().size());
        final HibernatedTaskMan taskMan = new HibernatedTaskMan((Session) session.proxy(), (TaskActionManager) actionMan.proxy());
        taskMan.start(task);

        assertEquals(1, task.getLog().size());
        final TaskLog taskLog = task.getLog().get(0);
        //assertEquals(taskLog.getDateTime());
        assertEquals(TaskEvent.started, taskLog.getTaskEvent());
        assertEquals(null, taskLog.getOldValue());
        assertEquals(null, taskLog.getNewValue());
    }

    public void testNewTaskInstanceGeneratesAnIdIfNonePassed() {
        //TODO
    }

    public void testNewTaskInstanceUsesTheGivenTaskId() {
        Mock session = mock(Session.class);
        Mock actionMan = mock(TaskActionManager.class);

        TaskInstanceImpl task = new TaskInstanceImpl();
        task.setId("myId");
        task.setName("some specific task name");
        task.setDescription("some specific desc");
        task.setTaskDef(getDummyTaskDef());

        session.expects(once()).method("load").with(eq(TaskDef.class), eq("TASKDEF-ID")).will(returnValue(getDummyTaskDef()));
        // can't expect eq(task) as a parameter, because creationDate will differ
        session.expects(once()).method("save").with(isA(TaskInstance.class));

        // can't expect eq(task) as a parameter, because creationDate will differ
        actionMan.expects(once()).method("getTaskAction").with(isA(TaskInstance.class), eq(TaskEvent.instanciated)).will(returnValue(null));

        final HibernatedTaskMan taskMan = new HibernatedTaskMan((Session) session.proxy(), (TaskActionManager) actionMan.proxy());
        final TaskInstance taskInstance = taskMan.newTaskInstance("TASKDEF-ID", "myId", "some specific task name", "some specific desc");
        assertEquals("myId", taskInstance.getId());
        assertEquals("some specific task name", taskInstance.getName());
        assertEquals("some specific desc", taskInstance.getDescription());
        assertEquals(getDummyTaskDef(), taskInstance.getTaskDef());
    }

//    public void testFindRemainingTasks() {
//        Mock session = mock(Session.class);
//        Mock crit = mock(Criteria.class);
//        session.expects(once()).method("createCriteria").with(eq(TaskInstance.class)).will(returnValue(crit.proxy()));
//
//        final HibernatedTaskMan taskMan = new HibernatedTaskMan((Session) session.proxy(), null);
//        taskMan.findRemainingTasks();
//    }

}
