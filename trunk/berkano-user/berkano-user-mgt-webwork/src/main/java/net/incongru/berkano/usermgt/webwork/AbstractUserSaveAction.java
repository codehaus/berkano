package net.incongru.berkano.usermgt.webwork;

import com.opensymphony.xwork.ActionSupport;
import net.incongru.berkano.user.UnknownUserException;
import net.incongru.berkano.user.UserDAO;
import net.incongru.berkano.user.User;

/**
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public abstract class AbstractUserSaveAction extends ActionSupport {
    final protected UserDAO userDAO;
    private String userName;
    private String password;
    private String passwordConfirm;
    private String email;
    private String fullName;

    public AbstractUserSaveAction(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public String execute() throws UnknownUserException {
        return doSave();
    }

    protected abstract String doSave();

    protected User updateUser(Long userId, final String userNameToBeUsed) {
        final User u = userDAO.updateUser(userId, userNameToBeUsed, email, fullName);
        if (password != null && !password.equals("")) {
            userDAO.changePassword(userId, password);
        }
        return u;
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
