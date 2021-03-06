package net.incongru.berkano.user.extensions;

import com.atlassian.seraph.auth.AuthenticationContext;
import net.incongru.berkano.user.User;
import net.incongru.berkano.user.UserDAO;

/**
 * Helper to store / retrieve user properties of the currently logged in user,
 * using berkano and seraph.
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class BerkanoSeraphUserPropertyHelper implements UserPropertyHelper {
    private final UserDAO userDAO;
    private final UserPropertyAccessor userPropertyAccessor;
    private final AuthenticationContext authenticationContext;

    public BerkanoSeraphUserPropertyHelper(UserPropertyAccessor userPropertyAccessor, UserDAO userDAO, AuthenticationContext authenticationContext) {
        this.userPropertyAccessor = userPropertyAccessor;
        this.userDAO = userDAO;
        this.authenticationContext = authenticationContext;
    }

    public void store(String key, Object value) {
        final User user = getUser();
        if (user != null) {
            userDAO.addProperty(user, key, value);
        }
    }

    public Object getUserOnlyValue(String key) {
        final User user = getUser();
        if (user == null) {
            return null;
        }
        return userPropertyAccessor.getUserOnlyValue(user, key);
    }

    public Object getSingleValue(String key) {
        final User user = getUser();
        if (user == null) {
            return null;
        }
        return userPropertyAccessor.getSingleValue(user, key);
    }

    public User getUser() {
        return (User) authenticationContext.getUser();
    }

}
