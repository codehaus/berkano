package net.incongru.swaf.roles;

import net.incongru.swaf.security.AbstractRole;

/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class DummyRole extends AbstractRole {
    public String getName() {
        return "dummy";
    }

    public String[] getAllowedPermissions() {
        return new String[]{"blah", "foo",
                            "bar"};
    }
}
