package net.incongru.util.io;

import junit.framework.TestCase;

import java.io.StringReader;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public class IOUtilTest extends TestCase {
    private static final String PROPS_STR = "foo=bar\nbaz=lol\n";
    public void testLoadProperties() throws IOException {
        Properties expected = new Properties();
        expected.setProperty("foo", "bar");
        expected.setProperty("baz", "lol");
        final ByteArrayInputStream in = new ByteArrayInputStream(PROPS_STR.getBytes());
        final Properties props = IOUtil.loadProperties(in);
        assertEquals(expected, props);
    }
}
