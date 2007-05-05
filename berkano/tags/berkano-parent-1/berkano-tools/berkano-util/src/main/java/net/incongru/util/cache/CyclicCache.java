package net.incongru.util.cache;

/**
 * A cyclic cache simply lets its values timeout after a given amount of time.
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class CyclicCache extends AbstractCache {
    private long timeout;

    public CyclicCache(long timeout) {
        super();
        this.timeout = timeout;
    }

    protected CacheValue getNewCacheValue(Object value) {
        return new CyclicCacheValue(value);
    }

    private class CyclicCacheValue implements CacheValue {
        private Object value;
        private long creationTime;

        public CyclicCacheValue(Object value) {
            this.creationTime = System.currentTimeMillis();
            this.value = value;
        }

        public boolean isValid() {
            long now = System.currentTimeMillis();
            return now - creationTime < timeout;
        }

        public Object getValue() {
            return value;
        }
    }
}
