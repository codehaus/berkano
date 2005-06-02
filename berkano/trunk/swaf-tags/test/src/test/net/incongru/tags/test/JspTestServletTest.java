package net.incongru.tags.test;

/**
 * A simple test to test our test framework ;)
 * @author gjoseph
 * @author $ $ (last edit)
 */
public class JspTestServletTest extends JspTestCase {
    private static final String NL = System.getProperty("line.separator");

    public void testAsText() throws Exception {
        assertJspAsText("test !"+NL+"2+2=4"+NL+":)"+NL, "net/incongru/tags/test/test.jsp");
    }

    public void testWithResource() throws Exception {
        assertJspAsResource("net/incongru/tags/test/expected/test.html", "net/incongru/tags/test/test.jsp", false);
    }

    public void testWithResourceIgnoringWhitespace() throws Exception {
        assertJspAsResource("net/incongru/tags/test/expected/test-and-space.html", "net/incongru/tags/test/test-and-space.jsp", true);
    }

}
