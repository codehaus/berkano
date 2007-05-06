package net.dasouk.puzzles.registries;

import net.dasouk.puzzles.PluginState;
import net.dasouk.puzzles.PluginStateManager;

import java.io.File;
import java.text.ParseException;

public class SimpleFlatFilePluginStateManagerTest extends AbstractPluginStateManagerTest {
    private File storeFile;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        storeFile = new File("storeFile.flat");
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        if (storeFile != null && storeFile.exists()) {
            storeFile.delete();
        }
    }

    public void testFromLine() throws Exception {
        String line = "2;2;ff;nn;DEPLOYED";
        SimpleFlatFilePluginStateManager p = new SimpleFlatFilePluginStateManager(storeFile);
        SimpleFlatFilePluginStateManager.DataLine dataLine = p.fromLine(line);
        assertEquals("ff", dataLine.getFamily());
        assertEquals("nn", dataLine.getName());
        assertEquals(PluginState.DEPLOYED.name(), dataLine.getState().name());
    }

    public void testFromLineWithWrongFormat() throws Exception {
        String line = "hello there this is a wrong format line :dawa:";
        SimpleFlatFilePluginStateManager p = new SimpleFlatFilePluginStateManager(storeFile);
        try {
            p.fromLine(line);
            fail("should throw ParseException");
        } catch (ParseException e) {
            assertEquals("could not parse line", e.getMessage());
        }
    }

    public void testToLine() throws Exception {
        SimpleFlatFilePluginStateManager.DataLine dataLine = new SimpleFlatFilePluginStateManager.DataLine("ff", "nn", PluginState.DEPLOYED);
        SimpleFlatFilePluginStateManager p = new SimpleFlatFilePluginStateManager(storeFile);
        String line = p.toLine(dataLine);
        assertEquals("2;2;ff;nn;DEPLOYED", line);


    }

    public PluginStateManager newInstance() throws Exception {
        return new SimpleFlatFilePluginStateManager(storeFile);
    }
}
