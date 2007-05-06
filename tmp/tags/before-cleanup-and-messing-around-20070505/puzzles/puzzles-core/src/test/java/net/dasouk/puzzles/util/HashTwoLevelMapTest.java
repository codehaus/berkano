package net.dasouk.puzzles.util;

import junit.framework.TestCase;

import java.util.Set;

/**
 */
public class HashTwoLevelMapTest extends TestCase {
    public void testSimpleInsert() {
        HashTwoLevelMap<Integer, Integer, String> twoLevelMap = new HashTwoLevelMap<Integer, Integer, String>();
        twoLevelMap.put(1, 1, "1");
        assertEquals("1", twoLevelMap.get(1, 1));
        assertEquals(1, twoLevelMap.firstLevelSize());
        assertEquals(1, twoLevelMap.secondLevelSize());
        assertEquals(1, twoLevelMap.size(1));
        twoLevelMap.put(1, 2, "2");
        assertEquals("2", twoLevelMap.get(1, 2));
        assertEquals(1, twoLevelMap.firstLevelSize());
        assertEquals(2, twoLevelMap.secondLevelSize());
        assertEquals(2, twoLevelMap.size(1));
        twoLevelMap.put(2, 1, "1");
        assertEquals("1", twoLevelMap.get(2, 1));
        assertEquals(2, twoLevelMap.firstLevelSize());
        assertEquals(3, twoLevelMap.secondLevelSize());
        assertEquals(2, twoLevelMap.size(1));
        assertEquals(1, twoLevelMap.size(2));
        assertEquals(0, twoLevelMap.size(44));
        assertNull(twoLevelMap.get(1, 3));
        assertNull(twoLevelMap.get(3, 654));

    }

    public void testSets() {
        HashTwoLevelMap<Integer, Integer, String> twoLevelMap = new HashTwoLevelMap<Integer, Integer, String>();
        twoLevelMap.put(1, 1, "1");
        twoLevelMap.put(1, 2, "2");
        twoLevelMap.put(2, 1, "1");
        //normal first level set
        Set<Integer> firstLevel = twoLevelMap.firstLevelSet();
        assertEquals(2, firstLevel.size());
        assertTrue(firstLevel.contains(1));
        assertTrue(firstLevel.contains(2));

        //normal second set
        Set<Integer> secondLevel = twoLevelMap.secondLevelSet(1);
        assertEquals(2, secondLevel.size());
        assertTrue(secondLevel.contains(1));
        assertTrue(secondLevel.contains(2));

        //empty second level set
        secondLevel = twoLevelMap.secondLevelSet(33);
        assertNotNull(secondLevel);
        assertEquals(0, secondLevel.size());

        //value set
        Set<String> valueSet = twoLevelMap.valuesSet(1);
        assertEquals(2, valueSet.size());
        assertTrue(valueSet.contains("1"));
        assertTrue(valueSet.contains("2"));

        //empty value set
        valueSet = twoLevelMap.valuesSet(654);
        assertNotNull(valueSet);
        assertEquals(0, valueSet.size());
    }

    public void testClear() {
        HashTwoLevelMap<Integer, Integer, String> twoLevelMap = new HashTwoLevelMap<Integer, Integer, String>();
        twoLevelMap.put(1, 1, "1");
        twoLevelMap.put(1, 2, "2");
        twoLevelMap.put(2, 1, "1");
        assertEquals(3, twoLevelMap.secondLevelSize());
        twoLevelMap.clear();
        assertEquals(0, twoLevelMap.secondLevelSize());
    }

    public void testRemove() {
        HashTwoLevelMap<Integer, Integer, String> twoLevelMap = new HashTwoLevelMap<Integer, Integer, String>();
        twoLevelMap.put(1, 1, "1");
        twoLevelMap.put(1, 2, "2");
        twoLevelMap.put(2, 1, "1");
        assertEquals("2", twoLevelMap.get(1, 2));
        assertEquals(3, twoLevelMap.secondLevelSize());
        assertEquals("2", twoLevelMap.remove(1, 2));
        assertNull(twoLevelMap.get(1, 2));
        assertEquals(2, twoLevelMap.secondLevelSize());
        assertNull(twoLevelMap.remove(1, 2));
        assertEquals("1", twoLevelMap.remove(2, 1));
        assertEquals(1, twoLevelMap.firstLevelSize());

    }

    public void testContains() {
        HashTwoLevelMap<Integer, Integer, String> twoLevelMap = new HashTwoLevelMap<Integer, Integer, String>();
        twoLevelMap.put(1, 1, "1");
        twoLevelMap.put(1, 2, "2");
        twoLevelMap.put(2, 1, "1");
        assertTrue(twoLevelMap.contains(1, 1));
        assertTrue(twoLevelMap.contains(2, 1));
        assertFalse(twoLevelMap.contains(33, 33));
    }


}
