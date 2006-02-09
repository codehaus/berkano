package net.dasouk.puzzles;

import java.net.URL;

/**
 * General wrapper for any Exception that may occur inside a {@link PluginStore}.
 *
 * @author souk
 * @version 0.1
 * @see PluginStore
 */
public class PluginStoreException extends PluginPersistenceException {

    public PluginStoreException(String message, URL url) {
        super(message, url);
    }

    public PluginStoreException(String message, Throwable cause, URL url) {
        super(message, cause, url);
    }
}
