package net.incongru.taskman.testmodel;

/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class Dummy {
    private String a;
    private int b;

    public Dummy(String a, int b) {
        this.a = a;
        this.b = b;
    }

    public String getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public String toString() {
        return a + "/" + b;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Dummy dummy = (Dummy) o;

        if (b != dummy.b) return false;
        if (!a.equals(dummy.a)) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = a.hashCode();
        result = 29 * result + b;
        return result;
    }
}
