package net.incongru.berkano.user.extensions;

import net.incongru.berkano.user.User;

/**
 * Helper to store / retrieve user properties of the currently logged in user.
 * 
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public interface UserPropertyHelper {
    void store(String key, Object value);

    Object getUserOnlyValue(String key);

    Object getSingleValue(String key);

    User getUser();
}
