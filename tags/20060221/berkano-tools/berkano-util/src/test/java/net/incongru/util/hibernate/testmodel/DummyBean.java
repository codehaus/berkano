package net.incongru.util.hibernate.testmodel;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class DummyBean {
    private long id;
    private String name;
    private Object complexObject;

    public DummyBean() {
    }

    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getComplexObject() {
        return complexObject;
    }

    public void setComplexObject(Object complexObject) {
        this.complexObject = complexObject;
    }
}
