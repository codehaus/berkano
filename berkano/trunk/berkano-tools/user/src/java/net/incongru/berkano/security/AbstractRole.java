package net.incongru.swaf.security;

/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.3 $
 */
public abstract class AbstractRole implements Role {
    /*
    public boolean equals(Object o) {
        if (!(o instanceof Role)) {
            return false;
        }
        Role other = (Role) o;
        return other.getClass().getName().equals(getClass().getName())
                && other.getName().equals(getName())
                && Arrays.equals(other.getAllowedPermissions(), getAllowedPermissions());
    }

    public int hashCode() {
        int result;
        result = getClass().hashCode();
        result = 29 * result + getName().hashCode();
        return result;
    }
    */
}
