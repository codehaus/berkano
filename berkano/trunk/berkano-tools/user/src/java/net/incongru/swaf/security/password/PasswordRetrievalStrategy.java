package net.incongru.swaf.security.password;

import net.incongru.swaf.user.User;

/**
 * 
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public interface PasswordRetrievalStrategy {
    void retrievePassword(User u) throws Exception;

    /**
     * Will usually return "username" or "email", depending
     * on how this strategy works.
     *
     * TODO : does this really belong here ?
     */
    String getRequiredField();
}
