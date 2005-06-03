package net.incongru.swaf.app;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class SimplePreferenceInfo implements PreferenceInfo {
    private String keyName;
    private Object[] allowedValues;
    private PreferenceWidget preferenceWidget;

    public SimplePreferenceInfo(String keyName, Object[] allowedValues, PreferenceWidget preferenceWidget) {
        this.keyName = keyName;
        this.allowedValues = allowedValues;
        this.preferenceWidget = preferenceWidget;
    }

    public String getKeyName() {
        return keyName;
    }

    public Object[] getAllowedValues() {
        return allowedValues;
    }

    public PreferenceWidget getPreferenceWidget() {
        return preferenceWidget;
    }
}
