package net.incongru.berkano.usermgt.webwork.self;

import com.atlassian.seraph.auth.AuthenticationContext;
import net.incongru.berkano.user.User;
import net.incongru.berkano.usermgt.webwork.AbstractUserViewAction;

/**
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class SelfUserViewAction extends AbstractUserViewAction {
    private AuthenticationContext authenticationContext;

    public SelfUserViewAction(AuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;
    }

    protected User loadUser() {
        User user = (User) authenticationContext.getUser();
        return user;
    }
}
