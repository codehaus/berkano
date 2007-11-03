package net.incongru.berkano.tree;

import java.io.Serializable;
import java.util.List;


/**
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.4 $
 */
public class Tree implements Serializable {
    private TreeNode root;

    public Tree() {
        this(new TreeNode(null));
    }

    Tree(TreeNode root) {
        this.root = root;
    }

    public void addObject(Object object, Object parentObject) {
        TreeNode parentNode = root.findNodeOf(parentObject);
        addObject(object, parentNode);
    }

    public void addNode(TreeNode newNode, Object parentObject) {
        TreeNode parentNode = root.findNodeOf(parentObject);
        parentNode.addChild(newNode);
    }

    /**
     * @param object object to add as a TreeNode to the Tree
     * @param parentNode the parent TreeNode. If null, the new node will be added to the root.
     * @return the added TreeNode
     */
    TreeNode addObject(Object object, TreeNode parentNode) {
        TreeNode newNode = new TreeNode(object);
        if (parentNode == null) {
            parentNode = root;
        }
        parentNode.addChild(newNode);
        return newNode;
    }

    public TreeNode removeObject(Object object) {
        TreeNode parentNode = root.findParentOf(object);
        // todo : this is the only place where ref to parent was useful, because the removeChild method needs to
        // todo : loop through children, or we would need to call parentNode.findNodeOf(Object) which loops
        // todo : through children too anyway.
        if (parentNode == null) {
            throw new RuntimeException("Can't find parent node for " + object);
        }
        return parentNode.removeChild(object);
    }

    /**
     * Moves the given object up or down in the children list of its parent.
     * @param object the object to move in the tree
     * @param up true if the object must move up, false if down
     *
     * TODO : improve this... hopefully it's improvable !
     */
    public void changeOrder(Object object, boolean up) {
        TreeNode parentNode = root.findParentOf(object);
        if (parentNode == null) {
            throw new RuntimeException("Can't find parent node for " + object);
        }
        TreeNode objNode = parentNode.findNodeOf(object);
        if (objNode == null) {
            throw new RuntimeException("Can't find node for " + object);
        }

        List childNodes = parentNode.getChildren();
        int currentIndex = childNodes.indexOf(objNode);
        if ((currentIndex == 0 && up) || (!up && currentIndex == childNodes.size() - 1)) {
            throw new IllegalStateException("Node " + object + " can't be moved");
        } else {
            childNodes.remove(currentIndex);
            int newIndex = up ? currentIndex - 1 : currentIndex + 1;
            childNodes.add(newIndex, objNode);
        }
    }

    public TreeIterator getTreeIterator() {
        return new TreeIterator(root);
    }

}
