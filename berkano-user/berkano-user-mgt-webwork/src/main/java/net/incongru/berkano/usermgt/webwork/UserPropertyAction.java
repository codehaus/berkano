package net.incongru.berkano.usermgt.webwork;

import com.opensymphony.xwork.ActionSupport;
import net.incongru.berkano.user.UnknownUserException;
import net.incongru.berkano.user.User;
import net.incongru.berkano.user.UserDAO;

/**
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.8 $
 */
public class UserPropertyAction extends ActionSupport {
    private UserDAO userDAO;
    private Long userId;
    private String propertyKey;
    private String newPropertyValue;

    public UserPropertyAction(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public String addProperty() throws UnknownUserException {
        userDAO.addProperty(userId, propertyKey, newPropertyValue);
        return SUCCESS;
    }

    public String removeProperty() throws UnknownUserException {
        userDAO.removeProperty(userId, propertyKey);
        return SUCCESS;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    public void setNewPropertyValue(String newPropertyValue) {
        this.newPropertyValue = newPropertyValue;
    }
}
