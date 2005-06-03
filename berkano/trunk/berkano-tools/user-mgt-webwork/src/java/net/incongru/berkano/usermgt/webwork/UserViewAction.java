package net.incongru.berkano.usermgt.webwork;

import com.opensymphony.xwork.ActionSupport;
import net.incongru.berkano.user.User;
import net.incongru.berkano.user.UserDAO;
import net.incongru.berkano.user.UnknownUserException;

/**
 * 
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.6 $
 */
public class UserViewAction extends ActionSupport {
    private UserDAO userDAO;
    private Long userId;
    private String userName;
    private User user;

    public UserViewAction(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // todo : check for unknown user (in dao or here?)
    public String execute() throws UnknownUserException {
        if (userId != null) {
            user = userDAO.getUserById(userId);
        } else {
            user = userDAO.getUserByName(userName);
        }
        return SUCCESS;
    }

    public User getUser() {
        return user;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
