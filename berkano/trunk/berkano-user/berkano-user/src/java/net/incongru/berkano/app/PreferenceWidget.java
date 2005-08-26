/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
package net.incongru.berkano.app;

/**
 * Determines what kind of widget to use to set the value.
 * TODO : replace with a java5 enum asap :(
 */
public class PreferenceWidget {
//public enum PreferenceWidget {
    //text, dropdown, radios, checkboxes;
    public static final PreferenceWidget text = new PreferenceWidget("text");
    public static final PreferenceWidget dropdown = new PreferenceWidget("dropdown");
    public static final PreferenceWidget combobox = new PreferenceWidget("combobox");
    public static final PreferenceWidget radios = new PreferenceWidget("radios");
    public static final PreferenceWidget checkboxes = new PreferenceWidget("checkboxes");

    private final String name;

    private PreferenceWidget(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;

        final PreferenceWidget preferenceWidget = (PreferenceWidget) o;

        if (!name.equals(preferenceWidget.name)) return false;

        return true;
    }

    public int hashCode() {
        return name.hashCode();
    }
}
