package net.incongru.berkano.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

/**
 * A simple wrapper around a map of registered applications.
 * Returns an instance of a null-Application
 *
 * TODO : a simple way to determine a default app (the first prefs screen that should be shown)
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class ApplicationsProvider {
    private static final Application NULL_APP = new NullApp();
    private Map appsMap;

    public ApplicationsProvider(Application[] apps) {
        this.appsMap = new HashMap(apps.length);
        for (int i = 0; i < apps.length; i++) {
            Application app = apps[i];
            appsMap.put(app.getName(), app);
        }
    }

    public Application getApp(String appName) {
        Application app = (Application) appsMap.get(appName);
        if (app == null) {
            return NULL_APP;
        }
        return app;
    }

    public Collection getAllApps() {
        return appsMap.values();
    }

    private static final class NullApp implements Application {
        public String getName() {
            return null;
        }

        public Object newPreferencesModel() {
            return null;
        }

        public PreferenceInfo[] getPreferencesInfo() {
            return new PreferenceInfo[0];
        }
    }
}
