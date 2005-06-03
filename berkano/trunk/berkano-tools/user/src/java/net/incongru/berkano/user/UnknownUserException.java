package net.incongru.swaf.user;

/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.6 $
 */
public class UnknownUserException extends RuntimeException {
    private Long userId;

    public UnknownUserException(Long userId) {
        super("unknown userId: " + userId);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
