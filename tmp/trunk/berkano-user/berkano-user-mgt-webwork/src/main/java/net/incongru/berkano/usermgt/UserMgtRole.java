package net.incongru.berkano.usermgt;

import net.incongru.berkano.security.Role;

/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 *
 * @deprecated currently unused - staying there for now just as a reminder
 */
public class UserMgtRole implements Role {
    public String getName() {
        return "user_manager";
    }

    public String[] getAllowedPermissions() {
        return new String[]{"add_user", "del_user", "add_user_prop", "del_user_prop",
                            "add_group_prop", "del_group_prop", "assign_user_groups", "assign_group_roles"};
    }
}
