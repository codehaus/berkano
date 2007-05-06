package net.incongru.beantag;

/**
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.4 $
 */
public class FakeTableWriter extends OgnlTableWriter {
    public Object getValue(String propertyName, Object o) throws PropertyDecoratorException {
        return "Fake !";
    }
}
