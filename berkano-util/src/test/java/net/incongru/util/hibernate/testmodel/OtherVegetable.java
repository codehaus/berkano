package net.incongru.util.hibernate.testmodel;

/**
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public enum OtherVegetable implements Vegetable {
    tomato, carrot, pepper;

    public boolean isGreen() {
        return false;
    }

    public int lifetimeInFridge() {
        return 3;
    }
}
