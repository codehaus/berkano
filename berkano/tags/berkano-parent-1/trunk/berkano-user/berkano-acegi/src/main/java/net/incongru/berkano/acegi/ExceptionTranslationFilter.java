package net.incongru.berkano.acegi;

import org.acegisecurity.AuthenticationTrustResolver;
import org.acegisecurity.AuthenticationTrustResolverImpl;
import org.acegisecurity.ui.AuthenticationEntryPoint;
import org.acegisecurity.util.PortResolver;
import org.acegisecurity.util.PortResolverImpl;
import org.picocontainer.Startable;

/**
 * A simple wrapper around Acegi's SecurityEnforcementFilter which
 * allows CDI.
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class ExceptionTranslationFilter extends org.acegisecurity.ui.ExceptionTranslationFilter implements Startable {

    public ExceptionTranslationFilter(AuthenticationEntryPoint authenticationEntryPoint) {
        setAuthenticationEntryPoint(authenticationEntryPoint);
        // set migration from 0.9 to 1.0 setFilterSecurityInterceptor(filterSecurityInterceptor);
        setAuthenticationTrustResolver(new AuthenticationTrustResolverImpl());
        setPortResolver(new PortResolverImpl());
    }

    public ExceptionTranslationFilter(AuthenticationEntryPoint authenticationEntryPoint, AuthenticationTrustResolver authenticationTrustResolver, PortResolver portResolver, boolean createSessionAllowed) {
        setAuthenticationEntryPoint(authenticationEntryPoint);
        setAuthenticationTrustResolver(authenticationTrustResolver);
        // set migration from 0.9 to 1.0 setFilterSecurityInterceptor(filterSecurityInterceptor);
        setPortResolver(portResolver);
        setCreateSessionAllowed(createSessionAllowed);
    }

    public void start() {
        try {
            afterPropertiesSet();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
    }

}
