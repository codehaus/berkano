package net.incongru.berkano.security.seraph;

import com.atlassian.seraph.auth.RoleMapper;
import com.atlassian.seraph.config.SecurityConfig;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

import net.incongru.berkano.security.Role;


/**
 * An implementation of seraph's RoleMapper that is actually based on
 * roles.
 * (Permissions are a more fine-grained level of security than
 * roles)
 * Each user can be a member of N groups, which in turn can have N
 * roles. (for later: Each role has a fixed set of permissions.)
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class UserGroupRoleMapper implements RoleMapper {
    private UserRoleCache userRoleCache;

    public UserGroupRoleMapper(UserRoleCache userRoleCache) {
        this.userRoleCache = userRoleCache;
    }

    /**
     * Checks if the given user has the given <b>Permission</b>.
     *
     * @param user
     * @param request
     * @param role    is in fact a <b>Permission</b>
     * @return
     */
    public boolean hasRole(Principal user, HttpServletRequest request, String role) {
        Set permissions = userRoleCache.getRoles(user, request);

        if (permissions == null && role == null) {
            return true;
        } else if (permissions == null) {
            return false;
        } else {
            return contains(permissions, role);
        }
    }

    private boolean contains(Set roles, String roleName) {
        Iterator it = roles.iterator();
        while (it.hasNext()) {
            Role role = (Role) it.next();
            if (role.getName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }

    public boolean canLogin(Principal user, HttpServletRequest request) {
        return user != null;
    }

    public void init(Map params, SecurityConfig config) {
        System.out.println("INITING PRM");
    }
}
