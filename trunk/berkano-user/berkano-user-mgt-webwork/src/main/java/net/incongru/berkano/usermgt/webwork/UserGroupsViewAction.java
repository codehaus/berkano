package net.incongru.berkano.usermgt.webwork;

import com.opensymphony.xwork.ActionSupport;
import net.incongru.berkano.user.GroupDAO;
import net.incongru.berkano.user.UnknownUserException;
import net.incongru.berkano.user.User;
import net.incongru.berkano.user.UserDAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class UserGroupsViewAction extends ActionSupport {
    private UserDAO userDAO;
    private GroupDAO groupDAO;
    private Long userId;
    private User user;
    private Set memberOfGroups;
    private List availableGroups;

    public UserGroupsViewAction(UserDAO userDAO, GroupDAO groupDAO) {
        this.userDAO = userDAO;
        this.groupDAO = groupDAO;
    }

    public String execute() throws UnknownUserException {
        user = userDAO.getUserById(userId);
        List allGroups = groupDAO.listAllGroups();
        memberOfGroups = user.getGroups();
        availableGroups = subtract(allGroups, memberOfGroups);
        return SUCCESS;
    }

    public User getUser() {
        return user;
    }

    public Set getMemberOfGroups() {
        return memberOfGroups;
    }

    public List getAvailableGroups() {
        return availableGroups;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private List subtract(final Collection all, final Collection toBeRemoved) {
        List result = new ArrayList(all);
        for (Object o : toBeRemoved) {
            result.remove(o);
        }
        return result;
    }

}
