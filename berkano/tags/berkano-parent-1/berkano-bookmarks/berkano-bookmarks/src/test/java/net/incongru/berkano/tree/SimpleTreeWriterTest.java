package net.incongru.berkano.tree;

import junit.framework.TestCase;

import java.io.IOException;
import java.io.StringWriter;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class SimpleTreeWriterTest extends TestCase {
    public void testBasic() throws IOException {
        Tree tree = new Tree();
        tree.addObject("test1", null);
        tree.addObject("test2", null);
        tree.addObject("dir", null);
        tree.addObject("test3", "dir");
        tree.addObject("test4", "dir");
        tree.addObject("test5", null);

        StringWriter sw = new StringWriter();
        TreeWriter treeWriter = new SimpleTreeWriter(2);
        treeWriter.write(tree, sw);

        String expected = "test1\n"
                + "test2\n"
                + "dir\n"
                + "  test3\n"
                + "  test4\n"
                + "test5\n";
        assertEquals(expected, sw.toString());
    }

    public void testClosedNodesShouldNotShowChildren() throws IOException {
        Tree tree = new Tree();
        tree.addObject("test1", null);
        tree.addObject("test2", null);
        tree.addObject("dir", null);
        tree.addObject("test3", "dir");
        tree.addObject("test4", "dir");
        tree.addObject("test5", null);
        TreeNode nodeDir2 = tree.addObject("dir2", null);
        tree.addObject("test23", "dir2");
        tree.addObject("test24", "dir2");
        tree.addObject("test25", null);
        nodeDir2.setIsOpen(false);

        StringWriter sw = new StringWriter();
        TreeWriter treeWriter = new SimpleTreeWriter(2);
        treeWriter.write(tree, sw);

        String expected = "test1\n"
                + "test2\n"
                + "dir\n"
                + "  test3\n"
                + "  test4\n"
                + "test5\n"
                + "dir2\n"
                + "test25\n";
        assertEquals(expected, sw.toString());
    }
}
