package net.incongru.berkano.profile;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.Preparable;
import net.incongru.berkano.app.ApplicationsProvider;
import net.incongru.berkano.user.extensions.UserPropertyHelper;

/**
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.3 $
 */
public class PreferencesSaveAction extends AbstractPreferencesAction implements Preparable {
    private Object prefsModel;

    public PreferencesSaveAction(ApplicationsProvider appProvider, UserPropertyHelper userPropertyHelper) {
        super(appProvider, userPropertyHelper);
    }

    public void prepare() throws Exception {
        prefsModel = getApp().newPreferencesModel();
        ActionContext.getContext().getValueStack().push(prefsModel);
    }

    public String execute() throws Exception {
        userPropertyHelper.store(getPreferenceKey(), prefsModel);

        return SUCCESS;
    }
}
