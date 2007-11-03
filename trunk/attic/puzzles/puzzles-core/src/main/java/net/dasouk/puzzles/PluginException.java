package net.dasouk.puzzles;

import java.net.URL;

/**
 * General abstract Exception concerning plugins deployments.
 */
public abstract class PluginException extends RuntimeException {
    private final URL url;

    protected PluginException(String message, URL url) {
        this(message, null, url);
    }

    protected PluginException(String message, Throwable cause, URL url) {
        super(message, cause);
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

}
