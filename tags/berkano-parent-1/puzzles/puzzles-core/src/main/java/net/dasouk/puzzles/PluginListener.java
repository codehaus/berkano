package net.dasouk.puzzles;

/**
 * Listener interface to receive plugins related events notification from a {@link PluginRegistry}.
 *
 * @author souk
 * @version 0.1
 */
public interface PluginListener {
    /**
     * called whenever a plugin is successfully deployed in the registry
     */
    public void deployedPlugin(PackagedPlugin plugin);

    /**
     * called whenever a plugin is successfully undeployed
     */
    public void undeployedPlugin(PackagedPlugin plugin);
}
