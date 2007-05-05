package net.incongru.berkano.app;

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
 * TODO : a "allowedValues" attribute? Is the value property relevant?
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public interface UserPreference {
    public String getName();

    public Object getModel();
    // types:
    // String, String[], int, Date, ... : use ognl converters ?
    // use a model driven action ?

}
