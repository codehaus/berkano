package net.incongru.berkano.acegi;

import net.sf.acegisecurity.providers.dao.AuthenticationDao;

/**
 * A simple wrapper around Acegi's DaoAuthenticationProvider which
 * allows CDI.
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class DaoAuthenticationProvider extends net.sf.acegisecurity.providers.dao.DaoAuthenticationProvider {
    public DaoAuthenticationProvider(AuthenticationDao authenticationDao) {
        setAuthenticationDao(authenticationDao);
    }
}
