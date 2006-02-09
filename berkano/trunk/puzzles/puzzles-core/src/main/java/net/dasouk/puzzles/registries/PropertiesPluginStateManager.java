package net.dasouk.puzzles.registries;

import net.dasouk.puzzles.PluginState;

import java.io.*;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * PluginStateManager implementation based on java.util.Properties.
 * Note: beware of concurrency issues
 * TODO this implementation has a serious problem: if the family name contains "/" characters
 * the .properties file can be written anywhere on the file system ... >_<
 */
public class PropertiesPluginStateManager extends AbstractPluginStateManager {
    private File storeFolder;

    public PropertiesPluginStateManager(File storeFolder) throws Exception {
        super();
        if (!storeFolder.exists() || storeFolder.isFile())
            throw new IllegalArgumentException(storeFolder.getAbsolutePath() + " does not exist or is not a folder");
        this.storeFolder = storeFolder;
        loadStates();
    }

    protected void loadStates() throws IOException {
        final File[] files = storeFolder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".properties");
            }
        });
        for (File file : files) {
            final String filename = file.getName();
            String family = filename.substring(0, filename.lastIndexOf("."));
            Properties properties = new Properties();
            properties.load(new FileInputStream(file));
            final Set<Map.Entry<Object, Object>> entries = properties.entrySet();
            for (Map.Entry<Object, Object> entry : entries) {
                String pluginName = (String) entry.getKey();
                PluginState state = PluginState.valueOf((String) entry.getValue());
                pluginStates.put(family, pluginName, state);
            }
        }
    }

    protected void persistStates() throws IOException {
        final Set<String> families = pluginStates.firstLevelSet();
        for (String family : families) {
            Properties properties = new Properties();
            final Set<String> pluginNames = pluginStates.secondLevelSet(family);
            for (String pluginName : pluginNames) {
                properties.put(pluginName, pluginStates.get(family, pluginName).toString());
            }
            properties.store(new FileOutputStream(new File(storeFolder, family + ".properties")), "no comment [:dawa]");
        }
    }


}
