package net.dasouk.puzzles;

import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: olivier
 * Date: Nov 16, 2005
 * Time: 4:05:25 PM
 * To change this template use File | Settings | File Templates.
 *
 * @todo documentation
 */
public class PluginDescriptorException extends Exception {
    private URL descriptorUrl;

    public PluginDescriptorException(URL descriptorUrl) {
        this.descriptorUrl = descriptorUrl;
    }

    public PluginDescriptorException(String message, URL descriptorUrl) {
        super(message);
        this.descriptorUrl = descriptorUrl;
    }

    public PluginDescriptorException(String message, Throwable cause, URL descriptorUrl) {
        super(message, cause);
        this.descriptorUrl = descriptorUrl;
    }

    public PluginDescriptorException(Throwable cause, URL descriptorUrl) {
        super(cause);
        this.descriptorUrl = descriptorUrl;
    }

    public URL getDescriptorUrl() {
        return descriptorUrl;
    }
}
