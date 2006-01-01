package net.incongru.util.io;

import java.io.Closeable;
import java.io.IOException;

/**
 * A simple wrapper + closure to hide the ugliness of the try/catch/finally mess to close an InputStream.
 * Sample usage:
 * <code>
 * new CloseableCloser<InputStream>().processAndClose(in, new FlowProcessor<InputStream>() {
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
public class CloseableCloser<T extends Closeable> {
    public void processAndClose(final T stream, final FlowProcessor<T> processor) throws IOException {
        IOException e = null;
        try {
            processor.process(stream);
        } catch (IOException processEx) {
            e = processEx;
        } finally {
            try {
                stream.close();
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

    static final class IONestedException extends IOException {
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