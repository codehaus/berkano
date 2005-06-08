package net.incongru.tags.simple;

import net.incongru.tags.test.JspTestCase;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public abstract class AbstractSimpleTagTestCase extends JspTestCase {
    public void assertJspAsResource(String resourceName, String jsp, boolean ignoreWhiteSpace) throws IOException, SAXException {
        super.assertJspAsResource("net/incongru/tags/simple/"+resourceName, "net/incongru/tags/simple/"+jsp, ignoreWhiteSpace);
    }

    public void assertJspAsText(String expectedContent, String jsp) throws IOException, SAXException {
        super.assertJspAsText(expectedContent, "net/incongru/tags/simple/"+jsp);
    }
}
