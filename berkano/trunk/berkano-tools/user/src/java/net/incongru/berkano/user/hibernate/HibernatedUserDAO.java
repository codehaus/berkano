package net.incongru.berkano.user.hibernate;

import net.incongru.berkano.user.GroupImpl;
import net.incongru.berkano.user.PropertiesAware;
import net.incongru.berkano.user.UnknownUserException;
import net.incongru.berkano.user.User;
import net.incongru.berkano.user.UserDAO;
import net.incongru.berkano.user.UserImpl;
import net.incongru.berkano.security.password.PasswordMatchingStrategy;
import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.LockMode;
import net.sf.hibernate.Session;
import net.sf.hibernate.expression.Expression;

import java.util.List;

/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.15 $
 */
public class HibernatedUserDAO extends AbstractHibernatedDAO implements UserDAO {
    private PasswordMatchingStrategy passwordMatchingStrategy;

    public HibernatedUserDAO(Session session, PasswordMatchingStrategy passwordMatchingStrategy) {
        super(session);
        this.passwordMatchingStrategy = passwordMatchingStrategy;
    }

    protected PropertiesAware getById(Long id) throws UnknownUserException {
        return (PropertiesAware) getUserById(id);
    }

    public void addProperty(User user, String propertyKey, Object value) throws UnknownUserException {
        try {
            session.lock(user, LockMode.READ);
            ((UserImpl) user).getProperties().put(propertyKey, value);
            session.save(user);
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserById(Long userId) throws UnknownUserException {
        try {
            User user = (User) session.get(UserImpl.class, userId);
            if (user == null) {
                throw new UnknownUserException(userId);
            }
            return user;
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Gets the user by name, in a case-insensitive manner.
     */
    public User getUserByName(String userName) {
        return getUserByField("userName", userName);
    }

    public User getUserByEmail(String email) {
        return getUserByField("email", email);
    }

    private User getUserByField(String fieldName, String value) {
        try {
            Criteria criteria = session.createCriteria(UserImpl.class);
            criteria.add(Expression.eq(fieldName, value).ignoreCase());
            User user = (User) criteria.uniqueResult();
            return user;
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean removeUser(Long userId) throws UnknownUserException {
        try {
            UserImpl user = (UserImpl) session.load(UserImpl.class, userId);
            session.delete(user);
            if (user == null) {
                throw new UnknownUserException(userId);
            }
            return true;
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public List listAllUsers() {
        try {
            List list = session.createCriteria(UserImpl.class).list();
            return list;
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }

    }

    public User newUser(String userName, String password, String email, String fullName) {
        try {
            UserImpl newUser = new UserImpl();
            newUser.setUserName(userName);
            newUser.setPassword(passwordMatchingStrategy.encode(password));
            newUser.setEmail(email);
            newUser.setFullName(fullName);
            session.save(newUser);
            return newUser;
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public User updateUser(Long userId, String userName, String email, String fullName) throws UnknownUserException {
        try {
            UserImpl user = (UserImpl) getUserById(userId);
            user.setUserName(userName);
            user.setFullName(fullName);
            user.setEmail(email);
            session.update(user);
            return user;
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public void changePassword(Long userId, String newPassword) throws UnknownUserException {
        try {
            UserImpl user = (UserImpl) getUserById(userId);
            user.setPassword(passwordMatchingStrategy.encode(newPassword));
            session.update(user);
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public void addToGroup(Long userId, Long groupId) throws UnknownUserException {
        try {
            GroupImpl group = (GroupImpl) session.get(GroupImpl.class, groupId);
            UserImpl user = (UserImpl) getUserById(userId);
            user.getGroups().add(group);
            group.getUsers().add(user);
            session.save(user);
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeFromGroup(Long userId, Long groupId) throws UnknownUserException {
        try {
            GroupImpl group = (GroupImpl) session.get(GroupImpl.class, groupId);
            UserImpl user = (UserImpl) getUserById(userId);
            user.getGroups().remove(group);
            group.getUsers().remove(user);
            session.save(user);
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

}
