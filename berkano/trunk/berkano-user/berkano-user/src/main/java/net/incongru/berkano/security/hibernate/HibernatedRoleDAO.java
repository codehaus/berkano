package net.incongru.berkano.security.hibernate;

import net.incongru.berkano.app.Application;
import net.incongru.berkano.security.Role;
import net.incongru.berkano.security.RoleDAO;
import net.incongru.berkano.security.RoleImpl;
import org.hibernate.Session;

import java.util.Collection;
import java.util.List;

/**
 * 
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class HibernatedRoleDAO implements RoleDAO {
    private Session session;

    public HibernatedRoleDAO(Session session) {
        this.session = session;
    }

    public Collection getRoles(Application app) {
        throw new IllegalStateException("not implemented yet");
    }

    public Role getRole(String name) {
        return (Role) session.get(RoleImpl.class, name);
    }

    public List listAllRoles() {
        return session.createCriteria(RoleImpl.class).list();
    }
}
