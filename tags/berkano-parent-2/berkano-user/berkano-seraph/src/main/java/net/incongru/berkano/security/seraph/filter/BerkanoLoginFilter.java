package net.incongru.berkano.security.seraph.filter;

import com.atlassian.seraph.auth.Authenticator;
import com.atlassian.seraph.config.SecurityConfig;
import com.atlassian.seraph.filter.LoginFilter;

import javax.servlet.FilterConfig;

/**
 * Extends Seraph's original LoginFilter by providing the SecurityConfig
 * through the constructor.
 *
 */
public class BerkanoLoginFilter extends LoginFilter {
    private SecurityConfig securityConfig;
    private Authenticator authenticator;

    public BerkanoLoginFilter(SecurityConfig securityConfig, Authenticator authenticator) {
        this.securityConfig = securityConfig;
        this.authenticator = authenticator;
    }

    // don't store the FilterConfig as in super(), we don't need it.
    public void init(FilterConfig config) {
    }

    public void destroy() {
        securityConfig = null;
    }

    protected Authenticator getAuthenticator() {
        return authenticator;
    }

    protected SecurityConfig getSecurityConfig() {
        return securityConfig;
    }
}
