package net.incongru.berkano.usermgt.webwork.self;

import com.atlassian.seraph.auth.AuthenticationContext;
import com.atlassian.seraph.auth.DefaultAuthenticator;
import com.opensymphony.xwork.ActionContext;
import net.incongru.berkano.user.User;
import net.incongru.berkano.user.UserDAO;
import net.incongru.berkano.usermgt.webwork.AbstractUserSaveAction;

/**
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class SelfUserSaveAction extends AbstractUserSaveAction {
    private AuthenticationContext authenticationContext;

    public SelfUserSaveAction(AuthenticationContext authenticationContext, UserDAO userDAO) {
        super(userDAO);
        this.authenticationContext = authenticationContext;
    }

    protected String doSave() {
        User user = (User) authenticationContext.getUser();
        user = updateUser(user.getUserId(), user.getUserName());
        authenticationContext.setUser(user);

        // ugly hack which introduces more dependency on seraph - this key should ideally not go outside the DefaultAuthenticator
        // TODO : we should rather have the authenticationContext be registered in the session-scope of pico
        ActionContext.getContext().getSession().put(DefaultAuthenticator.LOGGED_IN_KEY, user);

        return SUCCESS;
    }
}
