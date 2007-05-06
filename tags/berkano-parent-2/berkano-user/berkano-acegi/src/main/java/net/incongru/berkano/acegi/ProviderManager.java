package net.incongru.berkano.acegi;

import org.acegisecurity.providers.AuthenticationProvider;

import java.util.Arrays;

/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class ProviderManager extends org.acegisecurity.providers.ProviderManager {
    public ProviderManager(AuthenticationProvider[] authenticationProviders) {
        setProviders(Arrays.asList(authenticationProviders));
    }
}
