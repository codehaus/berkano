package net.incongru.berkano.usermgt.webwork.lostpw;

import com.opensymphony.xwork.ActionSupport;
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
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class RetrieveAction extends ActionSupport {
    private final UserDAO userDao;
    private final PasswordRetrievalStrategy passwordRetrievalStrategy;
    private String userName;
    private String email;
    private User user;

    public RetrieveAction(UserDAO userDao, PasswordRetrievalStrategy passwordRetrievalStrategy) {
        this.userDao = userDao;
        this.passwordRetrievalStrategy = passwordRetrievalStrategy;
    }

    public String execute() throws Exception {
        if (userName != null) {
            user = userDao.getUserByName(userName);
        } else if (email != null) {
            user = userDao.getUserByEmail(email);
        }
        if (user == null) {
            return "user_not_found";
        }

        passwordRetrievalStrategy.retrievePassword(user);

        // TODO : should show a different view depending on the strategy
        return SUCCESS;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
