package net.incongru.berkano.profile;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.TextProvider;
import com.opensymphony.xwork.TextProviderSupport;
import com.opensymphony.xwork.util.LocalizedTextUtil;
import com.opensymphony.xwork.util.OgnlValueStack;
import net.incongru.berkano.app.Application;
import net.incongru.berkano.app.ApplicationsProvider;
import net.incongru.berkano.user.extensions.UserPropertyHelper;

import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This action allows to display a list of preferences..
 * Overrides xwork's ActionSupport methods for text i18n.
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.3 $
 */
public class PreferencesListAction extends AbstractPreferencesAction {
    private transient TextProvider textProvider;

    public PreferencesListAction(ApplicationsProvider appProvider, UserPropertyHelper userPropertyHelper) {
        super(appProvider, userPropertyHelper);
    }

    /**
     * Pushes the current preferences values on the stack so the view can display it.
     */
    public String execute() throws Exception {
        Object prefs = userPropertyHelper.getUserOnlyValue(getPreferenceKey());
        ActionContext.getContext().getValueStack().push(prefs);

        return super.execute();
    }

    public Collection getApps() {
        return appProvider.getAllApps();
    }

    // i18n related methods :
    public String getTranslatedAppName(String appName) {
        Class<? extends Application> clazz = appProvider.getApp(appName).getClass();
        return LocalizedTextUtil.findText(clazz, appName, getLocale());
    }

    private Class getAppClass() {
        return getApp().getClass();
    }

    private TextProvider getTextProvider() {
        if (textProvider == null) {
            textProvider = new TextProviderSupport(getAppClass(), this);
        }
        return textProvider;
    }

    // --- text provider methods
    public String getText(String aTextName) {
        return getTextProvider().getText(aTextName);
    }

    public String getText(String aTextName, String defaultValue) {
        return getTextProvider().getText(aTextName, defaultValue);
    }

    public String getText(String aTextName, List args) {
        return getTextProvider().getText(aTextName, args);
    }

    public String getText(String aTextName, String defaultValue, List args) {
        return getTextProvider().getText(aTextName, defaultValue, args);
    }

    public String getText(String key, String defaultValue, List args, OgnlValueStack stack) {
        return getTextProvider().getText(key, defaultValue, args, stack);
    }

    public ResourceBundle getTexts() {
        return getTextProvider().getTexts();
    }

    public ResourceBundle getTexts(String aBundleName) {
        return getTextProvider().getTexts(aBundleName);
    }
}
