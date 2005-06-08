package net.incongru.berkano.usermgt.webwork.lostpw;

import com.opensymphony.xwork.ActionSupport;
import net.incongru.berkano.security.password.PasswordRetrievalStrategy;
import net.incongru.berkano.security.password.PasswordRetrievalStrategy;
import net.incongru.berkano.user.User;
import net.incongru.berkano.user.UserDAO;

/**
 * This action only triggers the configured {@link PasswordRetrievalStrategy}.
 * Depending on what this action does, it might be necessary to then
 * call another action. (For instance if the strategy sends a mail to the user
 * with a link that the user must click in order to generate a new password)
 *
 * @see PasswordRetrievalStrategy
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class RetrieveAction extends ActionSupport {
    private UserDAO userDao;
    private PasswordRetrievalStrategy passwordRetrievalStrategy;
    private String userName;
    private String email;

    public RetrieveAction(UserDAO userDao, PasswordRetrievalStrategy passwordRetrievalStrategy) {
        this.userDao = userDao;
        this.passwordRetrievalStrategy = passwordRetrievalStrategy;
    }

    public String execute() throws Exception {
        User u = null;
        if (userName != null) {
            u = userDao.getUserByName(userName);
        } else if (email != null) {
            u = userDao.getUserByEmail(email);
        }
        if (u == null) {
            return ERROR;
        }

        passwordRetrievalStrategy.retrievePassword(u);

        // TODO : should show a different view depending on the strategy
        return SUCCESS;
    }

    // TODO : don't think we need the getters here...

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
