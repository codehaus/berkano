package net.dasouk.puzzles.registries;

import com.thoughtworks.xstream.XStream;
import net.dasouk.puzzles.PluginState;
import net.dasouk.puzzles.PluginStatePersistenceException;
import net.dasouk.puzzles.util.TwoLevelMap;

import java.io.*;
import java.net.URL;

/**
 * implementation using XStream to persist the plugins' states.
 * Note: beware of concurrency issues here since the persistence is automatic
 */
public class XStreamPluginStateManager extends AbstractPluginStateManager {
    private File storeFile;
    private URL storeFileUrl;
    private XStream xStream;

    public XStreamPluginStateManager(File storeFile) throws Exception, FileNotFoundException {
        super();
        this.xStream = new XStream();
        this.storeFile = storeFile;
        this.storeFileUrl = storeFile.toURL();
        if (storeFile.exists() && storeFile.isDirectory()) {
            throw new IllegalArgumentException(storeFile.getAbsolutePath() + " is a folder");
        }
        loadStates();
    }

    protected void loadStates() throws FileNotFoundException {
        if (storeFile.exists()) {
            pluginStates = (TwoLevelMap<String, String, PluginState>) xStream.fromXML(new FileReader(this.storeFile));
        }
    }

    protected void persistStates() throws PluginStatePersistenceException {
        try {
            xStream.toXML(pluginStates, new FileWriter(storeFile));
        } catch (IOException e) {
            throw new PluginStatePersistenceException("unable to persist plugins' states to the disk", storeFileUrl);
        }
    }
}
