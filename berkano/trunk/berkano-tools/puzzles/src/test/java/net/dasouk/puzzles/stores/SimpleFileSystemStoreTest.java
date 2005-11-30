package net.dasouk.puzzles.stores;

import junit.framework.TestCase;
import net.dasouk.puzzles.StoreException;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.jmock.MockObjectTestCase;
import org.jmock.Mock;


public class SimpleFileSystemStoreTest extends MockObjectTestCase {
    SimpleFileSystemStore sfss;
    File storeFolder;

    protected void setUp() throws Exception {
        super.setUp();
        storeFolder = new File("tmpFolder");
        storeFolder.mkdir();
        sfss = new SimpleFileSystemStore(storeFolder, new IncrementalFileRenamer());//TODO:use mock here
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        File folder = sfss.getFolder();
        File[] files = folder.listFiles();
        for (File file : files) {
            file.delete();
        }
        folder.delete();
    }

    public void testStorageOfUnexistingPlugin() throws MalformedURLException {
        try {
            sfss.store(new URL("http://fake.url/fakeName.jar"));
            fail("no plugin was found at the given url, but the store didn't throw any exception");
        } catch (StoreException e) {
            assertTrue("the store threw an exception because there was no plugin at the specified URL", true);
        }
    }

    public void testStorageOfRealPlugin() throws MalformedURLException {
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
        } catch (StoreException e) {
            fail("The store could not save the file located at the specified URL, although the file exists");
        }
    }

    public void testStorageOfRealPluginWithExistingFilename() throws MalformedURLException {
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
            //restore same plugin
            Mock mockRenamer = mock(FileRenamer.class);
            mockRenamer.expects(once()).method("generateNewName").with(eq("logo.gif")).will(returnValue("logo-1.gif"));
            sfss.setFileRenamer((FileRenamer) mockRenamer.proxy());
            url = sfss.store(new URL("http://www.google.fr/intl/fr_fr/images/logo.gif"));
            f = new File(sfss.getFolder(), "logo-1.gif");
            assertTrue(url.sameFile(f.toURL()));
            pluginsInStore = sfss.getPluginsInStore();
            assertEquals(2, pluginsInStore.size());
            filesInStore = storeFolder.list();
            assertEquals(2, filesInStore.length);
            assertTrue(filesInStore[0].equals("logo.gif") || filesInStore[1].equals("logo.gif"));
            assertTrue(filesInStore[0].equals("logo-1.gif") || filesInStore[1].equals("logo-1.gif"));
            //restore same plugin AGAIN
            mockRenamer = mock(FileRenamer.class);
            mockRenamer.expects(once()).method("generateNewName").with(eq("logo.gif")).will(returnValue("logo-1.gif"));
            mockRenamer.expects(once()).method("generateNewName").with(eq("logo-1.gif")).will(returnValue("logo-2.gif"));
            sfss.setFileRenamer((FileRenamer) mockRenamer.proxy());
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

        } catch (StoreException e) {
            fail("The store could not save the file located at the specified URL, although the file exists");
        }
    }
}
