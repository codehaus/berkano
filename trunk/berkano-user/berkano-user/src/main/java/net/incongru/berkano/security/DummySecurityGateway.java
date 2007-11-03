package net.incongru.berkano.security;

/**
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 *
 * @deprecated
 */
public class DummySecurityGateway extends AbstractSecurityGateway {
    public boolean authenticate(Authenticator authenticator) {
        return false;
    }

    public boolean logout(Authenticator authenticator) {
        return false;
    }

}
