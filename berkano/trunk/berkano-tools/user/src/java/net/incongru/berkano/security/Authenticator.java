package net.incongru.berkano.security;

import net.incongru.berkano.user.UnknownUserException;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.3 $
 */
public interface Authenticator {
    public boolean authenticate() throws UnknownUserException;

    public boolean logout();
}
