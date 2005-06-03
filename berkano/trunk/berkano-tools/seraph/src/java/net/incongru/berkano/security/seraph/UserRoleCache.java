package net.incongru.berkano.security.seraph;

import net.incongru.berkano.user.Group;
import net.incongru.berkano.user.User;
import net.incongru.berkano.security.Role;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * temporarily bypassing the permissions level: user >> groups >> roles
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class UserRoleCache {
    private static final String ROLE_CACHE_KEY = UserRoleCache.class.getName();

    public UserRoleCache() {
    }

    Set getRoles(Principal principal, HttpServletRequest req) {
        if (principal == null) {
            return Collections.EMPTY_SET;
        }
        if (!(principal instanceof User)) {
            throw new IllegalStateException("UserRoleCache can only work with berkano-user!");
        }

        Set permissions = (Set) req.getAttribute(ROLE_CACHE_KEY);
        if (permissions == null) {
            permissions = new HashSet();
            User user = (User) principal;
            Set groups = user.getGroups();
            Iterator it = groups.iterator();
            while (it.hasNext()) {
                Group group = (Group) it.next();
                Set roles = (Set) group.getRoles();
                if (roles != null) {
                    Iterator itRoles = roles.iterator();
                    while (itRoles.hasNext()) {
                        Role role = (Role) itRoles.next();
                        permissions.add(role);
//                        String[] allowedPermissions = role.getAllowedPermissions();
//                        for (int i = 0; i < allowedPermissions.length; i++) {
//                            permissions.add(allowedPermissions[i]);
//                        }
                    }
                }
            }
            req.setAttribute(ROLE_CACHE_KEY, permissions);
        }
        return permissions;
    }
}
