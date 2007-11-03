package net.incongru.berkano.user;

/**
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.6 $
 */
public class DuplicateUserException extends RuntimeException {
    private final String userName;

    public DuplicateUserException(String userName) {
        super("User already exists: " + userName);
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
