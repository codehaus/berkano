package net.incongru.util.io;

import junit.framework.TestCase;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public class FlusherTest extends TestCase {
    public void testProcessFlushAndCloseAreOnlyCalledOnce() throws IOException {
        FlushableCloseable stream = new FlushableCloseable();
        new Flusher<FlushableCloseable>().processFlushAndClose(stream, new IOProcessor<FlushableCloseable>() {
            public void process(FlushableCloseable flux) throws IOException {
                flux.doStuff();
            }
        });
        stream.verify();
    }

    private static final class FlushableCloseable implements Flushable, Closeable {
        private boolean doCalled;
        private boolean flushCalled;
        private boolean closeCalled;

        public void doStuff() {
            assertFalse("doStuff was called more than once", doCalled);
            doCalled = true;
        }

        public void flush() throws IOException {
            assertFalse("flush was called more than once", flushCalled);
            flushCalled = true;
        }

        public void close() throws IOException {
            assertFalse("close was called more than once", closeCalled);
            closeCalled = true;
        }

        public void verify() {
            assertTrue("doStuff was not called", doCalled);
            assertTrue("flush was not called", flushCalled);
            assertTrue("close was not called", closeCalled);
        }
    }
}
