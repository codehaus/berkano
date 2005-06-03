package net.incongru.swaf.usermgt.webwork;

import com.opensymphony.xwork.ActionSupport;
import net.incongru.swaf.user.UserDAO;
import net.incongru.swaf.user.GroupDAO;
import net.incongru.swaf.user.UnknownUserException;
import net.incongru.swaf.user.User;

import java.util.List;

/**
 * 
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class UserGroupsViewAction extends ActionSupport {
    private UserDAO userDAO;
    private GroupDAO groupDAO;
    private Long userId;
    private User user;
    private List allGroups;

    public UserGroupsViewAction(UserDAO userDAO, GroupDAO groupDAO) {
        this.userDAO = userDAO;
        this.groupDAO = groupDAO;
    }

    public String execute() throws UnknownUserException {
        user = userDAO.getUserById(userId);
        allGroups = groupDAO.listAllGroups();
        return SUCCESS;
    }

    public User getUser() {
        return user;
    }

    public List getAllGroups() {
        return allGroups;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
