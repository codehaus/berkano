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
public class StreamFlushProcessor<T extends Closeable & Flushable> extends SmartStreamProcessor {

    public void processFlushAndClose(final T stream, final StreamProcessor processor) throws IOException {
        //assert stream instanceof Flushable : "can only operate on an implementation of Closeable AND Flushable";
        processAndClose(stream, new StreamProcessor() {
            public void process(final Closeable stream) throws IOException {
                processor.process(stream);
                try {
                    ((Flushable) stream).flush();
                } catch (IOException e) {
                    throw new IONestedException("Could not flush: " + e.getMessage(), e);
                }
            }
        });
    }
}
