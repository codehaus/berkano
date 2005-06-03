package net.incongru.swaf.usermgt.webwork;

import com.opensymphony.xwork.ActionSupport;
import net.incongru.swaf.user.UserDAO;
import net.incongru.swaf.user.UnknownUserException;

/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class UserSaveAction extends ActionSupport {
    private UserDAO userDAO;
    private Long userId;
    private String userName;
    private String password;
    private String passwordConfirm;
    private String email;
    private String fullName;

    public UserSaveAction(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public String execute() throws UnknownUserException {
        if (userId == null) {
            userDAO.newUser(userName, password, email, fullName);
        } else {
            userDAO.updateUser(userId, userName, email, fullName);
            if (password != null && !password.equals("")) {
                userDAO.changePassword(userId, password);
            }
        }
        return SUCCESS;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
