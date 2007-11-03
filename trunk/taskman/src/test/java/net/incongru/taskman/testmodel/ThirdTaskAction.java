package net.incongru.taskman.testmodel;

import net.incongru.taskman.action.TaskAction;
import net.incongru.taskman.action.TaskContext;

/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public class ThirdTaskAction implements TaskAction {
    public void execute(TaskContext taskContext) {
        System.out.println("ThirdTaskAction.execute(taskContext) taskContext = " + taskContext);
    }
}
