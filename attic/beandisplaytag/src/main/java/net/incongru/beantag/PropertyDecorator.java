package net.incongru.beantag;

/**
 * todo: .. in fact, a "real" decorator should probably be a class that extends the original bean class,
 * and take the original/current instance as a constructor argument...
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.4 $
 */
public abstract class PropertyDecorator {
    private Object item;

    final void setCurrentItem(Object currentItem) {
        this.item = currentItem;
    }

    protected Object getCurrentItem() {
        return item;
    }
}
