package net.dasouk.puzzles.registries;

import net.dasouk.puzzles.*;
import net.dasouk.puzzles.util.HashTwoLevelMap;
import net.dasouk.puzzles.util.TwoLevelMap;

import java.net.URL;
import java.util.*;

/**
 * Simple PluginRegistry implementation with only one store and one loader.
 * Having only one pluginLoader means that this registry can only load plugins from
 * one type of packaging (being zip, jars, or anything else)
 *
 * @todo refine documentation
 */
public class SimplePluginRegistry implements PluginRegistry {
    private PluginStore pluginStore;
    private PluginLoader pluginLoader;
    private TwoLevelMap<String, String, PackagedPlugin> plugins;
    private TwoLevelMap<String, String, URL> pluginsUrl;
    private Map<String, Set<PluginListener>> listeners;
    private PluginStateManager pluginStateManager;

    public SimplePluginRegistry(PluginStore pluginStore, PluginLoader pluginLoader, PluginStateManager pluginStateManager) {
        this.plugins = new HashTwoLevelMap<String, String, PackagedPlugin>();
        this.pluginsUrl = new HashTwoLevelMap<String, String, URL>();
        this.listeners = new HashMap<String, Set<PluginListener>>();
        this.pluginStateManager = pluginStateManager;
        this.pluginStore = pluginStore;
        this.pluginLoader = pluginLoader;
    }

    public Set<String> getFamilies() {
        return plugins.firstLevelSet();
    }

    public Set<PackagedPlugin> getInstalledPlugins(String family) {
        return plugins.valuesSet(family);
    }

    public PluginDescriptor getPluginDescriptor(String family, String name) throws PluginNotFoundException {
        PackagedPlugin packagedPlugin = getPackagedPlugin(family, name);
        return packagedPlugin.getPluginDescriptor();
    }

    public Object getPluginInstance(String family, String name) throws PluginNotFoundException {
        PackagedPlugin packagedPlugin = getPackagedPlugin(family, name);
        return packagedPlugin.getPlugin();
    }

    private PackagedPlugin getPackagedPlugin(String family, String name) throws PluginNotFoundException {
        PackagedPlugin packagedPlugin = plugins.get(family, name);
        if (packagedPlugin == null) {
            throw new PluginNotFoundException("the plugin could not be found in the registry", null, family, name);
        }
        return packagedPlugin;
    }

    public PackagedPlugin installPlugin(URL pluginUrl, boolean deploy) throws PluginException, PluginStoreException, PluginNotFoundException, PluginStatePersistenceException {
        //store plugin
        URL inStoreUrl = pluginStore.store(pluginUrl);
        final PackagedPlugin packagedPlugin = load(inStoreUrl);
        if (deploy) {
            deployPlugin(packagedPlugin.getPluginDescriptor());
        }
        return packagedPlugin;
    }

    private void deployPlugin(PluginDescriptor pluginDescriptor) throws PluginNotFoundException, PluginStatePersistenceException {
        deployPlugin(pluginDescriptor.getFamily(), pluginDescriptor.getName());
    }

    public void deployPlugin(String family, String pluginName) throws PluginNotFoundException, PluginStatePersistenceException {
        checkPlugin(family, pluginName);
        if (!pluginStateManager.isDeployed(family, pluginName)) {
            pluginStateManager.setPluginState(family, pluginName, PluginState.DEPLOYED);
            fireDeployedPlugin(family, getPackagedPlugin(family, pluginName));
        }
    }

    private void checkPlugin(String family, String pluginName) throws PluginNotFoundException {
        if (!plugins.contains(family, pluginName)) {
            throw new PluginNotFoundException("the plugin could not be found in the registry", null, family, pluginName);
        }
    }


    /**
     * undeploys the plugin and notifies listener. If the plugin does not exist, does nothing
     */
    public void undeployPlugin(String family, String pluginName) throws PluginNotFoundException, PluginStatePersistenceException {
        checkPlugin(family, pluginName);
        if (pluginStateManager.isDeployed(family, pluginName)) {
            pluginStateManager.setPluginState(family, pluginName, PluginState.UNDEPLOYED);
            fireUndeployedPlugin(family, getPackagedPlugin(family, pluginName));
        }
    }

