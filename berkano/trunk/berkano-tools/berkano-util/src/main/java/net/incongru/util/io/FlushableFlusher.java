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
public class FlushableFlusher<T extends Closeable & Flushable> {

    public void processFlushAndClose(final T stream, final FlowProcessor<T> processor) throws IOException {
        new CloseableCloser<T>().processAndClose(stream, new FlowProcessor<T>() {
            public void process(final T t) throws IOException {
                processor.process(t);
                try {
                    stream.flush();
                } catch (IOException e) {
                    throw new CloseableCloser.IONestedException("Could not flush: " + e.getMessage(), e);
                }
            }
        });
    }
}
