package net.incongru.beantag;

/**
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.3 $
 */
public class SomeOtherObject {
    private String one = null;
    private String two = "deux";

    public String getOne() {
        return one;
    }

    public String getTwo() {
        return two;
    }

    public SomeObject getNestedObject() {
        return new SomeObject("some", "other val", 47);
    }
}
