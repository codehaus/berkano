package net.incongru.berkano.usermgt.webwork;

import com.opensymphony.xwork.ActionSupport;
import net.incongru.berkano.user.GroupDAO;

import java.util.List;

/**
 * 
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class GroupListAction extends ActionSupport {
    private GroupDAO groupDAO;
    private List allGroups;

    public GroupListAction(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public String execute() throws Exception {
        allGroups = groupDAO.listAllGroups();
        return SUCCESS;
    }

    public List getAllGroups() {
        return allGroups;
    }
}
