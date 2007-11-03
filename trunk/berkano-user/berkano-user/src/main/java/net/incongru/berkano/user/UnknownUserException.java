package net.incongru.berkano.user;

/**
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.6 $
 */
public class UnknownUserException extends RuntimeException {
    private final Long userId;

    public UnknownUserException(Long userId) {
        super("unknown userId: " + userId);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
