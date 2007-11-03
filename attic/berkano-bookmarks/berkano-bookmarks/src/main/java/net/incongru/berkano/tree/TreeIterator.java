package net.incongru.berkano.tree;

import java.util.Iterator;

/**
 * Implementation of Iterator to iterate over Tree.
 * Provides an additional method: getChildIterator.
 * The remove() method is not supported.
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class TreeIterator implements Iterator {
    private Iterator nodeIterator;
    private TreeNode lastRetrievedNode;

    public TreeIterator(TreeNode node) {
        this.nodeIterator = node.getChildren().iterator();
    }

    public boolean hasNext() {
        return nodeIterator.hasNext();
    }

    public Object next() {
        lastRetrievedNode = (TreeNode) nodeIterator.next();
        return lastRetrievedNode;//.getObject();
    }

    public boolean hasChildren() {
        if (lastRetrievedNode == null) {
            throw new IllegalStateException("Can't call hasChildren() before having called next()");
        }
        return lastRetrievedNode.getChildren().size() > 0;
    }

    public TreeIterator getChildIterator() {
        if (lastRetrievedNode == null) {
            throw new IllegalStateException("Can't call hasChildren() before having called next()");
        }
        return new TreeIterator(lastRetrievedNode);
    }

    /**
     * not supported
     * @throws UnsupportedOperationException
     */
    public void remove() {
        throw new UnsupportedOperationException("Remove is not supported in this implementation");
    }

}
