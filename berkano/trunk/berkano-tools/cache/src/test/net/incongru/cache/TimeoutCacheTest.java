package net.incongru.cache;

import junit.framework.TestCase;

/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class TimeoutCacheTest extends TestCase {
    public void testTimeout() throws Exception {
        String value = "dummy-value";
        Cache cache = new TimeoutCache(1000); // these tests are going to be sloooow (1sec)
        cache.set("test", value);

        Thread.sleep(500);
        assertEquals("Value should still be there after 500msec", value, cache.get("test"));
        Thread.sleep(700);
        assertEquals("Value should still be there after 700 more msec, because it's been accessed lately", value, cache.get("test"));
        Thread.sleep(1005);
        assertNull("Value should have timedout", cache.get("test"));
    }
}
