package net.incongru.berkano.profile;

import com.opensymphony.webwork.interceptor.ParameterAware;
import com.opensymphony.xwork.ActionSupport;
import net.incongru.berkano.app.Application;
import net.incongru.berkano.app.ApplicationsProvider;
import net.incongru.berkano.user.extensions.UserPropertyHelper;

import java.util.Map;

/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public abstract class AbstractPreferencesAction extends ActionSupport implements ParameterAware {
    private static final String APPNAME_PARAMNAME = "app";

    protected ApplicationsProvider appProvider;
    protected UserPropertyHelper userPropertyHelper;
    protected String appName;

    public AbstractPreferencesAction(ApplicationsProvider appProvider, UserPropertyHelper userPropertyHelper) {
        this.appProvider = appProvider;
        this.userPropertyHelper = userPropertyHelper;
    }

    public void setParameters(Map map) {
        String[] paramValue = ((String[]) map.get(APPNAME_PARAMNAME));
        if (paramValue != null) {
            appName = paramValue[0];
        }
    }

    public Application getApp() {
        return appProvider.getApp(appName);
    }

    protected String getPreferenceKey() {
        return "preferences/" + getApp().getName();
    }
}
