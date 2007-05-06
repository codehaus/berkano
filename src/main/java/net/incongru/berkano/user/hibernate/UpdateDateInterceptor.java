package net.incongru.berkano.user.hibernate;

import net.incongru.berkano.user.User;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Date;

/**
 * Maintains the creationDate and updateDate fields of UserImpl
 *
 * TODO : this might be better as an EventListener? Not sure there's a real difference in "betterness",
 * but the interfaces of listeners are simpler/cleaner.
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class UpdateDateInterceptor extends EmptyInterceptor {

    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (entity instanceof User) {
            findAndSetTimeStamp("creationTimestamp", propertyNames, state);
            return true;
        } else {
            return false;
        }
    }

    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        if (entity instanceof User) {
            findAndSetTimeStamp("updateTimestamp", propertyNames, currentState);
            return true;
        } else {
            return false;
        }
    }

    private void findAndSetTimeStamp(String propertyName, String[] propertyNames, Object[] state) {
        for (int i = 0; i < propertyNames.length; i++) {
            if (propertyName.equals(propertyNames[i])) {
                state[i] = new Date();
                break;
            }
        }
    }
}
