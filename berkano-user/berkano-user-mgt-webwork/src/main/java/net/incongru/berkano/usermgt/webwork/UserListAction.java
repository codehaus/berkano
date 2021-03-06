package net.incongru.berkano.usermgt.webwork;

import com.opensymphony.xwork.ActionSupport;
import net.incongru.berkano.user.UserDAO;

import java.util.List;

/**
 * 
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class UserListAction extends ActionSupport {
    private UserDAO userDAO;
    private List allUsers;

    public UserListAction(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public String execute() throws Exception {
        allUsers = userDAO.listAllUsers();
        return SUCCESS;
    }

    public List getAllUsers() {
        return allUsers;
    }

}
