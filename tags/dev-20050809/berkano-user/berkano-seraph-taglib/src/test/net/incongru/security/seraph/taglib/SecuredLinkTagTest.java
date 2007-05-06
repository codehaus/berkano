package net.incongru.security.seraph.taglib;

import net.incongru.tags.test.JspTestCase;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * 
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class SecuredLinkTagTest extends JspTestCase {
    protected static final String PACKAGE = "net/incongru/security/seraph/taglib/";

    public void assertJspAsResource(String resourceName, String jsp, boolean ignoreWhiteSpace) throws IOException, SAXException {
        super.assertJspAsResource(PACKAGE + resourceName, PACKAGE + jsp, ignoreWhiteSpace);
    }

    public void testAll() throws Exception {
        //TODO : setup SecurityConfig !
        //assertJspAsResource("expected.html", "test.jsp", true);
    }

}
