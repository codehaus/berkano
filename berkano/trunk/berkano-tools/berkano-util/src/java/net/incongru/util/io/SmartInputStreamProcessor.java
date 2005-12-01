package net.incongru.util.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * A simple wrapper + closure to hide the ugliness of the try/catch/finally mess to close
 * an InputStream.
 * Sample usage:
 * <code>
 * new SmartInputStreamProcessor().processAndClose(in, new InputStreamProcessor() {
 *     public void process(InputStream in) {
 *         System.out.println("ah! ah! messing with my stream");
 *     }
 * } 
 * </code>
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public class SmartInputStreamProcessor {
    public void processAndClose(InputStream in, InputStreamProcessor processor) throws IOException {
        IOException e = null;
        try {
            processor.process(in);
        } catch (IOException processEx) {
            e = processEx;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException finallyEx) {
                    if (e != null) {
                        // throwing a specific exception with the process-exception nested
                        throw new IONestedException("IOException in process (" + e.getMessage() + ") but also when trying to close (" + e.getMessage() + ")", e);
                    } else {
                        throw new IONestedException("Could not close stream: " + finallyEx.getMessage(), finallyEx);
                    }
                }
            }
        }
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
