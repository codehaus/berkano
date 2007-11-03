package net.incongru.taskman;

import net.incongru.taskman.def.TaskDef;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * An immutable interface for Tasks.
 * Mutable implementations should only be used by implementations of {@link TaskMan}
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public interface TaskInstance extends Serializable {
    TaskDef getTaskDef();

    String getId();

    String getName(); // ? redundant w/ TaskDef ?

    String getDescription();

    String getReporter();

    Assignee getAssignee();

    DateTime getDueDate(); // ? this is slightly redundant with TaskDef's duePeriod + log's creationDate ...

    DateTime getLastReminder(); // ? is this the right place to store this ?

    /**
     * Returns an unmodifiable view, to ensure that variables are only added through a TaskMan instance,
     * thus ensuring event propagation.
     */
    Set<String> getVariableNames();

    Object getVariable(String name);

    TaskEvent getStatus();

    List<TaskLog> getLog();
}
