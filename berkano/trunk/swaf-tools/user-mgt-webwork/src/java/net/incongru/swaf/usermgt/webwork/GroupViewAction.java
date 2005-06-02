package net.incongru.swaf.usermgt.webwork;

import com.opensymphony.xwork.ActionSupport;
import net.incongru.swaf.user.GroupDAO;
import net.incongru.swaf.user.Group;
import net.incongru.swaf.security.RoleDAO;

import java.util.List;

/**
 * 
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class GroupViewAction extends ActionSupport {
    private GroupDAO groupDAO;
    private RoleDAO roleDAO;
    private List allRoles;
    private Long groupId;
    private String groupName;
    private Group group;

    public GroupViewAction(GroupDAO groupDAO, RoleDAO roleDAO) {
        this.groupDAO = groupDAO;
        this.roleDAO = roleDAO;
    }

    // todo : check for unknown group (in dao or here?)
    public String execute() throws Exception {
        if (groupId != null) {
            group = groupDAO.getGroupById(groupId);
        } else {
            group = groupDAO.getGroupByName(groupName);
        }
        allRoles = roleDAO.listAllRoles();
        return SUCCESS;
    }

    public Group getGroup() {
        return group;
    }

    public List getAllRoles() {
        return allRoles;
    }
    
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}