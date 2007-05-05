package net.incongru.berkano.acegi;

import java.io.Serializable;

/**
 * A simple bean for simple and generic Acegi configuration.
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class AcegiConfig implements Serializable {
    private String loginFormUrl;
    private boolean forceHttps = false;

    public AcegiConfig(String loginFormUrl, boolean forceHttps) {
        this.loginFormUrl = loginFormUrl;
        this.forceHttps = forceHttps;
    }

    public String getLoginFormUrl() {
        return loginFormUrl;
    }

    public void setLoginFormUrl(String loginFormUrl) {
        this.loginFormUrl = loginFormUrl;
    }

    public boolean isForceHttps() {
        return forceHttps;
    }

    public void setForceHttps(boolean forceHttps) {
        this.forceHttps = forceHttps;
    }

}
