package net.incongru.swaf.app;

/**
 * A user preference.
 * todo:
 *  - Should be typed.
 *
 * Each application can have multiple user preferences.
 * This class stores the user's actual preferences.
 * Another object should take care of defining the application's possible preferences,
 * and defining user permissions for each of these.
 *
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public interface PreferenceInfo {
    public String getKeyName();

    /** an empty array means any value is allowed */
    public Object[] getAllowedValues();

    public PreferenceWidget getPreferenceWidget();

}
