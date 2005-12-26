package net.incongru.berkano.bookmarks;

/**
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public interface Highlighter {
    boolean needsHighlight(Bookmark bookmark, String currentPath);
}
