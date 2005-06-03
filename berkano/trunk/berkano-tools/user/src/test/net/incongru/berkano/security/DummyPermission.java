package net.incongru.swaf.security;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
//public enum DummyPermission implements Permission {
public class DummyPermission extends Permission {
//    pouet(PermissionType.url),
//    troululu(PermissionType.action),
//    blabla(PermissionType.action);
    public static final DummyPermission pouet = new DummyPermission("pouet", PermissionType.url);
    public static final DummyPermission troululu = new DummyPermission("troululu", PermissionType.action);
    public static final DummyPermission blabla = new DummyPermission("blabla", PermissionType.action);

    DummyPermission(String name, PermissionType type) {
        super(name, type);
    }
}
