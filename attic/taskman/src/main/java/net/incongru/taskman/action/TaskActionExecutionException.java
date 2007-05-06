package net.incongru.taskman.action;

/**
 * Thrown by TaskAction implementations when they can't execute correctly.
 * Since actions are currently executed synchronously, these should simply
 * be handled by the UI.
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class TaskActionExecutionException extends RuntimeException {
    public TaskActionExecutionException(String message) {
        super(message);
    }

    public TaskActionExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
