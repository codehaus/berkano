package net.dasouk.puzzles.stores;

import junit.framework.TestCase;

import java.io.File;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.List;

import net.dasouk.puzzles.StoreException;


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

    public void testStore(){
        try {
            URL url = sfss.store(new URL("http://trucmuchE.ppp/bofbof.jar"));
            fail("should not work [:joce]");
        } catch (StoreException e) {
            assertTrue(true);
        } catch (MalformedURLException e) {
            fail("just hoping the url is ok @.@V");
        }

        try {
            URL url = sfss.store(new URL("http://www.google.fr/intl/fr_fr/images/logo.gif"));
            File f = new File(sfss.getFolder(),"logo.gif");
            assertTrue(url.sameFile(f.toURL()));
            List<URL> pluginsInStore = sfss.getPluginsInStore();
            assertEquals(1,pluginsInStore.size());
            assertTrue(url.sameFile(pluginsInStore.get(0)));
        } catch (StoreException e) {
            fail("no no no, storing the file should be ok");
        } catch (MalformedURLException e) {
            fail("just hoping the url is ok @.@V");
        }
    }
}
