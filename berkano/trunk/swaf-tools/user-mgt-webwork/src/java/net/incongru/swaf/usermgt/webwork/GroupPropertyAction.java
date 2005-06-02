package net.incongru.swaf.usermgt.webwork;

import com.opensymphony.xwork.ActionSupport;
import net.incongru.swaf.user.GroupDAO;

/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.5 $
 */
public class GroupPropertyAction extends ActionSupport {
    private GroupDAO groupDAO;
    private Long groupId;
    private String propertyKey;
    private String newPropertyValue;

    public GroupPropertyAction(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public String removeProperty() throws Exception {
        groupDAO.removeProperty(groupId, propertyKey);
        return SUCCESS;
    }

    public String addProperty() throws Exception {
        groupDAO.addProperty(groupId, propertyKey, newPropertyValue);
        return SUCCESS;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public void setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    public void setNewPropertyValue(String newPropertyValue) {
        this.newPropertyValue = newPropertyValue;
    }
}
