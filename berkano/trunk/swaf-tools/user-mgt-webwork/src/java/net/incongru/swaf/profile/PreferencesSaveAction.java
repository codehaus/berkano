package net.incongru.swaf.profile;

import net.incongru.swaf.app.ApplicationsProvider;
import net.incongru.swaf.user.extensions.UserPropertyHelper;

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
