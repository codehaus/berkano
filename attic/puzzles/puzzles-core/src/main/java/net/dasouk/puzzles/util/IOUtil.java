package net.dasouk.puzzles.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Simple utility class to handle common IO routines
 *
 * @author souk
 * @version 0.1
 */
public class IOUtil {
    public static final int BUFFER_SIZE = 4096;

    public static void copy(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        int read;
        IOException ioException = null;
        try {
            while ((read = input.read(buffer)) > 0) {
                output.write(buffer, 0, read);
            }
        } catch (IOException e) {
            ioException = e;
        } finally {
            try {
                output.flush();
                output.close();
                input.close();
            } catch (IOException e) {
                //both streams may not be properly closed, but there's no use going so much into details I guess
                if (ioException != null) {
                    throw new NestedIOException(e.getMessage(), ioException);
                }
            }
        }
    }


    public static class NestedIOException extends IOException {
        private final IOException cause;

        public NestedIOException(String s, IOException cause) {
            super(s);
            this.cause = cause;
        }

        public NestedIOException(IOException cause) {
            this(null, cause);
        }


        public IOException getCause() {
            return cause;
        }
    }
}
