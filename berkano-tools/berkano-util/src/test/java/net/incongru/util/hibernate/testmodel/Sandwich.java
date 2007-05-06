package net.incongru.util.hibernate.testmodel;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class Sandwich {
    private long id;
    private String name;
    private int cheeseAmount;
    private Sauce sauce;
    private Vegetable vegetable;

    public Sandwich() {
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

    public int getCheeseAmount() {
        return cheeseAmount;
    }

    public void setCheeseAmount(int cheeseAmount) {
        this.cheeseAmount = cheeseAmount;
    }

    public Sauce getSauce() {
        return sauce;
    }

    public void setSauce(Sauce sauce) {
        this.sauce = sauce;
    }

    public Vegetable getVegetable() {
        return vegetable;
    }

    public void setVegetable(Vegetable vegetable) {
        this.vegetable = vegetable;
    }
}
