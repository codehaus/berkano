package org.nanocontainer.nanowar.servlet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class FooFilter implements Filter {
    private static int initCounter;
    private Foo foo;

    public FooFilter(Foo foo) {
        this.foo = foo;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        initCounter++;
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        PrintWriter w = res.getWriter();
        w.write(foo != null ? foo.peek() : "foo was null for peek");
        filterChain.doFilter(req, res);
        w.write(foo != null ? foo.poke() : "foo was null for poke");
    }

    public void destroy() {
    }

    public static void resetInitCounter() {
        initCounter = 0;
    }

    public static int getInitCounter() {
        return initCounter;
    }

}