package net.incongru.taskman.id;

/**
 * This is an IdGenerator which will always return null, i.e. depend on an
 * underlying (possibly native) id generator.
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class NullIdGenerator implements IdGenerator {
    public String generate() {
        return null;
    }
}
