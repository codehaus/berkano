package net.incongru.taskman.testmodel;

import net.incongru.taskman.action.TaskAction;
import net.incongru.taskman.action.TaskContext;

/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public class SecondTaskAction implements TaskAction {
    public void execute(TaskContext taskContext) {
        System.out.println("FirstTaskAction.execute(taskContext) taskContext = " + taskContext);
    }
}
