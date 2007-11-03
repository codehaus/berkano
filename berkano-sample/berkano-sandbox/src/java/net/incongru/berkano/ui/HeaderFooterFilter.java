package net.incongru.berkano.ui;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This filter is probably not optimal, because the complete response
 * is held in a byte[] before the header&footer are included. Has to
 * do this trick because webwork forwards the requests to the jsp
 * views instead of including them. Possible solutions are to use
 * velocity or freemarker, for either the header/footer or the
 * webwork views.
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.4 $
 */
public class HeaderFooterFilter implements Filter {
    private String header;
    private String footer;

    public void init(FilterConfig filterConfig) throws ServletException {
        header = filterConfig.getInitParameter("header");
        footer = filterConfig.getInitParameter("footer");
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        final CharArrayServletResponseWrapper wrapper = new CharArrayServletResponseWrapper((HttpServletResponse) res);

        filterChain.doFilter(req, wrapper);

        final String responseStr = wrapper.toString();

        try {
            if (header != null) {
                req.getRequestDispatcher(header).include(req, res);
            }
            res.getWriter().write(responseStr);
            if (footer != null) {
                req.getRequestDispatcher(footer).include(req, res);
            }
        } catch (IllegalStateException e) {
            e.printStackTrace(System.err);
            
        }
    }

    public void destroy() {
    }

    private class CharArrayServletResponseWrapper extends HttpServletResponseWrapper {
        final private CharArrayWriter writer = new CharArrayWriter();

        public CharArrayServletResponseWrapper(final HttpServletResponse response) {
            super(response);
        }

        public final PrintWriter getWriter() {
            return new PrintWriter(writer);
        }

        public final String toString() {
            return writer.toString();
        }
    }
}