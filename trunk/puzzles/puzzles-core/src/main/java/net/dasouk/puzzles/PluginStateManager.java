package net.dasouk.puzzles;

/**
 * A plugin state manager persists the states of the installed plugins
 */
public interface PluginStateManager {
    /**
     * sets the state of the plugin, it is automatically persisted
     */
    public void setPluginState(String family, String pluginName, PluginState state) throws PluginStatePersistenceException;

    /**
     * retrieves the state of the given plugin
     * if the given plugin is not found in the plugin state manager, the method returns PluginState.UNDEPLOYED
     */
    public PluginState getPluginState(String family, String pluginName);

    /**
     * removes the state of the given plugin
     * if the given plugin is not found in the plugin state manager, the method returns PluginState.UNDEPLOYED
     */
    public PluginState removePluginState(String family, String pluginName) throws PluginStatePersistenceException;

    /**
     * returns true if the plugin's state is PluginState.DEPLOYED
     * if the given plugin is not found in the plugin state manager, the method returns false
     */
    public boolean isDeployed(String family, String name);
}
