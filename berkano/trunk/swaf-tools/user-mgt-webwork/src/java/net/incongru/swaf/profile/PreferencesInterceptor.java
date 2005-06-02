package net.incongru.swaf.profile;

import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.AroundInterceptor;
import net.incongru.swaf.app.Application;
import net.incongru.swaf.app.ApplicationsProvider;

/**
 * 
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class PreferencesInterceptor extends AroundInterceptor {
    private ApplicationsProvider appProvider;
    private static final String APPNAME_PARAMNAME = "app";

    public PreferencesInterceptor(ApplicationsProvider appProvider) {
        this.appProvider = appProvider;
    }

    protected void after(ActionInvocation dispatcher, String result) throws Exception {
    }

    protected void before(ActionInvocation invocation) throws Exception {
        AbstractPreferencesAction action = (AbstractPreferencesAction) invocation.getAction();

        Object appName = invocation.getInvocationContext().getParameters().get(APPNAME_PARAMNAME);
        if (appName != null) {
            String appNameStr = ((String[]) appName)[0];
            Application app = appProvider.getApp(appNameStr);
            Object prefsModel = app.newPreferencesModel();
            action.setPrefsModel(prefsModel);
        }
    }
}
