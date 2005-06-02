package net.incongru.swaf.security;

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
 */
public class Permission {
//    private long id;
    private String name;
    private PermissionType type;

    public Permission(String name, PermissionType type) {
        this.name = name;
        this.type = type;
    }

//    public Permission() {
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    protected void setId(long id) {
//        this.id = id;
//    }

//    public String getName();
    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

//    public PermissionType getType();
    public PermissionType getType() {
        return type;
    }

    protected void setType(PermissionType type) {
        this.type = type;
    }
}
