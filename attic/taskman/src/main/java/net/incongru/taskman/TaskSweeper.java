package net.incongru.taskman;

/**
 * TaskSweeper implementations are responsible for
 * collecting remaining tasks and pushing out reminders.
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public interface TaskSweeper {
    void sweepAndRemind();
}
