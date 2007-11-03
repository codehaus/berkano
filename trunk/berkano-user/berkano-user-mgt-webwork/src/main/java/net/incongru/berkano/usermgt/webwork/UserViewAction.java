package net.incongru.berkano.usermgt.webwork;

import net.incongru.berkano.user.User;
import net.incongru.berkano.user.UserDAO;

/**
 * 
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.6 $
 */
public class UserViewAction extends AbstractUserViewAction {
    private UserDAO userDAO;
    private Long userId;
    private String userName;

    public UserViewAction(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // todo : check for unknown user (in dao or here?)
    protected User loadUser() {
        if (userId != null) {
            return userDAO.getUserById(userId);
        } else {
            return userDAO.getUserByName(userName);
        }
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
