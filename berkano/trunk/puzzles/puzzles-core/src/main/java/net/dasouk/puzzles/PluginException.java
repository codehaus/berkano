package net.dasouk.puzzles;

import java.net.URL;

/**
 * General abstract Exception concerning plugins deployments.
 */
public abstract class PluginException extends Exception {
    private final URL url;

    protected PluginException(URL url) {
        this.url = url;
    }

    protected PluginException(String message, URL url) {
        super(message);
        this.url = url;
    }

    protected PluginException(String message, Throwable cause, URL url) {
        super(message, cause);
        this.url = url;
    }

    protected PluginException(Throwable cause, URL url) {
        super(cause);
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

}
