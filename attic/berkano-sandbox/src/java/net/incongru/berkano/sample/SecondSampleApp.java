package net.incongru.berkano.sample;

import net.incongru.berkano.app.Application;
import net.incongru.berkano.app.PreferenceInfo;
import net.incongru.berkano.app.PreferenceWidget;
import net.incongru.berkano.app.SimplePreferenceInfo;

/**
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class SecondSampleApp implements Application {
    private static final PreferenceInfo[] PREFS = new PreferenceInfo[] {
        new SimplePreferenceInfo("blah", new Object[0], PreferenceWidget.text)
    };

    public String getName() {
        return "2nd sample app";
    }

    public Object newPreferencesModel() {
        return new SecondSampleAppPrefs();
    }

    public PreferenceInfo[] getPreferencesInfo() {
        return PREFS;
    }

    public final static class SecondSampleAppPrefs {
        private String blah;

        public String getBlah() {
            return blah;
        }

        public void setBlah(String blah) {
            this.blah = blah;
        }
    }
}
