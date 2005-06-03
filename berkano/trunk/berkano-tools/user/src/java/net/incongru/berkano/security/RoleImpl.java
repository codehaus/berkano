package net.incongru.berkano.security;

/**
 * 
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class RoleImpl implements Role {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

 /* todo : check if it is correct to implement this with hibernate??
 public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleImpl)) return false;

        final RoleImpl role = (RoleImpl) o;

        if (name != null ? !name.equals(role.name) : role.name != null) return false;

        return true;
    }

    public int hashCode() {
        return (name != null ? name.hashCode() : 0);
    }
    */
}
