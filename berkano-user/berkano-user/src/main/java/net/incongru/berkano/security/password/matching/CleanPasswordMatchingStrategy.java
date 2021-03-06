package net.incongru.berkano.security.password.matching;

import net.incongru.berkano.security.password.PasswordMatchingStrategy;
import net.incongru.berkano.user.User;

/**
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class CleanPasswordMatchingStrategy implements PasswordMatchingStrategy {
    public boolean matches(String givenPassword, User user) {
        if (user == null) {
            throw new NullPointerException("Can't pass a null User");
        }

        if (givenPassword == null) {
            return user.getPassword() == null;
        }
        return givenPassword.equals(user.getPassword());
    }

    public String encode(String clearPassword) {
        return clearPassword;
    }
}
