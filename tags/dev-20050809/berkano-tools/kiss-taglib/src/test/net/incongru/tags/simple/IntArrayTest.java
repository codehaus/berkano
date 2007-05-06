package net.incongru.tags.simple;

import junit.framework.TestCase;
import net.incongru.tags.simple.IntArray;

/**
 * 
 *
 *
 * <p><br/><br/>created     Sep 9, 2003 11:54:10 PM</p>
 * @author     gjoseph
 * @author     $Author: gj $ (last edit)
 * @version    $Revision: 1.1 $
 */
public class IntArrayTest extends TestCase {

	private IntArray arr;

	public void testArrayGrowing() {
		assertEquals(2000, arr.size());
	}
	
	public void testGet() {
		assertEquals(2000, arr.get(0));
		assertEquals(500, arr.get(1500));
		assertEquals(1, arr.get(1999));
	}
	
	public void testContains() {
		assertTrue(arr.contains(1743));
		assertFalse(arr.contains(-33));
		assertFalse(arr.contains(2773));
	}
	
	public void testIndexOf() {
		assertEquals(0, arr.indexOf(2000));
		assertEquals(1999, arr.indexOf(1));
		assertEquals(1234, arr.indexOf(766));
		assertEquals(-1, arr.indexOf(0));
		assertEquals(-1, arr.indexOf(-38));
		assertEquals(-1, arr.indexOf(2222));		
	}
	
	protected void setUp() throws Exception {
		arr = new IntArray();
		for (int i=0; i<2000; i++) {
			arr.add(2000-i);
		}	
	}

}
