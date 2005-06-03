/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
package net.incongru.swaf.security;

//public enum PermissionType {
//    url, action;
//}

public class PermissionType {
    public static final PermissionType url = new PermissionType(1, "url");
    public static final PermissionType action = new PermissionType(2, "action");

    private final int id;
    private final String name;

    private PermissionType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    protected int getId() {
        return id;
    }

    protected String getName() {
        return name;
    }

    public String toString() {
        return name;
    }
}
