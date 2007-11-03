package net.incongru.util.cache;

/**
 * A cache implements a given caching strategy and keeps objets in a map according to this strategy.
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public interface Cache {
    public Object get(String key);

    public void set(String key, Object value);

    public void invalidate(String key);
}
