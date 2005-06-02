package net.incongru.hibernate.stuff;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public enum BasicVegetable implements Vegetable {
    salad(3), cucumber(4), roquette(2);

    private int i;

    BasicVegetable(int i) {
        this.i = i;
    }

    public boolean isGreen() {
        return true;
    }

    public int lifetimeInFridge() {
        return i;
    }
}
