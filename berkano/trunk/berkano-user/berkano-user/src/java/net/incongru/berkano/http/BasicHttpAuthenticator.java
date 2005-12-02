package net.incongru.berkano.http;

import net.incongru.berkano.security.Authenticator;
import net.incongru.berkano.security.password.PasswordMatchingStrategy;
import net.incongru.berkano.user.UnknownUserException;
import net.incongru.berkano.user.User;
import net.incongru.berkano.user.UserDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.4 $
 *
 * @deprecated currently unused - staying there for now just as a reminder
 */
public class BasicHttpAuthenticator implements Authenticator {
    private UserDAO userDAO;
    private PasswordMatchingStrategy passwordMatchingStrategy;
    private HttpServletRequest req;
    private static final String USER_KEY = "berkano.user";

    public BasicHttpAuthenticator(HttpServletRequest req, UserDAO userDAO, PasswordMatchingStrategy passwordMatchingStrategy) {
        this.req = req;
        this.userDAO = userDAO;
        this.passwordMatchingStrategy = passwordMatchingStrategy;
    }

    public boolean authenticate() throws UnknownUserException {
        String username = req.getParameter("berkano.username");
        String password = req.getParameter("berkano.password");
        User user = userDAO.getUserByName(username);
        if (user != null && passwordMatchingStrategy.matches(password, user)) {
            req.getSession(true).setAttribute(USER_KEY, user);
            return true;
        }
        return false;
    }

    public boolean logout() {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.removeAttribute(USER_KEY);
        }
        return true;
    }
}
