package net.dasouk.puzzles.util;

import java.io.*;
import java.net.URL;

/**
 * extracts an embedded dependency and store it in a temporary file.
 * this file can then be loaded by a classic URLClassLoader.
 *
 * @todo instead of creating temporary files, it may be better to create
 * a new protocol handler to manipulate jars embedded in another jar
 */
public class TempJarExtractor implements Serializable {
    private final static int DEFAULT_BUFFER_SIZE = 4096;
    private int bufferSize;

    public TempJarExtractor() {
        this(DEFAULT_BUFFER_SIZE);
    }

    public TempJarExtractor(int bufferSize) {
        this.bufferSize = bufferSize;
    }


    public URL extractJarToTempFile(URL source) throws IOException {
        File tempFile = File.createTempFile("puzzles", ".jar");
        OutputStream outputStream = new FileOutputStream(tempFile);
        InputStream inputStream = source.openStream();
        try {
            byte[] buffer = new byte[bufferSize];
            int read;
            while ((read = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, read);
            }
            return tempFile.toURL();
        } finally {
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }
    }
}
