package net.incongru.swaf.usermgt.webwork;

import com.opensymphony.xwork.ActionSupport;
import net.incongru.swaf.user.UserDAO;
import net.incongru.swaf.user.UnknownUserException;

/**
 * 
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.5 $
 */
public class UserRemoveAction extends ActionSupport {
    private UserDAO userDAO;
    private Long userId;

    public UserRemoveAction(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public String execute() throws UnknownUserException {
        userDAO.removeUser(userId);
        return SUCCESS;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
