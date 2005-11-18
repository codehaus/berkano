package net.dasouk.puzzles;

/**
 * Exception thrown if a resource is not found in a plugin package by the {@link PackageReader}.
 * It encapsulates the name of the concerned plugin and the missing resource's name.
 *
 * @author souk
 * @version 0.1
 * @see PackageReader
 */
public class ResourceNotFoundException extends Exception{
    private String pluginName;
    private String resourceName;

    public ResourceNotFoundException(String pluginName, String resourceName) {
        this.pluginName = pluginName;
        this.resourceName = resourceName;
    }

    public ResourceNotFoundException(String message, String pluginName, String resourceName) {
        super(message);
        this.pluginName = pluginName;
        this.resourceName = resourceName;
    }

    public ResourceNotFoundException(String message, Throwable cause, String pluginName, String resourceName) {
        super(message, cause);
        this.pluginName = pluginName;
        this.resourceName = resourceName;
    }

    public ResourceNotFoundException(Throwable cause, String pluginName, String resourceName) {
        super(cause);
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
