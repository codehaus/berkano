package net.incongru.swaf.security.password.matching;

import net.incongru.swaf.security.password.PasswordMatchingStrategy;
import net.incongru.swaf.user.User;

/**
 *
 * @author greg
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
