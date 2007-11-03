package net.incongru.berkano.security.seraph;

import com.atlassian.seraph.auth.DefaultAuthenticator;
import com.atlassian.seraph.auth.RoleMapper;
import com.atlassian.seraph.interceptor.LogoutInterceptor;
import net.incongru.berkano.user.User;
import net.incongru.berkano.user.UserDAO;
import net.incongru.berkano.security.password.PasswordMatchingStrategy;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;

/**
 * A Seraph Authenticator based on berkano-user.
 *
 * @see com.atlassian.seraph.auth.Authenticator
 * @see com.atlassian.seraph.auth.DefaultAuthenticator
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.4 $
 */
public class BerkanoUserAuthenticator extends DefaultAuthenticator {
    private UserDAO userDAO;
    private PasswordMatchingStrategy passwordMatchingStrategy;
    private RoleMapper roleMapper;
    private List logoutInterceptors;

    public BerkanoUserAuthenticator(UserDAO userDAO, PasswordMatchingStrategy passwordMatchingStrategy, RoleMapper roleMapper) {
        this(userDAO, passwordMatchingStrategy, roleMapper, new LogoutInterceptor[0]);
    }

    public BerkanoUserAuthenticator(UserDAO userDAO, PasswordMatchingStrategy passwordMatchingStrategy, RoleMapper roleMapper, LogoutInterceptor[] logoutInterceptors) {
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
        return "berkano-seraph-login";
    }

    protected List getLogoutInterceptors() {
        return logoutInterceptors;
    }

    protected String encodeCookie(String username, String password) {
        final String encoded = super.encodeCookie(username, password);
        return new String(Base64.encodeBase64(encoded.getBytes()));
    }

    protected String[] decodeCookie(String base64value) {
        final String value = new String(Base64.decodeBase64(base64value.getBytes()));
        return super.decodeCookie(value);
    }
}
