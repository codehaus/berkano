package net.incongru.taskman;

import org.joda.time.DateTime;

/**
 * .. some thing to keep a trace of events, assignements etc ?
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public class TaskLog {
    private DateTime dateTime;
    private TaskEvent taskEvent;
    private String oldValue;
    private String newValue;
}
