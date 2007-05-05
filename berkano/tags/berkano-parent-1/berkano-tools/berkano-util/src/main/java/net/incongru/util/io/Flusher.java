package net.incongru.util.io;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

/**
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public class Flusher<T extends Closeable & Flushable> {

    public void processFlushAndClose(final T stream, final IOProcessor<T> processor) throws IOException {
        new Closer<T>().processAndClose(stream, new IOProcessor<T>() {
            public void process(final T t) throws IOException {
                processor.process(t);
                try {
                    stream.flush();
                } catch (IOException e) {
                    throw new Closer.IONestedException("Could not flush: " + e.getMessage(), e);
                }
            }
        });
    }
}
