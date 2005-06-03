package net.incongru.berkano.security.seraph.webwork;

import com.atlassian.seraph.auth.AuthenticationContext;
import com.atlassian.seraph.auth.Authenticator;
import com.atlassian.seraph.auth.AuthenticatorException;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionSupport;

import javax.servlet.http.HttpSession;

/**
 * This simple action delegates to the inject seraph Authenticator to logout.
 * (Indeed, the seraph logout servlet only does the actual logging out if the
 * logout config param is absolute, for sso.
 *  
 * @author Grégory Joseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.3 $
 */
public class LogoutAction extends ActionSupport {
    private Authenticator authenticator;
    private AuthenticationContext authenticationContext;

    public LogoutAction(Authenticator authenticator, AuthenticationContext authenticationContext) {
        this.authenticator = authenticator;
        this.authenticationContext = authenticationContext;
    }

    public String execute() {
        try {
            authenticationContext.clearUser();
            authenticator.logout(ServletActionContext.getRequest(), ServletActionContext.getResponse());
            HttpSession session = ServletActionContext.getRequest().getSession(false);
            if (session != null) {
                session.invalidate();
            }
            return SUCCESS;
        } catch (AuthenticatorException e) {
            addActionError("logout.error");
            addActionError(e.getMessage()); // todo : check what this does
            return ERROR;
        }
    }
}
