package net.incongru.berkano.bookmarks;

import java.io.IOException;
import java.io.StringWriter;


/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.3 $
 */
public class BookmarksTreeTest extends AbstractBookmarksTestCase {
    public void testGetBookmark() {
        BookmarksTree tree = setupSimpleTree();
        Bookmark bm1 = tree.getBookmark(1);
        assertEquals(1, bm1.getId());
        assertEquals("url1", bm1.getLink());
        assertEquals("name1", bm1.getName());
        assertEquals(null, bm1.getDescription());
        Bookmark bm3 = tree.getBookmark(3);
        assertEquals(3, bm3.getId());
        assertEquals("url3", bm3.getLink());
        assertEquals("name3", bm3.getName());
        assertEquals("and a desc for link 3", bm3.getDescription());
    }

    public void testMoveRemove() {
        BookmarksTree t = setupSimpleTree();
        try {
            t.remove(notadded);
            fail();
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().startsWith("Can't find parent node for "));
        }
        try {
            t.moveTo(notadded2, dir);
            fail();
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().startsWith("Can't find parent node for "));
        }
//        assertTrue(t.remove(b6));
//        assertTrue(t.remove(subdir));
//        assertFalse(t.remove(b4));
//        assertFalse(t.remove(b5));
    }

    public void testMoveShouldNotFuckUpTheTree() throws IOException {
        String treeStateBefore = "" +
                "name1 (url1)\n" +
                "name2 (url2)\n" +
                "dir (null)\n" +
                "  name3 (url3)\n" +
                "  subdir (null)\n" +
                "    name4 (url4)\n" +
                "    name5 (url5)\n" +
                "  name6 (url6)\n" +
                "name7 (url7)\n";
        String treeStateAfter = "" +
                "name1 (url1)\n" +
                "  dir (null)\n" +
                "    name3 (url3)\n" +
                "    subdir (null)\n" +
                "      name4 (url4)\n" +
                "      name5 (url5)\n" +
                "    name6 (url6)\n" +
                "name2 (url2)\n" +
                "name7 (url7)\n";
        BookmarksTree t = setupSimpleTree();
        SimpleBookmarksWriter w = new SimpleBookmarksWriter();
        StringWriter out = new StringWriter();
        w.write(t, out);
        assertEquals(treeStateBefore, out.toString());
        t.moveTo(dir, b1);
        StringWriter out2 = new StringWriter();
        w.write(t, out2);
        assertEquals(treeStateAfter, out2.toString());
    }
}
