package net.incongru.taskman;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public interface TaskLog extends Serializable {
    DateTime getDateTime();

    TaskEvent getTaskEvent();

    String getOldValue();

    String getNewValue();
}
