package net.incongru.berkano.bookmarks;

/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public interface Highlighter {
    boolean needsHighlight(Bookmark bookmark, String currentPath);
}
