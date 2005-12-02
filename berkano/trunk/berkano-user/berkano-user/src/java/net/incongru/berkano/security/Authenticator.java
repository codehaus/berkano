package net.incongru.berkano.security;

import net.incongru.berkano.user.UnknownUserException;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.3 $
 *
 * @deprecated currently unused - staying there for now just as a reminder
 */
public interface Authenticator {
    public boolean authenticate() throws UnknownUserException;

    public boolean logout();
}
