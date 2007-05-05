package net.dasouk.puzzles;

import java.io.Serializable;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * A PluginRegistry manages installed plugins, plugins' installations & uninstallations, notifies listeners.
 * <p/>
 * The PluginRegistry is the core of Puzzles. It is the actual entry point to the
 * Puzzles Plugin Management system. When using Puzzles, you will only interact with
 * a PluginRegistry.
 * Note that the PluginRegistry interface supports Generics. But the interface itself cannot
 * enforce any Class constrint on plugins, this maybe delegated to specific implementations of the registry
 * or on the application level.
 *
 * @author souk
 * @version 0.1
 * @see PackagedPlugin, PluginDescriptor, PluginListener
 */
public interface PluginRegistry extends Serializable {

    /**
     * Retrieves a set of all the plugin families in the registry
     *
     * @return an empty Set if no plugin is installed (no plugin installed => no family).
     */
    public Set<String> getFamilies();

    /**
     * Retrieves all the installed plugins the registry manages.
     *
     * @return an empty Set if no plugin is installed.
     */
    public Set<PackagedPlugin> getInstalledPlugins(String family);

    /**
     * Retrieves the PluginDescriptor of a plugin specified by its name and its family
     *
     * @throws PluginNotFoundException if no plugin with the specified pluginName could be found
     */
    public PluginDescriptor getPluginDescriptor(String family, String pluginName) throws PluginNotFoundException;

    /**
     * Retrieves the actual plugin instance from the plugin name and its family
     *
     * @throws PluginNotFoundException if no plugin with the specified pluginName could be found
     */
    public Object getPluginInstance(String family, String pluginName) throws PluginNotFoundException;

    /**
     * Installs a plugin inside the registry, stores it in the {@link PluginStore}, and builds it.
     * Once the {@link PluginDescriptor} has been built and the plugin instanciated and deployed, the {@link PluginListener
     * PluginListeners} are then notified, and the method finally returns both the descriptor and the plugin instance
     * in the form of a {@link PackagedPlugin}.
     *
     * @param pluginUrl the URL where the {@link PluginStore} will retrieve the plugin from.
     * @param deploy    if true, the plugin will also be deployed, otherwise it'll just be installed but will remain undeployed
     * @throws PluginStoreException         thrown if anything related to the {@link PluginStore} goes wrong.
     *                                      In that case the plugin is automatically removed from the store (if ever the retrieval was successful)
     * @throws PluginDescriptorException    thrown whenever an exception related to the {@link PluginDescriptor} happens.
     *                                      This includes:
     *                                      <ul>
     *                                      <li>The descriptor could not be found in the package</li>
     *                                      <li>The descriptor was found but could not be read/parsed</li>
     *                                      <li>The descriptor was found and parsed but the information it contained was incomplete</li>
     *                                      </ul>
     * @throws PluginInstanciationException thrown whenever an exception related to the plugin's instanciation by the
     *                                      {@link PluginLoader} happens.
     *                                      This includes:
     *                                      <ul>
     *                                      <li>Classpath errors and missing libraries</li>
     *                                      <li>Any error regarding the "blue prints" of the plugin (that is the instructions on how to actually
     *                                      instantiate the plugin)</li>
     *                                      </ul>
     * @see PluginListener#deployedPlugin PluginListener.deployedPlugin(PackagedPlugin)
     * @see PluginLoader
     * @see PluginDescriptor
     */
    public PackagedPlugin installPlugin(URL pluginUrl, boolean deploy)
            throws PluginStoreException, PluginException, PluginNotFoundException, PluginStatePersistenceException;

    /**
     * todo
     */
    public void deployPlugin(String family, String pluginName) throws PluginNotFoundException, PluginStatePersistenceException;

    /**
     * todo
     */
    public void undeployPlugin(String family, String pluginName) throws PluginNotFoundException, PluginStatePersistenceException;


    /**
     * todo
     */
    public PluginState getPluginState(String family, String pluginName) throws PluginNotFoundException;


    /**
     * Uninstalls a plugin from the registry. This operation includes the removal of the plugin from the
     * {@link PluginStore} and the notification of all the {@link PluginListener} that the plugin was undeployed
     * if it was previously deployed.
     *
     * @throws PluginNotFoundException if no plugin with the specified pluginName could be found
     * @throws PluginStoreException    thrown if anything related to the {@link PluginStore} goes wrong. In that case, it can be
     *                                 expected that the stored plugin (in the packaged or exploded form) remains in the store and will be loaded next
     *                                 time the registry starts up, unless removed later inbetween.
     * @see PluginListener#undeployedPlugin PluginListener.undeployedPlugin(PackagedPlugin)
     */
    public void uninstallPlugin(String family, String pluginName) throws PluginNotFoundException, PluginStoreException, PluginStatePersistenceException;

    /**
     * Used to access plugins' resources. A resource is any file that comes packaged with the plugin
     * (or may be the package itself in the case of a CSS plugin for example). In order to be accessed
     * via this method, the resource has to be declared as a {@link PluginDescriptor#isPublicResource(String) public
     * resource} in the {@link PluginDescriptor} or a ResourceNotFoundException will be raised. This "public resource"
     * considerqtion is a security measure to prevent access to the actual plugins' source/compiled code.
     *
     * @return the resource is returned as a URL (which may represent either a local file on the file system or a remote
     *         resource)
     * @throws PluginNotFoundException   if no plugin with the specified pluginName could be found
     * @throws ResourceNotFoundException if no (public) resource with that name was found.
     */
    public URL getPluginResource(String family, String pluginName, String resourceName) throws PluginNotFoundException, ResourceNotFoundException;

    /**
     * Starts up the registry. This method enables a registry to initiate itself. The initialization of the registry
     * mainly consists of loading the plugins already installed in the underlying {@link PluginStore}, and notifying
     * the {@link PluginListener PluginListeners} of the installation of those aforementioned plugins.
     *
     * @throws PluginStoreException thrown if anything related to the {@link PluginStore} goes wrong.
     */
    public Map<URL, PluginException> startUp() throws PluginStoreException;

    public boolean addPluginListener(PluginListener pluginListener, String family);

    public boolean removePluginListener(PluginListener pluginListener, String family);


}
