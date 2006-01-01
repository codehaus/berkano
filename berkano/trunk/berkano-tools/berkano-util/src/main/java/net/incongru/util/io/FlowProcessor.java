package net.incongru.util.io;

import java.io.IOException;

/**
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public interface FlowProcessor<T> {
    void process(final T flow) throws IOException;
}
