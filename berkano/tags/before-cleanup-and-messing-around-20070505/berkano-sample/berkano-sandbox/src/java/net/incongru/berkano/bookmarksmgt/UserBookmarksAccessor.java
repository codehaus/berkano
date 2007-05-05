package net.incongru.berkano.bookmarksmgt;

import com.atlassian.seraph.auth.AuthenticationContext;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionSupport;
import net.incongru.berkano.bookmarks.Bookmark;
import net.incongru.berkano.bookmarks.BookmarksTree;
import net.incongru.berkano.bookmarks.DefaultBookmark;
import net.incongru.berkano.user.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class UserBookmarksAccessor extends ActionSupport {
    protected static final String USER_BOOKMARKS = "berkano.user.bookmarks";
    protected static final String DEFAULT_BOOKMARKS = "berkano.app.default.bookmarks";

    private AuthenticationContext authenticationContext;
    private BookmarksDAO bookmarksDAO;

    public UserBookmarksAccessor(AuthenticationContext authenticationContext, BookmarksDAO bookmarksDAO) {
        this.authenticationContext = authenticationContext;
        this.bookmarksDAO = bookmarksDAO;
    }

    /**
     * If user is not logged in: return default bookmarks.
     * If user is logged in: get bookmarks from its session. If its not set,
     * then get it from its profile. If its not set there either, return
     * default bookmarks.
     */
    public BookmarksTree getSelfBookmarks() {
        User user = (User) authenticationContext.getUser();
        if (user == null) {
            return getDefaultBookmarks();
        }

        Map session = ActionContext.getContext().getSession();
        BookmarksTree bookmarks = (BookmarksTree) session.get(USER_BOOKMARKS);
        if (bookmarks == null) {
            bookmarks = bookmarksDAO.getUserBookmarks(user);
            if (bookmarks == null) {
                bookmarks = getDefaultBookmarks();
            }
            session.put(USER_BOOKMARKS, bookmarks);
        }
        return bookmarks;
    }

    private BookmarksTree getDefaultBookmarks() {
        Map app = ActionContext.getContext().getApplication();
        BookmarksTree bookmarks = (BookmarksTree) app.get(DEFAULT_BOOKMARKS);
        if (bookmarks == null) {
            bookmarks = loadDefaultBookmarks();
            app.put(DEFAULT_BOOKMARKS, bookmarks);
        }
        return bookmarks;
    }

    protected BookmarksTree loadDefaultBookmarks() {
        InputStream in = getClass().getResourceAsStream("/bookmarks.default");
        if (in == null) {
            throw new RuntimeException("Can't open /bookmarks.default");
        }
        Properties p = new Properties();
        try {
            p.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Can't load /bookmarks.default : " + e.getMessage(), e);
        }
        BookmarksTree tree = new BookmarksTree();
        Iterator it = p.entrySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Bookmark b = new DefaultBookmark(i, (String) entry.getValue(), (String) entry.getKey(), null, null);
            tree.add(b, null);
            i++;
        }
        return tree;
    }
}
