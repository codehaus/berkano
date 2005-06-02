package net.incongru.swaf.i18n;

import net.incongru.swaf.app.Application;
import net.incongru.swaf.app.PreferenceInfo;
import net.incongru.swaf.security.Role;

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
