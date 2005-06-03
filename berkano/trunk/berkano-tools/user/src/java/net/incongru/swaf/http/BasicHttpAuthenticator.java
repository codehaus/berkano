package net.incongru.swaf.http;

import net.incongru.swaf.security.Authenticator;
import net.incongru.swaf.security.password.PasswordMatchingStrategy;
import net.incongru.swaf.user.UnknownUserException;
import net.incongru.swaf.user.User;
import net.incongru.swaf.user.UserDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.4 $
 */
public class BasicHttpAuthenticator implements Authenticator {
    private UserDAO userDAO;
    private PasswordMatchingStrategy passwordMatchingStrategy;
    private HttpServletRequest req;
    private static final String USER_KEY = "swaf.user";

    public BasicHttpAuthenticator(HttpServletRequest req, UserDAO userDAO, PasswordMatchingStrategy passwordMatchingStrategy) {
        this.req = req;
        this.userDAO = userDAO;
        this.passwordMatchingStrategy = passwordMatchingStrategy;
    }

    public boolean authenticate() throws UnknownUserException {
        String username = req.getParameter("swaf.username");
        String password = req.getParameter("swaf.password");
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
