package net.dasouk.puzzles;

/**
 * Listener interface to receive plugins related events notification from a {@link PluginRegistry}.
 *
 * @author souk
 * @version 0.1
 */
public interface PluginListener<PluginClass> {
    /**
     * called whenever a plugin is successfully installed in the registry
     */
    public void installedPlugin(PackagedPlugin<PluginClass> plugin);

    /**
     * called whenever a plugin is successfully uninstalled and removed from the registry
     */
    public void uninstalledPlugin(PackagedPlugin<PluginClass> plugin);
}
