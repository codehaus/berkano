package net.incongru.swaf.user;

import java.util.List;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.13 $
 */
public interface UserDAO {
    // this could return null instead of throwing an exception, but the exception allows to get some context/detail... is this any useful?
    User getUserById(Long userId) throws UnknownUserException;

    /**
     * Returns null if no such user was found.
     */
    User getUserByName(String userName);

    /**
     * Returns null if no such user was found.
     */
    User getUserByEmail(String email);

    boolean removeUser(Long userId) throws UnknownUserException;

    List listAllUsers();

    void addProperty(Long userId, String propertyKey, Object value) throws UnknownUserException;

    void addProperty(User user, String propertyKey, Object value) throws UnknownUserException;

    void removeProperty(Long userId, String propertyKey) throws UnknownUserException;

    User newUser(String userName, String password, String email, String fullName);

    User updateUser(Long userId, String userName, String email, String fullName) throws UnknownUserException;

    void changePassword(Long userId, String newPassword) throws UnknownUserException;

    void addToGroup(Long userId, Long groupId) throws UnknownUserException;

    void removeFromGroup(Long userId, Long groupId) throws UnknownUserException;

}
