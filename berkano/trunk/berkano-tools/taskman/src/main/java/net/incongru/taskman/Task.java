package net.incongru.taskman;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * An immutable interface for Tasks.
 * Mutable implementations should only be used by implementations of {@link TaskMan}
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public interface Task extends Serializable {
    TaskDef getTaskDef();

    String getId();

    String getName();

    String getDescription();

    Assignee getAssignee();

    TaskStatus getStatus(); // ? this is redundant with the various dates + dates are redundant with logs ...

    DateTime getDueDate(); // ? this might be better of as a Period in TaskDef ?

    Period getReminderPeriod(); // ? this might be better of as a Period in TaskDef ?

    DateTime getLastReminder(); // ?

    DateTime getDueDateTimeout(); // ? this might be better of as a Period in TaskDef ?

    DateTime getCreationDateTime();

    DateTime getStartDateTime();

    DateTime getEndDateTime();

    DateTime getCancelDateTime();

    Map<String, Object> getVariables();

    List<TaskLog> getLog();
}
