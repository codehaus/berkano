package net.incongru.berkano.bookmarks;

import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class XmlBookmarksTreeReaderTest extends TestCase {
    private static final String XML_SIMPLE_FLAT = "" +
            "<bookmarks-tree>" +
            "  <bookmark id=\"1\" href=\"foo\" name=\"foo.link\"/>" +
            "  <bookmark id=\"2\" href=\"bar\" name=\"bar.link\">some link to bar</bookmark>" +
            "</bookmarks-tree>";

    public void testCanReadSimpleFlatTree() throws Exception {
        BookmarksTreeReader reader = new XmlBookmarksTreeReader(new ByteArrayInputStream(XML_SIMPLE_FLAT.getBytes()));
        BookmarksTree tree = reader.readBookmarksTree();
        Bookmark bm1 = tree.getBookmark(1);
        Bookmark bm2 = tree.getBookmark(2);
        assertEquals(1, bm1.getId());
        assertEquals("foo", bm1.getLink());
        assertEquals("foo.link", bm1.getName());
        assertEquals(null, bm1.getDescription());
        assertEquals(2, bm2.getId());
        assertEquals("bar", bm2.getLink());
        assertEquals("bar.link", bm2.getName());
        assertEquals("some link to bar", bm2.getDescription());
    }

    private static final String XML_COMPLEX_TREE = "" +
            "<bookmarks-tree>\n" +
            "    <bookmark id=\"1\" href=\"foo\" name=\"foo.link\"/>\n" +
            "    <bookmark id=\"2\" href=\"bar\" name=\"bar.link\">some link to bar</bookmark>\n" +
            "    <bookmark id=\"3\" href=\"baz\" name=\"baz.link\">has a sub menu\n" +
            "        <bookmark id=\"4\" href=\"baz-sub-1\" name=\"baz-sub-1\"/>\n" +
            "        <bookmark id=\"5\" href=\"baz-sub-2\" name=\"baz-sub-2\"/>\n" +
            "        <bookmark id=\"6\" href=\"baz-sub-3\" name=\"baz-sub-3\">\n" +
            "            <bookmark id=\"16\" href=\"baz-sub-sub-1\" name=\"baz-sub-sub-1\"/>\n" +
            "            <bookmark id=\"17\" href=\"baz-sub-sub-2\" name=\"baz-sub-sub-2\"/>\n" +
            "            <bookmark id=\"18\" href=\"baz-sub-sub-3\" name=\"baz-sub-sub-3\"/>\n" +
            "        </bookmark>\n" +
            "        <bookmark id=\"7\" href=\"baz-sub-4\" name=\"baz-sub-4\"/>\n" +
            "    </bookmark>\n" +
            "</bookmarks-tree>";

    public void testCanReadMoreComplexTreeWithNestedBookmarks() throws Exception {
        BookmarksTreeReader reader = new XmlBookmarksTreeReader(new ByteArrayInputStream(XML_COMPLEX_TREE.getBytes()));
        BookmarksTree tree = reader.readBookmarksTree();
        BookmarksTreeWriter writer = new HtmlListBookmarksWriter();
        StringWriter out = new StringWriter();
        writer.write(tree, out);
        assertEquals("<ul>\n" +
                "<li><a href=\"foo\" title=\"foo.link\">foo.link</a></li>\n" +
                "<li><a href=\"bar\" title=\"some link to bar\">bar.link</a></li>\n" +
                "<li><a href=\"baz\" title=\"has a sub menu\">baz.link</a></li>\n" +
                "<ul>\n" +
                "<li><a href=\"baz-sub-1\" title=\"baz-sub-1\">baz-sub-1</a></li>\n" +
                "<li><a href=\"baz-sub-2\" title=\"baz-sub-2\">baz-sub-2</a></li>\n" +
                "<li><a href=\"baz-sub-3\" title=\"baz-sub-3\">baz-sub-3</a></li>\n" +
                "<ul>\n" +
                "<li><a href=\"baz-sub-sub-1\" title=\"baz-sub-sub-1\">baz-sub-sub-1</a></li>\n" +
                "<li><a href=\"baz-sub-sub-2\" title=\"baz-sub-sub-2\">baz-sub-sub-2</a></li>\n" +
                "<li><a href=\"baz-sub-sub-3\" title=\"baz-sub-sub-3\">baz-sub-sub-3</a></li>\n" +
                "</ul>\n" +
                "<li><a href=\"baz-sub-4\" title=\"baz-sub-4\">baz-sub-4</a></li>\n" +
                "</ul>\n" +
                "</ul>\n", out.toString());
    }

}
