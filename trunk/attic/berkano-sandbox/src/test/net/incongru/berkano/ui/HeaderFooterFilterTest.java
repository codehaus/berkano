package net.incongru.berkano.ui;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import junit.framework.TestCase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class HeaderFooterFilterTest extends TestCase {
    protected String doTest(String header, String footer) throws Exception {
        ServletRunner sr = new ServletRunner(getWebXml(header, footer));
        sr.registerServlet("empty", HeaderFooterFilterTest.EmptyServlet.class.getName());
        sr.registerServlet("header", HeaderFooterFilterTest.HeaderServlet.class.getName());
        sr.registerServlet("footer", HeaderFooterFilterTest.FooterServlet.class.getName());
        ServletUnitClient sc = sr.newClient();
        WebRequest request = new GetMethodWebRequest("http://incongru.net/empty");
        WebResponse response = sc.getResponse(request);
        return response.getText();
    }

    public void testHeaderAndFooterShouldBeIncluded() throws Exception {
        String res = doTest("/header", "/footer");
        assertEquals("-header--empty--footer-", res);
    }

    public void testUnspecifiedHeaderShouldStillIncludeFooter() throws Exception {
        String res = doTest(null, "/footer");
        assertEquals("-empty--footer-", res);
    }

    public void testUnspecifiedFooterShouldStillIncludeHeader() throws Exception {
        String res = doTest("/header", null);
        assertEquals("-header--empty-", res);
    }

    public void testUnspecifiedHeaderAndFootersShouldStillLeaveContent() throws Exception {
        String res = doTest(null, null);
        assertEquals("-empty-", res);
    }

    private InputStream getWebXml(String header, String footer) {
        return new ByteArrayInputStream((
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "\n" +
                "<web-app version=\"2.4\"\n" +
                "    xmlns=\"http://java.sun.com/xml/ns/j2ee\"\n" +
                "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "    xsi:schemaLocation=\"http://java.sun.com/xml/ns/j2ee http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd\">\n" +
                "\n" +
                "    <filter>\n" +
                "         <filter-name>header-footer</filter-name>\n" +
                "         <filter-class>net.incongru.berkano.ui.HeaderFooterFilter</filter-class>\n" +
                (header != null ?
                "         <init-param>\n" +
                "             <param-name>header</param-name>\n" +
                "             <param-value>" + header + "</param-value>\n" +
                "         </init-param>\n"
                : "") +
                (footer != null ?
                "         <init-param>\n" +
                "             <param-name>footer</param-name>\n" +
                "             <param-value>" + footer + "</param-value>\n" +
                "         </init-param>\n"
                : "") +
                "     </filter>\n" +
                "\n" +
                "    <filter-mapping>\n" +
                "        <filter-name>header-footer</filter-name>\n" +
                "        <url-pattern>/*</url-pattern>\n" +
                "    </filter-mapping>\n" +
                "\n" +
                "</web-app>").getBytes());
    }

    public static class EmptyServlet extends HttpServlet {
        protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
            res.getWriter().write("-empty-");
        }
    }

    public static class HeaderServlet extends HttpServlet {
        protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
            res.getWriter().write("-header-");
        }
    }

    public static class FooterServlet extends HttpServlet {
        protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
            res.getWriter().write("-footer-");
        }
    }
}
