package net.incongru.berkano.security;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class DummyRoleSet implements RoleSet {
    public Role[] getRoles() {
//        return (Role[]) DummyRole.values();
        return null;
    }
}
