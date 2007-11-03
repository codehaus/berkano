package net.incongru.taskman;

/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class AssigneeImpl implements Assignee {
    private Type type;
    private String name;

    public AssigneeImpl(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    public AssigneeImpl() {
    }


    public String toString() {
        return type + ":" + name;
    }

    // generated getters and setters
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // generated equals
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final AssigneeImpl assignee = (AssigneeImpl) o;

        if (name != null ? !name.equals(assignee.name) : assignee.name != null) return false;
        if (type != assignee.type) return false;

        return true;
    }

    // generated hashCode
    public int hashCode() {
        int result;
        result = (type != null ? type.hashCode() : 0);
        result = 29 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

}
