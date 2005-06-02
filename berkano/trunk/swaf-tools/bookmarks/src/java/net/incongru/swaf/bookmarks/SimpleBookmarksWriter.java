package net.incongru.swaf.bookmarks;

import net.incongru.swaf.tree.SimpleTreeWriter;
import net.incongru.swaf.tree.Tree;
import net.incongru.swaf.tree.TreeWriter;

import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class SimpleBookmarksWriter implements BookmarksTreeWriter {
    public void write(BookmarksTree bookmarksTree, Writer out) throws IOException {
        TreeWriter simpleTreeWriter = getTreeWriter();
        Tree tree = bookmarksTree.getTree();
        simpleTreeWriter.write(tree, out);
    }

    protected TreeWriter getTreeWriter() {
        return new SimpleTreeWriter() {
            protected void writeObject(Object nodeObject, Writer out) throws IOException {
                Bookmark bookmark = (Bookmark) nodeObject;
                writeBookmark(bookmark, out);
            }
        };
    }

    protected void writeBookmark(Bookmark bookmark, Writer out) throws IOException {
        out.write(bookmark.getDescription());
        out.write(" (");
        out.write(bookmark.getLink());
        out.write(")");
    }


}