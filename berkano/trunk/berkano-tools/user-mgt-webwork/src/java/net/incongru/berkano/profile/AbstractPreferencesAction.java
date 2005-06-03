package net.incongru.berkano.profile;

import com.opensymphony.xwork.ActionSupport;
import com.opensymphony.xwork.ModelDriven;
import net.incongru.berkano.app.ApplicationsProvider;
import net.incongru.berkano.user.extensions.UserPropertyHelper;

/**
 * 
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public abstract class AbstractPreferencesAction extends ActionSupport implements ModelDriven {
    protected ApplicationsProvider appProvider;
    protected UserPropertyHelper userPropertyHelper;
    protected Object prefsModel;
    protected String appName;

    public AbstractPreferencesAction(ApplicationsProvider appProvider, UserPropertyHelper userPropertyHelper) {
        this.appProvider = appProvider;
        this.userPropertyHelper = userPropertyHelper;
    }

    // the current app on which we're working
    public String getApp() {
        return appName;
    }

    public void setApp(String appName) {
        this.appName = appName;
    }

    public Object getPrefsModel() {
        return prefsModel;
    }

    public void setPrefsModel(Object prefsModel) {
        this.prefsModel = prefsModel;
    }

    public Object getModel() {
        return getPrefsModel();
    }

    protected String getPreferenceKey() {
        return "preferences/" + appName;
    }
}
