package net.incongru.berkano.bookmarks.writer;

import net.incongru.berkano.bookmarks.BookmarksTree;

import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public interface BookmarksTreeWriter {
    public void write(BookmarksTree tree, Writer out) throws IOException;
}
