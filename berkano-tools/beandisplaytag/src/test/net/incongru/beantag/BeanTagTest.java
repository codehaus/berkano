package net.incongru.beantag;

import com.meterware.httpunit.HttpInternalErrorException;
import net.incongru.tags.test.JspTestCase;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * @author gjoseph
 * @author $ $ (last edit)
 */
public class BeanTagTest extends JspTestCase {
    public void assertJspAsResource(String resourceName, String jsp, boolean ignoreWhiteSpace) throws IOException, SAXException {
        super.assertJspAsResource("net/incongru/beantag/" + resourceName, "net/incongru/beantag/" + jsp, ignoreWhiteSpace);
    }

    public void testNoProperties() throws Exception {
        assertJspAsResource("expected/noproperties.html", "noproperties.jsp", true);
    }

    public void testNoPropertiesAndBeanIsMap() throws Exception {
        assertJspAsResource("expected/noproperties_map.html", "noproperties_map.jsp", true);
    }

    /**
     * Tests absence of label both at bean level and at property level.
     */
    public void testBasicNoLabel() throws Exception {
        assertJspAsResource("expected/basic-nolabel.html", "basic-nolabel.jsp", true);
    }

    public void testBasicWithLabel() throws Exception {
        assertJspAsResource("expected/basic.html", "basic.jsp", true);
    }

    public void testBasicUsingBean() throws Exception {
        assertJspAsResource("expected/basic.html", "basic-bean.jsp", true);
    }

    public void testMissingAttributeShouldFail() throws Exception {
        try {
            assertJspAsResource("", "shouldfail.jsp", true);
            fail("defining both attributes bean and name should fail");
        } catch (HttpInternalErrorException ex) { // this is what httpunit throws instead of the JspException
        }
        try {
            assertJspAsResource("", "shouldfail2.jsp", true);
            fail("defining none of attributes bean and name should fail");
        } catch (HttpInternalErrorException ex) { // this is what httpunit throws instead of the JspException
        }
    }

    public void testWithWriter() throws Exception {
        assertJspAsResource("expected/writer.html", "writer.jsp", true);
    }

    public void testWithDecorator() throws Exception {
        assertJspAsResource("expected/decorator.html", "decorator.jsp", true);
    }

    public void testNulls() throws Exception {
        assertJspAsResource("expected/nulls.html", "nulls.jsp", true);
    }

    public void testCss() throws Exception {
        assertJspAsResource("expected/css.html", "css.jsp", true);
    }

    public void testCssOnColumns() throws Exception {
        assertJspAsResource("expected/css_columns.html", "css_columns.jsp", true);
    }

    public void testNestedPropertiesShouldBeSupported() throws Exception {
        assertJspAsResource("expected/nestedproperty.html", "nestedproperty.jsp", true);
    }

    public void testsWithConditions() throws Exception {
        assertJspAsResource("expected/conditions.html", "conditions.jsp", true);
    }

    public void testsWithContent() throws Exception {
        assertJspAsResource("expected/content.html", "content.jsp", true);
    }

    public void testValueCanBeExposed() throws Exception {
        assertJspAsResource("expected/content_valueisexposed.html", "content_valueisexposed.jsp", true);
    }

    public void testSplit() throws Exception {
        assertJspAsResource("expected/split.html", "split.jsp", true);
    }

}
