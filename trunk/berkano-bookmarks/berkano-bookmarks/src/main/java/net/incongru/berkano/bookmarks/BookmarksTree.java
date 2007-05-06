package net.incongru.berkano.bookmarks;

import net.incongru.berkano.tree.Tree;
import net.incongru.berkano.tree.TreeIterator;
import net.incongru.berkano.tree.TreeNode;

import java.io.Serializable;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.5 $
 */
public class BookmarksTree implements Serializable {
    private Tree tree;

    public BookmarksTree() {
        tree = new Tree();
    }

    public Bookmark getBookmark(int bookmarkId) {
        TreeIterator it = tree.getTreeIterator();
        return searchBookmark(it, bookmarkId);
    }

    private Bookmark searchBookmark(TreeIterator it, int bookmarkId) {
        while (it.hasNext()) {
            final TreeNode node = (TreeNode) it.next();
            final Bookmark bookmark = (Bookmark) node.getObject();
            if (bookmark.getId() == bookmarkId) {
                return bookmark;
            }
            if (it.hasChildren()) {
                final Bookmark res = searchBookmark(it.getChildIterator(), bookmarkId);
                if (res != null) {
                    return res;
                }
            }
        }
        return null;
    }

    public void moveTo(Bookmark bookmark, Bookmark parent) {
        TreeNode treeNode = tree.removeObject(bookmark);
        tree.addNode(treeNode, parent);
    }

    /**
     * Bookmark is added to the root if parent can't be found.
     */
    public void add(Bookmark bookmark, Bookmark parent) {
        tree.addObject(bookmark, parent);
    }

    public void remove(Bookmark bookmark) {
        tree.removeObject(bookmark);
    }

    public void changeOrder(Bookmark bookmark, boolean up) {
        tree.changeOrder(bookmark, up);
    }

    // TODO : set this back to protected ?
    public Tree getTree() {
        return this.tree;
    }
}
