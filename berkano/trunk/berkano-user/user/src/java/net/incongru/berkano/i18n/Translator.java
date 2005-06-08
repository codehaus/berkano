package net.incongru.berkano.i18n;

import net.incongru.berkano.app.Application;
import net.incongru.berkano.app.PreferenceInfo;
import net.incongru.berkano.security.Role;

/**
 * TODO : use this or trash this !
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.3 $
 */
public interface Translator {
    public String getApplicationName(Application application);

    public String getPreferenceName(PreferenceInfo preference);

    public String getRoleName(Role role);
}
