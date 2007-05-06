package net.incongru.taskman;

import org.joda.time.Period;

/**
 * TaskSweeperRunner implementations are responsible for
 * scheduling the regular execution of TaskSweeper(s).
 *
 * TODO : keep a log of reminders ?
 * TODO : how do we know which tasks we already reminded ? using getLastReminder ?
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public interface TaskSweeperRunner {
    /**
     * @return the period between two sweeps.
     */
    Period getPeriod();

    /**
     * Sets the period during runtime. This should change the frequency of sweepings without having
     * the user call (re)start or anything.
     * TODO : do we really want this? adds probably unnecessary complexity to impls.
     * @param period
     */
    //void setPeriod(Period period);

    //TODO void getLog(); // ? limit in size ? store it ?
}
