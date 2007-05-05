package net.incongru.berkano.security;

import java.io.Serializable;

/**
 * A role is a bunch of permissions.
 * They get assigned to groups, and are only there for administrative purpose (ie, ease the task of admins)
 * Permissions should not be configurable, at least not at runtime.
 *
 * <strong>You must implement equals and hashcode!!</strong>
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.5 $
 */

public interface Role extends Serializable {
    public String getName();
//    public Permission[] getAllowedPermissions();
    // public String[] getAllowedPermissions();
}

//public class Role {
//    private String name;
//    private Collection permissions; // Collection<Permission>
//
//    public Role(String name, Collection permissions) { // Collection<Permission>
//        this.name = name;
//        this.permissions = permissions;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public Collection getPermissions() { // Collection<Permission>
//        return permissions;
//    }
//}
