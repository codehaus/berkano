package net.incongru.berkano.security;

/**
 * A permission. Usually, permissions seem to be represented as Strings, but I'd rather have an object.
 * If a description is required, it should be provided by a translation service, based on the name of the
 * permission. The permission's name should be unique.
 * A permission is the smallest possible unit of security "lock".
 *
 * @see Role
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 *
 * @deprecated currently unused - staying there for now just as a reminder
 */
public class Permission {
    private String name;

    public Permission() {
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }
}
