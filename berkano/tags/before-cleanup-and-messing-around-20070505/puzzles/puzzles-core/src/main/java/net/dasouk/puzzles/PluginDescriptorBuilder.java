package net.dasouk.puzzles;

import java.io.Serializable;
import java.net.URL;

/**
 * A PluginDescriptorBuilder's role is to ... well ... build a plugin's descriptor, given the URL to
 * the descriptor document inside the plugin's package.
 *
 * @author souk
 * @version 0.1
 */
public interface PluginDescriptorBuilder extends Serializable {
    public PluginDescriptor buildDescriptor(URL descriptorUrl) throws PluginDescriptorException;
}
