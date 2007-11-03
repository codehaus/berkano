package net.incongru.berkano.user.hibernate;

import net.incongru.berkano.user.GroupImpl;
import net.incongru.berkano.user.User;
import net.incongru.berkano.user.UserImpl;
import net.incongru.berkano.user.extensions.UserPropertyAccessor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * An helper class to retrieve user/group properties.
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.3 $
 */
public class HibernatedUserPropertyAccessor implements UserPropertyAccessor {
    //private Session session;

    //public HibernatedUserPropertyAccessor(Session session) {
    public HibernatedUserPropertyAccessor() {
        //this.session = session;
    }

    /**
     * Returns a value from the user's properties, not taking the groups
     * properties into account
     */
    public Object getUserOnlyValue(User u, String key) {
        //session.lock(u, LockMode.NONE);
        return ((UserImpl) u).getProperty(key);
    }

    /**
     * Returns the first value encountered for this key. Searches
     * first in the user's properties, then in groups' properties.
     * <strong>This is non-deterministic, since multiple groups
     * could have the same property with different values.</strong>
     * However, it will <strong>always</strong> first return the user's
     * own property if it is set.
     */
    public Object getFirstValue(User u, String key) {
        //session.lock(u, LockMode.NONE);

        Object value = ((UserImpl) u).getProperty(key);
        if (value != null) {
            return value;
        }

        Iterator it = u.getGroups().iterator();
        while (it.hasNext()) {
            GroupImpl g = (GroupImpl) it.next();
            value = g.getProperty(key);
            if (value != null) {
                return value;
            }
        }

        return null;
    }

    /**
     * Returns a single value found for this key, following the following rules:
     * <ul>
     *  <li>If the property is defined at user level, it will be returned, regardless
     *      of the groups' properties</li>
     *  <li>If the property is defined in multiple groups, an exception will be thrown</li>
     *  <li>The property will be return from group level if it is defined in only one of
     *      the user's groups.</li>
     *  <li>Null will be returned if the property can't be found</li>
     * </ul>
     */
    public Object getSingleValue(User u, String key) {

        //session.lock(u, LockMode.NONE);

        Object value = ((UserImpl) u).getProperty(key);
        if (value != null) {
            return value;
        }

        return getGroupSingleValue(u, key);
    }

    /**
     * Returns a single value found for this key, following the following rules:
     * <ul>
     *  <li>The user's properties are not taken into account</li>
     *  <li>If the property is defined in multiple groups, an exception will be thrown</li>
     *  <li>The property will be return from group level if it is defined in only one of
     *      the user's groups.</li>
     *  <li>Null will be returned if the property can't be found</li>
     * </ul>
     */
    public Object getGroupSingleValue(User u, String key) {
        //session.lock(u, LockMode.NONE);

        Object value = null;
        int definedInGroupsCount = 0;
        Iterator it = ((UserImpl) u).getGroups().iterator();
        while (it.hasNext()) {
            GroupImpl g = (GroupImpl) it.next();
            Object groupValue = g.getProperty(key);
            if (groupValue != null) {
                value = groupValue;
                definedInGroupsCount++;
            }
        }

        if (definedInGroupsCount > 1) {
            throw new IllegalStateException("Property " + key + " is defined in several groups of the user");
        }
        return value;
    }

    /**
     * Returns a collection aggregating the different values of this property
     * found in both the user's properties and all of the user's groups' properties.
     * Returns a empty collection if the property is not found, will never return null.
     */
    public Collection getValues(User u, String key) {
        //session.lock(u, LockMode.NONE);

        Collection values = getGroupValues(u, key);
        Object v = ((UserImpl) u).getProperty(key);
        if (v != null) {
            values.add(v);
        }
        return values;
    }

    /**
     * Returns a collection aggregating the different values of this property
     * found in all of the user's groups' properties. (not the user's direct properties)
     * Returns a empty collection if the property is not found, will never return null.
     */
    public Collection getGroupValues(User u, String key) {
        //session.lock(u, LockMode.NONE);

        Collection values = new LinkedList();
        Iterator it = ((UserImpl) u).getGroups().iterator();
        while (it.hasNext()) {
            GroupImpl g = (GroupImpl) it.next();
            Object v = g.getProperty(key);
            if (v != null) {
                values.add(v);
            }
        }
        return values;
    }

    /**
     * This method glues all values found for the given user and property name.
     * In the case of collections, it returns them as a collection that includes
     * all the element of each of them. Same for arrays and maps. An exception
     * is thrown for other types.
     */
    public Object aggregate(User u, String key) {
        //session.lock(u, LockMode.NONE);

        Object result = null;
        Collection values = getValues(u, key);
        Iterator it = values.iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (result == null) {
                if (o instanceof Collection) {
                    result = new LinkedList((Collection) o);
                } else if (o instanceof Map) {
                    result = new HashMap((Map) o);
                    /** works but commented out - don't like the way this works
                     } else if (o instanceof Object[]) {
                     result = new Object[((Object[]) o).length];
                     System.arraycopy(o, 0, result, 0, ((Object[]) o).length);
                     */
                } else {
                    throw new IllegalArgumentException("Unsupported property type : " + o.getClass().getName());
                }
            } else {
                if (o instanceof Collection) {
                    ((Collection) result).addAll((Collection) o);
                } else if (o instanceof Map) {
                    ((Map) result).putAll((Map) o);
                    /** works but commented out - don't like the way this works
                     } else if (o instanceof Object[]) {
                     Object[] temp = new Object[((Object[]) result).length + ((Object[]) o).length];
                     System.arraycopy(result, 0, temp, 0, ((Object[]) result).length);
                     System.arraycopy(o, 0, temp, ((Object[]) result).length, ((Object[]) o).length);
                     result = temp;
                     */
                } else {
                    throw new IllegalArgumentException("Unsupported property type : " + o.getClass());
                }
            }
        }
        return result;
    }
}
