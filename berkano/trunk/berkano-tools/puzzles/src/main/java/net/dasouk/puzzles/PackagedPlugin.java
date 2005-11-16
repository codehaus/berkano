package net.dasouk.puzzles;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: olivier
 * Date: Nov 16, 2005
 * Time: 4:21:12 PM
 * To change this template use File | Settings | File Templates.
 *
 * @todo documentation
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
