package net.incongru.berkano.usermgt.webwork.self;

import com.atlassian.seraph.auth.AuthenticationContext;
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
        return SUCCESS;
    }
}
