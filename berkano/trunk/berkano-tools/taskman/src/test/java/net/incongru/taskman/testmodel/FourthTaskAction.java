package net.incongru.taskman.testmodel;

import net.incongru.taskman.TaskAction;
import net.incongru.taskman.TaskContext;

/**
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public class FourthTaskAction implements TaskAction {
    public void execute(TaskContext taskContext) {
        System.out.println("FourthTaskAction.execute(taskContext) taskContext = " + taskContext);
    }
}
