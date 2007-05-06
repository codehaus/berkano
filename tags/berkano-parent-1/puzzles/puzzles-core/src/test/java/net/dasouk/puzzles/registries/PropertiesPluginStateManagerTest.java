package net.dasouk.puzzles.registries;

import net.dasouk.puzzles.PluginStateManager;

import java.io.File;

/**
 */
public class PropertiesPluginStateManagerTest extends AbstractPluginStateManagerTest {
    public static final String STATES_FOLDER_NAME = "pluginStates";
    private File stateFolder;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        stateFolder = new File(".", STATES_FOLDER_NAME);
        stateFolder.mkdir();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        if (stateFolder.exists()) {
            final File[] files = stateFolder.listFiles();
            for (File file : files) {
                file.delete();
            }
            stateFolder.delete();
        }
    }

    public PluginStateManager newInstance() throws Exception {
        return new PropertiesPluginStateManager(stateFolder);
    }
}
