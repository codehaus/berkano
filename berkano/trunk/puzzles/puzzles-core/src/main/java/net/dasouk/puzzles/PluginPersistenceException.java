package net.dasouk.puzzles;

import java.net.URL;

/**
 *
 */
public abstract class PluginPersistenceException extends PluginException {
    protected PluginPersistenceException(String message, URL url) {
        super(message, url);
    }

    protected PluginPersistenceException(String message, Throwable cause, URL url) {
        super(message, cause, url);
    }
}
