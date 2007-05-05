package net.dasouk.puzzles.registries;

import net.dasouk.puzzles.PluginStateManager;

import java.io.File;

/**
 */
public class XStreamPluginStateManagerTest extends AbstractPluginStateManagerTest {
    private File stateFile;
    public static final String STATES_FILE_NAME = "pluginStates.xml";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        stateFile = new File(".", STATES_FILE_NAME);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        File stateFile = new File(".", STATES_FILE_NAME);
        if (stateFile.exists()) {
            stateFile.delete();
        }
    }


    public PluginStateManager newInstance() throws Exception {
        return new XStreamPluginStateManager(stateFile);
    }
}
