package net.incongru.berkano.bookmarks;

/**
 * A simple interface to allow hiding bookmarks (menu items) to
 * users who could not access the underlying resources.
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public interface SecurityAdapter {
    boolean isAllowed(Bookmark bookmark);
}
