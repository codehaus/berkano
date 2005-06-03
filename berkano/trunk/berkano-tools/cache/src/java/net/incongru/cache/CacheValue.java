package net.incongru.cache;

/**
 * A CacheValue holds an objet and is able to determine, for a given caching strategy, if it is still valid or not.
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public interface CacheValue {
    boolean isValid();
    Object getValue();
}
