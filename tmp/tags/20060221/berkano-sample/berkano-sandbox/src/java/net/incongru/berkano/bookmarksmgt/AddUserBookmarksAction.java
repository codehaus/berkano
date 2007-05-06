package net.incongru.berkano.bookmarksmgt;

import com.opensymphony.xwork.ActionSupport;
import net.incongru.berkano.bookmarks.Bookmark;
import net.incongru.berkano.bookmarks.BookmarksTree;
import net.incongru.berkano.bookmarks.ExternalBookmark;
import net.incongru.berkano.user.UserDAO;
import net.incongru.berkano.user.User;
import net.incongru.berkano.user.UnknownUserException;

/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.5 $
 */
public class AddUserBookmarksAction extends ActionSupport {
    private UserDAO userDAO;
    private BookmarksDAO bookmarksDAO;

    private Long userId;
    private int bookmarkId;
    private String url;
    private String description;
    private String longDescription;
    private Bookmark bookmark;

    public AddUserBookmarksAction(UserDAO userDAO, BookmarksDAO bookmarksDAO) {
        this.userDAO = userDAO;
        this.bookmarksDAO = bookmarksDAO;
    }

    // todo : validation
    public String execute() throws UnknownUserException {
        if (bookmarkId <= 0 || url == null || userId == null) {
            return ERROR;
        }
        if (description == null) {
            description = url;
        }

        if (longDescription == null) {
            longDescription = description;
        }

        User user = userDAO.getUserById(userId);
        BookmarksTree bookmarks = bookmarksDAO.getUserBookmarks(user);
        bookmark = new ExternalBookmark(bookmarkId, url, description, longDescription);
        bookmarks.add(bookmark, null);
        throw new IllegalStateException("to be reimplemented");
        //UserMutator userMutator = userDAO.getUserMutator(user);
        //bookmarksDAO.storeUserBookmarks(userMutator, bookmarks);
        //return SUCCESS;
    }

    public Bookmark getBookmark() {
        return bookmark;
    }

    public int getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(int bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }
}
