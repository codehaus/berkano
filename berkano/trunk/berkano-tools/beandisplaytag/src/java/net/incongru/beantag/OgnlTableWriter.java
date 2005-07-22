package net.incongru.beantag;

import ognl.NoSuchPropertyException;
import ognl.Ognl;
import ognl.OgnlException;
import ognl.OgnlRuntime;

import java.util.List;
import java.util.Collections;
import java.util.Map;
import java.util.LinkedList;
import java.beans.IntrospectionException;

/**
 *
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.4 $
 */
public class OgnlTableWriter extends AbstractTableWriter {
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(OgnlTableWriter.class);

    protected Object lookupValue(String propertyName, Object o) throws PropertyDecoratorException {
        try {
            Object value = null;
            // todo : cache pre-parsed expressions
            Object ognlExpr = Ognl.parseExpression(propertyName);
            if (propertyDecorator != null) {
                propertyDecorator.setCurrentItem(o);
                try {
                    value = Ognl.getValue(ognlExpr, propertyDecorator);
                } catch (NoSuchPropertyException e) {
                    // fine, the decorator doesn't provide this property.
                }
            }
            //todo : if decorator explicitely returns null, we probably don't want to get the value from the "real" bean.
            if (value == null) {
                value = Ognl.getValue(ognlExpr, o);
            }
            return value;
        } catch (OgnlException e) {
            logger.warn("Can't get property[" + propertyName + "] for [" + o + "]: " + e.getClass() + " : " + e.getMessage());
            return null;
            //throw new PropertyDecoratorException("Can't get property[" + p + "] for [" + o + "]: " + e.getClass() + " : " + e.getMessage(), e);
        }
    }

    public boolean evaluate(String expr, Object o) {
        try {
            // todo : cache pre-parsed expressions
            Object ognlExpr = Ognl.parseExpression(expr);
            Boolean result = (Boolean) Ognl.getValue(ognlExpr, o, Boolean.class);
            if (result==null){
                return false;
            }
            return result.booleanValue();
        } catch (OgnlException e) {
            logger.warn("Can't evaluate expression[" + expr + "]", e);
            return false;
        }
    }

    protected List<String> getAllPropertyNames(Object o) {
        if (o==null) {
            return Collections.emptyList();
        }
        try {
            Map propsDescs = OgnlRuntime.getPropertyDescriptors(o.getClass());
            return new LinkedList(propsDescs.keySet());
        } catch (OgnlException e) {
            throw new RuntimeException(e);
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }
    }
}
