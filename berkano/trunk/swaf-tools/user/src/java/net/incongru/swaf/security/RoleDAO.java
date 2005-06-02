package net.incongru.swaf.security;

import net.incongru.swaf.app.Application;

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
}
