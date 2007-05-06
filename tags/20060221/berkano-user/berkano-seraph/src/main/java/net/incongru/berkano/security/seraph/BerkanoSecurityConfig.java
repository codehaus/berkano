package net.incongru.berkano.security.seraph;

import com.atlassian.seraph.auth.Authenticator;
import com.atlassian.seraph.auth.RoleMapper;
import com.atlassian.seraph.config.ConfigurationException;
import com.atlassian.seraph.config.SecurityConfigImpl;
import com.atlassian.seraph.interceptor.Interceptor;
import org.w3c.dom.Element;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Constructable SecurityConfig. Merely delegates to super constructor and inhibit some
 * of the configure* methods.
 */
public class BerkanoSecurityConfig extends SecurityConfigImpl {
    public BerkanoSecurityConfig(Authenticator authenticator, RoleMapper roleMapper) throws ConfigurationException {
        this(authenticator, roleMapper, new Interceptor[0], DEFAULT_CONFIG_LOCATION);
    }

    public BerkanoSecurityConfig(Authenticator authenticator, RoleMapper roleMapper, Interceptor[] interceptors) throws ConfigurationException {
        this(authenticator, roleMapper, interceptors, DEFAULT_CONFIG_LOCATION);
    }

    public BerkanoSecurityConfig(Authenticator authenticator, RoleMapper roleMapper, String configFileLocation) throws ConfigurationException {
        this(authenticator, roleMapper, new Interceptor[0], configFileLocation);
    }

    public BerkanoSecurityConfig(Authenticator authenticator, RoleMapper roleMapper, Interceptor[] interceptors, String configFileLocation) throws ConfigurationException {
        super(configFileLocation);
        this.authenticator = authenticator;
        this.roleMapper = roleMapper;
        this.interceptors = Arrays.asList(interceptors);
        this.authenticator.init(new HashMap(), this); // todo this probably kills any param set in the authenticator!
    }

    protected void configureAuthenticator(Element rootEl) throws ConfigurationException {
        // ignore config, we got the Authenticator from the constructor
    }

    protected void configureRoleMapper(Element rootEl) throws ConfigurationException {
        // ignore config, we got the RoleMapper from the constructor
    }

    protected void configureInterceptors(Element rootEl) throws ConfigurationException {
        // ignore config, we got the Interceptor[] from the constructor
    }

    // todo : if we get rid of the thread-local-based AuthContext:
 //   public AuthenticationContext getAuthenticationContext() {
 //       throw new IllegalStateException("BOO !");
 //   }
}
