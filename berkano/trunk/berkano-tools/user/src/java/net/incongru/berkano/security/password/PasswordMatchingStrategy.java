package net.incongru.swaf.security.password;

import net.incongru.swaf.user.User;

/**
 * Is this any useful?
 * ... maybe we should try to reuse jaas stuff more...
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public interface PasswordMatchingStrategy {
    public boolean matches(String givenPassword, User user);

    public String encode(String clearPassword);
}
