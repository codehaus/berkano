package net.incongru.util.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public abstract class AbstractCache implements Cache {
    private Map cache;

    protected AbstractCache() {
        this.cache = new HashMap();
    }

    public Object get(String key) {
        CacheValue value = (CacheValue) cache.get(key);
        if (value != null && value.isValid()) {
            return value.getValue();
        } else {
            cache.remove(key);
            return null;
        }
    }

    public void set(String key, Object value) {
        cache.put(key, getNewCacheValue(value));
    }

    protected abstract CacheValue getNewCacheValue(Object value);

    public void invalidate(String key) {
        /*
        CacheValue value = (CacheValue) cache.get(key);
        if (value != null) {
            value.lastAccess = -1;
        }*/
        cache.remove(key);
    }

}
