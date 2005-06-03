package net.incongru.berkano.profile;

import net.incongru.berkano.app.ApplicationsProvider;
import net.incongru.berkano.user.extensions.UserPropertyHelper;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.3 $
 */
public class PreferencesSaveAction extends AbstractPreferencesAction {
    public PreferencesSaveAction(ApplicationsProvider appProvider, UserPropertyHelper userPropertyHelper) {
        super(appProvider, userPropertyHelper);
    }

    public String execute() throws Exception {
        userPropertyHelper.store(getPreferenceKey(), getModel());

        return SUCCESS;
    }
}
