package net.incongru.berkano.app;

import java.util.Map;

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
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public interface PreferenceInfo {
    public String getKeyName();

    /**
     * A Map of &lgt;actualValue, displayableValue translation key&gt;
     * for values allowed/proposed for this preference setting.
     * An empty or null Map means any value is allowed.
     */
    public Map getAllowedValues();

    public PreferenceWidget getPreferenceWidget();

}
