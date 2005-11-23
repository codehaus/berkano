package net.dasouk.puzzles;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

/**
 * A PluginBuilder's role is to instanciate a plugin from a list of jar dependencies
 * and the actual plugin's blue prints.
 *
 * @author souk
 * @version 0.1
 */
public interface PluginBuilder<PluginClass> extends Serializable {
    /**
     * instanciates the plugin object from a list of jar files' URLs and the plugin's blue prints' URL.
     *
     * @param jars             a list of URLs to jar files bundled with the plugin
     * @param pluginBluePrints the URL to the plugin's blue prints
     * @return an instance of the plugin
     * @throws PluginInstanciationException if any exception occurs while instanciating the plugin.
     */
    public PluginClass build(List<URL> jars, URL pluginBluePrints) throws PluginInstanciationException;
}
