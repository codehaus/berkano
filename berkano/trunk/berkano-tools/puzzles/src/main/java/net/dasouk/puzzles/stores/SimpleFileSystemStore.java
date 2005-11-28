package net.dasouk.puzzles.stores;

import net.dasouk.puzzles.PluginStore;
import net.dasouk.puzzles.StoreException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @todo documentation + file naming strategy
 */
public class SimpleFileSystemStore implements PluginStore {
    private File folder;
    private int bufferSize = 2048;

    public SimpleFileSystemStore(File folder) throws IllegalArgumentException {
        this.folder = folder;
        if (!folder.exists() || !folder.isDirectory()) {
            throw new IllegalArgumentException(folder.getAbsolutePath() + " does not exist or is not a folder");
        }
    }

    public SimpleFileSystemStore(File folder, int bufferSize) {
        this(folder);
        this.bufferSize = bufferSize;
    }

    public URL store(URL pluginUrl) throws StoreException {
        //retrieve name of file
        String filename = new File(pluginUrl.getPath()).getName();
        File pluginStoreFile = new File(folder, filename);//lets hope it does not exist yet @todo
        FileOutputStream fos = null;
        InputStream inputStream = null;
        boolean markToBeDeleted = false;
        try {

            boolean createdFile = pluginStoreFile.createNewFile();
            if (!createdFile) throw new StoreException("could not store plugin");
            fos = new FileOutputStream(pluginStoreFile);
            inputStream = pluginUrl.openStream();
            byte[] buffer = new byte[bufferSize];
            int read;
            while ((read = inputStream.read(buffer)) > 0) {
                fos.write(buffer, 0, read);
            }
            return pluginStoreFile.toURL();
        } catch (IOException e) {
            markToBeDeleted = true;
            throw new StoreException("error while storing plugin", e);
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (markToBeDeleted) {
                pluginStoreFile.delete();
            }
        }
    }

    public void remove(URL pluginUrl) throws StoreException {
        try {
            File pluginFile = new File(pluginUrl.toURI());
            boolean success = pluginFile.delete();
            if (!success) {
                throw new StoreException("could not delete file");
            }
        } catch (URISyntaxException e) {
            throw new StoreException(e);
        }
    }

    public List<URL> getPluginsInStore() throws StoreException {
        List<URL> results = new ArrayList<URL>();
        try {
            File[] files = folder.listFiles();
            for (File file : files) {
                results.add(file.toURL());
            }
            return results;
        } catch (MalformedURLException e) {
            throw new StoreException("error while listing files in the store folder");
        }
    }

    public File getFolder() {
        return folder;
    }

    public void setFolder(File folder) {
        this.folder = folder;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

}
