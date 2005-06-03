package net.incongru.berkano.user.hibernate;

import net.incongru.berkano.user.PropertiesAware;
import net.incongru.berkano.user.UnknownUserException;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

/**
 * 
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public abstract class AbstractHibernatedDAO {
    protected Session session;

    public AbstractHibernatedDAO(Session session) {
        this.session = session;
    }

    protected abstract PropertiesAware getById(Long id) throws UnknownUserException;

    public void addProperty(Long id, String propertyKey, Object value) throws UnknownUserException {
        PropertiesAware o = getById(id);
        addProperty(o, propertyKey, value);
    }

    public void addProperty(PropertiesAware o, String propertyKey, Object value) {
        try {
            o.getProperties().put(propertyKey, value);
            session.save(o);
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeProperty(Long id, String propertyKey) throws UnknownUserException {
        try {
            PropertiesAware o = getById(id);
            o.getProperties().remove(propertyKey);
            session.save(o);
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }
}
