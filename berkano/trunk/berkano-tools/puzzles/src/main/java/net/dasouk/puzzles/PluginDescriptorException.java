package net.dasouk.puzzles;

import java.net.URL;

/**
 * General Exception to report an exception
 * happening when building/reading the descriptor of a plugin.
 *
 * @author souk
 * @version 0.1
 */
public class PluginDescriptorException extends PluginException {

    protected PluginDescriptorException(URL url) {
        super(url);
    }

    protected PluginDescriptorException(String message, URL url) {
        super(message, url);
    }

    protected PluginDescriptorException(String message, Throwable cause, URL url) {
        super(message, cause, url);
    }

    protected PluginDescriptorException(Throwable cause, URL url) {
        super(cause, url);
    }
}
