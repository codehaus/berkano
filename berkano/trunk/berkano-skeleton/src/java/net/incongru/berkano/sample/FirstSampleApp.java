package net.incongru.berkano.sample;

import net.incongru.berkano.app.Application;
import net.incongru.berkano.app.PreferenceInfo;
import net.incongru.berkano.app.SimplePreferenceInfo;
import net.incongru.berkano.app.PreferenceWidget;

import java.util.Date;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class FirstSampleApp implements Application {
    private static final PreferenceInfo[] prefs = new PreferenceInfo[]{
        new SimplePreferenceInfo("foo", new Object[0], PreferenceWidget.text),
        new SimplePreferenceInfo("bar", new String[]{"0","3","6","9","12","15"}, PreferenceWidget.radios),
        new SimplePreferenceInfo("someDate", new Object[0], PreferenceWidget.text),
        new SimplePreferenceInfo("anArray", new String[]{"one", "two", "three"}, PreferenceWidget.checkboxes)
    };

    public String getName() {
        return "first_sample_app"; // this could be as a key used for i18n
    }

    public Object newPreferencesModel() {
        return new FirstSampleAppPrefs();
    }

    public PreferenceInfo[] getPreferencesInfo() {
        return prefs;
    }

    public final static class FirstSampleAppPrefs {
        private String foo;
        private int bar;
        private Date someDate;
        private String[] anArray;

        public FirstSampleAppPrefs() {
        }

        public String getFoo() {
            return foo;
        }

        public void setFoo(String foo) {
            this.foo = foo;
        }

        public int getBar() {
            return bar;
        }

        public void setBar(int bar) {
            this.bar = bar;
        }

        public Date getSomeDate() {
            return someDate;
        }

        public void setSomeDate(Date someDate) {
            this.someDate = someDate;
        }

        public String[] getAnArray() {
            return anArray;
        }

        public void setAnArray(String[] anArray) {
            this.anArray = anArray;
        }
    }
}
