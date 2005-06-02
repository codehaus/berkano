package net.incongru.tags.simple;

/**
 * This class holds an array of int that grows when needed
 * TODO: should probably replace this by jakarta-commons stuff.
 * TODO: write more unit test (although StringUtilTest.testParseCommaSeparatedValues 
 * already does quite a job since StringUtil.parseCommaSeparatedValues uses IntArray)
 * 
 *  
 * <p><br/><br/>created     Sep 9, 2003 11:19:00 PM</p>
 * @author     gjoseph
 * @author     $Author: gj $ (last edit)
 * @version    $Revision: 1.1 $
 */
public class IntArray {
	/** size added each time the array has to grow TODO: maybe we should multiply the array size instead */
	private int blockSize = 20;
	/** the data of the array */
	private int[] data;
	/** the current position */
	private int pos;

	/**
	 * 
	 */
	public IntArray(int initialSize, int blockSize) {
		this.data = new int[initialSize];
		this.blockSize = blockSize;
	}

	public IntArray(int initialSize) {
		this(initialSize, 20);
	}
	
	public IntArray() {
		this(20, 20);
	}
	
	public void add(int x) {
		if (pos>=data.length)
			resizeArray();
		data[pos]=x;
		pos++;
	}
	
	public int[] toArray() {
		int[] result = new int[pos];
		System.arraycopy(data, 0, result, 0, pos);
		return result;
	}
	
	public int size() {
		return pos;
	}
	
	public int get(int idx) {
		return data[idx];
	}
	
	public boolean contains(int x) {
		return indexOf(x)>=0;
	}
	
	public int indexOf(int x) {
		for (int i = 0; i < pos; i++) {
			if (x == data[i])
				return i;
		}
		return -1;
	}
	private void resizeArray() {
		int size = data.length;
		int[] oldData = data;
		data = new int[size+blockSize];
		System.arraycopy(oldData, 0, data, 0, size);
	}


}
