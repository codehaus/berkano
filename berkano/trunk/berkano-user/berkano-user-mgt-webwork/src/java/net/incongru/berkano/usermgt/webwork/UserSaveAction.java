package net.incongru.berkano.usermgt.webwork;

import net.incongru.berkano.user.UserDAO;

/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class UserSaveAction extends AbstractUserSaveAction {
    private Long userId;

    public UserSaveAction(UserDAO userDAO) {
        super(userDAO);
    }

    protected String doSave() {
        if (userId == null) {
            userDAO.newUser(getUserName(), getPassword(), getEmail(), getFullName());
        } else {
            updateUser(userId, getUserName());
        }
        return SUCCESS;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
