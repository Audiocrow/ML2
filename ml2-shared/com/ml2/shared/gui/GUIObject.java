package com.ml2.shared.gui;

import com.ml2.shared.utils.Dimensions;

/** Stores only position and a parent - everything else is left to subclasses. <p>
 * Will receive a parent upon being placed into a child-storing GUI class (usually via whatever.add(obj))
 * @author Alexander Edgar
 */
public abstract class GUIObject {
	public static final String TAG = GUIObject.class.getName();
	
	private GUIObject parent;
	private Dimensions dims;
	
	/** Copies another GUIObject's values ({@link GUIObject} fields only).
	 * @param other the GUIObject to copy.
	 */
	public GUIObject(GUIObject other) {
		parent = other.getParent();
		dims = new Dimensions(other.getDims());
	}
	public GUIObject(short x, short y, short width, short height) {
		parent = null;
		dims = new Dimensions(x, y, width, height);
	}
	public GUIObject(short x, short y, short width, short height, short minWidth, short minHeight) {
		parent = null;
		dims = new Dimensions(x, y, width, height, minWidth, minHeight);
	}
	public GUIObject(short x, short y, short width, short height, short minWidth, short minHeight, short maxWidth, short maxHeight) {
		parent = null;
		dims = new Dimensions(x, y, width, height, minWidth, minHeight, maxWidth, maxHeight);
	}
	public GUIObject getParent() { return parent; }
	protected void setParent(GUIObject parent) { this.parent = parent; }
	public Dimensions getDims() { return dims; }
}
