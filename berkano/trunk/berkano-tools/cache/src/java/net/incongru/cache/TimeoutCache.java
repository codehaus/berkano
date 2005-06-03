package net.incongru.cache;

/**
 * A cache object that caches Objects for a given amount of time after their last access.
 * If the object wasn't requested for that amount of time, the returned value will be null, because
 * the cached Object will be removed from the underlying map.
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class TimeoutCache extends AbstractCache {
    private long timeout;

    public TimeoutCache(long timeout) {
        super();
        this.timeout = timeout;
    }

    protected CacheValue getNewCacheValue(Object value) {
        return new TimeoutCacheValue(value);
    }

    private class TimeoutCacheValue implements CacheValue {
        private long lastAccess;
        private Object value;

        public TimeoutCacheValue(Object value) {
            this.value = value;
            this.lastAccess = System.currentTimeMillis();
        }

        public boolean isValid() {
            long current = System.currentTimeMillis();
            boolean valid = (current - timeout <= this.lastAccess);
            this.lastAccess = current;
            return valid;
        }

        public Object getValue() {
            return this.value;
        }
    }
}
