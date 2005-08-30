package net.incongru.beantag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Simple tag to define which properties should be displayed.
 * Use a decorator if you need different css class/style for label and value, or if you need to conditionally
 * set these.
 *
 * @jsp.tag name="property"
 *          display-name="BeanPropertyTag"
 *          body-content="scriptless"
 *          description="BeanPropertyTag must be nested inside BeanDisplayTag"
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.5 $
 */
public class BeanPropertyTag extends SimpleTagSupport {
    private String propertyName;
    private String refName;
    private String label;
    private String htmlClass;
    private String htmlStyle;
    private String condition;
    private String conditionalClass;

    /**
     * @param propertyName If not specified, then the body will be printed.
     * @jsp.attribute required=false
     */
    public void setName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * The name of the attribute with which the current value is exposed to the body of the tag.
     * @jsp.attribute rtexprvalue=true
     */
    public void setRef(String ref) {
        this.refName = ref;
    }

    /**
     * @param label
     * @jsp.attribute
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @param htmlClass
     * @jsp.attribute
     */
    public void setCssclass(String htmlClass) {
        this.htmlClass = htmlClass;
    }

    /**
     * @param htmlStyle
     * @jsp.attribute
     */
    public void setStyle(String htmlStyle) {
        this.htmlStyle = htmlStyle;
    }

    /**
     * @param test an expression which is going to be evaluated against the expression language (ognl currently)
     * @jsp.attribute
     */
    public void setTest(String test) {
        this.condition = test;
    }

    /**
     * @param conditionalClass a css class which is added to the row if the test evaluated positively.
     * @jsp.attribute
     */
    public void setConditionalClass(String conditionalClass) {
        this.conditionalClass = conditionalClass;
    }

    public void doTag() throws JspException, IOException {
        BeanDisplayTag parentTag = (BeanDisplayTag) findAncestorWithClass(this, BeanDisplayTag.class);
        if (parentTag == null) {
            throw new JspException("BeanPropertyTag must be nested inside a BeanDisplayTag");
        }

        try {
            Object value = parentTag.getValue(propertyName);
            JspFragment body = getJspBody();
            if (body != null) {
                if (refName != null) {
                    body.getJspContext().setAttribute(refName, value);
                }
                StringWriter sw = new StringWriter();
                body.invoke(sw);
                value = sw.toString();
                if (refName != null) {
                    body.getJspContext().removeAttribute(refName);
                }
            }
            Property p = new Property(value, propertyName, label, htmlClass, htmlStyle, condition, conditionalClass);
            parentTag.addProperty(p);
        } catch (PropertyDecoratorException e) {
            throw new JspException(e);
        }

    }
}
