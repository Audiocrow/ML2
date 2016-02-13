package com.ml2.shared.utils;

/** An ordered map of ints to ints. This is basically a copy of SparseIntArray (http://developer.android.com/reference/android/util/SparseIntArray.html). <p>
 * More memory-efficient than a HashMap due to not needing to box/unbox, and the performance difference <br>
 * is negligible if there are less than hundreds of items - making this perfect for tile layers. <br>
 * (Especially since we're just going to iterate through all the tile's layers 90% of the time anyway, <br>
 * which doesn't even require a binary search.)
 */
public class IntIntMap {
	private int[] keys;
	private int[] vals;
	private int size;
	public IntIntMap() {
		keys = new int[2];
		keys[0] = -1; keys[1] = -1;
		vals = new int[2];
		size = 0;
	}
	public IntIntMap(int initialCapacity) {
		keys = new int[initialCapacity];
		vals = new int[initialCapacity];
		size = 0;
	}
	public void clear() {
		for(int i=0; i<size; i++) {
			keys[i] = 0;
			vals[i] = 0;
		}
		size = 0;
	}
	public void expand() {
		int[] nkeys = new int[keys.length*2];
		int[] nvals = new int[vals.length*2];
		System.arraycopy(keys, 0, nkeys, 0, keys.length);
		System.arraycopy(vals, 0, nvals, 0, vals.length);
		keys = nkeys;
		vals = nvals;
	}
	public void put(int key, int val) {
		int i = binarySearch(keys, key, 0, size);
		if(i >= 0) {
			vals[i] = val;
			if(size == 0) size++;
			return;
		}
		if(size >= keys.length) expand();
		i = ~i;
		if(size-i > 0) {
			System.arraycopy(keys, i, keys, i+1, size-i);
			System.arraycopy(vals, i, vals, i+1, size-i);
		}
		keys[i] = key;
		vals[i] = val;
		size++;
	}
	public void append(int key, int val) {
		if(size > 0 && key > keys[size-1]) {
			if(size >= keys.length) expand();
			keys[size] = key;
			vals[size] = val;
			size++;
			return;
		}
		put(key, val);
	}
	public int get(int key, int failValue) {
		int i = binarySearch(keys, key, 0, size);
		if(i >= 0) return vals[i];
		return failValue;
	}
	public int get(int key) { return get(key, 0); }
	public int binarySearch(int[] array, int key, int low, int high) {
		int mid=0;
		while(high > low) {
			mid = low + (high-low)/2;
			if(array[mid] == key) return mid;
			else if(array[mid] < key)
				low = mid+1;
			else high = mid;
		}
		if(mid >= size) return ~mid;
		else if(array[mid] < key) return ~(mid+1);
		else return ~mid;
	}
	public void remove(int key) {
		int i = binarySearch(keys, key, 0, size);
		if(i < 0) return;
		System.arraycopy(keys, i+1, keys, i, (size-i)-1);
		System.arraycopy(vals, i+1, vals, i, (size-i)-1);
		size--;
	}
	public int[] values() { return vals; }
}
