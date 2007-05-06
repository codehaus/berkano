package net.incongru.berkano.usermgt.webwork;

import com.opensymphony.xwork.ActionSupport;
import net.incongru.berkano.user.GroupDAO;

/**
 * 
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class GroupAddAction extends ActionSupport {
    private GroupDAO groupDAO;
    private String groupName;

    public GroupAddAction(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public String execute() throws Exception {
        groupDAO.newGroup(groupName);

        return SUCCESS;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
