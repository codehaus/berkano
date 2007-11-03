package net.incongru.security.seraph.taglib;

import com.atlassian.seraph.SecurityService;
import com.atlassian.seraph.auth.AuthenticationContext;
import com.atlassian.seraph.auth.RoleMapper;
import com.atlassian.seraph.config.SecurityConfig;
import com.atlassian.seraph.service.PathService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public abstract class AbstractSecurityTag extends SimpleTagSupport {
    protected boolean isUserInRole(String requiredRole) {
        SecurityConfig securityConfig = getSecurityConfig();
        AuthenticationContext context = securityConfig.getAuthenticationContext();
        if (context == null) {
            return false;
        }
        RoleMapper roleMapper = securityConfig.getRoleMapper();
        boolean hasRole = roleMapper.hasRole(context.getUser(), getRequest(), requiredRole);
        return hasRole;
    }

    protected boolean isUserAllowedPath(String path) {
        SecurityConfig securityConfig = getSecurityConfig();
        List services = securityConfig.getServices();
        Iterator it = services.iterator();
        while (it.hasNext()) {
            SecurityService service = (SecurityService) it.next();
            if (service instanceof PathService) {
                PathService pathService = (PathService) service;
                Set requiredRoles = pathService.getRequiredRoles(path);
                Iterator itRoles = requiredRoles.iterator();
                while (itRoles.hasNext()) {
                    String role = (String) itRoles.next();
                    if (!isUserInRole(role)) {
                        return false;
                    }
                }
                return true;
            }
        }
        throw new IllegalStateException("Could not find the PathService in Securityconfig");
    }

    protected HttpServletRequest getRequest() {
        return (HttpServletRequest) ((PageContext) getJspContext()).getRequest();
    }

    protected SecurityConfig getSecurityConfig() {
        return (SecurityConfig) getJspContext().getAttribute(SecurityConfig.STORAGE_KEY, PageContext.APPLICATION_SCOPE);
    }

    protected void outputBody() throws IOException, JspException {
        outputBody(getJspContext().getOut());
    }

    protected void outputBody(JspWriter out) throws IOException, JspException {
        getJspBody().invoke(out);
    }
}
