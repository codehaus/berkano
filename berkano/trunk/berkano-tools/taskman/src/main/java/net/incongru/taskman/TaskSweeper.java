package net.incongru.taskman;

import org.joda.time.Period;

/**
 * TaskSweeper implementations are responsible for regularly
 * collecting remaining tasks and pushing out reminders.
 * Usually implemented with a Thread/Runnable.
 *
 * TODO : keep a log of reminders ?
 * TODO : how do we know which tasks we already reminded ? using getLastReminder ?
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public interface TaskSweeper {
    /**
     * @return the period between two sweeps.
     */
    Period getPeriod();

    void setPeriod(Period period);

    void sweepAndRemind();

    void getLog(); // ? limit in size ? store it ?
}
