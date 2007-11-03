package net.incongru.berkano.tree;

import junit.framework.TestCase;

/**
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class TreeNodeTest extends TestCase {
    private TreeNode root, child1, child1_1, child1_2, child1_2_1, child1_3, child2, child3, child3_1, child3_1_1, child3_1_2, child4, child4_1, child4_2, child4_2_1, child4_2_1_1;

    protected void setUp() throws Exception {
        setupSimpleNodes();
    }

    public void testFindObjectInChildren() {
        assertEquals(child2, root.findNodeOf("child2"));
        assertEquals(child3_1, root.findNodeOf("child3_1"));
        assertEquals(child3_1_1, child3.findNodeOf("child3_1_1"));
        assertEquals(child3_1_1, child3_1.findNodeOf("child3_1_1"));
        assertEquals(child3_1_1, root.findNodeOf("child3_1_1"));
    }

    public void testFindParentOf() {
        assertEquals(root, root.findParentOf("child2"));
        assertEquals(child3, root.findParentOf("child3_1"));
        assertEquals(child3_1, root.findParentOf("child3_1_2"));
        assertEquals(child3_1, root.findParentOf("child3_1_1"));
        assertEquals(child3_1, child3.findParentOf("child3_1_1"));
        assertEquals(child3_1, child3_1.findParentOf("child3_1_1"));
        assertEquals(child4_2_1, root.findParentOf("child4_2_1_1"));
    }

    public void testRemoveChildNode() {
        assertEquals("test not properly initialized", 4, root.getChildren().size());
        assertNotNull(root.removeChild("child2"));
        assertEquals(3, root.getChildren().size());
        assertNull(root.removeChild("child1_2"));
        assertEquals(3, root.getChildren().size());
    }

    public void testAddChild() {
        assertEquals(4, root.getChildren().size());
        assertEquals(child1, root.getChildren().get(0));
        assertEquals(child2, root.getChildren().get(1));
        assertEquals(child3, root.getChildren().get(2));
        assertEquals(child4, root.getChildren().get(3));

        assertEquals(3, child1.getChildren().size());
        assertEquals(child1_1, child1.getChildren().get(0));
        assertEquals(child1_2, child1.getChildren().get(1));
        assertEquals(child1_3, child1.getChildren().get(2));

        assertEquals(0, child2.getChildren().size());

        assertEquals(1, child3.getChildren().size());
        assertEquals(child3_1, child3.getChildren().get(0));

        assertEquals(2, child3_1.getChildren().size());
        assertEquals(child3_1_1, child3_1.getChildren().get(0));
        assertEquals(child3_1_2, child3_1.getChildren().get(1));

        assertEquals(2, child4.getChildren().size());
        assertEquals(child4_1, child4.getChildren().get(0));
        assertEquals(child4_2, child4.getChildren().get(1));

        assertEquals(1, child4_2.getChildren().size());
        assertEquals(child4_2_1, child4_2.getChildren().get(0));

        assertEquals(1, child4_2_1.getChildren().size());
        assertEquals(child4_2_1_1, child4_2_1.getChildren().get(0));

        assertEquals(0, child4_2_1_1.getChildren().size());
    }

    public void testCantAddSelfToNode() {
        TreeNode root = new TreeNode("R*O*O*T");
        TreeNode child1 = new TreeNode("1st child");
        root.addChild(child1);
        try {
            child1.addChild(child1);
            fail("Shouldn't be able to add itself to a node");
        } catch (IllegalArgumentException ex) {
        }
    }

    private void setupSimpleNodes() {
        root = new TreeNode("root");
        child1 = new TreeNode("child1");
        child1_1 = new TreeNode("child1_1");
        child1_2 = new TreeNode("child1_2");
        child1_2_1 = new TreeNode("child1_2_1");
        child1_3 = new TreeNode("child1_3");
        child2 = new TreeNode("child2");
        child3 = new TreeNode("child3");
        child3_1 = new TreeNode("child3_1");
        child3_1_1 = new TreeNode("child3_1_1");
        child3_1_2 = new TreeNode("child3_1_2");
        child4 = new TreeNode("child4");
        child4_1 = new TreeNode("child4_1");
        child4_2 = new TreeNode("child4_2");
        child4_2_1 = new TreeNode("child4_2_1");
        child4_2_1_1 = new TreeNode("child4_2_1_1");

        root.addChild(child1);
        child1.addChild(child1_1);
        child1.addChild(child1_2);
        child1.addChild(child1_3);
        child1_2.addChild(child1_2_1);
        root.addChild(child2);
        root.addChild(child3);
        child3.addChild(child3_1);
        child3_1.addChild(child3_1_1);
        child3_1.addChild(child3_1_2);
        root.addChild(child4);
        child4.addChild(child4_1);
        child4.addChild(child4_2);
        child4_2.addChild(child4_2_1);
        child4_2_1.addChild(child4_2_1_1);
    }

}
