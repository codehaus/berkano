package net.incongru.swaf.app;

/**
 * A basic descriptive interface for applications.
 *
 * Provides a name for the app, and user sett-able preferences.
 * TODO : an impl that populates itself from a config file?
 *
 * todo: (this was older description of the class - do we still want this?)
 * An application has a set of functionalities. Each of these functionalities
 * can be restricted to a user or a group.
 *
 * 
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.3 $
 */
public interface Application {
    public String getName();

    public Object newPreferencesModel();

    public PreferenceInfo[] getPreferencesInfo();

}
