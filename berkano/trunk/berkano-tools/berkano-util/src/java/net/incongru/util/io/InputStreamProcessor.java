package net.incongru.util.io;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public interface InputStreamProcessor {
    void process(final InputStream in) throws IOException;
}
