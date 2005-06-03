package net.incongru.swaf.user;

import java.util.Set;

/**
 * A Group is a group of users. A group can have roles and "lonely" permissions, 
 * which override those from the roles. Each implementation must provide a way
 * of persisting the group's data.
 *
 *
 * @author     gjoseph
 * @author     $Author: gj $ (last edit)
 * @version    $Revision: 1.9 $
 */
public interface Group {
    public Long getGroupId();

    public String getGroupName();

    public Set getUsers(); // Set<User>

    public Set getRoles(); // Collection<Role>
}
