package net.incongru.berkano.user.hibernate;

import net.incongru.berkano.user.Group;
import net.incongru.berkano.user.GroupDAO;
import net.incongru.berkano.user.GroupImpl;
import net.incongru.berkano.user.PropertiesAware;
import net.incongru.berkano.user.UnknownUserException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

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
        return (Group) session.get(GroupImpl.class, groupId);
    }

    public Group getGroupByName(String groupName) {
        Criteria criteria = session.createCriteria(GroupImpl.class);
        criteria.add(Expression.eq("groupName", groupName).ignoreCase());
        return (Group) criteria.uniqueResult();
    }

    public Group newGroup(String groupName) {
        GroupImpl g = new GroupImpl();
        g.setGroupName(groupName);
        session.save(g);
        return g;
    }

    public boolean removeGroup(Long groupId) {
        // todo : what is the difference between the get and load methods!?
        //session.get(GroupImpl.class, groupId);
        Object group = session.load(GroupImpl.class, groupId);
        session.delete(group);
        return true;
    }

    public List listAllGroups() {
        return session.createCriteria(GroupImpl.class).list();
    }

    public void assignRoles(Long groupId, Set roles) {
        GroupImpl g = (GroupImpl) getGroupById(groupId);
        g.setRoles(roles);
        session.save(g);
    }
}
