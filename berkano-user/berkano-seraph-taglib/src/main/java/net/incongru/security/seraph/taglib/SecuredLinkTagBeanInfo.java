/***************************************
 *                                     *
 *                                     *
 *     Copyright @2005   KIALA         *
 *                                     *
 *                                     *
 ***************************************/
package net.incongru.security.seraph.taglib;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class SecuredLinkTagBeanInfo extends SimpleBeanInfo {
    public PropertyDescriptor[] getPropertyDescriptors() {
        List proplist = new ArrayList();

        try {
            proplist.add(new PropertyDescriptor("href", SecuredLinkTag.class, null, "setHref"));
            proplist.add(new PropertyDescriptor("title", SecuredLinkTag.class, null, "setTitle"));
            proplist.add(new PropertyDescriptor("onclick", SecuredLinkTag.class, null, "setOnclick"));
            proplist.add(new PropertyDescriptor("alwaysOutputBody", SecuredLinkTag.class, null, "setAlwaysOutputBody"));
            proplist.add(new PropertyDescriptor("includeContext", SecuredLinkTag.class, null, "setIncludeContext"));
            proplist.add(new PropertyDescriptor("class", SecuredLinkTag.class, null, "setClass"));

            // make ATG Dynamo happy:
            proplist.add(new PropertyDescriptor("className", SecuredLinkTag.class, null, "setClass"));
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }

        PropertyDescriptor[] result = new PropertyDescriptor[proplist.size()];
        return ((PropertyDescriptor[]) proplist.toArray(result));
    }
}
