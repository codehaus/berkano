package net.incongru.util.io;

import java.io.Closeable;
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
    public static Properties loadProperties(InputStream in) throws IOException {
        final Properties props = new Properties();
        new SmartStreamProcessor().processAndClose(in, new StreamProcessor() {
            public void process(final Closeable stream) throws IOException {
                props.load((InputStream) stream);
            }
        });
        return props;
    }
}
