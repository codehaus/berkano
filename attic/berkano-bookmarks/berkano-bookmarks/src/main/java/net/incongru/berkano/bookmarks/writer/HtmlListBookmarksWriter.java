package net.incongru.berkano.bookmarks.writer;

import net.incongru.berkano.bookmarks.Bookmark;
import net.incongru.berkano.bookmarks.BookmarksTree;
import net.incongru.berkano.bookmarks.MenuTranslator;
import net.incongru.berkano.tree.SimpleTreeWriter;
import net.incongru.berkano.tree.TreeWriter;

import java.io.IOException;
import java.io.Writer;

/**
 * This class is not threadsafe : indentation might be corrupted if
 * we have multiple concurrent calls on the same instance.
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.3 $
 */
public class HtmlListBookmarksWriter extends SimpleBookmarksWriter {
    private final MenuTranslator translator;
    private int lastIndentLevel;

    public HtmlListBookmarksWriter(MenuTranslator translator) {
        this.translator = translator;
    }

    public void write(BookmarksTree bookmarksTree, Writer out) throws IOException {
        lastIndentLevel = 0;
        out.write("<ul>\n");
        super.write(bookmarksTree, out);
        for (; lastIndentLevel >= 0; lastIndentLevel--) {
            out.write("</ul>\n");
        }
    }

    protected void writeBookmark(Bookmark bookmark, Writer out) throws IOException {
        final String name = bookmark.getName();
        final String desc = bookmark.getDescription();
        out.write("<li>");
        out.write("<a href=\"");
        out.write(bookmark.getLink());
        out.write("\" title=\"");
        out.write(translator.translate(desc != null ? desc : name));
        out.write("\">");
        out.write(translator.translate(name));
        out.write("</a>");
        out.write("</li>");
    }

    protected TreeWriter getTreeWriter() {
        return new SimpleTreeWriter() {
            protected void writeObject(Object nodeObject, Writer out) throws IOException {
                Bookmark bookmark = (Bookmark) nodeObject;
                writeBookmark(bookmark, out);
            }

            protected void indent(Writer out, int level) throws IOException {
                htmlListIndent(out, level);
                //super.indent(out, level); todo: no indentation for now
            }
        };
    }

    protected void htmlListIndent(Writer out, int level) throws IOException {
        if (level > lastIndentLevel) {
            out.write("<ul>\n");
        } else if (level < lastIndentLevel) {
            out.write("</ul>\n");
        }
        lastIndentLevel = level;
    }

}
