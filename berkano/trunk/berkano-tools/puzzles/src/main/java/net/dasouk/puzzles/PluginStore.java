package net.dasouk.puzzles;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

/**
 * The PluginStore's role, if it was not obvious, it is to store plugins.
 * Besides that, it must be able to retrieve a Plugin from a URL. Once retrieved
 * from that URL (the url can be remote, local, http, file, whatever) the store provides
 * a another url with wich other components will access the plugin package
 *
 *
 * @todo documentation
 */
public interface PluginStore extends Serializable {
    public URL store(URL pluginUrl) throws StoreException;

    public void remove(URL pluginUrl) throws StoreException;

    public List<URL> getPluginsInStore() throws StoreException;

}
