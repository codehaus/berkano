package net.incongru.taskman.def;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.Map;
import java.io.Serializable;

import net.incongru.taskman.TaskEvent;
import net.incongru.taskman.TaskAction;

/**
 * A TaskDef is a task definition: once deployed, it can tell TaskMan what TaskAction
 * to take upon given events.
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public interface TaskDef extends Serializable {
    Long getId();

    Long getVersionId();

    DateTime getDeploymentDateTime();

    /**
     * A generic name for this task definition, thus shared by all instances of
     * this definition. This is what determines if the version should be incremented
     * (i.e. if a TaskDef with this name already exists)
     */
    String getName();

    String getDescription();

    /**
     * How much time is allowed before the assigne is reminded.
     */
    Period getDuePeriod();

    /**
     * How much time between each reminder.
     */
    Period getReminderPeriod();

    /**
     * How much time before no more reminders are sent and the task is automatically cancelled.
     */
    Period getDueDateTimeout();

    Class<? extends TaskAction> getEventActionClass(TaskEvent event);
}