    public PluginState getPluginState(String family, String pluginName) throws PluginNotFoundException {
        return pluginStateManager.getPluginState(family, pluginName);
    }

    private PackagedPlugin load(URL inStoreUrl) throws PluginStoreException, PluginException {
        //load plugin
        PackagedPlugin packagedPlugin = pluginLoader.load(inStoreUrl);
        String name = packagedPlugin.getPluginDescriptor().getName();
        String family = packagedPlugin.getPluginDescriptor().getFamily();
        //check if no plugin in the registry has the same name
        if (plugins.contains(family, name)) {
            pluginStore.remove(inStoreUrl);
            throw new PluginDescriptorException("there is already an installed plugin with the same name and family", null);
        }
        plugins.put(family, name, packagedPlugin);
        pluginsUrl.put(family, name, inStoreUrl);

        return packagedPlugin;
    }

    public void uninstallPlugin(String family, String name) throws PluginNotFoundException, PluginStoreException, PluginStatePersistenceException {
        //first undeploy
        undeployPlugin(family, name);

        //uninstall stuff
        URL url = getPluginUrl(family, name);
        pluginStateManager.removePluginState(family, name);
        pluginStore.remove(url);
        plugins.remove(family, name);
        pluginsUrl.remove(family, name);
    }

    public URL getPluginResource(String family, String name, String resourceName) throws PluginNotFoundException, ResourceNotFoundException {
        PluginDescriptor pluginDescriptor = getPluginDescriptor(family, name);
        URL pluginUrl = getPluginUrl(family, name);
        if (pluginDescriptor.isPublicResource(resourceName)) {
            return pluginLoader.getResource(pluginUrl, resourceName);
        } else {
            throw new ResourceNotFoundException("the resource could not be found or is not declared as public", pluginUrl, family, name, resourceName);
        }
    }

    private URL getPluginUrl(String family, String name) throws PluginNotFoundException {
        URL pluginUrl = pluginsUrl.get(family, name);
        if (pluginUrl == null) {
            throw new PluginNotFoundException("the plugin could not be found in the registry", null, family, name);
        }
        return pluginUrl;
    }

    public Map<URL, PluginException> startUp() throws PluginStoreException {
        //load all the plugins of the store
        Map<URL, PluginException> exceptions = new HashMap<URL, PluginException>();
        List<URL> pluginsInStore = pluginStore.getPluginsInStore();
        for (URL pluginUrl : pluginsInStore) {
            try {
                final PackagedPlugin packagedPlugin = load(pluginUrl);
                PluginDescriptor descriptor = packagedPlugin.getPluginDescriptor();
                if (pluginStateManager.isDeployed(descriptor.getFamily(), descriptor.getName())) {
                    fireDeployedPlugin(descriptor.getFamily(), packagedPlugin);
                }
            } catch (PluginException e) {
                exceptions.put(pluginUrl, e);
            }
        }
        return exceptions;
    }

    public boolean addPluginListener(PluginListener pluginListener, String family) {
        Set<PluginListener> pluginListeners = listeners.get(family);
        if (pluginListeners == null) {
            pluginListeners = new HashSet<PluginListener>();
            listeners.put(family, pluginListeners);
        }
        return pluginListeners.add(pluginListener);
    }

    public boolean removePluginListener(PluginListener pluginListener, String family) {
        Set<PluginListener> pluginListeners = listeners.get(family);
        if (pluginListeners == null) {
            return false;
        }
        return pluginListeners.remove(pluginListener);
    }

    //listeners stuff
    private void fireUndeployedPlugin(String family, PackagedPlugin packagedPlugin) {
        Set<PluginListener> pluginListeners = listeners.get(family);
        if (pluginListeners == null) return;

        for (PluginListener listener : pluginListeners) {
            listener.undeployedPlugin(packagedPlugin);
        }
    }

    private void fireDeployedPlugin(String family, PackagedPlugin packagedPlugin) {
        Set<PluginListener> pluginListeners = listeners.get(family);
        if (pluginListeners == null) return;

        for (PluginListener listener : pluginListeners) {
            listener.deployedPlugin(packagedPlugin);
        }
    }
}
