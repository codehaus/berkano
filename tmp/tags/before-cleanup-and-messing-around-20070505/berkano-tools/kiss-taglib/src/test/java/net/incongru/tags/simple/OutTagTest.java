package net.incongru.tags.simple;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class OutTagTest extends AbstractSimpleTagTestCase {
    public void testOut() throws Throwable {
        assertJspAsResource("expected/out.html", "out.jsp", true);
    }
}
