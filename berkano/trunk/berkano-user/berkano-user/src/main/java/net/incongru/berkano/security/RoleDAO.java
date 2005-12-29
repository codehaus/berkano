package net.incongru.berkano.security;

import net.incongru.berkano.app.Application;

import java.util.Collection;
import java.util.List;

/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.3 $
 */
public interface RoleDAO {
    /**
     * Get the list of roles for the given application.
     */
    public Collection getRoles(Application app); // Collection<Role>

    public Role getRole(String name);

    public List listAllRoles();

    public Role newRole(String name);
}
