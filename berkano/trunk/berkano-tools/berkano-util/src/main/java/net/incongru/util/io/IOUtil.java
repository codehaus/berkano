package net.incongru.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class IOUtil {
    /**
     * Loads properties from an InputStream, closes it and returns.
     */
    public static Properties loadProperties(final InputStream in) throws IOException {
        final Properties props = new Properties();
        new CloseableCloser<InputStream>().processAndClose(in, new FlowProcessor<InputStream>() {
            public void process(final InputStream stream) throws IOException {
                props.load(in);
            }
        });
        return props;
    }
}
