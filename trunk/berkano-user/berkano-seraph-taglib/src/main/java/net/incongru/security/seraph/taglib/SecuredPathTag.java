package net.incongru.security.seraph.taglib;

import javax.servlet.jsp.JspException;
import java.io.IOException;

/**
 * A simple jsp tag that will output its body if the current user
 * has access to the path denoted by <code>href</code>. This path
 * is relative to the context path.
 * If the PathService is not available, an exception will be thrown.
 * 
 * @jsp.tag name="spath"
 *          display-name="Berkano-Seraph-SecuredPath Tag"
 *          body-content="scriptless"
 *          description=""
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class SecuredPathTag extends AbstractSecurityTag {
    private String href;

    /**
     * @jsp.attribute rtexprvalue=true required=true
     */
    public void setHref(String href) {
        this.href = href;
    }

    public void doTag() throws JspException, IOException {
        boolean granted = isUserAllowedPath(href);
        if (granted) {
            outputBody();
        }
    }
}
