package net.incongru.berkano.user;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This is an hibernated implementation of User.
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.12 $
 */
public class UserImpl implements User, PropertiesAware {
    private Long userId;
    private String userName;
    private String password;
    private String email;
    private String fullName;
    private Set groups;
    private Map properties;

    public UserImpl() {
        groups = new HashSet(); // new HashSet<Group>();
        properties = new HashMap();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set getGroups() { // Set<Group>
        return groups;
    }

    public void setGroups(Set groups) { // Set<Group>
        this.groups = groups;
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public void removeGroup(Group group) {
        groups.remove(group);
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
