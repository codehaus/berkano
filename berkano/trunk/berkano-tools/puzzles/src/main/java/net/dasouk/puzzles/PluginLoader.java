package net.dasouk.puzzles;

import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: olivier
 * Date: Nov 9, 2005
 * Time: 3:11:02 PM
 * To change this template use File | Settings | File Templates.
 *
 * @todo documentation
 */
public interface PluginLoader<PluginClass> {
    public PackagedPlugin<PluginClass> load(URL pluginPackageUrl) throws PluginInstanciationException;

    public URL getResource(URL pluginUrl, String resourceName) throws ResourceNotFoundException;
}
