package net.incongru.swaf.usermgt;

import net.incongru.swaf.security.AbstractRole;

/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class UserMgtRole extends AbstractRole {
    public String getName() {
        return "user_manager";
    }

    public String[] getAllowedPermissions() {
        return new String[]{"add_user", "del_user", "add_user_prop", "del_user_prop",
                            "add_group_prop", "del_group_prop", "assign_user_groups", "assign_group_roles"};
    }
}
