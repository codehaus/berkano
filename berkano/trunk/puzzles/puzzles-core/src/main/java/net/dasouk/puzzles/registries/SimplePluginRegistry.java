package net.dasouk.puzzles.registries;

import net.dasouk.puzzles.*;

import java.net.URL;
import java.util.*;

/**
 * Simple PluginRegistry implementation with only one store and one loader.
 * Having only one pluginLoader means that this registry can only load plugins from
 * one type of packaging (being zip, jars, or anything else)
 *
 * @todo refine documentation
 */
public class SimplePluginRegistry<PluginClass> implements PluginRegistry<PluginClass> {
    private PluginStore pluginStore;
    private PluginLoader<PluginClass> pluginLoader;
    private Map<String, PackagedPlugin<PluginClass>> plugins;
    private Map<String, URL> pluginsUrl;
    private Set<PluginListener<PluginClass>> listeners;
    private Class enforceClass = null;

    public SimplePluginRegistry(PluginStore pluginStore, PluginLoader<PluginClass> pluginLoader) {
        this(pluginStore,  pluginLoader, null);
    }

    public SimplePluginRegistry(PluginStore pluginStore, PluginLoader<PluginClass> pluginLoader, Class enforceClass) {
        this.plugins = new HashMap<String, PackagedPlugin<PluginClass>>();
        this.pluginsUrl = new HashMap<String, URL>();
        this.listeners = new HashSet<PluginListener<PluginClass>>();
        this.pluginStore = pluginStore;
        this.pluginLoader = pluginLoader;
        this.enforceClass = enforceClass;
    }

    public Set<PackagedPlugin<PluginClass>> getInstalledPlugins() {
        return new HashSet<PackagedPlugin<PluginClass>>(plugins.values());
    }

    public PluginDescriptor getPluginDescriptor(String name) throws PluginNotFoundException {
        PackagedPlugin<PluginClass> packagedPlugin = getPackagedPlugin(name);
        return packagedPlugin.getPluginDescriptor();
    }

    public PluginClass getPluginInstance(String name) throws PluginNotFoundException {
        PackagedPlugin<PluginClass> packagedPlugin = getPackagedPlugin(name);
        return packagedPlugin.getPlugin();
    }

    private PackagedPlugin<PluginClass> getPackagedPlugin(String name) throws PluginNotFoundException {
        PackagedPlugin<PluginClass> packagedPlugin = plugins.get(name);
        if (packagedPlugin == null) {
            throw new PluginNotFoundException(name);
        }
        return packagedPlugin;
    }

    public PackagedPlugin<PluginClass> installPlugin(URL pluginUrl) throws PluginException, StoreException {
        //store plugin
        URL inStoreUrl = pluginStore.store(pluginUrl);
        return load(inStoreUrl);
    }

    private PackagedPlugin<PluginClass> load(URL inStoreUrl) throws StoreException, PluginException {
        //load plugin
        try {
            PackagedPlugin<PluginClass> packagedPlugin = pluginLoader.load(inStoreUrl);
            if (enforceClass != null) {
                PluginClass pluginInstance = packagedPlugin.getPlugin();
                if (!enforceClass.isAssignableFrom(pluginInstance.getClass())){
                    throw new PluginInstanciationException("Wrong plugin class: plugin must be a "+enforceClass.getCanonicalName(),inStoreUrl);
                }
            }
            String name = packagedPlugin.getPluginDescriptor().getName();
            //check if no plugin in the registry has the same name
            if(plugins.keySet().contains(name)){
                throw new PluginDescriptorException("there is already an installed plugin with the same name",null);
            }
            plugins.put(name, packagedPlugin);
            pluginsUrl.put(name, inStoreUrl);

            //notify listeners
            fireInstalledPlugin(packagedPlugin);
            return packagedPlugin;
        } catch (PluginException e) {
            pluginStore.remove(inStoreUrl);
            throw e;//rethrow the exception after cleaning the store
        }
    }

    public void uninstallPlugin(String name) throws PluginNotFoundException, StoreException {
        //uninstall stuff
        PackagedPlugin<PluginClass> packagedPlugin = getPackagedPlugin(name);
        URL url = pluginsUrl.get(name);
        pluginStore.remove(url);
        plugins.remove(name);
        pluginsUrl.remove(name);

        //notifyListeners
        fireUninstalledPlugin(packagedPlugin);
    }

    public URL getPluginResource(String name, String resourceName) throws PluginNotFoundException, ResourceNotFoundException {
        PluginDescriptor pluginDescriptor = getPluginDescriptor(name);
        if (pluginDescriptor.isPublicResource(resourceName)) {
            URL pluginUrl = pluginsUrl.get(name);
            return pluginLoader.getResource(pluginUrl, resourceName);
        } else {
            throw new PluginNotFoundException(name);
        }
    }

    public void startUp() throws StoreException {
        //load all the plugins of the store
        List<URL> pluginsInStore = pluginStore.getPluginsInStore();
        for (URL pluginUrl : pluginsInStore) {
            try {
                load(pluginUrl);
            } catch (PluginException e) {
                pluginStore.remove(pluginUrl); //maybe too extreme :petrus75:
            }
        }
    }

    public PluginStore getPluginStore() {
        return pluginStore;
    }

    public void setPluginStore(PluginStore pluginStore) {
        this.pluginStore = pluginStore;
    }

    public PluginLoader<PluginClass> getPluginLoader() {
        return pluginLoader;
    }

    public void setPluginLoader(PluginLoader<PluginClass> pluginLoader) {
        this.pluginLoader = pluginLoader;
    }

    public boolean addPluginListener(PluginListener<PluginClass> pluginListener) {
        return listeners.add(pluginListener);
    }

    public boolean removePluginListener(PluginListener<PluginClass> pluginListener) {
        return listeners.remove(pluginListener);
    }

    //listeners stuff
    private void fireUninstalledPlugin(PackagedPlugin<PluginClass> packagedPlugin) {
        for (PluginListener<PluginClass> listener : listeners) {
            listener.uninstalledPlugin(packagedPlugin);
        }
    }

    private void fireInstalledPlugin(PackagedPlugin<PluginClass> packagedPlugin) {
        for (PluginListener<PluginClass> listener : listeners) {
            listener.installedPlugin(packagedPlugin);
        }
    }
}