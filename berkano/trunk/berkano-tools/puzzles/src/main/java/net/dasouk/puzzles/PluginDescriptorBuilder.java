package net.dasouk.puzzles;

import java.io.Serializable;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: olivier
 * Date: Nov 16, 2005
 * Time: 4:04:18 PM
 * To change this template use File | Settings | File Templates.
 *
 * @todo documentation
 */
public interface PluginDescriptorBuilder extends Serializable {
    public PluginDescriptor builDeacriptor(URL descriptorUrl)throws PluginDescriptorException;
}
