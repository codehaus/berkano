package net.incongru.berkano.acegi;

import org.acegisecurity.userdetails.UserDetailsService;


/**
 * A simple wrapper around Acegi's DaoAuthenticationProvider which
 * allows CDI.
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class DaoAuthenticationProvider extends org.acegisecurity.providers.dao.DaoAuthenticationProvider {
    public DaoAuthenticationProvider(UserDetailsService userDetailsService) {
        setUserDetailsService(userDetailsService);
    }
}
