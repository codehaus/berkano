package net.dasouk.puzzles;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

/**
 * General interface for a component aimed at retrieving and storing plugins.
 * It is a local repository that is able to retrieve a Plugin from a URL.
 * Once retrieved from that URL (the url can be remote, local, http, file, whatever)
 * the store provides another url with wich other components will access the plugin
 * package.
 * The implementation is free to keep the plugin packaged or to explode it or leave it
 * unpackaged if the plugin consists of only one file (e.g. a "Style" plugin for a web application,
 * consisting of only one CSS file).
 *
 * @author souk
 * @version 0.1
 * @see net.dasouk.puzzles.stores.SimpleFileSystemStore
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
