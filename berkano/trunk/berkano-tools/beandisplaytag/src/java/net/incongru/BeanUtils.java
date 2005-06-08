package net.incongru;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;

/**
 * An attempt to replace BeanUtils, which, being good enough, has the huge disadvantage of being dependent on
 * commons-log! :(
 *
 * todo : not used yet
 *
 * @author gjoseph
 * @author $ $ (last edit)
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.3 $
 */
public class BeanUtils {

    private static HashMap descriptorsCache = new HashMap();

    private PropertyDescriptor[] getAllProperties(Object bean) throws IntrospectionException {
        Class c = bean.getClass();
        BeanInfo beanInfo = Introspector.getBeanInfo(c);
        PropertyDescriptor[] propsDesc = (PropertyDescriptor[]) descriptorsCache.get(c);
        if (propsDesc == null) {
            propsDesc = beanInfo.getPropertyDescriptors();
            descriptorsCache.put(c, propsDesc);
        }
        return propsDesc;
    }
}
