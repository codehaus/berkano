package net.incongru.taskman;

import net.incongru.taskman.def.TaskDef;
import net.incongru.taskman.def.TaskDefImpl;
import net.incongru.taskman.testmodel.FirstTaskAction;
import net.incongru.taskman.testmodel.SecondTaskAction;
import org.jmock.MockObjectTestCase;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public abstract class AbstractTaskManTestCase extends MockObjectTestCase {

    protected TaskInstanceImpl getDummyTaskInstance(TaskDef taskDef) {
        final TaskInstanceImpl task = new TaskInstanceImpl();
        task.setDescription("some task instance desc");
        task.setName("some task instance name");
        task.setDueDate(new DateTime());
        task.setId("task instance id");
        task.setLastReminder(null);
        task.setTaskDef(taskDef);
        return task;
    }

    protected TaskDef getDummyTaskDef() {
        final TaskDefImpl taskDefImpl = new TaskDefImpl();
        taskDefImpl.setName("some name");
        taskDefImpl.setDescription("some desc");
        taskDefImpl.setDuePeriod(Period.weeks(2));
        taskDefImpl.setDueDateTimeout(Period.days(3));
        taskDefImpl.setReminderPeriod(Period.minutes(10));
        Map<TaskEvent, Class<? extends TaskAction>> map = new HashMap<TaskEvent, Class<? extends TaskAction>>();
        map.put(TaskEvent.started, FirstTaskAction.class);
        map.put(TaskEvent.cancelled, SecondTaskAction.class);
        taskDefImpl.setEventActionMap(map);
        return taskDefImpl;
    }
}
