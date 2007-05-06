package net.incongru.berkano.roles;

import net.incongru.berkano.security.Role;

/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class BookmarkManagerRole implements Role {
    public String getName() {
        return "bookmark_manager";
    }

    public String[] getAllowedPermissions() {
        return new String[]{"add_user_bookmark", "del_user_bookmark",
                            "add_group_bookmark", "del_group_bookmark",
                            "arrange_user_bookmarks", "arrange_group_bookmarks",
                            "setup_default_bookmarks"};
    }
}
