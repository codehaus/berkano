package net.incongru.berkano.security;

import net.incongru.berkano.user.Group;
import net.incongru.berkano.user.User;

import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 *
 * @deprecated
 */
public abstract class AbstractSecurityGateway implements SecurityGateway {

//    public boolean hasPermission(User user, Permission permission) {

    public boolean hasPermission(User user, String permission) {
        //Set<Group> groups = user.getGroups();
        //Iterator<Group> groupsIt = groups.iterator();
        Set groups = user.getGroups();
        Iterator groupsIt = groups.iterator();
        while (groupsIt.hasNext()) {
            Group group = (Group) groupsIt.next();
            //Iterator<Role> rolesIt = group.getRoles().iterator();
            Iterator rolesIt = group.getRoles().iterator();
            while (rolesIt.hasNext()) {
                Role role = (Role) rolesIt.next();
//                Permission[] allowedPermissions = role.getAllowedPermissions();
//                String[] allowedPermissions = role.getAllowedPermissions();
//                for (int i = 0; i < allowedPermissions.length; i++) {
//                    if (allowedPermissions[i].equals(permission)) {
//                        return true;
//                    }
//                }
            }
        }
        return false;
    }
}
