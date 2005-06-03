package org.nanocontainer.nanowar.servlet;

import org.nanocontainer.nanowar.ServletContainerFinder;
import org.picocontainer.PicoContainer;
import org.picocontainer.PicoInitializationException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A javax.servlet.Filter which delegates to another Filter
 * which is DI'd by picocontainer.
 *
 * <p>The delegate Filter must be registered with a String key specified
 * as the "delegate-key" init-param of this Filter, or a Class name
 * specified as the "delegate-class" init-param of this Filter.</p>
 *
 * <p>The initialization is done lazily, but you must set a <code>init-type</code> init-param
 * to control it. "context" will call init() on the filter only once, "request"
 * will re-init it at every request, "never" will never init it. The default is context.
 * </p>
 *
 * <p>The lookup in the picocontainer is by default done for each request, but you
 * can control that behaviour with the <code>lookup-only-once</code> init parameter.
 * If set to "true", then PicoFilterProxy will only lookup your delegate filter
 * at the first request. (Be aware that any dependency on your filter, in this setup, will stay
 * referenced by your filter for its whole lifetime, eventhough this dependency
 * might have been set up at request level in your composer!)
 * </p>
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class PicoFilterProxy implements Filter {
    private static final int INIT_CONTEXT = 1;
    private static final int INIT_REQUEST = 4;
    private static final int INIT_NEVER = 7;
    private static final Map contextTypes = new HashMap();

    static {
        contextTypes.put(null, new Integer(INIT_CONTEXT));
        contextTypes.put("context", new Integer(INIT_CONTEXT));
        contextTypes.put("request", new Integer(INIT_REQUEST));
        contextTypes.put("never", new Integer(INIT_NEVER));
    };

    private int initType;
    private boolean lookupOnlyOnce;
    private FilterConfig filterConfig;
    private Filter delegate;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        String initTypeStr = filterConfig.getInitParameter("init-type");
        Integer i = (Integer) contextTypes.get(initTypeStr);
        if (i == null) {
            throw new ServletException("Valid values for init-type are " + contextTypes.keySet());
        }
        initType = i.intValue();

        String lookupOnlyOneStr = filterConfig.getInitParameter("lookup-only-once");
        lookupOnlyOnce = "true".equals(lookupOnlyOneStr);
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        if (delegate == null || !lookupOnlyOnce) {
            lookupDelegate((HttpServletRequest) req);
            if (initType == INIT_CONTEXT) {
                initDelegate();
            }
        }
        if (initType == INIT_REQUEST) {
            initDelegate();
        }
        delegate.doFilter(req, res, filterChain);
    }

    public void destroy() {
        if (delegate != null) {
            delegate.destroy();
        }
    }

    private void lookupDelegate(HttpServletRequest req) throws ServletException {
        ServletContainerFinder picoFinder = new ServletContainerFinder();
        PicoContainer pico = picoFinder.findContainer(req);

        String delClassName = filterConfig.getInitParameter("delegate-class");
        String delKey = filterConfig.getInitParameter("delegate-key");
        if (delClassName != null) {
            try {
                // todo: either remove this or user a better class loading mechanism
                Class delClass = Class.forName(delClassName);
                delegate = (Filter) pico.getComponentInstanceOfType(delClass);
            } catch (ClassNotFoundException e) {
                throw new PicoInitializationException("Can not load " + delClassName + " : " + e.getMessage(), e);
            }
        } else if (delKey != null) {
            delegate = (Filter) pico.getComponentInstance(delKey);
        } else {
            throw new PicoInitializationException("You must specify one of delegate-class or delegate-key in the filter config, and you must register the corresponding component in your picocontainer");
        }

        if (delegate == null) {
            throw new PicoInitializationException("Could not find component for " + delClassName + delKey);
        }
    }

    private void initDelegate() throws ServletException {
        if (delegate == null) {
            throw new IllegalStateException("Delegate filter was not set up!");
        }
        delegate.init(filterConfig);
    }
}
