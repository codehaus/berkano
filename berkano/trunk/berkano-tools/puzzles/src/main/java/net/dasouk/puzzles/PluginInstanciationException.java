package net.dasouk.puzzles;

import java.net.URL;

/**
 * General Exception to report an exception
 * happening when loading a plugin. The PluginLoader implementations
 * are free to use any specialization of this Exception.
 *
 * @author souk
 * @version 0.1
 */
public class PluginInstanciationException extends PluginException {
    public PluginInstanciationException(URL url) {
        super(url);
    }

    public PluginInstanciationException(String message, URL url) {
        super(message, url);
    }

    public PluginInstanciationException(String message, Throwable cause, URL url) {
        super(message, cause, url);
    }

    public PluginInstanciationException(Throwable cause, URL url) {
        super(cause, url);
    }
}
