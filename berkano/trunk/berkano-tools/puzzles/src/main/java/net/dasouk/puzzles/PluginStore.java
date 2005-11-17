package net.dasouk.puzzles;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

/**
 * The PluginStore's role, if it was not obvious, is to store plugins.
 * Besides that, it must be able to retrieve a Plugin from a URL. Once retrieved
 * from that URL (the url can be remote, local, http, file, whatever) the store provides
 * a another url with wich other components will access the plugin package
 *
 *
 * @author souk
 * @version 0.1
 * @todo do not use URL for local storage.
 */
public interface PluginStore extends Serializable {
    /**
     * Retrieves a plugin from the given URL and store it.
     * @param pluginUrl the URL to download the plugin
     * @return the URL of the plugin inside the store
     * @throws StoreException thrown if anything related to the downloading
     * or the storing of the plugin goes wrong
     */
    public URL store(URL pluginUrl) throws StoreException;

    /**
     * removes a plugin from the store
     * @param pluginUrl the URL of the plugin inside the store
     * @throws StoreException if the removal encounters problems
     */
    public void remove(URL pluginUrl) throws StoreException;

    /**
     * List the urls of the installed plugins. the returned URLs are the
     * urls of the plugins inside the store.
     * @return a list of the URLs of the installed plugins.
     * @throws StoreException
     */
    public List<URL> getPluginsInStore() throws StoreException;

}
