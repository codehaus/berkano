package net.incongru.beantag;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;

/**
 * 
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.6 $
 */
public abstract class AbstractTableWriter implements TableWriter {
    private static final String DEFAULT_EVEN_CLASS = "even";
    private static final String DEFAULT_ODD_CLASS = "odd";

    private int rowCount;
    protected PropertyDecorator propertyDecorator = null;
    private BeanTagConfig config;

    public AbstractTableWriter() {
        this.rowCount = 0;
    }

    public void setConfig(BeanTagConfig beanTagConfig) {
        this.config = beanTagConfig;
    }

    public Object getValue(String propertyName, Object o) throws PropertyDecoratorException {
        if (propertyName == null) {
            return null;
        }

        return lookupValue(propertyName, o);
    }

    protected abstract Object lookupValue(String propertyName, Object o) throws PropertyDecoratorException;

    // ideally, this should not be public, but it should still be in the interface, so... but do we need the interface?
    public final void setPropertyDecorator(PropertyDecorator propertyDecorator) {
        this.propertyDecorator = propertyDecorator;
    }

    public void writeHeader(JspWriter out, String label, String htmlTableClass, String htmlTableStyle, String htmlTableId) throws IOException {
        out.print("<table");
        if (htmlTableClass != null) {
            out.print(" class=\"");
            out.print(htmlTableClass);
            out.print("\"");
        }
        if (htmlTableStyle != null) {
            out.print(" style=\"");
            out.print(htmlTableStyle);
            out.print("\"");
        }
        if (htmlTableId != null) {
            out.print(" id=\"");
            out.print(htmlTableId);
            out.print("\"");
        }
        out.println(">");
        if (label != null) {
            /*
            out.println("<tr>");
            out.print("<th colspan=\"2\">");
            out.print(label);
            out.println("</th>");
            out.println("</tr>");
            */
            out.print("<caption>");
            out.print(label);
            out.println("</caption>");
        }
        out.println("<tbody>");
    }

    public void writeFooter(JspWriter out) throws IOException {
        boolean emptyRow = Boolean.valueOf(config.getProperty(BeanTagConfig.OUTPUT_EMPTYTABLE_NOTICE)).booleanValue();
        if (rowCount == 0 && emptyRow) {
            out.print("<tr><td colspan=\"2\">");
            out.print(translate(BeanTagConfig.OUTPUT_EMPTYTABLE_NOTICE_TEXT));
            out.println("</td></tr>");
        }
        out.println("</tbody>");
        out.println("</table>");
    }

    public String getLabel(Property p) throws PropertyDecoratorException {
        if (p.getLabel() != null) {
            return p.getLabel();
        } else {
            return p.getPropertyName();
        }
    }

    public String getRowClass(Property p, Object o) throws PropertyDecoratorException {
        rowCount++;
        if (p.getRowClass() != null) {
            return p.getRowClass();
        } else {
            return rowCount % 2 == 0 ? DEFAULT_EVEN_CLASS : DEFAULT_ODD_CLASS;
        }
    }

    public String getRowStyle(Property p, Object o) throws PropertyDecoratorException {
        return p.getRowStyle();
    }

    public String getLabelClass(Property p, Object o) throws PropertyDecoratorException {
        return p.getLabelClass();
    }

    public String getLabelStyle(Property p, Object o) throws PropertyDecoratorException {
        return p.getLabelStyle();
    }

    public String getValueClass(Property p, Object o) throws PropertyDecoratorException {
        return p.getValueClass();
    }

    public String getValueStyle(Property p, Object o) throws PropertyDecoratorException {
        return p.getValueStyle();
    }

    protected String translate(String text) {
        return text;
    }
}
