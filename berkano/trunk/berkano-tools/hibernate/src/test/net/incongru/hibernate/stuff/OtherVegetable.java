package net.incongru.hibernate.stuff;

/**
 *
 * @author greg
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
