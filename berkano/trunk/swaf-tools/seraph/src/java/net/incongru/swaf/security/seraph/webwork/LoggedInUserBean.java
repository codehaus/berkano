package net.incongru.swaf.security.seraph.webwork;

import com.atlassian.seraph.auth.AuthenticationContext;

import java.security.Principal;

/**
 * A simple bean to get the logged in user's name. Might be irrelevant, since
 * seraph wraps the request object and exposes the user in request.getUserPrincipal()
 * 
 * @author Grégory Joseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class LoggedInUserBean {
    private Principal user;

    public LoggedInUserBean(AuthenticationContext authenticationContext) {
        this.user = authenticationContext.getUser();
    }

    public String getUsername() {
        if (user == null) {
            return null;
        }
        return user.getName();
    }
}
