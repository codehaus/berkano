package net.dasouk.puzzles.readers;

import junit.framework.TestCase;
import net.dasouk.puzzles.ResourceNotFoundException;

import java.net.MalformedURLException;
import java.net.URL;


public class ZipPackageReaderTest extends TestCase {
    private static final String DUMMY_DESCRIPTOR_NAME = "descriptor.ext";
    private static final String DUMMY_RES_NAME = "lib/toto.jar";
    private static final String DUMMY_SPEC = "http://sub.domain.com:48/repository/plugins/";
    private static final String DUMMY_JAR_SPEC = "jar:" + DUMMY_SPEC + "!/";
    private static final String EXPECTED_RES_URL = DUMMY_JAR_SPEC + "lib/toto.jar";

    public void testGetResource() throws MalformedURLException {
        final ZipPackageReader reader = new ZipPackageReader(DUMMY_DESCRIPTOR_NAME);
        URL pluginUrl = new URL(DUMMY_SPEC);
        try {
            URL resourceUrl = reader.getResource(pluginUrl, DUMMY_RES_NAME);
            String s = resourceUrl.toString();
            assertEquals(EXPECTED_RES_URL, s);
        } catch (ResourceNotFoundException e) {
            fail("the URL is correct and thus it should not fail : " + e.getMessage());
        }
    }

    public void testGetResourceWithLeadingSlash() throws MalformedURLException {
        final ZipPackageReader reader = new ZipPackageReader("/" + DUMMY_DESCRIPTOR_NAME);
        URL pluginUrl = new URL(DUMMY_SPEC);
        try {
            URL resourceUrl = reader.getResource(pluginUrl, "/" + DUMMY_RES_NAME);
            String s = resourceUrl.toString();
            assertEquals(EXPECTED_RES_URL, s);
        } catch (ResourceNotFoundException e) {
            fail("the URL is correct and thus it should not fail : " + e.getMessage());
        }
    }
}
