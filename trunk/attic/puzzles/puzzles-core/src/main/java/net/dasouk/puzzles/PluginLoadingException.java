package net.dasouk.puzzles;

import java.net.URL;

/**
 * @todo documentation
 */
public class PluginLoadingException extends PluginException {
    protected PluginLoadingException(String message, URL url) {
        super(message, url);
    }

    protected PluginLoadingException(String message, Throwable cause, URL url) {
        super(message, cause, url);
    }
}
