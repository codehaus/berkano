package net.incongru.berkano.security.password;

import net.incongru.berkano.user.User;

/**
 * Matches and encodes passwords.
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public interface PasswordMatchingStrategy {
    public boolean matches(String givenPassword, User user);

    public String encode(String clearPassword);
}
