package net.incongru.beantag;

import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @jsp.tag name="beandisplay"
 *          display-name="BeanDisplayTag"
 *          body-content="scriptless"
 *          description="A Tag to nicely display bean properties as tabular data."
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.14 $
 */
public class BeanDisplayTag extends SimpleTagSupport {
    private Object bean;
    private String name;
    private String label;
    private String showNulls;
    private String writerClass;
    private String decoratorClass;
    private String htmlTableClass;
    private String htmlTableStyle;
    private String htmlTableId;
    private String keyCssStyle;
    private String keyCssClass;
    private String valueCssStyle;
    private String valueCssClass;

    /**
     * stores the properties that the user wants to display *
     */
    private Collection properties;

    private TableWriter tableWriter;

    // -- tag attributes --
    /**
     * @jsp.attribute rtexprvalue=true
     */
    public void setBean(Object bean) {
        this.bean = bean;
    }

    /**
     * @jsp.attribute rtexprvalue=true
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @jsp.attribute rtexprvalue=true
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @jsp.attribute
     */
    public void setShownulls(String showNulls) {
        this.showNulls = showNulls;
    }

    /**
     * @jsp.attribute
     */
    public void setWriter(String writerClass) {
        this.writerClass = writerClass;
    }

    /**
     * @jsp.attribute
     */
    public void setDecorator(String decoratorClass) {
        this.decoratorClass = decoratorClass;
    }

    /**
     * @jsp.attribute
     */
    public void setCssclass(String htmlTableClass) {
        this.htmlTableClass = htmlTableClass;
    }

    /**
     * @jsp.attribute
     */
    public void setStyle(String htmlTableStyle) {
        this.htmlTableStyle = htmlTableStyle;
    }

    /**
     * @jsp.attribute
     */
    public void setId(String htmlTableId) {
        this.htmlTableId = htmlTableId;
    }

    /**
     * @jsp.attribute
     */
    public void setKeyclass(String keyCssClass) {
        this.keyCssClass = keyCssClass;
    }

    /**
     * @jsp.attribute
     */
    public void setKeystyle(String keyCssStyle) {
        this.keyCssStyle = keyCssStyle;
    }

    /**
     * @jsp.attribute
     */
    public void setValueclass(String valueCssClass) {
        this.valueCssClass = valueCssClass;
    }

    /**
     * @jsp.attribute
     */
    public void setValuestyle(String valueCssStyle) {
        this.valueCssStyle = valueCssStyle;
    }

    /**
     * Method used by nested tag to add properties.
     *
     * @param prop
     */
    void addProperty(Property prop) {
        properties.add(prop);
    }

    /**
     * Initializes parameters.
     */
    public void doTag() throws JspException, IOException {
        BeanTagConfig config = BeanTagConfig.getConfig((PageContext) getJspContext());

        if (bean != null && name != null) {
            throw new JspException("Only one of bean or name attributes is supported.");
        } else if (bean == null && name == null) {
            throw new JspException("You must define at least one of 'bean' or 'name' attribute.");
        } else if (bean == null) {
            bean = getJspContext().findAttribute(name);
        }

        // instanciate eventual TableWriter and PropertyDecorator implementations
        if (writerClass != null) {
            tableWriter = config.instanciateWriter(writerClass);
            writerClass = null;
        } else {
            tableWriter = config.instanciateDefaultWriter();
        }

        if (decoratorClass != null) {
            PropertyDecorator decorator = config.instanciatePropertyDecorator(decoratorClass);
            tableWriter.setPropertyDecorator(decorator);
            decoratorClass = null;
        }

        try {
            properties = new LinkedList();
            JspFragment jspBody = getJspBody();
            if (jspBody != null) {
                jspBody.invoke(getJspContext().getOut());
            }

            // get all properties from bean if none specified
            if (properties.size() == 0) {
                getAllProperties();
            }

            JspWriter out = getJspContext().getOut();

            tableWriter.writeHeader(out, label, htmlTableClass, htmlTableStyle, htmlTableId);
            rows(out);
            tableWriter.writeFooter(out);

            out.flush();
        } catch (PropertyDecoratorException e) {
            throw new JspException(e);
        } catch (IOException e) {
            throw new JspException(e);
        }
    }

