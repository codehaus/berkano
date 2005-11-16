package net.dasouk.puzzles;

import java.io.Serializable;
import java.util.Set;
import java.net.URL;

/**
 * @todo documentation
 */
public interface PluginsRegistry<PluginClass> extends Serializable {
    public Set<PackagedPlugin<PluginClass>> getInstalledPlugins();

    public PluginDescriptor getPluginInfo(String name) throws PluginNotFoundException;

    public PluginClass getPluginInstance(String name) throws PluginNotFoundException;

    public PackagedPlugin<PluginClass> installPlugin(URL pluginUrl) throws StoreException, PluginInstanciationException;

    public void uninstallPlugin(String name) throws PluginNotFoundException, StoreException;

    public URL getPluginResource(String name, String resourceName) throws PluginNotFoundException, ResourceNotFoundException;

    public boolean addPluginListener(PluginListener<PluginClass> pluginListener);

    public boolean removePluginListener(PluginListener<PluginClass> pluginListener);

    public void startUp() throws StoreException;
    


}
