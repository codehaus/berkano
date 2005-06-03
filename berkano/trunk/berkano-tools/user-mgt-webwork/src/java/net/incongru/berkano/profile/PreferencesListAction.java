package net.incongru.berkano.profile;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.LocalizedTextUtil;
import com.opensymphony.xwork.util.OgnlValueStack;
import net.incongru.berkano.app.Application;
import net.incongru.berkano.app.ApplicationsProvider;
import net.incongru.berkano.app.PreferenceInfo;
import net.incongru.berkano.user.extensions.UserPropertyHelper;

import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This action allows to display a list of preferences..
 * Overrides xwork's ActionSupport methods for text i18n.
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.3 $
 */
public class PreferencesListAction extends AbstractPreferencesAction {
    public PreferencesListAction(ApplicationsProvider appProvider, UserPropertyHelper userPropertyHelper) {
        super(appProvider, userPropertyHelper);
    }

    public Collection getApps() {
        return appProvider.getAllApps();
    }

    public PreferenceInfo[] getPreferencesInfo() {
        Application app = appProvider.getApp(appName);
        return app.getPreferencesInfo();
    }

    public String getAppName(Application app) {
        Class appClass = app.getClass();
        return getText(appClass, app.getName(), app.getName(), null, ActionContext.getContext().getValueStack());
    }

    public String getText(String key, String defaultValue, List args, OgnlValueStack stack) {
        Class appClass = getAppClass();
        return getText(appClass, key, defaultValue, args, stack);
    }

    private String getText(Class appClass, String key, String defaultValue, List args, OgnlValueStack stack) {
        if (appClass == null) {
            return super.getText(key, defaultValue, args, stack);
        }
        Object[] argsArray = ((args != null) ? args.toArray(new Object[args.size()]) : null);
        return LocalizedTextUtil.findText(appClass, key, getLocale(), defaultValue, argsArray, stack);
    }

    public ResourceBundle getTexts() {
        Class appClass = getAppClass();
        return appClass != null ? getTexts(appClass.getName()) : super.getTexts();
    }

    private Class getAppClass() {
        Application app = appProvider.getApp(appName);
        Class appClass = app.getClass();
        return appClass;
    }
}
