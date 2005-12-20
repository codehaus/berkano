package net.incongru.util.io;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

/**
 * A simple wrapper + closure to hide the ugliness of the try/catch/finally mess to close an InputStream.
 * Sample usage:
 * <code>
 * new SmartStreamProcessor().processAndClose(in, new StreamProcessor() {
 *     public void process(InputStream in) {
 *         System.out.println("ah! ah! messing with my stream");
 *     }
 * }
 * </code>
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $
 *
 * TODO : how about finding a decent name for this class ?
 */
public class SmartStreamProcessor {
    public void processAndClose(final Closeable closeable, final StreamProcessor processor) throws IOException {
        assert closeable != null;
        IOException e = null;
        try {
            processor.process(closeable);
        } catch (IOException processEx) {
            e = processEx;
        } finally {
            try {
                closeable.close();
            } catch (IOException finallyEx) {
                if (e != null) {
                    // throwing a specific exception with the process-exception nested
                    throw new IONestedException("IOException in process (" + e.getMessage() + ") but also when trying to close (" + e.getMessage() + ")", e);
                } else {
                    throw new IONestedException("Could not close closeable: " + finallyEx.getMessage(), finallyEx);
                }
            }
        }
    }

    public void processFlushAndClose(final Flushable flushable, final StreamProcessor processor) throws IOException {
        assert flushable instanceof Closeable : "can only operate on an implementation of Closeable AND Flushable";
        processAndClose((Closeable) flushable, new StreamProcessor() {
            public void process(final Closeable c) throws IOException {
                processor.process(c);
                try {
                    ((Flushable) c).flush();
                } catch (IOException e) {
                    throw new IONestedException("Could not flush: " + e.getMessage(), e);
                }
            }
        });
    }

    public static final class IONestedException extends IOException {
        private final IOException cause;

        public IONestedException(String message, IOException cause) {
            super(message);
            this.cause = cause;
        }

        public Throwable getCause() {
            return cause;
        }
    }
}
