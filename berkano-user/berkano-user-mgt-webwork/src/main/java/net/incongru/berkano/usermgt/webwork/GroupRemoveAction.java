package net.incongru.berkano.usermgt.webwork;

import com.opensymphony.xwork.ActionSupport;
import net.incongru.berkano.user.GroupDAO;

/**
 * 
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class GroupRemoveAction extends ActionSupport {
    private GroupDAO groupDAO;
    private Long groupId;

    public GroupRemoveAction(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public String execute() throws Exception {
        groupDAO.removeGroup(groupId);

        return SUCCESS;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
