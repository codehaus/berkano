package net.dasouk.puzzles;

/**
 * Created by IntelliJ IDEA.
 * User: olivier
 * Date: Nov 9, 2005
 * Time: 3:02:30 PM
 * To change this template use File | Settings | File Templates.
 *
 * @todo documentation
 */
public interface PluginListener<PluginClass> {
    public void installedPlugin(PackagedPlugin<PluginClass> plugin);

    public void uninstalledPlugin(PackagedPlugin<PluginClass> plugin);
}
