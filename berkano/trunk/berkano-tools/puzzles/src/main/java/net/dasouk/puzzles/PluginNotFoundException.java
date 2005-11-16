package net.dasouk.puzzles;

/**
 * @todo documentation
 */
public class PluginNotFoundException extends Exception {
    private String pluginName;

    public PluginNotFoundException(String pluginName) {
        this.pluginName = pluginName;
    }

    public PluginNotFoundException(String message, String pluginName) {
        super(message);
        this.pluginName = pluginName;
    }

    public PluginNotFoundException(String message, Throwable cause, String pluginName) {
        super(message, cause);
        this.pluginName = pluginName;
    }

    public PluginNotFoundException(Throwable cause, String pluginName) {
        super(cause);
        this.pluginName = pluginName;
    }

    public String getPluginName() {
        return pluginName;
    }


}
