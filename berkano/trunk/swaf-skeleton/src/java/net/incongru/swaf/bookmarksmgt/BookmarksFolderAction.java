package net.incongru.swaf.bookmarksmgt;

import com.opensymphony.xwork.ActionSupport;

/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.3 $
 */
public class BookmarksFolderAction extends ActionSupport {
    private UserBookmarksAccessor userBookmarksAccessor;
    private int newParentBookmarkId;
    private int bookmarkToMove;
    private String userName;

    public BookmarksFolderAction(UserBookmarksAccessor userBookmarksAccessor) {
        this.userBookmarksAccessor = userBookmarksAccessor;
    }
/*TODO
    public String execute() throws EntityNotFoundException {
        BookmarksTree bookmarks = userBookmarksAccessor.getUserBookmarks(userName);
        Bookmark parent = bookmarks.getBookmark(newParentBookmarkId);
        Bookmark bookmark = bookmarks.getBookmark(bookmarkToMove);//[i]);
        bookmarks.moveTo(bookmark, parent);
        userBookmarksAccessor.storeUserBookmarks(userName, bookmarks);
        return SUCCESS;
    }*/

    public int getNewParentBookmarkId() {
        return newParentBookmarkId;
    }

    public void setNewParentBookmarkId(int newParentBookmarkId) {
        this.newParentBookmarkId = newParentBookmarkId;
    }

    public int getBookmarkToMove() {
        return bookmarkToMove;
    }

    public void setBookmarkToMove(int bookmarkToMove) {
        this.bookmarkToMove = bookmarkToMove;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
