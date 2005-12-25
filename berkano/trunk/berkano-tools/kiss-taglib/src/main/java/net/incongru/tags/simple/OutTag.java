package net.incongru.tags.simple;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A very simple tag that outputs an attribute stored in one of PageContext,
 * ServletRequest, Session, or ServletContext(page, request, session, application).
 * When the required value is null, the tag outputs an empty string instead of
 * "null", except if the displayNull attribute is set to true.
 *
 * TODO : make sure this does not exist elsewhere already, but i could not find it
 *
 * todo: check the scope thing... isn't there something more standard !?
 *
 * TODO : move to SimpleTag(jsp2)
 *
 * @jsp.tag name="out"
 *          body-content="empty"
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class OutTag extends TagSupport {
    // TODO : There's another construct which i can't find right now that involves anonymous subclassing...
    private final static List scopes = new ArrayList();

    static {
        scopes.add("page");
        scopes.add("request");
        scopes.add("session");
        scopes.add("application");
    }

    private String key;
    private String scope = "request";
    private boolean displayNull = false;

    /**
     *
     * @param key
     * @jsp.attribute required="true" rtexprvalue="true" type="java.lang.String"
     *                description="name of the attribute to print"
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * The scope of the attribute to output.
     * @param scope one of page, request, session, application
     * @jsp.attribute required="false" rtexprvalue="true" type="java.lang.String"
     *                description="Default is request."
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * Whether the tag will out an empty string or the "null" string.
     *
     * @param displayNull if true, then "null" is displayed; if false, empty
     *                    string it outputted for null values.
     * @jsp.attribute required="false" rtexprvalue="true" type="boolean"
     *                description="Default is false."
     */
    public void setDisplayNull(boolean displayNull) {
        this.displayNull = displayNull;
    }

    /**
     *
     * @return
     * @throws JspException
     */
    public int doStartTag() throws JspException {
        // This is highly dependent on the fact that the scopes of page, request,
        // session and application have values from 1 to 4. If this was to change,
        // we would be in trouble. TODO: fix this issue.
        int scopeInt = scopes.indexOf(scope) + 1;
        if (scopeInt < 1) {
            throw new JspException("The specified scope (" + scope + ") is invalid. Valid values are \"page\", \"request\", \"session\" and \"application\". If unspecified, the default value will be \"request\".");
        }

        Object value = pageContext.getAttribute(this.key, scopeInt);
        if (!displayNull && value == null) {
            value = "";
        }
        try {
            pageContext.getOut().print(value);
        } catch (IOException ex) {
            throw new JspException("Could not print body: " + ex.getMessage(), ex);
        }
        return SKIP_BODY;
    }
}
