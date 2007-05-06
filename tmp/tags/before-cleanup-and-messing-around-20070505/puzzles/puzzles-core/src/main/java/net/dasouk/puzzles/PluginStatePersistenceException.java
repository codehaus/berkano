package net.dasouk.puzzles;

import java.net.URL;

/**
 * Exception throw whenever an exception occurs in the PluginStateManager
 */
public class PluginStatePersistenceException extends PluginPersistenceException {

    public PluginStatePersistenceException(String message, URL url) {
        super(message, url);
    }

    public PluginStatePersistenceException(String message, Throwable cause, URL url) {
        super(message, cause, url);
    }
}
