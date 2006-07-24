package net.incongru.berkano.acegi;

import org.acegisecurity.util.PortMapper;
import org.acegisecurity.util.PortResolver;
import org.picocontainer.Startable;

/**
 * A simple wrapper around Acegi's AuthenticationProcessingFilterEntryPoint which
 * allows CDI.
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class AuthenticationProcessingFilterEntryPoint extends org.acegisecurity.ui.webapp.AuthenticationProcessingFilterEntryPoint implements Startable {
    public AuthenticationProcessingFilterEntryPoint() {
        super();
    }

    public AuthenticationProcessingFilterEntryPoint(AcegiConfig config, PortMapper portMapper, PortResolver portResolver) {
        this(config);
        setPortMapper(portMapper);
        setPortResolver(portResolver);
    }

    public AuthenticationProcessingFilterEntryPoint(AcegiConfig config) {
        setLoginFormUrl(config.getLoginFormUrl());
        setForceHttps(config.isForceHttps());
    }

    public void setAcegiConfig(AcegiConfig config) {
        setLoginFormUrl(config.getLoginFormUrl());
        setForceHttps(config.isForceHttps());
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
