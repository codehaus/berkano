package net.incongru.taskman;

/**
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public interface TaskAction {
    /**
     * TODO : would pass in a TaskInstance if that wouldn't make it confusing,
     * because it would mean "execute this task".
     */
    void execute(TaskContext taskContext);
}
