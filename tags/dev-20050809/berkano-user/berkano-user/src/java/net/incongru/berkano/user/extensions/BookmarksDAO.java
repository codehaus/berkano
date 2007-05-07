package net.incongru.berkano.user.extensions;

import net.incongru.berkano.bookmarks.BookmarksTree;
import net.incongru.berkano.user.User;
import net.incongru.berkano.user.UserDAO;

/**
 * Defines a strategy for serializing bookmarks.
 * (basic implementations will be java serialization and xstream)
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class BookmarksDAO {
    private UserDAO userDAO;
    private UserPropertyAccessor userPropertyAccessor;

    public BookmarksDAO(UserDAO userDAO, UserPropertyAccessor userPropertyAccessor) {
        this.userDAO = userDAO;
        this.userPropertyAccessor = userPropertyAccessor;
    }

    public void storeUserBookmarks(User user, BookmarksTree bookmarksTree) {
        userDAO.addProperty(user, "berkano.bookmarks", bookmarksTree);
    }

    public BookmarksTree getUserBookmarks(User user) {
        return (BookmarksTree) userPropertyAccessor.getFirstValue(user, "berkano.bookmarks");
    }
}