package net.incongru.beantag;

/**
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class FakeDecorator extends PropertyDecorator {
    public String getAnotherProperty() {
        return "decorated string property";
    }

    public String getIntegerProperty() {
        return "the decorator will always superseed the bean, even if the method return type is different";
    }

    /**
     * the decorator will always superseed the bean, even if the method return type is different
     */
    public int getBar() {
        return 123;
    }

    public String getFoo() {
        return "a property which doesn't exist on the original bean";
    }

    public String getPropertyWhichUsesTheCurrentItem() {
        return "!" + ((SomeObject) getCurrentItem()).getSomeProperty() + "!";
    }
}
