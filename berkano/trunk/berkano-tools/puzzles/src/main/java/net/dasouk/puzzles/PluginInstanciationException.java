package net.dasouk.puzzles;

import java.net.URL;

/**
 * General Exception to report an exception
 * happening when loading a plugin. The PluginLoader implementations
 * are free to use any specialization of this Exception.
 */
public class PluginInstanciationException extends Exception {
    private URL pluginUrl;

    public PluginInstanciationException(URL pluginUrl, Throwable cause) {
        super(cause);
        this.pluginUrl = pluginUrl;
    }

    public URL getPluginUrl() {
        return pluginUrl;
    }
}
