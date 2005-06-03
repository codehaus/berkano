package net.incongru.swaf.security.seraph;

import com.atlassian.seraph.auth.DefaultAuthenticator;
import com.atlassian.seraph.auth.RoleMapper;
import com.atlassian.seraph.interceptor.LogoutInterceptor;
import net.incongru.swaf.user.User;
import net.incongru.swaf.user.UserDAO;
import net.incongru.swaf.security.password.PasswordMatchingStrategy;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Arrays;

/**
 * A Seraph Authenticator based on swaf-user.
 *
 * @see com.atlassian.seraph.auth.Authenticator
 * @see com.atlassian.seraph.auth.DefaultAuthenticator
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.4 $
 */
public class SwafUserAuthenticator extends DefaultAuthenticator {
    private UserDAO userDAO;
    private PasswordMatchingStrategy passwordMatchingStrategy;
    private RoleMapper roleMapper;
    private List logoutInterceptors;

    public SwafUserAuthenticator(UserDAO userDAO, PasswordMatchingStrategy passwordMatchingStrategy, RoleMapper roleMapper) {
        this(userDAO, passwordMatchingStrategy, roleMapper, new LogoutInterceptor[0]);
    }

    public SwafUserAuthenticator(UserDAO userDAO, PasswordMatchingStrategy passwordMatchingStrategy, RoleMapper roleMapper, LogoutInterceptor[] logoutInterceptors) {
        this.userDAO = userDAO;
        this.passwordMatchingStrategy = passwordMatchingStrategy;
        this.roleMapper = roleMapper;
        this.logoutInterceptors = Arrays.asList(logoutInterceptors);
    }

    protected Principal getUser(String username) {
        return userDAO.getUserByName(username);
    }

    protected boolean authenticate(Principal user, String password) {
        return passwordMatchingStrategy.matches(password, (User) user);
    }

    protected RoleMapper getRoleMapper() {
        return roleMapper;
    }

    public boolean isUserInRole(HttpServletRequest request, String role) {
        throw new IllegalStateException("GET OUT OF HERE");
    }

    protected String getLoginCookieKey() {
        return "swaf-seraph-login";
    }

    protected List getLogoutInterceptors() {
        return logoutInterceptors;
    }
}
