package net.incongru.swaf.bookmarks;

import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public interface BookmarksTreeWriter {
    public void write(BookmarksTree tree, Writer out) throws IOException;
}
