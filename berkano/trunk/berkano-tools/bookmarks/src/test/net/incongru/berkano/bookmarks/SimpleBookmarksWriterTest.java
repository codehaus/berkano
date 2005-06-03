package net.incongru.berkano.bookmarks;

import java.io.IOException;
import java.io.StringWriter;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class SimpleBookmarksWriterTest extends AbstractBookmarksTestCase {

    public void testSimpleWriter() throws IOException {
        BookmarksTree tree = setupSimpleTree();
        SimpleBookmarksWriter simpleBookmarksWriter = new SimpleBookmarksWriter();
        StringWriter sw = new StringWriter();
        simpleBookmarksWriter.write(tree, sw);

        String expected = "name1 (url1)\n"
                + "name2 (url2)\n"
                + "dir (null)\n"
                + "  name3 (url3)\n"
                + "  subdir (null)\n"
                + "    name4 (url4)\n"
                + "    name5 (url5)\n"
                + "  name6 (url6)\n"
                + "name7 (url7)\n";
        assertEquals(expected, sw.toString());
    }
}
