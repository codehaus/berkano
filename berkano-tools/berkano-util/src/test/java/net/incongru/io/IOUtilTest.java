package net.incongru.io;

import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public class IOUtilTest extends TestCase {
    public void testLoadProperties() throws IOException {
        final Properties expected = new Properties();
        expected.setProperty("foo", "bar");
        expected.setProperty("baz", "lol");

        final String inStr = "foo=bar\nbaz=lol\n";
        final ByteArrayInputStream in = new ByteArrayInputStream(inStr.getBytes());

        final Properties props = IOUtil.loadProperties(in);
        assertEquals(expected, props);
    }
}
