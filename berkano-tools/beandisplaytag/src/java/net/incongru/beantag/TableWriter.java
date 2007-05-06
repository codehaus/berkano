package net.incongru.beantag;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.5 $
 */
public interface TableWriter {
    public void writeHeader(JspWriter out, String label, String htmlTableClass, String htmlTableStyle, String htmlTableId) throws IOException;

    public void writeFooter(JspWriter out) throws IOException;

    public String getLabel(Property p) throws PropertyDecoratorException;

    public Object getValue(String propertyName, Object o) throws PropertyDecoratorException;

    public String getRowClass(Property p, Object o) throws PropertyDecoratorException;

    public String getRowStyle(Property p, Object o) throws PropertyDecoratorException;

    public String getLabelClass(Property p, Object o) throws PropertyDecoratorException;

    public String getLabelStyle(Property p, Object o) throws PropertyDecoratorException;

    public String getValueClass(Property p, Object o) throws PropertyDecoratorException;

    public String getValueStyle(Property p, Object o) throws PropertyDecoratorException;

    public void setPropertyDecorator(PropertyDecorator pd);

    boolean evaluate(String condition, Object o);

    List getAllProperties(Object o) throws PropertyDecoratorException;

    void setConfig(BeanTagConfig beanTagConfig);
}
