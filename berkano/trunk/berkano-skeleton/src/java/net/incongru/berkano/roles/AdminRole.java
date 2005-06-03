package net.incongru.swaf.roles;

import net.incongru.swaf.security.AbstractRole;

/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class AdminRole extends AbstractRole {
    public String getName() {
        return "admin";
    }

    public String[] getAllowedPermissions() {
        return new String[]{"add_user", "del_user", "add_user_prop", "del_user_prop",
                            "add_group_prop", "del_group_prop", "assign_user_groups"};
    }
}
