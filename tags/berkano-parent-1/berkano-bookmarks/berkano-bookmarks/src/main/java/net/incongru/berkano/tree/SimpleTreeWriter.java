package net.incongru.berkano.tree;

import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class SimpleTreeWriter implements TreeWriter {
    private int indent;

    public SimpleTreeWriter() {
        this(2);
    }

    /**
     * @param indent number of spaces used by indent level
     */
    public SimpleTreeWriter(int indent) {
        this.indent = indent;
    }

    public void write(Tree tree, Writer out) throws IOException {
        TreeIterator it = tree.getTreeIterator();
        iterateNode(it, out, 0);
    }

    private void iterateNode(TreeIterator it, Writer out, int level) throws IOException {
        while (it.hasNext()) {
            TreeNode node = (TreeNode) it.next();
            Object nodeObject = node.getObject();
            writeNode(nodeObject, out, level);
            if (node.isOpen() && it.hasChildren()) {
                iterateNode(it.getChildIterator(), out, level + 1);
            }
        }
    }

    protected void writeNode(Object nodeObject, Writer out, int level) throws IOException {
        indent(out, level);
        if (nodeObject != null) {
            writeObject(nodeObject, out);
        }
        out.write('\n');
    }

    protected void writeObject(Object nodeObject, Writer out) throws IOException {
        out.write(nodeObject.toString());
    }

    protected void indent(Writer out, int level) throws IOException {
        for (int i = 0; i < level * indent; i++) {
            out.write(' ');
        }
    }
}
