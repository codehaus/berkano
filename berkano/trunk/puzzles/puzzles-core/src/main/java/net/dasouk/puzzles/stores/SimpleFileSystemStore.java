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
 * @todo documentation
 */
public class SimpleFileSystemStore implements PluginStore {
    private File folder;
    private int bufferSize = 2048;
    private FileRenamer fileRenamer;

    public SimpleFileSystemStore(File folder) {
        this(folder, new NullFileRenamer(), 2048);
    }

    public SimpleFileSystemStore(File folder, FileRenamer fileRenamer) {
        this(folder, fileRenamer, 2048);
    }

    public SimpleFileSystemStore(File folder, FileRenamer fileRenamer, int bufferSize) {
        this.fileRenamer = fileRenamer;
        this.bufferSize = bufferSize;
        this.folder = folder;
        if (!folder.exists() || !folder.isDirectory() || !folder.canRead() || !folder.canWrite()) {
            throw new IllegalArgumentException(folder.getAbsolutePath() + " does not exist or is not a directory or can not be written");
        }
    }

    public URL store(URL pluginUrl) throws StoreException {
        //retrieve name of file
        String filename = new File(pluginUrl.getPath()).getName();
        File pluginStoreFile = new File(folder, filename);
        FileOutputStream fos = null;
        InputStream inputStream = null;
        boolean markToBeDeleted = false;
        try {
            while (!pluginStoreFile.createNewFile()) { //beware of infinite loops >_<
                filename = fileRenamer.generateNewName(filename);
                pluginStoreFile = new File(folder, filename);
            }
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
            throw new StoreException("Error while storing plugin: " + e.getMessage(), e);
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    // TODO we're losing a possible IOException from the catch block : what do we do ?
                    throw new StoreException("Error while storing plugin: " + e.getMessage(), e);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO we're losing a possible IOException from the catch block : what do we do ?
                    throw new StoreException("Error while storing plugin: " + e.getMessage(), e);
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

    public FileRenamer getFileRenamer() {
        return fileRenamer;
    }

    public void setFileRenamer(FileRenamer fileRenamer) {
        this.fileRenamer = fileRenamer;
    }

    /**
     * This silly FileRenamer just throws an exception when called, i.e.
     * it does not allow the store overwrite an existing file, but does
     * not provide another name for it either.
     *
     * @author greg
     * @author $Author: $ (last edit)
     * @version $Revision: $
     */
    public final static class NullFileRenamer implements FileRenamer {
        public NullFileRenamer() {
        }

        public String generateNewName(String filename) throws StoreException {
            throw new StoreException("Could not store plugin: a file with the same name already exists in the store");
        }
    }
}
