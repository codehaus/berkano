package net.dasouk.puzzles;

import java.net.URL;

/**
 * Exception to be thrown by the {@link PluginRegistry} when no plugin with a given name could be found.
 *
 * @author souk
 * @version 0.1
 * @see net.dasouk.puzzles.PluginRegistry
 */
public class PluginNotFoundException extends PluginException {
    private final String pluginName;
    private final String pluginFamily;

    public PluginNotFoundException(String message, URL url, String pluginFamily, String pluginName) {
        this(message, null, url, pluginFamily, pluginName);
    }

    public PluginNotFoundException(String message, Throwable cause, URL url, String pluginFamily, String pluginName) {
        super(message, cause, url);
        this.pluginName = pluginName;
        this.pluginFamily = pluginFamily;
    }

    public String getPluginName() {
        return pluginName;
    }

    public String getPluginFamily() {
        return pluginFamily;
    }


}
