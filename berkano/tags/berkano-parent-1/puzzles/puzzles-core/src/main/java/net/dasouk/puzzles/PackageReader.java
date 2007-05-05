package net.dasouk.puzzles;

import java.io.Serializable;
import java.net.URL;

/**
 * A PackageReader is a class that is able to read a bundled plugin (aka a package).
 * It should know how to retrieve the descriptor of a plugin, as well as the blue prints
 * of the plugin (how to instanciate the plugin if the plugin consists of a java object).
 * Most of the time in Puzzles, plugins come packaged as a jar/zip file, the package reader
 * will handle those packages transparently.
 *
 * @author souk
 * @version 0.1
 */
public interface PackageReader extends Serializable {
    /**
     * Retrieves the URL to the plugin descriptor, given the URL of the plugin inside a
     * plugin store.
     *
     * @throws PluginDescriptorException if the descriptor could not be found
     */
    public URL getPluginDescriptor(URL pluginUrl) throws PluginDescriptorException;

    /**
     * Retrieves the URL to a plugin's resource (public or private), given the URL of the plugin inside a
     * plugin store.
     *
     * @throws ResourceNotFoundException if the resource with the given name could not be found.
     */
    public URL getResource(URL pluginUrl, String name) throws ResourceNotFoundException;
}
