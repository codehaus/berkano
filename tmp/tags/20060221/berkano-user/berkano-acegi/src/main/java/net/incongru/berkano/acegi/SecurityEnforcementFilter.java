package net.incongru.berkano.acegi;

import net.sf.acegisecurity.util.PortResolver;
import net.sf.acegisecurity.util.PortResolverImpl;
import net.sf.acegisecurity.AuthenticationTrustResolver;
import net.sf.acegisecurity.AuthenticationTrustResolverImpl;
import net.sf.acegisecurity.intercept.web.FilterSecurityInterceptor;
import net.sf.acegisecurity.intercept.web.AuthenticationEntryPoint;
import org.picocontainer.Startable;

/**
 * A simple wrapper around Acegi's SecurityEnforcementFilter which
 * allows CDI.
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class SecurityEnforcementFilter extends net.sf.acegisecurity.intercept.web.SecurityEnforcementFilter implements Startable {

    public SecurityEnforcementFilter(AuthenticationEntryPoint authenticationEntryPoint, FilterSecurityInterceptor filterSecurityInterceptor) {
        setAuthenticationEntryPoint(authenticationEntryPoint);
        setFilterSecurityInterceptor(filterSecurityInterceptor);
        setAuthenticationTrustResolver( new AuthenticationTrustResolverImpl());
        setPortResolver(new PortResolverImpl());
    }

    public SecurityEnforcementFilter(AuthenticationEntryPoint authenticationEntryPoint, AuthenticationTrustResolver authenticationTrustResolver, FilterSecurityInterceptor filterSecurityInterceptor, PortResolver portResolver, boolean createSessionAllowed) {
        setAuthenticationEntryPoint(authenticationEntryPoint);
        setAuthenticationTrustResolver(authenticationTrustResolver);
        setFilterSecurityInterceptor(filterSecurityInterceptor);
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
