package net.incongru.berkano.usermgt.webwork;

import com.opensymphony.xwork.ActionSupport;
import net.incongru.berkano.user.UnknownUserException;
import net.incongru.berkano.user.UserDAO;

/**
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class UserGroupsEditAction extends ActionSupport {
    private UserDAO userDAO;
    private Long userId;
    private Long[] groupsToRemove;
    private Long[] groupsToAdd;
    private String add;
    private String remove;

    public UserGroupsEditAction(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // TODO : maybe have two different methods.. see how editusergroups.jsp can be adapted (there is currently only one form action..)
    public String execute() throws UnknownUserException {
        if (add != null) {
            for (int i = 0; i < groupsToAdd.length; i++) {
                userDAO.addToGroup(userId, groupsToAdd[i]);
            }
        } else if (remove != null) {
            for (int i = 0; i < groupsToRemove.length; i++) {
                userDAO.removeFromGroup(userId, groupsToRemove[i]);
            }
        }

        return SUCCESS;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setGroupsToAdd(Long[] groupsToAdd) {
        this.groupsToAdd = groupsToAdd;
    }

    public void setGroupsToRemove(Long[] groupsToRemove) {
        this.groupsToRemove = groupsToRemove;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public void setRemove(String remove) {
        this.remove = remove;
    }
}
