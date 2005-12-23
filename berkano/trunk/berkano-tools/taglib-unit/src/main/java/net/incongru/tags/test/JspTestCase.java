package net.incongru.tags.test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpInternalErrorException;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import junit.framework.TestCase;
import org.apache.log4j.varia.NullAppender;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Extend this class to test jsp results.
 * JSPs and resources names should be relative to src/test/java/.
 * @author gjoseph
 * @author $ $ (last edit)
 */
public abstract class JspTestCase extends TestCase {
    private static final String ABS_PREFIX = "src/test/java/";
    private static final String RES_PREFIX = "/";

    static {
        org.apache.log4j.BasicConfigurator.configure(new NullAppender());
    }

    private WebResponse setupJsp(String jsp) throws Throwable {
        HttpUnitOptions.setScriptingEnabled(false);
        ServletRunner sr = new ServletRunner();
        sr.registerServlet("jsp", JspTestServlet.class.getName());

        ServletUnitClient sc = sr.newClient();
        WebRequest request = new GetMethodWebRequest("http://blah/jsp");
        request.setParameter("jsp", getBaseDir() + ABS_PREFIX + jsp);
        try {
            WebResponse response = sc.getResponse(request);
            assertNotNull("No response received", response);
            return response;
        } catch (HttpInternalErrorException e) {
            //fail(e.getMessage());
            //return null;
            //e.printStackTrace();
            throw e;
        }
    }

    public void assertJspAsText(String expectedContent, String jsp) throws Throwable {
        WebResponse response = setupJsp(jsp);
        assertEquals(expectedContent, response.getText());
    }

    /**
     * Compares the given resource's inputstream with the result jsp's stream, byte by byte.
     * @param resourceName
     * @param jsp
     * @throws IOException
     * @throws SAXException
     */
    public void assertJspAsResource(String resourceName, String jsp, boolean ignoreWhiteSpace) throws Throwable {
        WebResponse res = setupJsp(jsp);
        Reader actualIn = new InputStreamReader(res.getInputStream());
        InputStream expectedInputStream = this.getClass().getResourceAsStream(RES_PREFIX + resourceName);
        assertNotNull("can't get expected resource " + resourceName, expectedInputStream);
        Reader expectedIn = new InputStreamReader(expectedInputStream);
        compareText(expectedIn, actualIn, ignoreWhiteSpace);
    }

    /**
     * Compares two reader, ignoring white lines and trimming all lines.
     * @param expected
     * @param actual
     */
    public void compareText(Reader expected, Reader actual, boolean ignoreWhiteSpace) throws IOException {
        BufferedReader bExpected = new BufferedReader(expected);
        BufferedReader bActual = new BufferedReader(actual);
        StringBuffer actualBuffer = new StringBuffer(); // TODO omg u are teh ugly
        while (true) {
            String expectedLine = ignoreWhiteSpace ? getNextNotEmptyLine(bExpected) : bExpected.readLine();
            String actualLine = ignoreWhiteSpace ? getNextNotEmptyLine(bActual) : bActual.readLine();
            actualBuffer.append(actualLine).append('\n');
            if (expectedLine == null) {
                assertNull(actualLine);
                break;
            }
            try {
                assertEquals(expectedLine, actualLine);
            } catch (junit.framework.AssertionFailedError e) {
                System.err.println("Actual content up until failure:");
                System.err.println(actualBuffer.toString());
                throw e;
            }
        }
    }

    private String getNextNotEmptyLine(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (!line.equals("")) {
                return line;
            }
        }
        return null;
    }

    /**
     * Figures out the current building dir, because the jsp path
     * must be relative to that, for some reason. Since when building
     * from maven with the reactor, the current directory is *not*
     * the project's basedir, we're relying on the "basedire" system
     * property set by maven. If the property's not there, we're
     * assuming the current dir was set properly.
     */
    private String getBaseDir() {
        String basedirProp = System.getProperty("basedir");
        String currentPath = new File("").getAbsolutePath();
        if (basedirProp != null) {
            if (!basedirProp.startsWith(currentPath)) {
                fail("Can't figure out the current directory(basedirProp=" + basedirProp + ",currentPath=" + currentPath + ")");
            }

            String res = basedirProp.substring(currentPath.length()) + File.separatorChar;
            res = res.substring(res.startsWith(File.separator) ? 1 : 0);
            return res;
        } else {
            return "";
        }
    }
}
