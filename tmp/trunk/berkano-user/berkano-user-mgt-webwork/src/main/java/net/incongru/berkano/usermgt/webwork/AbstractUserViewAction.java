package net.incongru.berkano.usermgt.webwork;

import com.opensymphony.xwork.ActionSupport;
import net.incongru.berkano.user.UnknownUserException;
import net.incongru.berkano.user.User;

/**
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public abstract class AbstractUserViewAction extends ActionSupport {
    private User user;

    public String execute() throws UnknownUserException {
        user = loadUser();
        return SUCCESS;
    }

    protected abstract User loadUser();

    public User getUser() {
        return user;
    }

    public Long getUserId() {
        return user.getUserId();
    }

    public String getUserName() {
        return user.getUserName();
    }

    public String getFullName() {
        return user.getFullName();
    }

    public String getEmail() {
        return user.getEmail();
    }
}
