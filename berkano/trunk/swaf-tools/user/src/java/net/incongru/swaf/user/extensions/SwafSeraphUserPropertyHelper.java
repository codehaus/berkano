/***************************************
 *                                     *
 *                                     *
 *     Copyright @2005   KIALA         *
 *                                     *
 *                                     *
 ***************************************/
package net.incongru.swaf.user.extensions;

import com.atlassian.seraph.auth.AuthenticationContext;
import net.incongru.swaf.user.User;
import net.incongru.swaf.user.UserDAO;

/**
 * Helper to store / retrieve user properties of the currently logged in user,
 * using swaf and seraph.
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class SwafSeraphUserPropertyHelper implements UserPropertyHelper {
    private UserDAO userDAO;
    private UserPropertyAccessor userPropertyAccessor;
    private AuthenticationContext authenticationContext;

    public SwafSeraphUserPropertyHelper(UserPropertyAccessor userPropertyAccessor, UserDAO userDAO, AuthenticationContext authenticationContext) {
        this.userPropertyAccessor = userPropertyAccessor;
        this.userDAO = userDAO;
        this.authenticationContext = authenticationContext;
    }

    public void store(String key, Object value) {
        User user = getUser();
        if (user != null) {
            userDAO.addProperty(user, key, value);
        }
    }

    public Object getUserOnlyValue(String key) {
        User user = getUser();
        if (user == null) {
            return null;
        }
        return userPropertyAccessor.getUserOnlyValue(user, key);
    }

    public Object getSingleValue(String key) {
        User user = getUser();
        if (user == null) {
            return null;
        }
        return userPropertyAccessor.getSingleValue(user, key);
    }

    public User getUser() {
        return (User) authenticationContext.getUser();
    }

}
