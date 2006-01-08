package net.dasouk.puzzles;

import java.net.URL;

/**
 * This class is a simple front end to the whole process of reading the descriptor of a plugin,
 * building the descriptor, reading the blue prints, instanciate it, etc.
 * A PluginRegistry's implementation may choose to do all this itself, but the PluginLoader abstraction
 * enables reuse among different PluginRegitry's implementations.
 *
 * @author souk
 * @version 0.1
 */
public interface PluginLoader<PluginClass> {
    /**
     * Produces a PackagedPlugin containing both the PluginDescriptor and the plugin instance
     * from the URL of the plugin's package's URL inside a PluginStore.
     *
     * @throws PluginException if any exception related to the PluginDescriptor or plugin instanciation goes wrong
     */
    public PackagedPlugin<PluginClass> load(URL pluginPackageUrl) throws PluginException;

    /**
     * @param pluginUrl
     * @param resourceName
     * @return
     * @throws ResourceNotFoundException
     */
    public URL getResource(URL pluginUrl, String resourceName) throws ResourceNotFoundException;
}
