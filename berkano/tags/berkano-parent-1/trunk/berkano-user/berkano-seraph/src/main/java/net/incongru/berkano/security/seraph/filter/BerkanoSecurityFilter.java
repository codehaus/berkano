package net.incongru.berkano.security.seraph.filter;

import com.atlassian.seraph.config.SecurityConfig;
import com.atlassian.seraph.filter.SecurityFilter;

import javax.servlet.FilterConfig;

/**
 * Copied from Seraph's original BerkanoSecurityFilter
 *
 */
public class BerkanoSecurityFilter extends SecurityFilter {
    private SecurityConfig securityConfig;

    public BerkanoSecurityFilter(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }

    // don't store the FilterConfig as in super(), we don't need it.
    public void init(FilterConfig config) {
        //todo: this is needed by LogoutServlet
        config.getServletContext().setAttribute(SecurityConfig.STORAGE_KEY, securityConfig);
    }

    public void destroy() {
        securityConfig.destroy();
        securityConfig = null;
    }

    protected SecurityConfig getSecurityConfig() {
        return securityConfig;
    }
}
