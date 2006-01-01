package net.incongru.util.io;

import junit.framework.TestCase;

import java.io.Closeable;
import java.io.IOException;

/**
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public class CloseableCloserTest extends TestCase {
    public void testProcessAndCloseAreOnlyCalledOnce() throws IOException {
        DummyCloseable stream = new DummyCloseable();
        new CloseableCloser<DummyCloseable>().processAndClose(stream, new FlowProcessor<DummyCloseable>() {
            public void process(DummyCloseable flux) throws IOException {
                flux.doStuff();
            }
        });
        stream.verify();
    }

    private static final class DummyCloseable implements Closeable {
        private boolean doCalled;
        private boolean closeCalled;

        public void doStuff() {
            assertFalse("doStuff was called more than once", doCalled);
            doCalled = true;
        }

        public void close() throws IOException {
            assertFalse("close was called more than once", closeCalled);
            closeCalled = true;
        }

        public void verify() {
            assertTrue("doStuff was not called", doCalled);
            assertTrue("close was not called", closeCalled);
        }
    }
}
