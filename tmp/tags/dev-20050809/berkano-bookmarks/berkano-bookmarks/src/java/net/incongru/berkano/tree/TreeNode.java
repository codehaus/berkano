package net.incongru.berkano.tree;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.5 $
 */
public class TreeNode implements Serializable {
    /**
     * List<TreeNode>
     */
    private List children;
    private Object object;
    private boolean isOpen;

    TreeNode(Object object) {
        this.children = new LinkedList();
        this.object = object;
        this.isOpen = true;
    }

    List getChildren() {
        return children;
    }


    Object getObject() {
        return object;
    }

    boolean isOpen() {
        return isOpen;
    }

    void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }


    void addChild(TreeNode childNode) {
        if (childNode == this) {
            throw new IllegalArgumentException("Can't add a node to itself");
        }
        children.add(childNode);
    }

    TreeNode findParentOf(Object o) {
        return findNodeOf(o, true);
    }

    TreeNode findNodeOf(Object o) {
        return findNodeOf(o, false);
    }

    /**
     * @param returnParent if true, the returned TreeNode will be the parent of the searched object.
     * @return the parent TreeNode or the TreeNode that contains o, depending on returnParent.
     */
    private TreeNode findNodeOf(Object o, boolean returnParent) {
        Iterator it = children.iterator();
        while (it.hasNext()) {
            TreeNode childNode = (TreeNode) it.next();
            if (childNode.getObject() != null && childNode.getObject().equals(o)) {
                return returnParent ? this : childNode;
            } else if (o == null && childNode.getObject() == null) {
                return returnParent ? this : childNode;
            } else {
                TreeNode foundInChild = childNode.findNodeOf(o, returnParent);
                if (foundInChild != null) {
                    return foundInChild;
                }
            }
        }
        return null;
    }

    TreeNode removeChild(Object objectToRemoveFromChildren) {
        Iterator it = children.iterator();
        while (it.hasNext()) {
            TreeNode treeNode = (TreeNode) it.next();
            if (treeNode.getObject() == objectToRemoveFromChildren) {
                it.remove();
                return treeNode;
            }
        }
        return null;
    }
}
