package net.incongru.beantag;

import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.4 $
 *
 * @deprecated should use OgnlTableWriter instead
 */
public class BeanUtilsTableWriter extends AbstractTableWriter {

    protected Object lookupValue(String propertyName, Object o) throws PropertyDecoratorException {
        try {
            Object value = null;
            if (propertyDecorator != null) {
                try {
                    propertyDecorator.setCurrentItem(o);
                    //value = BeanUtils.getProperty(propertyDecorator, propertyName);
                    value = PropertyUtils.getNestedProperty(propertyDecorator, propertyName);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(propertyDecorator.getClass() + " threw InvocationTargetException : " + e.getCause(), e.getCause() == null ? e : e.getCause());
                } catch (NoSuchMethodException e) { // the decorator does not define this property, it's ok.
                }
            }
            //todo : if decorator explicitely returns null, we probably don't want to get the value from the "real" bean.
            if (value == null) {
                //value = BeanUtils.getProperty(o, propertyName);
                value = PropertyUtils.getNestedProperty(o, propertyName);
            }
            return value;
        } catch (IllegalAccessException e) {
            throw new PropertyDecoratorException("Can't get property[" + propertyName + "] for [" + o + "]: " + e.getClass() + " : " + e.getMessage(), e);
        } catch (InvocationTargetException e) {
            throw new PropertyDecoratorException("Can't get property[" + propertyName + "] for [" + o + "]: " + e.getClass() + " : " + e.getMessage(), e);
        } catch (NoSuchMethodException e) {
            throw new PropertyDecoratorException("Can't get property[" + propertyName + "] for [" + o + "]: " + e.getClass() + " : " + e.getMessage(), e);
        }
    }

    public boolean evaluate(String condition, Object o) {
        throw new IllegalArgumentException("not implemented");
    }
}
