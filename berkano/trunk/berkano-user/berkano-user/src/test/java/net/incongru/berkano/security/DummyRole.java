package net.incongru.berkano.security;

/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 *
 * @deprecated currently unused - staying there for now just as a reminder
 */
//public enum DummyRole implements Role {
//    littleRole(DummyPermission.blabla),
//    bigRole(DummyPermission.blabla, DummyPermission.pouet, DummyPermission.troululu),
//    rolalacon(),
//    rolnul();
public class DummyRole implements Role {
    public static final Role littleRole = new DummyRole("littleRole", DummyPermission.blabla);
    public static final Role bigRole = new DummyRole("bigRole", new DummyPermission[]{DummyPermission.blabla, DummyPermission.pouet, DummyPermission.troululu});
    static final public Role rolalacon = new DummyRole("rolalacon");
    public static final Role rolnul = new DummyRole("rolnul");

    private DummyPermission[] allowedPermissions;
    private String name;

//    private DummyRole(DummyPermission... permissions) {

    private DummyRole(String name, DummyPermission permissions) {
        this.allowedPermissions = new DummyPermission[]{permissions};
        this.name = name;
    }

    private DummyRole(String name, DummyPermission[] permissions) {
        this.allowedPermissions = permissions;
        this.name = name;
    }

    private DummyRole(String name) {
        this.name = name;
        this.allowedPermissions = new DummyPermission[]{};
    }

    public String getName() {
        return this.name;
//        return this.name();
    }

    //public Permission[] getAllowedPermissions() {
    public String[] getAllowedPermissions() {
        String[] allowedPermissionsStr = new String[allowedPermissions.length];
        for (int i = 0; i < allowedPermissions.length; i++) {
            allowedPermissionsStr[i] = allowedPermissions[i].getName();
        }
        return allowedPermissionsStr;
    }
}
