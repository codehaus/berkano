package net.incongru.tags.simple;

/**
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class NumericOptionsTagTest extends AbstractSimpleTagTestCase {
    public void testBasic() throws Throwable {
        assertJspAsResource("expected/numopts_basic.html", "numopts_basic.jsp", true);
    }

    public void testStep() throws Throwable {
        assertJspAsResource("expected/numopts_step.html", "numopts_step.jsp", true);
    }

    public void testNegativeValues() throws Throwable {
        assertJspAsResource("expected/numopts_negatives.html", "numopts_negatives.jsp", true);
    }

    public void testSelected() throws Throwable {
        assertJspAsResource("expected/numopts_selected.html", "numopts_selected.jsp", true);
    }
}
