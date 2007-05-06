package net.dasouk.puzzles;

/**
 * General wrapper for any Exception that may occur inside a {@link PluginStore}.
 *
 * @author souk
 * @version 0.1
 * @see PluginStore
 */
public class StoreException extends Exception {
    public StoreException() {
    }

    public StoreException(String message) {
        super(message);
    }

    public StoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public StoreException(Throwable cause) {
        super(cause);
    }
}
