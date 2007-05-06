package net.incongru.util.cache;

import junit.framework.TestCase;

/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class CyclicCacheTest extends TestCase {
    public void testCyclicCache() throws Exception {
        String value = "dummy-value";
        Cache cache = new CyclicCache(1000); // these tests are going to be sloooow (1sec)
        cache.set("test", value);

        Thread.sleep(500);
        assertEquals("Value should still be there after 500msec", value, cache.get("test"));
        Thread.sleep(200);
        assertEquals("Value should still be there after 500+200msec", value, cache.get("test"));
        Thread.sleep(400);
        assertNull("Value should have timedout", cache.get("test"));
    }
}
