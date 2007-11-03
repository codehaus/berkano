package net.dasouk.puzzles.stores;

import net.dasouk.puzzles.PluginStore;
import net.dasouk.puzzles.PluginStoreException;
import net.dasouk.puzzles.util.IOUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @todo documentation
 */
public class SimpleFileSystemStore implements PluginStore {
    private File folder;
    private FileRenamer fileRenamer;

    public SimpleFileSystemStore(File folder) {
        this(folder, new NullFileRenamer());
    }

    public SimpleFileSystemStore(File folder, FileRenamer fileRenamer) {
        this.fileRenamer = fileRenamer;
        this.folder = folder;
        if (!folder.exists() || !folder.isDirectory() || !folder.canRead() || !folder.canWrite()) {
            throw new IllegalArgumentException(folder.getAbsolutePath() + " does not exist or is not a directory or can not be written");
        }
    }

    public URL store(URL pluginUrl) throws PluginStoreException {
        //retrieve name of file
        String filename = new File(pluginUrl.getPath()).getName();
        File pluginStoreFile = new File(folder, filename);

        try {
            while (!pluginStoreFile.createNewFile()) { //beware of infinite loops >_<
                filename = fileRenamer.generateNewName(filename);
                pluginStoreFile = new File(folder, filename);
            }

            IOUtil.copy(pluginUrl.openStream(),
                    new FileOutputStream(pluginStoreFile));

            return pluginStoreFile.toURL();
        } catch (IOException e) {
            final boolean deleted = pluginStoreFile.delete();
            throw new PluginStoreException("Error while storing plugin from URL" + pluginUrl.toExternalForm()
                    + (deleted
                    ? " but the file was sucessfully deleted from the store"
                    : " and the file could not be deleted from the store"), e, pluginUrl);
        }
    }

    public void remove(URL pluginUrl) throws PluginStoreException {
        try {
            File pluginFile = new File(pluginUrl.toURI());
            boolean success = pluginFile.delete();
            if (!success) {
                throw new PluginStoreException("could not delete plugin", pluginUrl);
            }
        } catch (URISyntaxException e) {
            throw new PluginStoreException(e.getMessage(), e, pluginUrl);
        }
    }

    public List<URL> getPluginsInStore() throws PluginStoreException {
        List<URL> results = new ArrayList<URL>();
        try {
            File[] files = folder.listFiles();
            for (File file : files) {
                if (file.isFile()) {//don't add folders, files only !
                    results.add(file.toURL());
                }
            }
            return results;
        } catch (MalformedURLException e) {
            throw new PluginStoreException(e.getMessage(), e, null);
        }
    }

    public File getFolder() {
        return folder;
    }

    /**
     * This silly FileRenamer just throws an exception when called, i.e.
     * it does not allow the store to overwrite an existing file, but does
     * not provide another name for it either.
     *
     * @author gjoseph
     * @author $Author: $ (last edit)
     * @version $Revision: $
     */
    public final static class NullFileRenamer implements FileRenamer {
        public NullFileRenamer() {
        }

        public String generateNewName(String filename) throws PluginStoreException {
            throw new PluginStoreException("Could not store plugin: a file with the same name (" + filename + ") already exists in the store", null);
        }
    }
}
