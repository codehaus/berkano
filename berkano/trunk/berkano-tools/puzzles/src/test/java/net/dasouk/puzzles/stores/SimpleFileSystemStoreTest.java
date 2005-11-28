package net.dasouk.puzzles.stores;

import junit.framework.TestCase;
import net.dasouk.puzzles.StoreException;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class SimpleFileSystemStoreTest extends TestCase {
    SimpleFileSystemStore sfss;

    protected void setUp() throws Exception {
        super.setUp();
        File tmpFolder = new File("tmpFolder");
        tmpFolder.mkdir();
        sfss = new SimpleFileSystemStore(tmpFolder);
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

    // TODO : test method name should be more explicit
    public void testStoreShouldFail() throws MalformedURLException {
        try {
            sfss.store(new URL("http://trucmuchE.ppp/bofbof.jar"));
            fail("should not work [:joce]");
        } catch (StoreException e) {
            // TODO : the exception message should more explicit
            assertEquals("Error while storing plugin: trucmuchE.ppp", e.getMessage());
        }
    }

    // TODO : test method name should be more explicit
    public void testStoreShouldWork() throws MalformedURLException {
        try {
            URL url = sfss.store(new URL("http://www.google.fr/intl/fr_fr/images/logo.gif"));
            File f = new File(sfss.getFolder(), "logo.gif");
            assertTrue(url.sameFile(f.toURL()));
            List<URL> pluginsInStore = sfss.getPluginsInStore();
            assertEquals(1, pluginsInStore.size());
            assertTrue(url.sameFile(pluginsInStore.get(0)));
        } catch (StoreException e) {
            fail("no no no, storing the file should be ok : " + e.getMessage());
        }
    }
}
