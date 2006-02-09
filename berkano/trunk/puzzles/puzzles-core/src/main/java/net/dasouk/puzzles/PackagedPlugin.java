package net.dasouk.puzzles;

import java.io.Serializable;

/**
 * Simple model class to hold information related to a plugin: it includes the descriptor and the plugin instance (if there is one)
 *
 * @author souk
 * @version 0.1
 */
public class PackagedPlugin implements Serializable {
    private PluginDescriptor pluginDescriptor;
    private Object plugin;

    public PackagedPlugin(PluginDescriptor pluginDescriptor, Object plugin) {
        this.pluginDescriptor = pluginDescriptor;
        this.plugin = plugin;
    }

    public PluginDescriptor getPluginDescriptor() {
        return pluginDescriptor;
    }

    public Object getPlugin() {
        return plugin;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final PackagedPlugin that = (PackagedPlugin) o;

        return !(pluginDescriptor != null ? !pluginDescriptor.equals(that.pluginDescriptor) : that.pluginDescriptor != null);

    }

    public int hashCode() {
        return (pluginDescriptor != null ? pluginDescriptor.hashCode() : 0);
    }
}
