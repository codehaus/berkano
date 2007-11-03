package net.incongru.berkano.app;

import java.util.Map;

/**
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class SimplePreferenceInfo implements PreferenceInfo {
    private String keyName;
    private Map allowedValues;
    private PreferenceWidget preferenceWidget;

    public SimplePreferenceInfo(String keyName, Map allowedValues, PreferenceWidget preferenceWidget) {
        this.keyName = keyName;
        this.allowedValues = allowedValues;
        this.preferenceWidget = preferenceWidget;
    }

    public String getKeyName() {
        return keyName;
    }

    public Map getAllowedValues() {
        return allowedValues;
    }

    public PreferenceWidget getPreferenceWidget() {
        return preferenceWidget;
    }
}
