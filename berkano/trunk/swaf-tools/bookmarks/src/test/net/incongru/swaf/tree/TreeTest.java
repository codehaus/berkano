package net.incongru.swaf.tree;

import junit.framework.TestCase;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.4 $
 */
public class TreeTest extends TestCase {
    public void testAddRemoveObject() {
        Tree tree = new Tree();
        tree.addObject("pouet", null);
        tree.addObject("tralala", "pouet");
        TreeNode blahNode = tree.addObject("blah", null);

        try {
            tree.removeObject("bleh");
            fail();
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().startsWith("Can't find parent node for "));
        }
        assertEquals(blahNode, tree.removeObject("blah"));
    }

    public void testAddObjectInParentWithTreeNodeReference() {
        Tree tree = new Tree();
        TreeNode pouet = tree.addObject("pouet", null);
        TreeNode tralala = tree.addObject("tralala", pouet);
        tree.addObject("blululu", pouet);
        tree.addObject("halala", tralala);

        TreeNode parent = pouet.findParentOf("halala");
        assertEquals(tralala, parent);
    }

    public void testAddObjectInParentWithObjectReference() {
        Tree tree = new Tree();
        TreeNode pouet = tree.addObject("pouet", null);
        tree.addObject("tralala", "pouet");
        tree.addObject("blululu", "pouet");
        tree.addObject("halala", "tralala");

        TreeNode parent = pouet.findParentOf("halala");
        assertNotNull(parent);
        assertEquals("tralala", parent.getObject());
    }

    public void testIterator() {
        Tree tree = new Tree();
        tree.addObject("pouet", null);
        tree.addObject("tralala", "pouet");
        tree.addObject("yopyop", "pouet");
        tree.addObject("blah", null);

        TreeIterator it = tree.getTreeIterator();
        try {
            it.getChildIterator();
            fail();
        } catch (IllegalStateException e) {
        }
        try {
            it.hasChildren();
            fail();
        } catch (IllegalStateException e) {
        }

        assertTrue(it.hasNext());
        assertEquals("pouet", ((TreeNode)it.next()).getObject());
        assertTrue(it.hasChildren());
        TreeIterator subIt = it.getChildIterator();
        assertTrue(subIt.hasNext());
        assertEquals("tralala", ((TreeNode)subIt.next()).getObject());
        assertTrue(subIt.hasNext());
        assertEquals("yopyop", ((TreeNode)subIt.next()).getObject());
        assertFalse(subIt.hasNext());
        assertTrue(it.hasNext());
        assertEquals("blah", ((TreeNode)it.next()).getObject());
        assertFalse(it.hasChildren());
        assertFalse(it.hasNext());
    }

    public void testChangeOrder() {
        Tree tree = setupFoodTree();
        tree.changeOrder("emmenthal", true);
        tree.changeOrder("cheddar", false);
        try {
            tree.changeOrder("emmenthal", true);
            fail();
        } catch (RuntimeException e) {
            assertEquals("Node emmenthal can't be moved", e.getMessage());
        }
        try {
            tree.changeOrder("parmiggiano", false);
            fail();
        } catch (RuntimeException e) {
            assertEquals("Node parmiggiano can't be moved", e.getMessage());
        }

        TreeIterator tit = tree.getTreeIterator();
        assertTrue(tit.hasNext());
        assertEquals("cheeses", ((TreeNode)tit.next()).getObject());
        assertTrue(tit.hasChildren());
        TreeIterator cheeses = tit.getChildIterator();
        assertTrue(cheeses.hasNext());
        assertEquals("emmenthal", ((TreeNode)cheeses.next()).getObject());
        assertEquals("gouda", ((TreeNode)cheeses.next()).getObject());
        assertEquals("cheddar", ((TreeNode)cheeses.next()).getObject());
        assertEquals("parmiggiano", ((TreeNode)cheeses.next()).getObject());
        assertFalse(cheeses.hasNext());

        assertEquals("fishes", ((TreeNode)tit.next()).getObject());
        TreeIterator fishes = tit.getChildIterator();
        assertTrue(fishes.hasNext());
        assertEquals("salmon", ((TreeNode)fishes.next()).getObject());
        assertEquals("tuna", ((TreeNode)fishes.next()).getObject());
        assertEquals("clown fish", ((TreeNode)fishes.next()).getObject());
        assertFalse(fishes.hasNext());
    }

    private Tree setupFoodTree() {
        Tree tree = new Tree();
        tree.addObject("cheeses", null);
        tree.addObject("cheddar", "cheeses");
        tree.addObject("emmenthal", "cheeses");
        tree.addObject("gouda", "cheeses");
        tree.addObject("parmiggiano", "cheeses");
        tree.addObject("fishes", null);
        tree.addObject("salmon", "fishes");
        tree.addObject("tuna", "fishes");
        tree.addObject("clown fish", "fishes");
        return tree;
    }
}
