package net.incongru.berkano.usermgt.webwork;

import com.opensymphony.xwork.ActionSupport;
import net.incongru.berkano.security.Role;
import net.incongru.berkano.security.RoleDAO;
import net.incongru.berkano.user.GroupDAO;

import java.util.HashSet;
import java.util.Set;

/**
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.6 $
 */
public class GroupRolesAction extends ActionSupport {
    private GroupDAO groupDAO;
    private RoleDAO roleDAO;
    private Long groupId;
    private String[] associatedRoleNames;

    public GroupRolesAction(GroupDAO groupDAO, RoleDAO roleDAO) {
        this.groupDAO = groupDAO;
        this.roleDAO = roleDAO;
    }

    public String execute() {
        Set roles = new HashSet();
        for (int i = 0; i < associatedRoleNames.length; i++) {
            Role role = roleDAO.getRole(associatedRoleNames[i]);
            roles.add(role);
        }
        groupDAO.assignRoles(groupId, roles);
        return SUCCESS;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public void setRoleNames(String[] associatedRoleNames) {
        this.associatedRoleNames = associatedRoleNames;
    }

}
