package net.incongru.swaf.bookmarks;

import net.incongru.swaf.tree.Tree;
import net.incongru.swaf.tree.TreeIterator;
import net.incongru.swaf.tree.TreeNode;

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
            Bookmark bm = (Bookmark) it.next();
            if (bm.getId() == bookmarkId) {
                return bm;
            }
            if (it.hasChildren()) {
                Bookmark res = searchBookmark(it.getChildIterator(), bookmarkId);
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

    protected Tree getTree() {
        return this.tree;
    }
}
