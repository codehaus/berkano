package net.incongru.util.io;

import java.io.Closeable;
import java.io.IOException;

/**
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public interface StreamProcessor {
    void process(final Closeable stream) throws IOException;
}
