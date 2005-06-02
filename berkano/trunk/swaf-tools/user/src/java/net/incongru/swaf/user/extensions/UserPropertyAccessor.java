package net.incongru.swaf.user.extensions;

import net.incongru.swaf.user.User;

import java.util.Collection;

/**
 * The interface used to manipulate a user's properties.
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.6 $
 */
public interface UserPropertyAccessor {
    Object getUserOnlyValue(User u, String key);

    Object getFirstValue(User u, String key);

    Object getSingleValue(User u, String key);

    Object getGroupSingleValue(User u, String key);

    Collection getValues(User u, String key);

    Collection getGroupValues(User u, String key);

    Object aggregate(User u, String key);
}
