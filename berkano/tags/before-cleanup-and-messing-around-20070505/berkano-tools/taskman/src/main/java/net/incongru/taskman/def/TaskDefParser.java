package net.incongru.taskman.def;

/**
 * Implementations of TaskDefParser should read TaskDefs using
 * some parameter passed in their constructor. (TODO , make this better, maybe)
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public interface TaskDefParser {
    TaskDef loadTaskDesk();

}
