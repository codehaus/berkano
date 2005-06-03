package net.incongru.berkano.user;

import java.util.List;
import java.util.Set;

/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.7 $
 */
public interface GroupDAO {
    public Group getGroupById(Long groupId);

    public Group getGroupByName(String groupName);

    public Group newGroup(String groupName);

    public boolean removeGroup(Long groupId);

    public List listAllGroups();

    void addProperty(Long userId, String propertyKey, Object value) throws UnknownUserException;

    void removeProperty(Long userId, String propertyKey) throws UnknownUserException;

    void assignRoles(Long groupId, Set roles);
}
