package net.dasouk.puzzles;

import java.net.URL;

/**
 * General Exception to report an exception
 * happening when building/reading the descriptor of a plugin.
 *
 * @author souk
 * @version 0.1
 */
public class PluginDescriptorException extends PluginLoadingException {

    public PluginDescriptorException(String message, URL url) {
        super(message, url);
    }

    public PluginDescriptorException(String message, Throwable cause, URL url) {
        super(message, cause, url);
    }
}
