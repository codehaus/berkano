package net.incongru.berkano.user.hibernate;

import net.incongru.berkano.security.password.PasswordMatchingStrategy;
import net.incongru.berkano.user.GroupImpl;
import net.incongru.berkano.user.PropertiesAware;
import net.incongru.berkano.user.UnknownUserException;
import net.incongru.berkano.user.User;
import net.incongru.berkano.user.UserDAO;
import net.incongru.berkano.user.UserImpl;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

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
        session.lock(user, LockMode.NONE);
        ((UserImpl) user).getProperties().put(propertyKey, value);
        session.save(user);
    }

    public User getUserById(Long userId) throws UnknownUserException {
        User user = (User) session.get(UserImpl.class, userId);
        if (user == null) {
            throw new UnknownUserException(userId);
        }
        return user;
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
        Criteria criteria = session.createCriteria(UserImpl.class);
        criteria.add(Expression.eq(fieldName, value).ignoreCase());
        return (User) criteria.uniqueResult();
    }

    public boolean removeUser(Long userId) throws UnknownUserException {
        UserImpl user = (UserImpl) session.load(UserImpl.class, userId);
        session.delete(user);
        if (user == null) {
            throw new UnknownUserException(userId);
        }
        return true;
    }

    public List listAllUsers() {
        return session.createCriteria(UserImpl.class).list();
    }

    public User newUser(String userName, String cleanPassword, String email, String fullName) {
        UserImpl newUser = new UserImpl();
        newUser.setUserName(userName);
        newUser.setPassword(passwordMatchingStrategy.encode(cleanPassword));
        newUser.setEmail(email);
        newUser.setFullName(fullName);
        session.save(newUser);
        return newUser;
    }

    public User updateUser(Long userId, String userName, String email, String fullName) throws UnknownUserException {
        UserImpl user = (UserImpl) getUserById(userId);
        user.setUserName(userName);
        user.setFullName(fullName);
        user.setEmail(email);
        session.update(user);
        return user;
    }

    public void changePassword(Long userId, String newPassword) throws UnknownUserException {
        UserImpl user = (UserImpl) getUserById(userId);
        user.setPassword(passwordMatchingStrategy.encode(newPassword));
        session.update(user);
    }

    public void addToGroup(Long userId, Long groupId) throws UnknownUserException {
        GroupImpl group = (GroupImpl) session.get(GroupImpl.class, groupId);
        UserImpl user = (UserImpl) getUserById(userId);
        user.getGroups().add(group);
        group.getUsers().add(user);
        session.save(user);
    }

    public void removeFromGroup(Long userId, Long groupId) throws UnknownUserException {
        GroupImpl group = (GroupImpl) session.get(GroupImpl.class, groupId);
        UserImpl user = (UserImpl) getUserById(userId);
        user.getGroups().remove(group);
        group.getUsers().remove(user);
        session.save(user);
    }

}
