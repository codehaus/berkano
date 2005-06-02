package net.incongru.swaf.security;

/**
 * A set of roles. Tied to a particular application. It only defines all possible
 * roles in a given application, but roles are still assigned one by one to groups.
 * RoleSets are just used to get available roles.
 * Can be configured by files, by code, but it should not be end-user configurable
 * (ie, not through the web ui)
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.4 $
 *
 * @deprecated if we use enums to define permissions and roles, they're available through PermissionClass.family() or PermissionClass.VALUES
 */
public interface RoleSet {
//    private Collection roles; // Collection<Role>
//
//    public RoleSet(Collection roles) { // Collection<Role>
//        this.roles = roles;
//    }
//
    public Role[] getRoles();
//    public Collection getRoles() { // Collection<Role>
//        return roles;
//    }
}
