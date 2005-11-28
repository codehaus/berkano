package net.dasouk.puzzles.readers;

import junit.framework.TestCase;
import net.dasouk.puzzles.ResourceNotFoundException;

import java.net.MalformedURLException;
import java.net.URL;


public class ZipPackageReaderTest extends TestCase {
    private ZipPackageReader reader;

    public void testGetResource() throws MalformedURLException {
        String descriptorName = "descriptor.ext";
        reader = new ZipPackageReader(descriptorName);
        String spec = "http://sub.domain.com:48/repository/plugins/";
        String jarSpec = "jar:" + spec + "!/";
        URL pluginUrl = new URL(spec);
        try {
            URL resourceUrl = reader.getResource(pluginUrl, "lib/toto.jar");
            String s = resourceUrl.toString();
            assertEquals(jarSpec + "lib/toto.jar", s);
        } catch (ResourceNotFoundException e) {
            fail("the URL is correct and thus it should not fail");
        }
        //test with a leading /
        reader = new ZipPackageReader("/" + descriptorName);
        spec = "http://sub.domain.com:48/repository/plugins/";
        jarSpec = "jar:" + spec + "!/";
        pluginUrl = new URL(spec);
        try {
            URL resourceUrl = reader.getResource(pluginUrl, "/lib/toto.jar");
            String s = resourceUrl.toString();
            assertEquals(jarSpec + "lib/toto.jar", s);
        } catch (ResourceNotFoundException e) {
            fail("the URL is correct and thus it should not fail");
        }
    }
}
