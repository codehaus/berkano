package net.incongru.berkano.user.hibernate;

import net.incongru.berkano.user.Group;
import net.incongru.berkano.user.GroupDAO;
import net.incongru.berkano.user.GroupImpl;
import net.incongru.berkano.user.PropertiesAware;
import net.incongru.berkano.user.UnknownUserException;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;
import java.util.Set;

/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.5 $
 */
public class HibernatedGroupDAO extends AbstractHibernatedDAO implements GroupDAO {
    public HibernatedGroupDAO(Session session) {
        super(session);
    }

    protected PropertiesAware getById(Long id) throws UnknownUserException {
        return (PropertiesAware) getGroupById(id);
    }

    public Group getGroupById(Long groupId) {
        try {
            return (Group) session.get(GroupImpl.class, groupId);
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public Group getGroupByName(String groupName) {
        throw new RuntimeException("not implemented yet");
    }

    public Group newGroup(String groupName) {
        try {
            GroupImpl g = new GroupImpl();
            g.setGroupName(groupName);
            session.save(g);
            return g;
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean removeGroup(Long groupId) {
        try {
            // todo : what is the difference between the get and load methods!?
            //session.get(GroupImpl.class, groupId);
            Object group = session.load(GroupImpl.class, groupId);
            session.delete(group);
            return true;
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public List listAllGroups() {
        try {
            return session.createCriteria(GroupImpl.class).list();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public void assignRoles(Long groupId, Set roles) {
        try {
            GroupImpl g = (GroupImpl) getGroupById(groupId);
            g.setRoles(roles);
            session.save(g);
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

}
