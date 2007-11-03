package net.dasouk.puzzles.stores;

import junit.framework.TestCase;


public class IncrementalFileRenamerTest extends TestCase {
    public void testBasicRenaming() {
        IncrementalFileRenamer renamer = new IncrementalFileRenamer();
        String[] filenames = new String[]{"plugin.zip", "plugin-23d5.tar", "plug-in-1-13b.tar.gz"};
        String[] expectedFilenames = new String[]{"plugin-1.zip", "plugin-23d5-1.tar", "plug-in-1-13b-1.tar.gz"};
        for (int i = 0; i < filenames.length; i++) {
            String filename = filenames[i];
            String expectedFilename = expectedFilenames[i];
            String generatedFilename = renamer.generateNewName(filename);
            assertEquals(expectedFilename, generatedFilename);
        }
    }

    public void testRenamingWithPreviouslyGeneratedFilenames() {
        IncrementalFileRenamer renamer = new IncrementalFileRenamer();
        String[] filenames = new String[]{"plugin-12.zip", "plugin-23d5-209.tar", "plug-in-1-13b-0.tar.gz", "plugin-2.4.zip"};
        String[] expectedFilenames = new String[]{"plugin-13.zip", "plugin-23d5-210.tar", "plug-in-1-13b-1.tar.gz", "plugin-3.4.zip"};
        for (int i = 0; i < filenames.length; i++) {
            String filename = filenames[i];
            String expectedFilename = expectedFilenames[i];
            assertEquals(expectedFilename, renamer.generateNewName(filename));
        }
    }
}
