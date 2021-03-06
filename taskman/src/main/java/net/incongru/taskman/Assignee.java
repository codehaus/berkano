package net.incongru.taskman;

/**
 * An assignee can be a single person or a group.
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public interface Assignee {
    public enum Type {
        user,group,role
    }

    String getName();

    Type getType();
}
