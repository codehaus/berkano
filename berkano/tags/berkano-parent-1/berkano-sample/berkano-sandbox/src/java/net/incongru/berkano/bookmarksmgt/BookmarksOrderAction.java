package net.incongru.berkano.bookmarksmgt;

import com.opensymphony.xwork.ActionSupport;
import net.incongru.berkano.bookmarks.BookmarksTree;

/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.4 $
 */
public class BookmarksOrderAction extends ActionSupport {
    private UserBookmarksAccessor userBookmarksAccessor;
    private String userName;
    private int bookmarkId;
    private BookmarksTree bookmarksTree;

    public BookmarksOrderAction(UserBookmarksAccessor userBookmarksAccessor) {
        this.userBookmarksAccessor = userBookmarksAccessor;
    }

    public String up() {
        return changeBookmarkOrder(true);
    }

    public String down() {
        return changeBookmarkOrder(false);
    }

    private String changeBookmarkOrder(boolean up) {
        //this.bookmarksTree = userBookmarksAccessor.getUserBookmarks(userName);
        /*TODO :
        this.bookmarksTree = userBookmarksAccessor.getSelfBookmarks();
        Bookmark bookmark = bookmarksTree.getBookmark(bookmarkId);

        bookmarksTree.changeOrder(bookmark, up);
        userBookmarksAccessor.storeUserBookmarks(userName, bookmarksTree);*/
        return SUCCESS;
    }

    public int getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(int bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
