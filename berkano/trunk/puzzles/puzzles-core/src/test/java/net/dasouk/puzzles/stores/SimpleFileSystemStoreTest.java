package net.dasouk.puzzles.stores;

import net.dasouk.puzzles.PluginStoreException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class SimpleFileSystemStoreTest extends MockObjectTestCase {
    File storeFolder;

    protected void setUp() throws Exception {
        super.setUp();
        storeFolder = new File("tmpFolder");
        storeFolder.mkdir();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        File[] files = storeFolder.listFiles();
        for (File file : files) {
            file.delete();
        }
        storeFolder.delete();
    }

    public void testStorageOfUnexistingPlugin() throws MalformedURLException {
        SimpleFileSystemStore sfss = new SimpleFileSystemStore(storeFolder, new IncrementalFileRenamer());
        try {
            sfss.store(new URL("http://fake.url/fakeName.jar"));
            fail("no plugin was found at the given url, but the store didn't throw any exception");
        } catch (PluginStoreException e) {
            assertTrue("the store threw an exception because there was no plugin at the specified URL", true);
        }
    }

    public void testStorageOfRealPlugin() throws MalformedURLException {
        SimpleFileSystemStore sfss = new SimpleFileSystemStore(storeFolder, new IncrementalFileRenamer());
        try {
            URL url = sfss.store(new URL("http://www.google.fr/intl/fr_fr/images/logo.gif"));
            File f = new File(sfss.getFolder(), "logo.gif");
            assertTrue(url.sameFile(f.toURL()));
            List<URL> pluginsInStore = sfss.getPluginsInStore();
            assertEquals(1, pluginsInStore.size());
            assertTrue(url.sameFile(pluginsInStore.get(0)));
            String[] filesInStore = storeFolder.list();
            assertEquals(1, filesInStore.length);
            assertEquals(filesInStore[0], "logo.gif");
        } catch (PluginStoreException e) {
            fail("The store could not save the file located at the specified URL, although the file exists");
        }
    }

    //TODO Make test methods shorter !!!!!!!!!!!!!! :fou: :fou:
    public void testStorageOfRealPluginWithExistingFilename() throws MalformedURLException {
        Mock mockRenamer = mock(FileRenamer.class);
        SimpleFileSystemStore sfss = new SimpleFileSystemStore(storeFolder, (FileRenamer) mockRenamer.proxy());
        try {
            URL url = sfss.store(new URL("http://www.google.fr/intl/fr_fr/images/logo.gif"));
            File f = new File(sfss.getFolder(), "logo.gif");
            assertTrue(url.sameFile(f.toURL()));
            List<URL> pluginsInStore = sfss.getPluginsInStore();
            assertEquals(1, pluginsInStore.size());
            assertTrue(url.sameFile(pluginsInStore.get(0)));
            String[] filesInStore = storeFolder.list();
            assertEquals(1, filesInStore.length);
            assertEquals(filesInStore[0], "logo.gif");
            //re-store same plugin
            mockRenamer.expects(once()).method("generateNewName").with(eq("logo.gif")).will(returnValue("logo-1.gif"));
            url = sfss.store(new URL("http://www.google.fr/intl/fr_fr/images/logo.gif"));
            f = new File(sfss.getFolder(), "logo-1.gif");
            assertTrue(url.sameFile(f.toURL()));
            pluginsInStore = sfss.getPluginsInStore();
            assertEquals(2, pluginsInStore.size());
            filesInStore = storeFolder.list();
            assertEquals(2, filesInStore.length);
            assertTrue(filesInStore[0].equals("logo.gif") || filesInStore[1].equals("logo.gif"));
            assertTrue(filesInStore[0].equals("logo-1.gif") || filesInStore[1].equals("logo-1.gif"));
            //re-store same plugin AGAIN
            mockRenamer.expects(once()).method("generateNewName").with(eq("logo.gif")).will(returnValue("logo-1.gif"));
            mockRenamer.expects(once()).method("generateNewName").with(eq("logo-1.gif")).will(returnValue("logo-2.gif"));
            url = sfss.store(new URL("http://www.google.fr/intl/fr_fr/images/logo.gif"));
            f = new File(sfss.getFolder(), "logo-2.gif");
            assertTrue(url.sameFile(f.toURL()));
            pluginsInStore = sfss.getPluginsInStore();
            assertEquals(3, pluginsInStore.size());
            filesInStore = storeFolder.list();
            assertEquals(3, filesInStore.length);
            assertTrue(filesInStore[0].equals("logo.gif") || filesInStore[1].equals("logo.gif") || filesInStore[2].equals("logo.gif"));
            assertTrue(filesInStore[0].equals("logo-1.gif") || filesInStore[1].equals("logo-1.gif") || filesInStore[2].equals("logo-1.gif"));
            assertTrue(filesInStore[0].equals("logo-2.gif") || filesInStore[1].equals("logo-2.gif") || filesInStore[2].equals("logo-2.gif"));

        } catch (PluginStoreException e) {
            fail("The store could not save the file located at the specified URL, although the file exists");
        }
    }

    public void testStorageshouldFailWithNoRenamerAndAnExistingFilename() throws MalformedURLException, PluginStoreException {
        SimpleFileSystemStore sfss = new SimpleFileSystemStore(storeFolder);
        URL url = sfss.store(new URL("http://www.google.fr/intl/fr_fr/images/logo.gif"));
        File f = new File(sfss.getFolder(), "logo.gif");
        assertTrue(url.sameFile(f.toURL()));
        List<URL> pluginsInStore = sfss.getPluginsInStore();
        assertEquals(1, pluginsInStore.size());
        assertTrue(url.sameFile(pluginsInStore.get(0)));
        String[] filesInStore = storeFolder.list();
        assertEquals(1, filesInStore.length);
        assertEquals(filesInStore[0], "logo.gif");
        //re-store same plugin
        try {
            sfss.store(new URL("http://www.google.fr/intl/fr_fr/images/logo.gif"));
        } catch (PluginStoreException e) {
            assertEquals("Could not store plugin: a file with the same name (logo.gif) already exists in the store", e.getMessage());
        }
    }

    public void testInvalidArgument() {
        try {
            SimpleFileSystemStore sfss = new SimpleFileSystemStore(new File("inexistantFile"));
            fail("should have thrown an exception");
        } catch (IllegalArgumentException e) {
            assertTrue("Failed as expected", true);
        }
    }

    public void testGetPluginsInStoreWithAFolderInTheStore() throws PluginStoreException, MalformedURLException {
        File folder = new File(storeFolder, "aFolder");
        final boolean b = folder.mkdir();
        assertTrue(b);
        SimpleFileSystemStore sfss = new SimpleFileSystemStore(storeFolder);
        URL url = sfss.store(new URL("http://www.google.fr/intl/fr_fr/images/logo.gif"));
        final List<URL> pluginsInStore = sfss.getPluginsInStore();
        assertEquals(1, pluginsInStore.size());
        assertTrue(pluginsInStore.contains(url));
        folder.delete();

    }

    public void testRemove() throws PluginStoreException, MalformedURLException {
        SimpleFileSystemStore sfss = new SimpleFileSystemStore(storeFolder);
        URL url = sfss.store(new URL("http://www.google.fr/intl/fr_fr/images/logo.gif"));
        List<URL> pluginsInStore = sfss.getPluginsInStore();
        assertEquals(1, pluginsInStore.size());
        assertTrue(pluginsInStore.contains(url));
        sfss.remove(url);
        pluginsInStore = sfss.getPluginsInStore();
        assertEquals(0, pluginsInStore.size());
    }
}
