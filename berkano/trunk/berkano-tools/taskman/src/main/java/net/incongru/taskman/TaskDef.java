package net.incongru.taskman;

import org.joda.time.DateTime;

import java.util.Map;

/**
 * A TaskDef is a task definition: once deployed, it can tell TaskMan what TaskAction
 * to take upon given events.
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public interface TaskDef {
    String getId();

    DateTime getDeploymentDateTime();

    /**
     * A generic name for this task definition, thus shared by all instances of
     * this definition.
     */
    String getName();

    /**
     * A map mapping events to TaskAction classes to be executed.
     */
    Map<TaskEvent, Class<TaskAction>> getEventActionMap();
}
