package net.incongru.util.io;

import java.io.IOException;

/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public interface IOProcessor<T> {
    void process(final T flow) throws IOException;
}
