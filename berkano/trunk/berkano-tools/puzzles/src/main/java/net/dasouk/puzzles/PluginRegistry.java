package net.dasouk.puzzles;

import java.io.Serializable;
import java.util.Set;
import java.net.URL;

/**
 * @todo
 * @author souk
 * @version 0.1
 *
 * @see PackagedPlugin, PluginDescriptor, PluginListener
 */
public interface PluginRegistry<PluginClass> extends Serializable {
    /**
     * Retrieves all the installed plugins the registry manages.
     * @return an empty Set if no plugin is installed.
     */
    public Set<PackagedPlugin<PluginClass>> getInstalledPlugins();

    /**
     * Retrieves the PluginDescriptor of a plugin specified by its name
     * @throws PluginNotFoundException if no plugin with the specified pluginName could be found
     */
    public PluginDescriptor getPluginDescriptor(String pluginName) throws PluginNotFoundException;

    /**
     * Retrieves the actual plugin instance from the plugin name
     * @throws PluginNotFoundException if no plugin with the specified pluginName could be found
     */
    public PluginClass getPluginInstance(String pluginName) throws PluginNotFoundException;

    /**
     * Installs a plugin inside the registry, stores it in the {@link PluginStore}, and builds it.
     * Once the {@link PluginDescriptor} has been built and the plugin instanciated, the {@link PluginListener
     * PluginListeners} are then notified, and the method finally returns both the descriptor and the plugin instance
     * in the form of a {@link PackagedPlugin}.
     * @param pluginUrl the URL where the {@link PluginStore} will retrieve the plugin from.
     * @throws StoreException thrown if anything related to the {@link PluginStore} goes wrong.
     * In that case the plugin is automatically removed from the store (if ever the retrieval was successful)
     * @throws PluginDescriptorException thrown whenever an exception related to the {@link PluginDescriptor} happens.
     * This includes:
     * <ul>
     *      <li>The descriptor could not be found in the package</li>
     *      <li>The descriptor was found but could not be read/parsed</li>
     *      <li>The descriptor was found and parsed but the information it contained was incomplete</li>
     * </ul>
     * @throws PluginInstanciationException thrown whenever an exception related to the plugin's instanciation by the
     * {@link PluginBuilder} happens.
     * This includes:
     * <ul>
     *      <li>Classpath errors and missing libraries</li>
     *      <li>Any error regarding the "blue prints" of the plugin (that is the instructions on how to actually
     * instantiate the plugin)</li>
     * </ul>
     * @see net.dasouk.puzzles.PluginListener#installedPlugin PluginListener.installedPlugin(PackagedPlugin<PluginClass>)
     * @see PluginBuilder
     * @see PluginDescriptor
     */
    public PackagedPlugin<PluginClass> installPlugin(URL pluginUrl)
            throws StoreException, PluginInstanciationException, PluginDescriptorException;

    /**
     * Uninstalls a plugin from the registry. This operation includes the removal of the plugin from the
     * {@link PluginStore} and the notification of all the {@link PluginListener}.
     * @throws PluginNotFoundException if no plugin with the specified pluginName could be found
     * @throws StoreException thrown if anything related to the {@link PluginStore} goes wrong. In that case, it can be
     * expected that the stored plugin (in the packaged or exploded form) remains in the store and will be loaded next
     * time the registry starts up, unless removed later inbetween.
     *
     * @see net.dasouk.puzzles.PluginListener#uninstalledPlugin PluginListener.uninstalledPlugin(PackagedPlugin<PluginClass>)
     */
    public void uninstallPlugin(String pluginName) throws PluginNotFoundException, StoreException;

    /**
     * @todo
     * @param pluginName
     * @param resourceName
     * @return
     * @throws PluginNotFoundException
     * @throws ResourceNotFoundException
     */
    public URL getPluginResource(String pluginName, String resourceName) throws PluginNotFoundException, ResourceNotFoundException;

    /**
     * Starts up the registry. This method enables a registry to initiate itself. The initialization of the registry
     * mainly consists of loading the plugins already installed in the underlying {@link PluginStore}, and notifying
     * the {@link PluginListener PluginListeners} of the installation of those aforementioned plugins.
     * @throws StoreException thrown if anything related to the {@link PluginStore} goes wrong.
     */
    public void startUp() throws StoreException;

    public boolean addPluginListener(PluginListener<PluginClass> pluginListener);

    public boolean removePluginListener(PluginListener<PluginClass> pluginListener);


    


}
