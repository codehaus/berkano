package net.dasouk.puzzles;

import java.io.Serializable;

/**
 * Simple model class to hold information related to a plugin: it includes the descriptor and the plugin instance (if there is one)
 *
 * @author souk
 * @version 0.1
 */
public class PackagedPlugin<PluginClass> implements Serializable {
    private PluginDescriptor pluginDescriptor;
    private PluginClass plugin;

    public PackagedPlugin(PluginDescriptor pluginDescriptor, PluginClass plugin) {
        this.pluginDescriptor = pluginDescriptor;
        this.plugin = plugin;
    }

    public PluginDescriptor getPluginDescriptor() {
        return pluginDescriptor;
    }

    public PluginClass getPlugin() {
        return plugin;
    }
}
