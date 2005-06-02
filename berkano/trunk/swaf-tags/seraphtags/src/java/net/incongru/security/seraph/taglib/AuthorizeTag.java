package net.incongru.security.seraph.taglib;

import javax.servlet.jsp.JspException;
import java.io.IOException;

/**
 * A simple jsp tag that includes its body only if the required role
 * is granted to the logged in user.
 *
 * @jsp.tag name="auth"
 *          display-name="Swaf-Seraph-Authorization Tag"
 *          body-content="scriptless"
 *          description=""
 * */
public class AuthorizeTag extends AbstractSecurityTag {
    private String requiredRole = null;

    /**
     * @jsp.attribute rtexprvalue=true required=true
     */
    public void setRequiredrole(String requiredRole) {
        this.requiredRole = requiredRole;
    }

    public void doTag() throws IOException, JspException {
        if (shouldIncludeBody()) {
            getJspBody().invoke(null);
        }
    }

    private boolean shouldIncludeBody() {
        if (requiredRole == null || requiredRole.equals("")) {
            return false;
        } else if (isUserInRole(requiredRole)) {
            return true;
        } else {
            return false;
        }
    }
}