    /** TODO there is a beanutils dependency here oh! */
    private void getAllProperties() throws JspException, PropertyDecoratorException {
        try {
            List all = new ArrayList(BeanUtils.describe(bean).keySet());
            Collections.sort(all);
            Iterator it = all.iterator();
            while (it.hasNext()) {
                String propName = (String) it.next();
                addProperty(new Property(getValue(propName), propName, propName, null, null, null));
            }
        } catch (IllegalAccessException ex) {
            throw new JspException(ex);
        } catch (InvocationTargetException ex) {
            throw new JspException(ex);
        } catch (NoSuchMethodException ex) {
            throw new JspException(ex);
        }
    }

    private void rows(JspWriter out) throws IOException, JspException {
        Iterator it = properties.iterator();
        while (it.hasNext()) {
            Property prop = (Property) it.next();
            try {
                String expr = prop.getCondition();
                if (expr != null && !evalCondition(expr, bean)) {
                    continue;
                }

                Object value = prop.getValue();

                if ("skip".equals(showNulls) && value == null) {
                    continue;
                }
                out.print("<tr");
                String s = tableWriter.getRowClass(prop, bean);
                if (s != null) {
                    out.print(" class=\"");
                    out.print(s);
                    out.print("\"");
                }
                s = tableWriter.getRowStyle(prop, bean);
                if (s != null) {
                    out.print(" style=\"");
                    out.print(s);
                    out.print("\"");
                }
                out.println(">");
                out.print("<td");
                s = tableWriter.getLabelClass(prop, bean);
                if (s != null || keyCssClass != null) {
                    out.print(" class=\"");
                    out.print(silentNull(s));
                    if (s != null && keyCssClass != null) {
                        out.print(" ");
                    }
                    out.print(silentNull(keyCssClass));
                    out.print("\"");
                }
                s = tableWriter.getLabelStyle(prop, bean);
                if (s != null || keyCssStyle != null) {
                    out.print(" style=\"");
                    out.print(silentNull(s));
                    if (s != null && keyCssStyle != null) {
                        out.print(" ");
                    }
                    out.print(silentNull(keyCssStyle));
                    out.print("\"");
                }
                out.print(">");
                out.print(tableWriter.getLabel(prop));
                out.println("</td>");
                out.print("<td");
                s = tableWriter.getValueClass(prop, bean);
                if (s != null || valueCssClass != null) {
                    out.print(" class=\"");
                    out.print(silentNull(s));
                    if (s != null && valueCssClass != null) {
                        out.print(" ");
                    }
                    out.print(silentNull(valueCssClass));
                    out.print("\"");
                }
                s = tableWriter.getValueStyle(prop, bean);
                if (s != null || valueCssStyle != null) {
                    out.print(" style=\"");
                    out.print(silentNull(s));
                    if (s != null && valueCssStyle != null) {
                        out.print(" ");
                    }
                    out.print(silentNull(valueCssStyle));
                    out.print("\"");
                }
                out.print(">");
                if (value != null) {
                    out.print(value);
                } else if ("true".equals(showNulls)) {
                    out.print("null");
                }
                out.println("</td>");
                out.println("</tr>");
            } catch (PropertyDecoratorException ex) {
                throw new JspException("Can't read property[" + prop + "] for [" + bean + "]: " + ex.getMessage(), ex);
            }
        }
    }

    /** used by child BeanPropertyTag instances */
    Object getValue(String propertyName) throws PropertyDecoratorException {
        return tableWriter.getValue(propertyName, bean);
    }

    private boolean evalCondition(String condition, Object o) {
        return tableWriter.evaluate(condition, o);
    }

    private String silentNull(String s) {
        return s != null ? s : "";
    }
}
