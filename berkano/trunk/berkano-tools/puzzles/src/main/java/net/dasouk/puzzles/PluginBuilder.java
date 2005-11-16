package net.dasouk.puzzles;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: olivier
 * Date: Nov 16, 2005
 * Time: 3:57:24 PM
 * To change this template use File | Settings | File Templates.
 *
 * @todo documentation
 */
public interface PluginBuilder<PluginClass> extends Serializable {
    public PluginClass build(List<URL> jars, URL pluginDescriptor)throws PluginInstanciationException;
}
