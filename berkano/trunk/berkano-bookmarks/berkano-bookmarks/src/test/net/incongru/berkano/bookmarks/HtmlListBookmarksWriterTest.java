package net.incongru.berkano.bookmarks;

import java.io.IOException;
import java.io.StringWriter;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.3 $
 */
public class HtmlListBookmarksWriterTest extends AbstractBookmarksTestCase {
    public void testWriter() throws IOException {
        BookmarksTree tree = setupSimpleTree();
        HtmlListBookmarksWriter bookmarksWriter = new HtmlListBookmarksWriter();
        StringWriter sw = new StringWriter();
        bookmarksWriter.write(tree, sw);

        String expected = "<ul>\n" +
                "<li><a href=\"url1\" title=\"name1\">name1</a></li>\n" +
                "<li><a href=\"url2\" title=\"name2\">name2</a></li>\n" +
                "<li><a href=\"null\" title=\"dir\">dir</a></li>\n" +
                "<ul>\n" +
                "<li><a href=\"url3\" title=\"and a desc for link 3\">name3</a></li>\n" +
                "<li><a href=\"null\" title=\"subdir\">subdir</a></li>\n" +
                "<ul>\n" +
                "<li><a href=\"url4\" title=\"name4\">name4</a></li>\n" +
                "<li><a href=\"url5\" title=\"name5\">name5</a></li>\n" +
                "</ul>\n" +
                "<li><a href=\"url6\" title=\"name6\">name6</a></li>\n" +
                "</ul>\n" +
                "<li><a href=\"url7\" title=\"name7\">name7</a></li>\n" +
                "</ul>\n";
        assertEquals(expected, sw.toString());
    }

    public void testWriterWithOrderChange() throws IOException {
        BookmarksTree tree = setupSimpleTree();
        tree.changeOrder(b2, true);
        tree.changeOrder(dir, false);
        HtmlListBookmarksWriter bookmarksWriter = new HtmlListBookmarksWriter();
        StringWriter sw = new StringWriter();
        bookmarksWriter.write(tree, sw);

        String expected = "<ul>\n" +
                "<li><a href=\"url2\" title=\"name2\">name2</a></li>\n" +
                "<li><a href=\"url1\" title=\"name1\">name1</a></li>\n" +
                "<li><a href=\"url7\" title=\"name7\">name7</a></li>\n" +
                "<li><a href=\"null\" title=\"dir\">dir</a></li>\n" +
                "<ul>\n" +
                "<li><a href=\"url3\" title=\"and a desc for link 3\">name3</a></li>\n" +
                "<li><a href=\"null\" title=\"subdir\">subdir</a></li>\n" +
                "<ul>\n" +
                "<li><a href=\"url4\" title=\"name4\">name4</a></li>\n" +
                "<li><a href=\"url5\" title=\"name5\">name5</a></li>\n" +
                "</ul>\n" +
                "<li><a href=\"url6\" title=\"name6\">name6</a></li>\n" +
                "</ul>\n" +
                "</ul>\n";
        assertEquals(expected, sw.toString());
    }
}
