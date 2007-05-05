package net.incongru.tags.test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A simple servlet to help us test jsp tags! :)
 * Processes requests with get method. Expects a parameter with name "jsp" in the request which gives a jsp name.
 *
 * @author gjoseph
 * @author $ $ (last edit)
 */
public class JspTestServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String jsp = req.getParameter("jsp");
        if (jsp == null) {
            throw new ServletException("Missing parameter: jsp");
        }
        RequestDispatcher rd = req.getRequestDispatcher(jsp);
        if (rd == null) {
            throw new ServletException("Could not get a RequestDispatcher for: " + jsp);
        }
        rd.include(req, res);
    }
}
