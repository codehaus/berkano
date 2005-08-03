package net.incongru.security.seraph.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;

/**
 * A simple jsp tag that will output an html link (<code>&lt;a href="..."&gt;...@lt;/a@gt;</code>)
 * if the user has the permission to access the given path, based on seraph's PathService.
 * If the user doesn't have the required role(s), the tag will either output nothing
 * or just the content of the tag. <code>href</code>'s path is relative to the context path.
 * If the PathService is not available, an exception will be thrown.
 * 
 * @jsp.tag name="slink"
 *          display-name="Berkano-Seraph-SecuredLink Tag"
 *          body-content="scriptless"
 *          description=""
 *
 * TODO : i18n for title!?
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.3 $
 */
public class SecuredLinkTag extends AbstractSecurityTag {
    private String href;
    private String title;
    private String onclick; // TODO : if we add more attributes, we should find some easy way to delegate/abstract
    private boolean alwaysOutputBody = false;

    /**
     * A href use in the generated <code>&lt;a&gt;</code> tag,
     * either relative to the current page, or relative to the
     * context path if it starts with a <code>/</code>.
     * @jsp.attribute rtexprvalue=true required=true
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * @jsp.attribute rtexprvalue=true required=false
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @jsp.attribute rtexprvalue=true required=false
     */
    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    /**
     * @jsp.attribute rtexprvalue=true required=false type=boolean
     */
    public void setAlwaysOutputBody(boolean alwaysOutputBody) {
        this.alwaysOutputBody = alwaysOutputBody;
    }

    public void doTag() throws JspException, IOException {
        boolean granted = isUserAllowedPath(href);
        if (granted) {
            outputLink();
        } else if (alwaysOutputBody) {
            outputBody();
        }
    }

    private void outputLink() throws IOException, JspException {
        JspWriter out = getJspContext().getOut();
        out.write("<a href=\"");
        if (href.startsWith("/")) {
            out.write(getRequest().getContextPath());
        }
        out.write(href);
        out.write("\"");
        if (title != null) {
            out.write(" title=\"");
            out.write(title);
            out.write("\"");
        }
        if (onclick != null) {
            out.write(" onclick=\"");
            out.write(onclick);
            out.write("\"");
        }
        out.write(">");
        outputBody(out);
        out.write("</a>");
    }
}
