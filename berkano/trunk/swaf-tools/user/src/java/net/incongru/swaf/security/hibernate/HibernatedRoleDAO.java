package net.incongru.swaf.security.hibernate;

import net.incongru.swaf.app.Application;
import net.incongru.swaf.security.Role;
import net.incongru.swaf.security.RoleDAO;
import net.incongru.swaf.security.RoleImpl;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

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
        try {
            return (Role) session.get(RoleImpl.class, name);
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public List listAllRoles() {
        try {
            return session.createCriteria(RoleImpl.class).list();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }
}
