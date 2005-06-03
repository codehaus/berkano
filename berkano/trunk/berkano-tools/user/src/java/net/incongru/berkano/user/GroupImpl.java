package net.incongru.berkano.user;

import net.incongru.berkano.security.Role;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.9 $
 */
public class GroupImpl implements Group, PropertiesAware {
    private Long groupId;
    private String groupName;
    private Set users;
    private Set roles;
    private Map properties;

    public GroupImpl() {
        this.roles = new HashSet();
        this.properties = new HashMap();
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Set getUsers() {
        return users;
    }

    public void setUsers(Set users) {
        this.users = users;
    }

    public Set getRoles() {
        return roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }

    public void setRoles(Set roles) {
        this.roles = roles;
    }

    public Object getProperty(String key) {
        return properties.get(key);
    }

    public void setProperty(String key, Object object) {
        properties.put(key, object);
    }

    public void removeProperty(String key) {
        properties.remove(key);
    }

    public Map getProperties() {
        return properties;
    }

    private void setProperties(Map properties) {
        this.properties = properties;
    }
}
