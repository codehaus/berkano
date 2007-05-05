package net.incongru.berkano.app;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple wrapper around a map of registered applications.
 *
 * The default application will be the first one that was registered,
 * as soon as PICO-243 is fixed.
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class ApplicationsProvider {
    private final String defaultAppName;
    private final Map<String, Application> appsMap;

    public ApplicationsProvider(Application[] apps) {
        if (apps.length < 1) {
            throw new IllegalStateException("No Application was registered");
        }
        this.defaultAppName = apps[0].getName();
        this.appsMap = new HashMap<String, Application>(apps.length);
        for (Application app : apps) {
            appsMap.put(app.getName(), app);
        }
    }

    /**
     * Never returns null: if the appName param was null, returns
     * the default Application.
     */
    public Application getApp(String appName) {
        if (appName == null) {
            appName = defaultAppName;
        }
        Application app = appsMap.get(appName);
        if (app == null) {
            throw new IllegalArgumentException("Unknown Application name");
        }
        return app;
    }

    public Collection getAllApps() {
        return appsMap.values();
    }
}
