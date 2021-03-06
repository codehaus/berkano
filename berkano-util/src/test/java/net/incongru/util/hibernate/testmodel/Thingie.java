package net.incongru.util.hibernate.testmodel;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class Thingie {
    private long id;
    private String name;
    private Set stuff;

    public Thingie() {
        stuff = new HashSet();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set getStuff() {
        return stuff;
    }

    public void setStuff(Set stuff) {
        this.stuff = stuff;
    }
}
