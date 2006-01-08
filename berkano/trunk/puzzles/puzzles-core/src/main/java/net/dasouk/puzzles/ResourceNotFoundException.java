package net.dasouk.puzzles;

import java.net.URL;

/**
 * Exception thrown if a resource is not found in a plugin package by the {@link PackageReader}.
 * It encapsulates the name of the concerned plugin and the missing resource's name.
 *
 * @author souk
 * @version 0.1
 * @see PackageReader
 */
public class ResourceNotFoundException extends PluginException {
    private String pluginName;
    private String resourceName;

    public ResourceNotFoundException(URL url, String pluginName, String resourceName) {
        super(url);
        this.pluginName = pluginName;
        this.resourceName = resourceName;
    }

    public ResourceNotFoundException(String message, URL url, String pluginName, String resourceName) {
        super(message, url);
        this.pluginName = pluginName;
        this.resourceName = resourceName;
    }

    public ResourceNotFoundException(String message, Throwable cause, URL url, String pluginName, String resourceName) {
        super(message, cause, url);
        this.pluginName = pluginName;
        this.resourceName = resourceName;
    }

    public ResourceNotFoundException(Throwable cause, URL url, String pluginName, String resourceName) {
        super(cause, url);
        this.pluginName = pluginName;
        this.resourceName = resourceName;
    }

    public String getPluginName() {
        return pluginName;
    }

    public String getResourceName() {
        return resourceName;
    }

}
