package net.incongru.berkano.roles;

import net.incongru.berkano.security.Role;

/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class DummyRole implements Role {
    public String getName() {
        return "dummy";
    }

    public String[] getAllowedPermissions() {
        return new String[]{"blah", "foo",
                            "bar"};
    }
}
